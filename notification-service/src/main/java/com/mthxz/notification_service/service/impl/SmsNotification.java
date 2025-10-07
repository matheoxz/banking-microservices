package com.mthxz.notification_service.service.impl;

import com.mthxz.notification_service.factory.Notificator;
import com.mthxz.notification_service.enums.TipoNotificacao;
import com.mthxz.notification_service.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsNotification implements Notificator {

    private final NotificationService notificationService;
    private final Logger logger = LoggerFactory.getLogger(SmsNotification.class);

    public SmsNotification(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void enviar(String clienteId, String mensagem) {
        // Simulate sending SMS
        logger.info("Enviando SMS para cliente {}: {}", clienteId, mensagem);
        notificationService.enviar(clienteId, mensagem, TipoNotificacao.SMS);
    }
}

