package com.mthxz.account_service.service.impl;

import com.mthxz.account_service.entity.ContaEntity;
import com.mthxz.account_service.entity.TransacaoEntity;
import com.mthxz.account_service.repository.ContaRepository;
import com.mthxz.account_service.repository.TransacaoRepository;
import com.mthxz.account_service.service.ContaService;
import com.mthxz.bankcommons.enums.StatusTransacao;
import com.mthxz.bankcommons.enums.TipoConta;
import com.mthxz.bankcommons.model.TransacaoConcluidaModel;
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
        logger.info("Creating new account for client={} type={}", clienteId, tipoConta);
        ContaEntity conta = new ContaEntity();
        // Do NOT set the ID manually; let Hibernate generate it to ensure an insert occurs
        conta.setClienteId(clienteId);
        conta.setNumero(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        conta.setAgencia("0001");
        conta.setTipo(tipoConta);
        conta.setSaldo(BigDecimal.ZERO);
        logger.debug("New account details: {}", conta);
        return contaRepository.save(conta);
    }

    @Override
    public void executeTransaction(TransacaoConcluidaModel model) {
        logger.info("Starting execution of transaction={} status={} origin={} destination={}",
                model.getTransacao(), model.getStatus(), model.getOrigem(), model.getDestino());
        if (model.getStatus() == StatusTransacao.CONFIRMADA) {
            Optional<ContaEntity> origem = contaRepository.findById(model.getOrigem());
            Optional<ContaEntity> destino = contaRepository.findById(model.getDestino());

            if (origem.isEmpty()) {
                logger.warn("Origin account {} not found for transaction {}", model.getOrigem(), model.getTransacao());
            }
            if (destino.isEmpty()) {
                logger.warn("Destination account {} not found for transaction {}", model.getDestino(), model.getTransacao());
            }

            if (origem.isPresent() && destino.isPresent()) {
                ContaEntity contaOrigem = origem.get();
                ContaEntity contaDestino = destino.get();

                logger.debug("Balances before transaction: origin={} balance={} | destination={} balance={}",
                        contaOrigem.getId(), contaOrigem.getSaldo(), contaDestino.getId(), contaDestino.getSaldo());
                BigDecimal valor = transacaoRepository.findById(model.getTransacao())
                        .map(TransacaoEntity::getValor)
                        .orElse(BigDecimal.ZERO);

                contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
                contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

                ContaEntity updatedOrigem = contaRepository.save(contaOrigem);
                ContaEntity updatedDestino = contaRepository.save(contaDestino);
                logger.info("Transaction {} applied: origin new balance={} | destination new balance={}",
                        model.getTransacao(), updatedOrigem.getSaldo(), updatedDestino.getSaldo());
            }
        } else {
            logger.info("Skipping transaction {}. Status not confirmed: {}", model.getTransacao(), model.getStatus());
        }
    }
}
