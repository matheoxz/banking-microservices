package com.mthxz.transaction_service.service;

import com.mthxz.transaction_service.model.ExecucaoTransacaoModel;
import com.mthxz.transaction_service.model.TransacaoRequestModel;

public interface NewTransactionService {
    boolean solicitaTransacao(TransacaoRequestModel transacao);
    void executaTransacao(ExecucaoTransacaoModel executaTransacao);
}
