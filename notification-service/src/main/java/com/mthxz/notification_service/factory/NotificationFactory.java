package com.mthxz.notification_service.factory;

import com.mthxz.bankcommons.enums.TipoNotificacao;
import com.mthxz.notification_service.service.impl.EmailNotification;
import com.mthxz.notification_service.service.impl.SmsNotification;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {

    public Notificator getNotificator(TipoNotificacao tipo) {
        return switch (tipo) {
            case EMAIL -> new EmailNotification();
            case SMS -> new SmsNotification();
            default -> throw new IllegalArgumentException("Tipo de notificação não suportado: " + tipo);
        };
    }
}

