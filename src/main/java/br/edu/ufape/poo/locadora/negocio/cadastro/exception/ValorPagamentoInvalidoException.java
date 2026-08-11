package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class ValorPagamentoInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ValorPagamentoInvalidoException(String message) {
        super(message);
    }
}