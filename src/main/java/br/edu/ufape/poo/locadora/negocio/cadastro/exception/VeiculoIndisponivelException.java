package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class VeiculoIndisponivelException extends Exception {
    public VeiculoIndisponivelException(String placa) {
        super("O veículo de placa " + placa + " não está disponível para locação no momento.");
    }
}