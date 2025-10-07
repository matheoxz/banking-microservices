package com.mthxz.audit_service.listener;

import com.mthxz.audit_service.service.AuditoriaService;
import com.mthxz.audit_service.config.KafkaConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuditListener {

    private final AuditoriaService auditoriaService;

    public AuditListener(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_CLIENTE_NOTIFICATIONS, groupId = "audit-service", containerFactory = "kafkaListenerContainerFactory")
    public void listenClienteNotifications(String message) {
        auditoriaService.saveAudit("Transação", message);
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_TRANSACTION_NOTIFICATIONS, groupId = "audit-service", containerFactory = "kafkaListenerContainerFactory")
    public void listenTransactionNotifications(String message) {
        auditoriaService.saveAudit("Transação", message);
    }
}

