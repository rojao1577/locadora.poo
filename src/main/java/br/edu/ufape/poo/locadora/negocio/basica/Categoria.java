package br.edu.ufape.poo.locadora.negocio.basica;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "valor_diaria_base", nullable = false)
    private BigDecimal valorDiariaBase;

    public Categoria() {
    }

    public Categoria(final String nome, final BigDecimal valorDiariaBase) {
        this.nome = nome;
        this.valorDiariaBase = valorDiariaBase;
    }

    public BigDecimal calcularValorDiaria() {
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
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        final Categoria categoria = (Categoria) o;
        return Objects.equals(nome, categoria.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
