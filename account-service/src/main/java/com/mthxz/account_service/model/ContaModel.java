package com.mthxz.account_service.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.mthxz.bankcommons.enums.TipoConta;

public class ContaModel {
    private UUID id;
    private String numero;
    private String agencia;
    private TipoConta tipo;
    private BigDecimal saldo;

    public UUID getId() {
        return id;
    }

    public ContaModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public ContaModel setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public String getAgencia() {
        return agencia;
    }

    public ContaModel setAgencia(String agencia) {
        this.agencia = agencia;
        return this;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public ContaModel setTipo(TipoConta tipo) {
        this.tipo = tipo;
        return this;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public ContaModel setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }
}