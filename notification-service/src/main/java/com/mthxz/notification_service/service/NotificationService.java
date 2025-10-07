package com.mthxz.notification_service.service;

import com.mthxz.notification_service.enums.TipoNotificacao;
import com.mthxz.notification_service.model.Notificacao;
import com.mthxz.notification_service.repository.NotificacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificacaoRepository notificacaoRepository;
    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public NotificationService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    public void enviar(String clienteId, String mensagem, TipoNotificacao tipo) {
        Notificacao notificacao = new Notificacao(clienteId, mensagem, tipo.name(), LocalDateTime.now());
        notificacaoRepository.save(notificacao);
        logger.info("Notificação enviada para cliente {}: {} via {}", clienteId, mensagem, tipo.name());
    }
}

