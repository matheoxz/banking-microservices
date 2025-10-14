package com.mthxz.transaction_service.model;

import com.mthxz.bankcommons.enums.TipoTransacao;

import java.math.BigDecimal;
import java.util.UUID;


public class TransacaoRequestModel {
    private TipoTransacao tipo;
    private BigDecimal valor;
    private UUID origem;
    private UUID destino;

    public TipoTransacao getTipo() {
        return tipo;
    }

    public TransacaoRequestModel setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
        return this;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TransacaoRequestModel setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public UUID getOrigem() {
        return origem;
    }

    public TransacaoRequestModel setOrigem(UUID origem) {
        this.origem = origem;
        return this;
    }

    public UUID getDestino() {
        return destino;
    }

    public TransacaoRequestModel setDestino(UUID destino) {
        this.destino = destino;
        return this;
    }
}
