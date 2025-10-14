package com.mthxz.transaction_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "conta")
public class ContaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "saldo", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldo;

    public UUID getId() {
        return id;
    }

    public ContaEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public ContaEntity setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }
}
