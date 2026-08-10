package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class StatusVeiculoObrigatorioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StatusVeiculoObrigatorioException(String mensagem) {
        super(mensagem);
    }
}