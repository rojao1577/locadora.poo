package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class CategoriaJaExisteException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoriaJaExisteException(String mensagem) {
        super(mensagem);
    }
}