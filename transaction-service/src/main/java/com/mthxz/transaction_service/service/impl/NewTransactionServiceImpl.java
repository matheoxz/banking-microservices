package com.mthxz.transaction_service.service.impl;

import com.mthxz.transaction_service.config.KafkaConfig;
import com.mthxz.transaction_service.entity.TransacaoEntity;
import com.mthxz.transaction_service.model.ConclusaoTransacaoModel;
import com.mthxz.transaction_service.model.ExecucaoTransacaoModel;
import com.mthxz.transaction_service.model.StatusTransacao;
import com.mthxz.transaction_service.model.TransacaoRequestModel;
import com.mthxz.transaction_service.repository.ContaRepository;
import com.mthxz.transaction_service.repository.TransacaoRepository;
import com.mthxz.transaction_service.service.NewTransactionService;
import com.mthxz.transaction_service.transaction.TransacaoFactory;
import com.mthxz.transaction_service.transaction.TransactionHandler;
import com.mthxz.transaction_service.validation.TransactionValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewTransactionServiceImpl implements NewTransactionService {

    private final TransacaoRepository transacaoRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TransacaoFactory transacaoFactory;
    private final TransactionValidations transactionValidations;

    @Override
    @Transactional
    public boolean solicitaTransacao(TransacaoRequestModel transacao) {
        try {
            var entity = TransacaoEntity.builder()
                    .tipo(transacao.getTipo())
                    .valor(transacao.getValor())
                    .origem(transacao.getOrigem())
                    .destino(transacao.getDestino())
                    .status(StatusTransacao.PENDENTE)
                    .build();

            var savedTransaction = transacaoRepository.save(entity);

            var exec = new ExecucaoTransacaoModel(savedTransaction.getId(), entity.getOrigem(), entity.getDestino());

            kafkaTemplate.send(KafkaConfig.TOPIC_TRANSACAO_SOLICITADA, exec).get();
        } catch (DataAccessException e) {
            log.error("[ERROR] [DATABASE] error: {}", e.getMessage());
            return false;
        } catch (ExecutionException | InterruptedException e) {
            log.error("[ERROR] [KAFKA] error: {}", e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void executaTransacao(ExecucaoTransacaoModel executaTransacao) {
        var id = executaTransacao.getTransacao();
        var opt = transacaoRepository.findById(id);
        if (opt.isEmpty()) {
            log.warn("m=executaTransacao Transacao nao existe na base de dados uuid:{}", id);
            var concl = new ConclusaoTransacaoModel(executaTransacao, StatusTransacao.FALHOU,
                    "Transacao nao existe na base de dados uuids:" + id);
            kafkaTemplate.send(KafkaConfig.TOPIC_TRANSACAO_CONCLUIDA, concl);
            return;
        }
        TransacaoEntity entity = opt.get();

        // Validations via chain of responsibility
        var validationResult = transactionValidations.validate(entity, executaTransacao);
        if (validationResult.isPresent()) {
            ConclusaoTransacaoModel concl = validationResult.get();
            kafkaTemplate.send(KafkaConfig.TOPIC_TRANSACAO_CONCLUIDA, concl);
            transacaoRepository.save(updateStatus(entity, concl.getStatus()));
            return;
        }

        // PROCESSANDO
        entity.setStatus(StatusTransacao.PROCESSANDO);
        transacaoRepository.save(entity);

        TransactionHandler handler = transacaoFactory.getHandler(entity.getTipo());
        StatusTransacao result = handler.efetuar(executaTransacao);

        ConclusaoTransacaoModel concl = new ConclusaoTransacaoModel(executaTransacao, result, "Transação efetuada com sucesso!");
        kafkaTemplate.send(KafkaConfig.TOPIC_TRANSACAO_CONCLUIDA, concl);
        transacaoRepository.save(updateStatus(entity, result));
    }

    private TransacaoEntity updateStatus(TransacaoEntity entity, StatusTransacao status) {
        entity.setStatus(status);
        return entity;
    }
}
