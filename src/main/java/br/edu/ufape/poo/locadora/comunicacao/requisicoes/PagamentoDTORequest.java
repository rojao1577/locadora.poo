package br.edu.ufape.poo.locadora.comunicacao.requisicoes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoDTORequest(
        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser positivo")
        BigDecimal valor,

        @NotBlank(message = "A forma de pagamento é obrigatória")
        String formaPagamento,

        @NotNull(message = "A data de pagamento é obrigatória")
        LocalDate dataPagamento,

        @NotNull(message = "O id da locação é obrigatório")
        Long idLocacao
) {}