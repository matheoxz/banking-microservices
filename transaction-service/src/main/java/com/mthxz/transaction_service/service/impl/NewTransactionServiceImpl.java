package com.mthxz.transaction_service.service.impl;

import com.mthxz.transaction_service.config.KafkaConfig;
import com.mthxz.transaction_service.entity.TransacaoEntity;
import com.mthxz.transaction_service.model.*;
import com.mthxz.transaction_service.repository.TransacaoRepository;
import com.mthxz.transaction_service.service.NewTransactionService;
import com.mthxz.transaction_service.transaction.TransactionHandler;
import com.mthxz.transaction_service.transaction.TransacaoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewTransactionServiceImpl implements NewTransactionService {

    private final TransacaoRepository transacaoRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TransacaoFactory transacaoFactory;
    private final StubAccountService accountService = new StubAccountService();

    @Override
    @Transactional
    public boolean solicitaTransacao(TransacaoRequestModel transacao) {
        UUID id = UUID.randomUUID();
        TransacaoEntity entity = TransacaoEntity.builder()
                .id(id)
                .tipo(transacao.getTipo())
                .valor(transacao.getValor())
                .origem(transacao.getOrigem())
                .destino(transacao.getDestino())
                .status(StatusTransacao.PENDENTE)
                .build();
        try {
            transacaoRepository.save(entity);
        } catch (Exception e) {
            log.error("[ERROR] [DATABASE] error: {}", e.getMessage());
            return false;
        }

        ExecucaoTransacaoModel exec = new ExecucaoTransacaoModel(id, entity.getOrigem(), entity.getDestino());
        try {
            // ensure send success or rollback save
            kafkaTemplate.send(KafkaConfig.TOPIC_TRANSACAO_SOLICITADA, exec).get();
        } catch (Exception e) {
            log.error("Falha ao publicar na fila TransacaoSolicitada uuid={}", id);
            // rethrow to force transaction rollback (so it won't be double-saved on retry)
            throw new RuntimeException("Kafka publish failed", e);
        }
        return true;
    }

    @Override
    public void executaTransacao(ExecucaoTransacaoModel executaTransacao) {
        UUID id = executaTransacao.getTransacao();
        Optional<TransacaoEntity> opt = transacaoRepository.findById(id);
        if (opt.isEmpty()) {
            log.warn("Transacao nao existe na base de dados uuids:{}", id);
            ConclusaoTransacaoModel concl = new ConclusaoTransacaoModel(executaTransacao, StatusTransacao.FALHOU,
                    "Transacao nao existe na base de dados uuids:" + id);
            kafkaTemplate.send(KafkaConfig.TOPIC_TRANSACAO_CONCLUIDA, concl);
            return;
        }
        TransacaoEntity entity = opt.get();

        // Check origin and destination existence (stubbed)
        boolean origemExiste = accountService.exists(entity.getOrigem());
        boolean destinoExiste = accountService.exists(entity.getDestino());
        if (!origemExiste) {
            ConclusaoTransacaoModel concl = new ConclusaoTransacaoModel(executaTransacao, StatusTransacao.FALHOU,
                    "Origem nao existe na base de dados uuids: " + entity.getOrigem());
            kafkaTemplate.send(KafkaConfig.TOPIC_TRANSACAO_CONCLUIDA, concl);
            transacaoRepository.save(updateStatus(entity, StatusTransacao.FALHOU, concl.getDetalhes()));
            return;
        }
        if (!destinoExiste) {
            ConclusaoTransacaoModel concl = new ConclusaoTransacaoModel(executaTransacao, StatusTransacao.FALHOU,
                    "Destino nao existe na base dados uuids:" + entity.getDestino());
            kafkaTemplate.send(KafkaConfig.TOPIC_TRANSACAO_CONCLUIDA, concl);
            transacaoRepository.save(updateStatus(entity, StatusTransacao.FALHOU, concl.getDetalhes()));
            return;
        }

        // Check saldo (stubbed)
        BigDecimal saldoOrigem = accountService.getSaldo(entity.getOrigem());
        if (entity.getValor().compareTo(saldoOrigem) > 0) {
            ConclusaoTransacaoModel concl = new ConclusaoTransacaoModel(executaTransacao, StatusTransacao.CANCELADA,
                    "Transacao com valor maior que saldo");
            kafkaTemplate.send(KafkaConfig.TOPIC_TRANSACAO_CONCLUIDA, concl);
            transacaoRepository.save(updateStatus(entity, StatusTransacao.CANCELADA, concl.getDetalhes()));
            return;
        }

        // PROCESSANDO
        entity.setStatus(StatusTransacao.PROCESSANDO);
        transacaoRepository.save(entity);

        TransactionHandler handler = transacaoFactory.getHandler(entity.getTipo());
        StatusTransacao result = handler.efetuar(executaTransacao);

        ConclusaoTransacaoModel concl = new ConclusaoTransacaoModel(executaTransacao, result, null);
        kafkaTemplate.send(KafkaConfig.TOPIC_TRANSACAO_CONCLUIDA, concl);
        transacaoRepository.save(updateStatus(entity, result, null));
        return;
    }

    private TransacaoEntity updateStatus(TransacaoEntity entity, StatusTransacao status, String detalhes) {
        entity.setStatus(status);
        return entity;
    }

    /**
     * Stubbed account service to simulate account existence and balance.
     */
    static class StubAccountService {
        boolean exists(UUID id) { return true; }
        BigDecimal getSaldo(UUID id) { return BigDecimal.valueOf(1_000_000); }
    }
}
