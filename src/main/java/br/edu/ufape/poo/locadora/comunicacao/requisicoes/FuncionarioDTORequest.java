package br.edu.ufape.poo.locadora.comunicacao.requisicoes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioDTORequest(
        @NotBlank(message = "O nome é obrigatório") String nome,
        @NotBlank(message = "O CPF é obrigatório") String cpf,
        String telefone,
        String endereco,
        @NotBlank(message = "O cargo é obrigatório") String cargo,
        LocalDate dataContratacao,
        @NotNull(message = "O salário é obrigatório") BigDecimal salario
) {}