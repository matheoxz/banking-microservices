package com.mthxz.transaction_service.validation;

import com.mthxz.transaction_service.entity.ContaEntity;
import com.mthxz.transaction_service.entity.TransacaoEntity;
import com.mthxz.transaction_service.model.ConclusaoTransacaoModel;
import com.mthxz.transaction_service.model.ExecucaoTransacaoModel;
import com.mthxz.transaction_service.model.StatusTransacao;
import com.mthxz.transaction_service.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@Order(3)
@RequiredArgsConstructor
public class SaldoSuficienteValidation implements TransactionValidation {

    private final ContaRepository contaRepository;

    @Override
    public Optional<ConclusaoTransacaoModel> validate(TransacaoEntity entity, ExecucaoTransacaoModel exec) {
        BigDecimal saldoOrigem = contaRepository.findById(entity.getOrigem())
                .map(ContaEntity::getSaldo)
                .orElse(BigDecimal.ZERO);
        if (entity.getValor() != null && entity.getValor().compareTo(saldoOrigem) > 0) {
            return Optional.of(new ConclusaoTransacaoModel(exec, StatusTransacao.CANCELADA,
                    "Transacao com valor maior que saldo"));
        }
        return Optional.empty();
    }
}
