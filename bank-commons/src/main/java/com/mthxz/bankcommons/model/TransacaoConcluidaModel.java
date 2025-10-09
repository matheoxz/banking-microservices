package com.mthxz.bankcommons.model;

import com.mthxz.bankcommons.enums.StatusTransacao;

import java.util.UUID;

public class TransacaoConcluidaModel {
    private UUID transacao;
    private UUID origem;
    private UUID destino;
    private StatusTransacao status;
    private String detalhes;

    public java.util.UUID getTransacao() {
        return transacao;
    }

    public java.util.UUID getOrigem() {
        return origem;
    }

    public java.util.UUID getDestino() {
        return destino;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public TransacaoConcluidaModel setTransacao(UUID transacao) {
        this.transacao = transacao;
        return this;
    }

    public TransacaoConcluidaModel setOrigem(UUID origem) {
        this.origem = origem;
        return this;
    }

    public TransacaoConcluidaModel setDestino(UUID destino) {
        this.destino = destino;
        return this;
    }

    public TransacaoConcluidaModel setStatus(StatusTransacao status) {
        this.status = status;
        return this;
    }

    public TransacaoConcluidaModel setDetalhes(String detalhes) {
        this.detalhes = detalhes;
        return this;
    }
}

