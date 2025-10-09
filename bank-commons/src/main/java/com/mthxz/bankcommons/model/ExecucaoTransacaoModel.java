package com.mthxz.bankcommons.model;


import java.util.UUID;

public class ExecucaoTransacaoModel {
    private UUID transacao;
    private UUID origem;
    private UUID destino;
    
    // Explicit constructors
    public ExecucaoTransacaoModel() {}

    public ExecucaoTransacaoModel(UUID transacao, UUID origem, UUID destino) {
        this.transacao = transacao;
        this.origem = origem;
        this.destino = destino;
    }

    // Explicit getters
    public UUID getTransacao() { return transacao; }

    public UUID getOrigem() { return origem; }

    public UUID getDestino() { return destino; }

    // Explicit setters
    public void setTransacao(UUID transacao) { this.transacao = transacao; }

    public void setOrigem(UUID origem) { this.origem = origem; }

    public void setDestino(UUID destino) { this.destino = destino; }
}