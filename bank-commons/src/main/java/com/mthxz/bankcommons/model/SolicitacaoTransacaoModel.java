package com.mthxz.bankcommons.model;

import com.mthxz.bankcommons.enums.TipoTransacao;

import java.math.BigDecimal;
import java.util.UUID;

public class SolicitacaoTransacaoModel {
    private TipoTransacao tipo;
    private BigDecimal valor;
    private UUID origem;
    private UUID destino;
    
    // Explicit constructors
    public SolicitacaoTransacaoModel() {}

    public SolicitacaoTransacaoModel(TipoTransacao tipo, BigDecimal valor, UUID origem, UUID destino) {
        this.tipo = tipo;
        this.valor = valor;
        this.origem = origem;
        this.destino = destino;
    }

    // Explicit getters
    public TipoTransacao getTipo() { return tipo; }

    public BigDecimal getValor() { return valor; }

    public UUID getOrigem() { return origem; }

    public UUID getDestino() { return destino; }

    // Explicit setters
    public void setTipo(TipoTransacao tipo) { this.tipo = tipo; }

    public void setValor(BigDecimal valor) { this.valor = valor; }

    public void setOrigem(UUID origem) { this.origem = origem; }

    public void setDestino(UUID destino) { this.destino = destino; }
}