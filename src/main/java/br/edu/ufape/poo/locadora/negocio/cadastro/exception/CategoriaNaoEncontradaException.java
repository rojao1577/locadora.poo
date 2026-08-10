package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class CategoriaNaoEncontradaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CategoriaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}