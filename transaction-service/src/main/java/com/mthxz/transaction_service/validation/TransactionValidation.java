package com.mthxz.transaction_service.validation;

import com.mthxz.bankcommons.model.ExecucaoTransacaoModel;
import com.mthxz.bankcommons.model.TransacaoConcluidaModel;
import com.mthxz.transaction_service.entity.TransacaoEntity;

import java.util.Optional;

public interface TransactionValidation {
    Optional<TransacaoConcluidaModel> validate(TransacaoEntity entity, ExecucaoTransacaoModel exec);
}
