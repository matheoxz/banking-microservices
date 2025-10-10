package com.mthxz.notification_service.entity;

import com.mthxz.bankcommons.enums.StatusTransacao;
import com.mthxz.bankcommons.enums.TipoTransacao;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacao")
public class TransacaoEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoTransacao tipo;

    @Column(name = "valor", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "data")
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusTransacao status;

    @Column(name = "origem")
    private UUID origem;

    @Column(name = "destino")
    private UUID destino;

    public UUID getId() {
        return id;
    }

    public TransacaoEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public TransacaoEntity setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
        return this;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TransacaoEntity setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public LocalDateTime getData() {
        return data;
    }

    public TransacaoEntity setData(LocalDateTime data) {
        this.data = data;
        return this;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public TransacaoEntity setStatus(StatusTransacao status) {
        this.status = status;
        return this;
    }

    public UUID getOrigem() {
        return origem;
    }

    public TransacaoEntity setOrigem(UUID origem) {
        this.origem = origem;
        return this;
    }

    public UUID getDestino() {
        return destino;
    }

    public TransacaoEntity setDestino(UUID destino) {
        this.destino = destino;
        return this;
    }}
