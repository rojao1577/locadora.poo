package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class CpfInvalidoException extends Exception {
	private final static long serialVersionUID = 1L;
	
	public CpfInvalidoException(String mensagem) {
		super(mensagem);
	}
}
