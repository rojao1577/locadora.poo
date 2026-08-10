package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class CategoriaVeiculoObrigatoriaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CategoriaVeiculoObrigatoriaException(String mensagem) {
        super(mensagem);
    }
}