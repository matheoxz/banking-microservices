package com.mthxz.transaction_service.controller;

import com.mthxz.transaction_service.model.SolicitacaoTransacaoModel;
import com.mthxz.transaction_service.model.TransacaoRequestModel;
import com.mthxz.transaction_service.service.NewTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final NewTransactionService newTransactionService;

    @PostMapping("/solicitar")
    public ResponseEntity<Void> solicitar(@RequestBody SolicitacaoTransacaoModel solicitacao) {
        var ok = newTransactionService.solicitaTransacao(new TransacaoRequestModel(
                solicitacao.getTipo(), solicitacao.getValor(), solicitacao.getOrigem(), solicitacao.getDestino()
        ));
        return ok ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
    }
}
