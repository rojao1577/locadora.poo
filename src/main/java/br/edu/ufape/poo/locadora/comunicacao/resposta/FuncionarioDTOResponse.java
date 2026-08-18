package br.edu.ufape.poo.locadora.comunicacao.resposta;

public record FuncionarioDTOResponse(
        Long id,
        String nome,
        String cpf
) {}