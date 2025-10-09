package com.mthxz.transaction_service.transaction;

import com.mthxz.bankcommons.enums.StatusTransacao;
import com.mthxz.bankcommons.model.ExecucaoTransacaoModel;

public interface TransactionHandler {
    StatusTransacao efetuar(ExecucaoTransacaoModel transacao);
}
