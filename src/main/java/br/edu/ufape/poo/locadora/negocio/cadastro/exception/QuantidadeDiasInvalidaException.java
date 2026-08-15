package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class QuantidadeDiasInvalidaException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public QuantidadeDiasInvalidaException(String message) {
    super(message);
  }
}