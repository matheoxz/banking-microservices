package com.mthxz.notification_service.repository;

import com.mthxz.notification_service.entity.ClienteEntity;
import com.mthxz.notification_service.entity.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {
}