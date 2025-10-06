package com.mthxz.account_service.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mthxz.account_service.model.TransacaoConcluidaModel;
import com.mthxz.account_service.service.ContaService;

@Component
public class TransacaoConcluidaListener {

    private static final Logger logger = LoggerFactory.getLogger(TransacaoConcluidaListener.class);

    @Autowired
    private ContaService contaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Async
    @KafkaListener(topics = "TransacaoConcluida", groupId = "account-service-group")
    public void listen(String message) {
        try {
            TransacaoConcluidaModel model = objectMapper.readValue(message, TransacaoConcluidaModel.class);
            logger.info("Received TransacaoConcluida message: {}", model.getTransacao());
            contaService.executeTransaction(model);
        } catch (Exception e) {
            logger.error("Failed to process TransacaoConcluida message", e);
        }
    }
}

