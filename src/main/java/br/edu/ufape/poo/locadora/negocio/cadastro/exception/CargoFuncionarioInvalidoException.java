package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class CargoFuncionarioInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CargoFuncionarioInvalidoException(String message) {
        super(message);
    }
}