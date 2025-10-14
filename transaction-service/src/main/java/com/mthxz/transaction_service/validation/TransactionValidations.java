package com.mthxz.transaction_service.validation;

import com.mthxz.bankcommons.model.ExecucaoTransacaoModel;
import com.mthxz.bankcommons.model.TransacaoConcluidaModel;
import com.mthxz.transaction_service.entity.TransacaoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TransactionValidations {

    private final List<TransactionValidation> validators;

    public TransactionValidations(List<TransactionValidation> validators) {
        this.validators = validators;
    }

    public Optional<TransacaoConcluidaModel> validate(TransacaoEntity entity, ExecucaoTransacaoModel exec) {
        if (validators == null || validators.isEmpty()) {
            return Optional.empty();
        }
        for (TransactionValidation v : validators) {
            Optional<TransacaoConcluidaModel> res = v.validate(entity, exec);
            if (res.isPresent()) {
                return res;
            }
        }
        return Optional.empty();
    }
}
