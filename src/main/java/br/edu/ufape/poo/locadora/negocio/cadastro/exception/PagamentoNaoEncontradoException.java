package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class PagamentoNaoEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PagamentoNaoEncontradoException(String message) {
        super(message);
    }
}