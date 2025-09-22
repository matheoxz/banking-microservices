package com.mthxz.transaction_service.validation;

import com.mthxz.transaction_service.entity.TransacaoEntity;
import com.mthxz.transaction_service.model.ConclusaoTransacaoModel;
import com.mthxz.transaction_service.model.ExecucaoTransacaoModel;

import java.util.Optional;

/**
 * A single validation step in the transaction processing pipeline.
 * Should return Optional.with a ConclusaoTransacaoModel when the validation fails,
 * or Optional.empty() when the validation passes and the chain should continue.
 */
public interface TransactionValidation {
    Optional<ConclusaoTransacaoModel> validate(TransacaoEntity entity, ExecucaoTransacaoModel exec);
}
