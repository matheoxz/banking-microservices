package com.mthxz.transaction_service.validation;

import com.mthxz.bankcommons.enums.StatusTransacao;
import com.mthxz.bankcommons.model.ExecucaoTransacaoModel;
import com.mthxz.bankcommons.model.TransacaoConcluidaModel;
import com.mthxz.transaction_service.entity.TransacaoEntity;
import com.mthxz.transaction_service.repository.ContaRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(2)
public class DestinoExisteValidation implements TransactionValidation {

    private final ContaRepository contaRepository;

    public DestinoExisteValidation(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public Optional<TransacaoConcluidaModel> validate(TransacaoEntity entity, ExecucaoTransacaoModel exec) {
        boolean destinoExiste = entity.getDestino() != null && contaRepository.existsById(entity.getDestino());
        if (!destinoExiste) {
            return Optional.of(new TransacaoConcluidaModel(
                    exec.getTransacao(),
                    exec.getOrigem(),
                    exec.getDestino(),
                    StatusTransacao.FALHOU,
                    "Destino nao existe na base dados uuids:" + entity.getDestino()));
        }
        return Optional.empty();
    }
}
