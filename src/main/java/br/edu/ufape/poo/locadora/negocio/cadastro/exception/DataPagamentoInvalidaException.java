package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class DataPagamentoInvalidaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataPagamentoInvalidaException(String message) {
        super(message);
    }
}