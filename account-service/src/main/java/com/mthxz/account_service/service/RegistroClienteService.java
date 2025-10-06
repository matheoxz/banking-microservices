package com.mthxz.account_service.service;

import com.mthxz.account_service.model.ClienteModel;

import java.util.UUID;

public interface RegistroClienteService {
    UUID createNewClient(ClienteModel clienteModel);
}

