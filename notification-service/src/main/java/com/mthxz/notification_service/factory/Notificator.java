package com.mthxz.notification_service.factory;

public interface Notificator {
    void enviar(String clienteId, String mensagem);
}
