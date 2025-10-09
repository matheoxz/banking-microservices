package com.mthxz.transaction_service.transaction;

import com.mthxz.bankcommons.enums.TipoTransacao;
import org.springframework.stereotype.Component;

@Component
public class TransacaoFactory {

    public TransactionHandler getHandler(TipoTransacao tipo) {
        return switch (tipo) {
            case PIX -> new PIXTransactionHandler();
            case TED -> new TEDTransactionHandler();
            case DOC -> new DOCTransactionHandler();
            case PAGAMENTO -> new PagamentoTransactionHandler();
            case DEBITO_AUTOMATICO -> new DebitoAutomaticoTransactionHandler();
        };
    }
}
