package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class VeiculoJaExisteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VeiculoJaExisteException(String mensagem) {
        super(mensagem);
    }
}