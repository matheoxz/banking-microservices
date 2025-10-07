package com.mthxz.notification_service.factory;

import com.mthxz.notification_service.enums.TipoNotificacao;
import com.mthxz.notification_service.service.NotificationService;
import com.mthxz.notification_service.service.impl.EmailNotification;
import com.mthxz.notification_service.service.impl.SmsNotification;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {

    private final NotificationService notificationService;

    public NotificationFactory(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public Notificator getNotificator(TipoNotificacao tipo) {
        switch (tipo) {
            case EMAIL:
                return new EmailNotification(notificationService);
            case SMS:
                return new SmsNotification(notificationService);
            default:
                throw new IllegalArgumentException("Tipo de notificação não suportado: " + tipo);
        }
    }
}

