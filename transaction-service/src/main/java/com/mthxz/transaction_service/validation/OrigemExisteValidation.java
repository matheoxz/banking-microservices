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
@Order(1)
public class OrigemExisteValidation implements TransactionValidation {

    private final ContaRepository contaRepository;

    public OrigemExisteValidation(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public Optional<TransacaoConcluidaModel> validate(TransacaoEntity entity, ExecucaoTransacaoModel exec) {
        boolean origemExiste = entity.getOrigem() != null && contaRepository.existsById(entity.getOrigem());
        if (!origemExiste) {
            return Optional.of(new TransacaoConcluidaModel(
                    exec.getTransacao(),
                    exec.getOrigem(),
                    exec.getDestino(), StatusTransacao.FALHOU,
                    "Origem nao existe na base de dados uuids: " + entity.getOrigem()));
        }
        return Optional.empty();
    }
}
