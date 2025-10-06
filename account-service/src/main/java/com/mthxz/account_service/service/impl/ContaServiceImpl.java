package com.mthxz.account_service.service.impl;

import com.mthxz.account_service.entity.ContaEntity;
import com.mthxz.account_service.enums.StatusTransacao;
import com.mthxz.account_service.enums.TipoConta;
import com.mthxz.account_service.model.TransacaoConcluidaModel;
import com.mthxz.account_service.repository.ContaRepository;
import com.mthxz.account_service.repository.TransacaoRepository;
import com.mthxz.account_service.service.ContaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContaServiceImpl implements ContaService {

    private static final Logger logger = LoggerFactory.getLogger(ContaServiceImpl.class);

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Override
    public ContaEntity createNewAccount(UUID clienteId, TipoConta tipoConta) {
        ContaEntity conta = new ContaEntity();
        conta.setId(UUID.randomUUID());
        conta.setNumero(UUID.randomUUID().toString().substring(0, 10));
        conta.setAgencia("0001");
        conta.setTipo(tipoConta);
        conta.setSaldo(BigDecimal.ZERO);
        return contaRepository.save(conta);
    }

    @Override
    public void executeTransaction(TransacaoConcluidaModel model) {
        if (model.getStatus() == StatusTransacao.CONFIRMADA) {
            Optional<ContaEntity> origem = contaRepository.findById(model.getOrigem());
            Optional<ContaEntity> destino = contaRepository.findById(model.getDestino());

            if (origem.isPresent() && destino.isPresent()) {
                ContaEntity contaOrigem = origem.get();
                ContaEntity contaDestino = destino.get();

                // Assuming transaction value is fetched from the database
                BigDecimal valor = transacaoRepository.findById(model.getTransacao())
                        .map(transacao -> new BigDecimal(transacao.getDetalhes()))
                        .orElse(BigDecimal.ZERO);

                contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
                contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

                contaRepository.save(contaOrigem);
                contaRepository.save(contaDestino);
            }
        } else {
            logger.info("Transaction {} not confirmed. Status: {}", model.getTransacao(), model.getStatus());
        }
    }
}
