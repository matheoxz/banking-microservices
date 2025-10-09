package com.mthxz.transaction_service.validation;

import com.mthxz.bankcommons.enums.StatusTransacao;
import com.mthxz.bankcommons.model.ExecucaoTransacaoModel;
import com.mthxz.bankcommons.model.TransacaoConcluidaModel;
import com.mthxz.transaction_service.entity.ContaEntity;
import com.mthxz.transaction_service.entity.TransacaoEntity;
import com.mthxz.transaction_service.repository.ContaRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@Order(3)
public class SaldoSuficienteValidation implements TransactionValidation {

    private final ContaRepository contaRepository;

    public SaldoSuficienteValidation(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public Optional<TransacaoConcluidaModel> validate(TransacaoEntity entity, ExecucaoTransacaoModel exec) {
        BigDecimal saldoOrigem = contaRepository.findById(entity.getOrigem())
                .map(ContaEntity::getSaldo)
                .orElse(BigDecimal.ZERO);
        if (entity.getValor() != null && entity.getValor().compareTo(saldoOrigem) > 0) {
            return Optional.of(new TransacaoConcluidaModel(
                    exec.getTransacao(),
                    exec.getOrigem(),
                    exec.getDestino(), StatusTransacao.CANCELADA,
                    "Transacao com valor maior que saldo"));
        }
        return Optional.empty();
    }
}
