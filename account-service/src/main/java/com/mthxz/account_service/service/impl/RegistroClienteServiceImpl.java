package com.mthxz.account_service.service.impl;

import com.mthxz.account_service.entity.ClienteEntity;
import com.mthxz.account_service.enums.TipoConta;
import com.mthxz.account_service.model.ClienteModel;
import com.mthxz.account_service.repository.ClienteRepository;
import com.mthxz.account_service.service.ContaService;
import com.mthxz.account_service.service.RegistroClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegistroClienteServiceImpl implements RegistroClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaService contaService;

    @Override
    public UUID createNewClient(ClienteModel clienteModel) {
        Optional<ClienteEntity> existingByCpf = clienteRepository.findByCpf(clienteModel.getCpf());
        Optional<ClienteEntity> existingByEmail = clienteRepository.findByEmail(clienteModel.getEmail());
        Optional<ClienteEntity> existingByTelefone = clienteRepository.findByTelefone(clienteModel.getTelefone());

        if (existingByCpf.isPresent() || existingByEmail.isPresent() || existingByTelefone.isPresent()) {
            throw new IllegalArgumentException("Client with the same CPF, email, or phone already exists.");
        }

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(UUID.randomUUID());
        clienteEntity.setNome(clienteModel.getNome());
        clienteEntity.setCpf(clienteModel.getCpf());
        clienteEntity.setEmail(clienteModel.getEmail());
        clienteEntity.setSenha(clienteModel.getSenha());
        clienteEntity.setTelefone(clienteModel.getTelefone());
        clienteEntity.setNotificacao(clienteModel.getNotificacao());

        clienteEntity = clienteRepository.save(clienteEntity);

        contaService.createNewAccount(clienteEntity.getId(), TipoConta.CORRENTE);

        return clienteEntity.getId();
    }
}
