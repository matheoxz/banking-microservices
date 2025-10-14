package com.mthxz.bankcommons.model;

import com.mthxz.bankcommons.enums.TipoTransacao;

import java.math.BigDecimal;
import java.util.UUID;

public class SolicitacaoTransacaoModel {
    private TipoTransacao tipo;
    private BigDecimal valor;
    private UUID origem;
    private UUID destino;

    public SolicitacaoTransacaoModel(TipoTransacao tipo, BigDecimal valor, UUID origem, UUID destino) {
        this.tipo = tipo;
        this.valor = valor;
        this.origem = origem;
        this.destino = destino;
    }

    public SolicitacaoTransacaoModel() {
    }

    public TipoTransacao getTipo() { return tipo; }

    public BigDecimal getValor() { return valor; }

    public UUID getOrigem() { return origem; }

    public UUID getDestino() { return destino; }

    public SolicitacaoTransacaoModel setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
        return this;
    }

    public SolicitacaoTransacaoModel setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public SolicitacaoTransacaoModel setOrigem(UUID origem) {
        this.origem = origem;
        return this;
    }

    public SolicitacaoTransacaoModel setDestino(UUID destino) {
        this.destino = destino;
        return this;
    }

    @Override
    public String toString() {
        return "SolicitacaoTransacaoModel{" +
                "tipo=" + tipo +
                ", valor=" + valor +
                ", origem=" + origem +
                ", destino=" + destino +
                '}';
    }
}