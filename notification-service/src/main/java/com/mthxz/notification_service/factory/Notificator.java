package com.mthxz.notification_service.factory;

import com.mthxz.notification_service.entity.ClienteEntity;

public interface Notificator {
    void enviar(ClienteEntity cliente, String mensagem);
}
