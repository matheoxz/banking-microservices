package com.mthxz.transaction_service.messaging;

import com.mthxz.bankcommons.model.ExecucaoTransacaoModel;
import com.mthxz.transaction_service.config.KafkaConfig;
import com.mthxz.transaction_service.service.NewTransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransacaoSolicitadaListener {
    private static final Logger log = LoggerFactory.getLogger(TransacaoSolicitadaListener.class);

    private final NewTransactionService newTransactionService;

    public TransacaoSolicitadaListener(NewTransactionService newTransactionService) {
        this.newTransactionService = newTransactionService;
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_TRANSACAO_SOLICITADA, containerFactory = "execucaoKafkaListenerContainerFactory")
    public void onSolicitada(ExecucaoTransacaoModel execucao) {
        log.info("Mensagem recebida em TransacaoSolicitada: {}", execucao);
        processAsync(execucao);
    }

    @Async
    void processAsync(ExecucaoTransacaoModel execucao) {
        try {
            newTransactionService.executaTransacao(execucao);
        } catch (Exception e) {
            log.error("Erro ao executar transacao {}: {}", execucao.getTransacao(), e.getMessage(), e);
        }
    }
}
