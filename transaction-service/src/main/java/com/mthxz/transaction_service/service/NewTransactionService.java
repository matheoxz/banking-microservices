package com.mthxz.transaction_service.service;

import com.mthxz.bankcommons.model.ExecucaoTransacaoModel;
import com.mthxz.transaction_service.model.TransacaoRequestModel;

import java.util.UUID;

public interface NewTransactionService {
    UUID solicitaTransacao(TransacaoRequestModel transacao);
    void executaTransacao(ExecucaoTransacaoModel executaTransacao);
}
