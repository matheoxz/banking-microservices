package com.mthxz.audit_service.messaging;

import com.mthxz.audit_service.service.AuditoriaService;
import com.mthxz.audit_service.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuditListener {

    private static final Logger log = LoggerFactory.getLogger(AuditListener.class);
    private final AuditoriaService auditoriaService;

    public AuditListener(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @KafkaListener(
        topics = {KafkaConfig.TOPIC_TRANSACAO_SOLICITADA, KafkaConfig.TOPIC_TRANSACAO_CONCLUIDA},
        containerFactory = "genericListenerFactory"
    )
    public void auditListener(ConsumerRecord<String, Object> record) {
        String topic = record.topic();
        Object payload = record.value();
        log.info("Received {}: {}", topic, payload);
        auditoriaService.saveAudit(topic, payload.toString());
    }
}

