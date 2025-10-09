package com.mthxz.account_service.entity;

import com.mthxz.bankcommons.enums.TipoConta;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "conta")
public class ContaEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String numero;

    @Column(nullable = false)
    private String agencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConta tipo;

    @Column(nullable = false)
    private BigDecimal saldo;

    // Keep a lightweight FK to Cliente without a JPA relationship for simplicity
    @Column(name = "cliente_id", columnDefinition = "uuid")
    private UUID clienteId;


    public UUID getId() {
        return id;
    }

    public ContaEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public ContaEntity setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public String getAgencia() {
        return agencia;
    }

    public ContaEntity setAgencia(String agencia) {
        this.agencia = agencia;
        return this;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public ContaEntity setTipo(TipoConta tipo) {
        this.tipo = tipo;
        return this;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public ContaEntity setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public ContaEntity setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
        return this;
    }
}
