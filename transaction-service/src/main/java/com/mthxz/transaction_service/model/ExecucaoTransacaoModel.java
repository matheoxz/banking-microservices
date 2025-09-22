package com.mthxz.transaction_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecucaoTransacaoModel {
    private UUID transacao;
    private UUID origem;
    private UUID destino;
}
