package com.mthxz.account_service.model;

import com.mthxz.account_service.enums.StatusTransacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoConcluidaModel {
    private UUID transacao;
    private UUID origem;
    private UUID destino;
    private StatusTransacao status;
    private String detalhes;
}

