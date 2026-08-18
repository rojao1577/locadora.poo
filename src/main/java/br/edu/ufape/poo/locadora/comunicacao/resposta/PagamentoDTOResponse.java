package br.edu.ufape.poo.locadora.comunicacao.resposta;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoDTOResponse(
        Long id,
        BigDecimal valor,
        String formaPagamento,
        LocalDate dataPagamento,
        Long idLocacao
) {}