package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class ClienteInadimplenteException extends Exception {
    
	private static final long serialVersionUID = 1L;

	public ClienteInadimplenteException(String cpf) {
        super("O cliente de CPF " + cpf + " possui inadimplências e não pode realizar novas locações.");
    }
}