package com.mthxz.audit_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String operacao;

    private LocalDateTime data;

    @Column(columnDefinition = "TEXT")
    private String detalhes;

    public Auditoria() {
        this.data = LocalDateTime.now();
    }

    public Auditoria(String operacao, String detalhes) {
        this.operacao = operacao;
        this.detalhes = detalhes;
        this.data = LocalDateTime.now();
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
}

