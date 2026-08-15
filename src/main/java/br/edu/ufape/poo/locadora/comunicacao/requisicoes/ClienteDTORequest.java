package br.edu.ufape.poo.locadora.comunicacao.requisicoes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteDTORequest(
		 @NotBlank(message = "O nome é obrigatório")
	        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
	        String nome,

	        @NotBlank(message = "O CPF é obrigatório")
	        String cpf,

	        String telefone,

	        String endereco,

	        @NotBlank(message = "O email é obrigatório")
	        @Email(message = "Email inválido")
	        String email,

	        @Min(value = 0, message = "O score de crédito não pode ser negativo")
	        int scoreCredito) 
{}
