package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class NomeCategoriaInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NomeCategoriaInvalidoException(String mensagem) {
        super(mensagem);
    }
}