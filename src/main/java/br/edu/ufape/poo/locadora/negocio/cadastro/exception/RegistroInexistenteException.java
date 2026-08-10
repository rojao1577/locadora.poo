package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class RegistroInexistenteException extends Exception {
	
	private static final long serialVersionUID = 1L;

    public RegistroInexistenteException(String mensagem) {
        super(mensagem);
    }
}
