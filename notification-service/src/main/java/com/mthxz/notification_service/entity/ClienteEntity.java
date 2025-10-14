package com.mthxz.notification_service.entity;

import com.mthxz.bankcommons.enums.TipoNotificacao;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha;

    private String telefone;

    @Enumerated(EnumType.STRING)
    private List<TipoNotificacao> notificacao;

    public UUID getId() {
        return id;
    }

    public ClienteEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ClienteEntity setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getCpf() {
        return cpf;
    }

    public ClienteEntity setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ClienteEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public ClienteEntity setSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public ClienteEntity setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public List<TipoNotificacao> getNotificacao() {
        return notificacao;
    }

    public ClienteEntity setNotificacao(List<TipoNotificacao> notificacao) {
        this.notificacao = notificacao;
        return this;
    }
}
