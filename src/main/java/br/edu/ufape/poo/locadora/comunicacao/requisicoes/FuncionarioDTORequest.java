package br.edu.ufape.poo.locadora.comunicacao.requisicoes;

import jakarta.validation.constraints.NotBlank;

public record FuncionarioDTORequest(
        @NotBlank(message = "O nome é obrigatório") String nome,
        @NotBlank(message = "O CPF é obrigatório") String cpf
) {}