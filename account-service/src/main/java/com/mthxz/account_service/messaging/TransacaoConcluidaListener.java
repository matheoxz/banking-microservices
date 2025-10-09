package com.mthxz.account_service.messaging;

import com.mthxz.account_service.service.ContaService;
import com.mthxz.bankcommons.model.TransacaoConcluidaModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TransacaoConcluidaListener {

    private static final Logger logger = LoggerFactory.getLogger(TransacaoConcluidaListener.class);

    @Autowired
    private ContaService contaService;

    // ...existing code...

    @Async
    @KafkaListener(topics = "TransacaoConcluida", groupId = "account-service-group")
    public void listen(TransacaoConcluidaModel model) {
        logger.info("Received TransacaoConcluida message: {}", model.getTransacao());
        try {
            contaService.executeTransaction(model);
        } catch (Exception e) {
            logger.error("Failed to process TransacaoConcluida message", e);
        }
    }
}
