package br.edu.ufape.poo.locadora.comunicacao.resposta;

public record ClienteDTOResponse(
		Long id,
		String nome,
		String cpf,
		String telefone,
		String endereco,
		String email,
		int scoreCredito) {}
