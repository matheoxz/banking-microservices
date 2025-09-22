package com.mthxz.transaction_service.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConclusaoTransacaoModel extends ExecucaoTransacaoModel {
    private StatusTransacao status;
    private String detalhes;

    public ConclusaoTransacaoModel(ExecucaoTransacaoModel base, StatusTransacao status, String detalhes) {
        super(base.getTransacao(), base.getOrigem(), base.getDestino());
        this.status = status;
        this.detalhes = detalhes;
    }
}
