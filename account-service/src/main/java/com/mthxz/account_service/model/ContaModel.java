package com.mthxz.account_service.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.mthxz.account_service.enums.TipoConta;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContaModel {
    private UUID id;
    private String numero;
    private String agencia;
    private TipoConta tipo;
    private BigDecimal saldo;

    // default constructor provided by Lombok
}

