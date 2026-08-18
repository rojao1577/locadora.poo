package br.edu.ufape.poo.locadora.comunicacao.requisicoes;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemLocacaoDTORequest(
		@NotNull(message = "O valor da diaria eh obrigatorio")
		
		@DecimalMin(value = "0.01", message = "O valor da diaria deve ser maior que zero")
		BigDecimal valorDiaria,
		
		@Min(value = 1, message = "A quantidade de dias deve ser maior que zero")
		int dias,
		
		@NotNull(message = "O ID da locacao é obrigatorio")
		Long locacaoId,
		
		@NotNull(message = "O ID do veiculo é obrigatorio")
		Long veiculoId) 
{}
