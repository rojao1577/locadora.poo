package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class ValorDiariaInvalidoException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValorDiariaInvalidoException(String mensagem) {
        super(mensagem);
    }
}