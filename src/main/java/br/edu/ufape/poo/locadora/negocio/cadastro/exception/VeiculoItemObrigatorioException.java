package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class VeiculoItemObrigatorioException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public VeiculoItemObrigatorioException(String message) {
        super(message);
    }
}