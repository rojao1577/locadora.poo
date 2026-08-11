package br.edu.ufape.poo.locadora.negocio.cadastro.exception;

public class ClienteInadimplenteException extends Exception {
    public ClienteInadimplenteException(String cpf) {
        super("O cliente de CPF " + cpf + " possui inadimplências e não pode realizar novas locações.");
    }
}