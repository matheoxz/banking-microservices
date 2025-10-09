package com.mthxz.account_service.service;

import com.mthxz.account_service.entity.ContaEntity;
import com.mthxz.bankcommons.enums.TipoConta;
import com.mthxz.bankcommons.model.TransacaoConcluidaModel;

import java.util.UUID;

public interface ContaService {
    ContaEntity createNewAccount(UUID clienteId, TipoConta tipoConta);
    void executeTransaction(TransacaoConcluidaModel model);
}

