package com.mthxz.transaction_service.transaction;

import com.mthxz.bankcommons.enums.StatusTransacao;
import com.mthxz.bankcommons.model.ExecucaoTransacaoModel;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebitoAutomaticoTransactionHandler implements TransactionHandler {
    private static final Logger log = LoggerFactory.getLogger(DebitoAutomaticoTransactionHandler.class);
    @Override
    public StatusTransacao efetuar(ExecucaoTransacaoModel transacao) {
        log.info("Efetuando DEBITO_AUTOMATICO para transacao={} origem={} destino={}",
                transacao.getTransacao(), transacao.getOrigem(), transacao.getDestino());
        boolean success = ThreadLocalRandom.current().nextDouble() < 0.8;
        return success ? StatusTransacao.CONFIRMADA : StatusTransacao.FALHOU;
    }
}
