package com.mthxz.notification_service.service.impl;

import com.mthxz.notification_service.entity.ClienteEntity;
import com.mthxz.notification_service.factory.Notificator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class EmailNotification implements Notificator {

    private final Logger logger = LoggerFactory.getLogger(EmailNotification.class);

    @Override
    public void enviar(ClienteEntity clienteId, String mensagem) {
        logger.info("Enviando EMAIL para email {}: {}", clienteId.getEmail(), mensagem);
    }
}

