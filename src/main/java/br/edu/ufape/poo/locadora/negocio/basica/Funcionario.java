package br.edu.ufape.poo.locadora.negocio.basica;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class Funcionario extends Pessoa{
	private String cargo;
	private BigDecimal salario;
	private LocalDate dataContratacao;
	
	public Funcionario() {
		super();		
	}

	public Funcionario(String nome, String cpf, String telefone, String endereco, String cargo, BigDecimal salario, LocalDate dataContratacao) {
		super(nome, cpf, telefone, endereco);
		this.cargo = cargo;
		this.salario = salario;
		this.dataContratacao = dataContratacao;
	}
	
	@Override
	public boolean validarCpf() {
		if (getCpf() == null) {
			return false;
		}
		String cpfLimpo = getCpf().replaceAll("[^0-9]", "");
		return cpfLimpo.length() == 11;
	}
	
	public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }
}

