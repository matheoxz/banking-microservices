package com.mthxz.transaction_service.transaction;

import com.mthxz.transaction_service.model.ExecucaoTransacaoModel;
import com.mthxz.transaction_service.model.StatusTransacao;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class DebitoAutomaticoTransactionHandler implements TransactionHandler {
    @Override
    public StatusTransacao efetuar(ExecucaoTransacaoModel transacao) {
        log.info("Efetuando DEBITO_AUTOMATICO para transacao={} origem={} destino={}",
                transacao.getTransacao(), transacao.getOrigem(), transacao.getDestino());
        boolean success = ThreadLocalRandom.current().nextDouble() < 0.8;
        return success ? StatusTransacao.CONFIRMADA : StatusTransacao.FALHOU;
    }
}
