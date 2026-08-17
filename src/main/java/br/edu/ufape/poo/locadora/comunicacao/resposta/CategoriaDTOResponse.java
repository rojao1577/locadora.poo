package br.edu.ufape.poo.locadora.comunicacao.resposta;

import java.math.BigDecimal;

public record CategoriaDTOResponse(
    Long id,
    String nome,
    BigDecimal valorDiariaBase
) {
}