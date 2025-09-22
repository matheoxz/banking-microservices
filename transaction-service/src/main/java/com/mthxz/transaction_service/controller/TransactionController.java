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
    public ResponseEntity<Boolean> solicitar(@RequestBody SolicitacaoTransacaoModel solicitacao) {
        try {
            boolean ok = newTransactionService.solicitaTransacao(new TransacaoRequestModel(
                    solicitacao.getTipo(), solicitacao.getValor(), solicitacao.getOrigem(), solicitacao.getDestino()
            ));
            return ResponseEntity.ok(ok);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(false);
        }
    }
}
