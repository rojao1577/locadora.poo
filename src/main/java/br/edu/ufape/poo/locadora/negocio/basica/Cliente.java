package br.edu.ufape.poo.locadora.negocio.basica;

import jakarta.persistence.Entity;

@Entity
public class Cliente extends Pessoa{
	private String email;
	private int scoreCredito;
	
	public Cliente() {
		super();
	}
	
	public Cliente(String nome, String cpf, String telefone, String endereco, String email, int scoreCredito) {
		super(nome, cpf, telefone, endereco);
		this.email = email;
		this.scoreCredito = scoreCredito;
	}
	
	public boolean verificarInadimplencia() {
		return scoreCredito < 300;
	}
	
	@Override
	public boolean validarCpf() {
		if (getCpf() == null) {
			return false;
		}
		String cpfLimpo = getCpf().replaceAll("[^0-9]", "");
		return cpfLimpo.length() == 11;
	}
	
	public String getEmail() {
		return email;
	}
	
	public int getScoreCredito() {
		return scoreCredito;
	}
	
	public void setScoreCredito(int scoreCredito) {
		this.scoreCredito = scoreCredito;
	}
}
 