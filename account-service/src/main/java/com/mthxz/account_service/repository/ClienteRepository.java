package com.mthxz.account_service.repository;

import com.mthxz.account_service.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {
    Optional<ClienteEntity> findByCpf(String cpf);
    Optional<ClienteEntity> findByEmail(String email);
    Optional<ClienteEntity> findByTelefone(String telefone);
}
