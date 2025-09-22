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
@Order(2)
@RequiredArgsConstructor
public class DestinoExisteValidation implements TransactionValidation {

    private final ContaRepository contaRepository;

    @Override
    public Optional<ConclusaoTransacaoModel> validate(TransacaoEntity entity, ExecucaoTransacaoModel exec) {
        boolean destinoExiste = entity.getDestino() != null && contaRepository.existsById(entity.getDestino());
        if (!destinoExiste) {
            return Optional.of(new ConclusaoTransacaoModel(exec, StatusTransacao.FALHOU,
                    "Destino nao existe na base dados uuids:" + entity.getDestino()));
        }
        return Optional.empty();
    }
}
