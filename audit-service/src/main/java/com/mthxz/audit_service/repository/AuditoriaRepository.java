package com.mthxz.audit_service.repository;

import com.mthxz.audit_service.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, UUID> {
}

