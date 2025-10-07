package com.mthxz.audit_service.service;

import com.mthxz.audit_service.model.Auditoria;
import com.mthxz.audit_service.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    public void saveAudit(String operacao, String detalhes) {
        Auditoria auditoria = new Auditoria(operacao, detalhes);
        auditoriaRepository.save(auditoria);
    }
}

