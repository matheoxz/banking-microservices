package com.mthxz.transaction_service.transaction;

import com.mthxz.transaction_service.model.ExecucaoTransacaoModel;
import com.mthxz.transaction_service.model.StatusTransacao;

public interface TransactionHandler {
    StatusTransacao efetuar(ExecucaoTransacaoModel transacao);
}
