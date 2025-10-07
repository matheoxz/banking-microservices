package com.mthxz.notification_service.listener;

import com.mthxz.notification_service.enums.TipoNotificacao;
import com.mthxz.notification_service.factory.NotificationFactory;
import com.mthxz.notification_service.factory.Notificator;
import com.mthxz.notification_service.config.KafkaConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationListener {

    private final NotificationFactory notificationFactory;

    public NotificationListener(NotificationFactory notificationFactory) {
        this.notificationFactory = notificationFactory;
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_CLIENTE_NOTIFICATIONS, groupId = "notification-service", containerFactory = "kafkaListenerContainerFactory")
    public void listenClienteNotifications(String message) {
        // TODO: parse message to get clienteId, mensagem, and notificacoes
        String clienteId = "123"; // dummy
        String mensagem = message;
        List<TipoNotificacao> notificacoes = List.of(TipoNotificacao.EMAIL, TipoNotificacao.SMS); // dummy

        notificacoes.forEach(tipo -> {
            Notificator notificator = notificationFactory.getNotificator(tipo);
            notificator.enviar(clienteId, mensagem);
        });
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_TRANSACTION_NOTIFICATIONS, groupId = "notification-service", containerFactory = "kafkaListenerContainerFactory")
    public void listenTransactionNotifications(String message) {
        // TODO: parse message to get clienteId, mensagem, and notificacoes
        String clienteId = "456"; // dummy
        String mensagem = message;
        List<TipoNotificacao> notificacoes = List.of(TipoNotificacao.EMAIL); // dummy

        notificacoes.forEach(tipo -> {
            Notificator notificator = notificationFactory.getNotificator(tipo);
            notificator.enviar(clienteId, mensagem);
        });
    }
}