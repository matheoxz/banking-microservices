package com.mthxz.transaction_service.messaging;

import com.mthxz.transaction_service.config.KafkaConfig;
import com.mthxz.transaction_service.model.ExecucaoTransacaoModel;
import com.mthxz.transaction_service.service.NewTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransacaoSolicitadaListener {

    private final NewTransactionService newTransactionService;

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
