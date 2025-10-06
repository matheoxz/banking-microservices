package com.mthxz.account_service.controller;

import com.mthxz.account_service.model.ClienteModel;
import com.mthxz.account_service.service.RegistroClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private RegistroClienteService registroClienteService;

    @PostMapping("/cliente")
    public ResponseEntity<UUID> registerClient(@RequestBody ClienteModel clienteModel) {
        UUID clientId = registroClienteService.createNewClient(clienteModel);
        return ResponseEntity.ok(clientId);
    }
}
