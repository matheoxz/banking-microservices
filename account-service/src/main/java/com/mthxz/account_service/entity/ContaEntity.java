package com.mthxz.account_service.entity;

import com.mthxz.account_service.enums.TipoConta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String numero;

    private String agencia;

    @Enumerated(EnumType.STRING)
    private TipoConta tipo;

    private BigDecimal saldo;
}
