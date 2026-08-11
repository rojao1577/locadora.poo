package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class CpfFuncionarioInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CpfFuncionarioInvalidoException(String message) {
        super(message);
    }
}