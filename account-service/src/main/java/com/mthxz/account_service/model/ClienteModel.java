package com.mthxz.account_service.model;

import com.mthxz.bankcommons.enums.TipoNotificacao;

import java.util.List;
import java.util.UUID;


public class ClienteModel {
    private UUID id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;
    private List<TipoNotificacao> notificacao;

    public UUID getId() {
        return id;
    }

    public ClienteModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ClienteModel setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getCpf() {
        return cpf;
    }

    public ClienteModel setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ClienteModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public ClienteModel setSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public ClienteModel setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public List<TipoNotificacao> getNotificacao() {
        return notificacao;
    }

    public ClienteModel setNotificacao(List<TipoNotificacao> notificacao) {
        this.notificacao = notificacao;
        return this;
    }
}

