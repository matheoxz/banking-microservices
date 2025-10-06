package com.mthxz.account_service.entity;

import com.mthxz.account_service.enums.StatusTransacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID origem;

    private UUID destino;

    @Enumerated(EnumType.STRING)
    private StatusTransacao status;

    private String detalhes;
}
