package br.edu.ufape.poo.locadora.negocio.basica;

import java.math.BigDecimal;

public class Categoria {
    private Long id;
    private String nome;
    private BigDecimal valorDiariaBase;

    
    public Categoria() {
    }

    
    public Categoria(final Long id, final String nome, final BigDecimal valorDiariaBase) {
        this.id = id;
        this.nome = nome;
        this.valorDiariaBase = valorDiariaBase;
    }

   
    public BigDecimal calcularValorDiaria() {
        // Lógica para cálculo da diária base 
        return this.valorDiariaBase;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorDiariaBase() {
        return valorDiariaBase;
    }

    public void setValorDiariaBase(final BigDecimal valorDiariaBase) {
        this.valorDiariaBase = valorDiariaBase;
    }
}