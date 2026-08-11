package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class FormaPagamentoInvalidaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FormaPagamentoInvalidaException(String message) {
        super(message);
    }
}