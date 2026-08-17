package br.edu.ufape.poo.locadora.comunicacao.resposta;

import br.edu.ufape.poo.locadora.negocio.basica.StatusVeiculo;

public record VeiculoDTOResponse(
    Long id,
    String placa,
    String modelo,
    String marca,
    int anoFabricacao,
    StatusVeiculo status,
    Long idCategoria
) {
}