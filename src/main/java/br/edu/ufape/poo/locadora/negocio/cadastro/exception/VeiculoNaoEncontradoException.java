package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class VeiculoNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VeiculoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}