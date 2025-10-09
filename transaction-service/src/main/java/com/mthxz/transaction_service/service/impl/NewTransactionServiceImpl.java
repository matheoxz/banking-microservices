package com.mthxz.transaction_service.service.impl;

import com.mthxz.bankcommons.enums.StatusTransacao;
import com.mthxz.bankcommons.model.ExecucaoTransacaoModel;
import com.mthxz.bankcommons.model.TransacaoConcluidaModel;
import com.mthxz.transaction_service.config.KafkaConfig;
import com.mthxz.transaction_service.entity.TransacaoEntity;
import com.mthxz.transaction_service.model.TransacaoRequestModel;
import com.mthxz.transaction_service.repository.TransacaoRepository;
import com.mthxz.transaction_service.service.NewTransactionService;
import com.mthxz.transaction_service.transaction.TransacaoFactory;
import com.mthxz.transaction_service.transaction.TransactionHandler;
import com.mthxz.transaction_service.validation.TransactionValidations;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

@Service
public class NewTransactionServiceImpl implements NewTransactionService {
    private static final Logger log = LoggerFactory.getLogger(NewTransactionServiceImpl.class);

    private final TransacaoRepository transacaoRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TransacaoFactory transacaoFactory;
    private final TransactionValidations transactionValidations;

    public NewTransactionServiceImpl(TransacaoRepository transacaoRepository, KafkaTemplate<String, Object> kafkaTemplate, TransacaoFactory transacaoFactory, TransactionValidations transactionValidations) {
        this.transacaoRepository = transacaoRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.transacaoFactory = transacaoFactory;
        this.transactionValidations = transactionValidations;
    }

    @Override
    @Transactional
    public boolean solicitaTransacao(TransacaoRequestModel transacao) {
        try {
            var entity = new TransacaoEntity()
                    .setTipo(transacao.getTipo())
                    .setValor(transacao.getValor())
                    .setOrigem(transacao.getOrigem())
                    .setDestino(transacao.getDestino())
                    .setStatus(StatusTransacao.PENDENTE);

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
            // Map to common TransacaoConcluidaModel and send
            var bankConcl = new TransacaoConcluidaModel(
                executaTransacao.getTransacao(),
                executaTransacao.getOrigem(),
                executaTransacao.getDestino(),
                StatusTransacao.FALHOU,
                "Transacao nao existe na base de dados uuids:" + id
            );
            kafkaTemplate.send(KafkaConfig.TOPIC_TRANSACAO_CONCLUIDA, bankConcl);
            return;
        }
        TransacaoEntity entity = opt.get();

        // Validations via chain of responsibility
        var validationResult = transactionValidations.validate(entity, executaTransacao);
        if (validationResult.isPresent()) {
            // Map validation result to common model and send
            var concl = new TransacaoConcluidaModel(
                    validationResult.get().getTransacao(),
                    validationResult.get().getOrigem(),
                    validationResult.get().getDestino(),
                    validationResult.get().getStatus(),
                    validationResult.get().getDetalhes()
            );
            kafkaTemplate.send(KafkaConfig.TOPIC_TRANSACAO_CONCLUIDA, concl);
            transacaoRepository.save(updateStatus(entity, concl.getStatus()));
            return;
        }

        // PROCESSANDO
        entity.setStatus(StatusTransacao.PROCESSANDO);
        transacaoRepository.save(entity);

        TransactionHandler handler = transacaoFactory.getHandler(entity.getTipo());
        StatusTransacao result = handler.efetuar(executaTransacao);

        // Map final result to common model and send
        var bankConclFinal = new TransacaoConcluidaModel(
                executaTransacao.getTransacao(),
                executaTransacao.getOrigem(),
                executaTransacao.getDestino(),
                result,
                "Transação efetuada com sucesso!"
        );
        kafkaTemplate.send(KafkaConfig.TOPIC_TRANSACAO_CONCLUIDA, bankConclFinal);
        transacaoRepository.save(updateStatus(entity, result));
    }

    private TransacaoEntity updateStatus(TransacaoEntity entity, StatusTransacao status) {
        entity.setStatus(status);
        return entity;
    }
}
