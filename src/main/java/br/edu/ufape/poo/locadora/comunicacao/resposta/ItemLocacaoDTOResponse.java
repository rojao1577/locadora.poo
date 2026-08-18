package br.edu.ufape.poo.locadora.comunicacao.resposta;

import java.math.BigDecimal;

public record ItemLocacaoDTOResponse(
		Long id,
        BigDecimal valorDiaria,
        int dias,
        Long locacaoId,
        String veiculoPlaca) 

{}
