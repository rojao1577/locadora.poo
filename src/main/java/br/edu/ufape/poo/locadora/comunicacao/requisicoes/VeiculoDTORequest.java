package br.edu.ufape.poo.locadora.comunicacao.requisicoes;

import br.edu.ufape.poo.locadora.negocio.basica.StatusVeiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VeiculoDTORequest(

    @NotBlank
    String placa,

    @NotBlank
    String modelo,

    @NotBlank
    String marca,

    @Positive
    int anoFabricacao,

    @NotNull
    StatusVeiculo status,

    @NotNull
    Long idCategoria

) {
}