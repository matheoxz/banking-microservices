package com.mthxz.transaction_service.repository;

import com.mthxz.transaction_service.entity.TransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, UUID> {
}
