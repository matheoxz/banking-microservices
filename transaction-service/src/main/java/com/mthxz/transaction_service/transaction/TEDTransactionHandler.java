package com.mthxz.transaction_service.transaction;

import com.mthxz.bankcommons.enums.StatusTransacao;
import com.mthxz.bankcommons.model.ExecucaoTransacaoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TEDTransactionHandler implements TransactionHandler {
    private static final Logger log = LoggerFactory.getLogger(TEDTransactionHandler.class);
    @Override
    public StatusTransacao efetuar(ExecucaoTransacaoModel transacao) {
        log.info("Efetuando TED para transacao={} origem={} destino={}",
                transacao.getTransacao(), transacao.getOrigem(), transacao.getDestino());
        return StatusTransacao.FALHOU;
    }
}
