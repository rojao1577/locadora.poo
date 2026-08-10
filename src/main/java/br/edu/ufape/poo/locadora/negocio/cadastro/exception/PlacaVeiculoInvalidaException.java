package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class PlacaVeiculoInvalidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PlacaVeiculoInvalidaException(String mensagem) {
        super(mensagem);
    }
}