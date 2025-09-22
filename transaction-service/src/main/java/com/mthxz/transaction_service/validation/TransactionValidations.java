package com.mthxz.transaction_service.validation;

import com.mthxz.transaction_service.entity.TransacaoEntity;
import com.mthxz.transaction_service.model.ConclusaoTransacaoModel;
import com.mthxz.transaction_service.model.ExecucaoTransacaoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionValidations {

    private final List<TransactionValidation> validators;

    public Optional<ConclusaoTransacaoModel> validate(TransacaoEntity entity, ExecucaoTransacaoModel exec) {
        if (validators == null || validators.isEmpty()) {
            return Optional.empty();
        }
        for (TransactionValidation v : validators) {
            Optional<ConclusaoTransacaoModel> res = v.validate(entity, exec);
            if (res.isPresent()) {
                return res;
            }
        }
        return Optional.empty();
    }
}
