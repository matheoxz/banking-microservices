package com.mthxz.bankcommons.model;

import com.mthxz.bankcommons.enums.StatusTransacao;

import java.util.UUID;

public class TransacaoConcluidaModel {
    private UUID transacao;
    private UUID origem;
    private UUID destino;
    private StatusTransacao status;
    private String detalhes;
    // Explicit getters to ensure availability without Lombok processing
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
    
    // Default constructor for Jackson deserialization
    public TransacaoConcluidaModel() {
    }
    // Explicit all-args constructor
    public TransacaoConcluidaModel(UUID transacao, UUID origem, UUID destino, StatusTransacao status, String detalhes) {
        this.transacao = transacao;
        this.origem = origem;
        this.destino = destino;
        this.status = status;
        this.detalhes = detalhes;
    }
    
    // Explicit setters
    public void setTransacao(UUID transacao) { this.transacao = transacao; }
    public void setOrigem(UUID origem) { this.origem = origem; }
    public void setDestino(UUID destino) { this.destino = destino; }
    public void setStatus(StatusTransacao status) { this.status = status; }
    public void setDetalhes(String detalhes) { this.detalhes = detalhes; }
}

