package com.mthxz.account_service.model;

import java.util.UUID;

import com.mthxz.account_service.enums.TipoNotificacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteModel {
    private UUID id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;
    private TipoNotificacao[] notificacao;
}

