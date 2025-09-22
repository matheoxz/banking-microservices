package com.mthxz.transaction_service.validation;

import com.mthxz.transaction_service.entity.TransacaoEntity;
import com.mthxz.transaction_service.model.ConclusaoTransacaoModel;
import com.mthxz.transaction_service.model.ExecucaoTransacaoModel;
import com.mthxz.transaction_service.model.StatusTransacao;
import com.mthxz.transaction_service.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(1)
@RequiredArgsConstructor
public class OrigemExisteValidation implements TransactionValidation {

    private final ContaRepository contaRepository;

    @Override
    public Optional<ConclusaoTransacaoModel> validate(TransacaoEntity entity, ExecucaoTransacaoModel exec) {
        boolean origemExiste = entity.getOrigem() != null && contaRepository.existsById(entity.getOrigem());
        if (!origemExiste) {
            return Optional.of(new ConclusaoTransacaoModel(exec, StatusTransacao.FALHOU,
                    "Origem nao existe na base de dados uuids: " + entity.getOrigem()));
        }
        return Optional.empty();
    }
}
