package br.edu.ufape.poo.locadora.comunicacao.requisicoes;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CategoriaDTORequest(

    @NotBlank
    String nome,

    @NotNull
    @PositiveOrZero
    BigDecimal valorDiariaBase

) {
}