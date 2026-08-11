package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class ItemLocacaoNaoEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ItemLocacaoNaoEncontradoException(String message) {
        super(message);
    }
}