package com.mthxz.audit_service.listener;

import com.mthxz.audit_service.service.AuditoriaService;
import com.mthxz.audit_service.config.KafkaConfig;
import com.mthxz.bankcommons.model.ExecucaoTransacaoModel;
import com.mthxz.bankcommons.model.TransacaoConcluidaModel;
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

    @KafkaListener(topics = KafkaConfig.TOPIC_TRANSACAO_SOLICITADA, groupId = "audit-service", containerFactory = "kafkaListenerContainerFactory")
    public void listenSolicitada(ExecucaoTransacaoModel model) {
        log.info("Received TransacaoSolicitada: {}", model);
        auditoriaService.saveAudit("TransacaoSolicitada", model.toString());
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_TRANSACAO_CONCLUIDA, groupId = "audit-service", containerFactory = "kafkaListenerContainerFactory")
    public void listenConcluida(TransacaoConcluidaModel model) {
        log.info("Received TransacaoConcluida: {}", model);
        auditoriaService.saveAudit("TransacaoConcluida", model.toString());
    }
}

