package com.mthxz.transaction_service.controller;

import com.mthxz.bankcommons.model.SolicitacaoTransacaoModel;
import com.mthxz.transaction_service.model.TransacaoRequestModel;
import com.mthxz.transaction_service.service.NewTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static java.util.Objects.isNull;

@RestController
public class TransactionController {

    private final NewTransactionService newTransactionService;

    public TransactionController(NewTransactionService newTransactionService) {
        this.newTransactionService = newTransactionService;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<UUID> solicitar(@RequestBody SolicitacaoTransacaoModel solicitacao) {
        var transaction = newTransactionService.solicitaTransacao(new TransacaoRequestModel()
                        .setTipo(solicitacao.getTipo())
                        .setValor(solicitacao.getValor())
                        .setOrigem(solicitacao.getOrigem())
                        .setDestino(solicitacao.getDestino())
        );
        return !isNull(transaction) ? ResponseEntity.ok(transaction) : ResponseEntity.internalServerError().build();
    }
}
