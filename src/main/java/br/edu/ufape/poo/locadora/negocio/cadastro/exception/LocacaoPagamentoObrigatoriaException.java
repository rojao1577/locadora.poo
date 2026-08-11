package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class LocacaoPagamentoObrigatoriaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LocacaoPagamentoObrigatoriaException(String message) {
        super(message);
    }
}