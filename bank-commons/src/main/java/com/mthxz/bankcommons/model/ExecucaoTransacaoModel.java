package com.mthxz.bankcommons.model;


import java.util.UUID;

public class ExecucaoTransacaoModel {
    private UUID transacao;
    private UUID origem;
    private UUID destino;
    
    public ExecucaoTransacaoModel() {}

    public ExecucaoTransacaoModel(UUID transacao, UUID origem, UUID destino) {
        this.transacao = transacao;
        this.origem = origem;
        this.destino = destino;
    }

    public UUID getTransacao() { return transacao; }

    public UUID getOrigem() { return origem; }

    public UUID getDestino() { return destino; }

    public ExecucaoTransacaoModel setTransacao(UUID transacao) {
        this.transacao = transacao;
        return this;
    }

    public ExecucaoTransacaoModel setOrigem(UUID origem) {
        this.origem = origem;
        return this;
    }

    public ExecucaoTransacaoModel setDestino(UUID destino) {
        this.destino = destino;
        return this;
    }

    @Override
    public String toString() {
        return "ExecucaoTransacaoModel{" +
                "transacao=" + transacao +
                ", origem=" + origem +
                ", destino=" + destino +
                '}';
    }
}