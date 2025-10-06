package com.mthxz.account_service.repository;

import com.mthxz.account_service.entity.TransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoEntity, UUID> {
}
