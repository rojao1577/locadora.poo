package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class FuncionarioJaExisteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FuncionarioJaExisteException(String message) {
        super(message);
    }
}