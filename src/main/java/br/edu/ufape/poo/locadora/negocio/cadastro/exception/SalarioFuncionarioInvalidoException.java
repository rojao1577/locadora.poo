package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class SalarioFuncionarioInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SalarioFuncionarioInvalidoException(String message) {
        super(message);
    }
}