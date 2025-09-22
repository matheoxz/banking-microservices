package com.mthxz.transaction_service.repository;

import com.mthxz.transaction_service.entity.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContaRepository extends JpaRepository<ContaEntity, UUID> {
}
