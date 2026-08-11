package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class FuncionarioNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FuncionarioNaoEncontradoException(String message) {
        super(message);
    }
}