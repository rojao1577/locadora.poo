package br.edu.ufape.poo.locadora.negocio.basica;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class ItemLocacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valorDiaria;

    // === Aqui está o atributo que faltava! ===
    @Column(nullable = false)
    private int dias;

    @ManyToOne(optional = false)
    @JoinColumn(name = "locacao_id")
    private Locacao locacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    public ItemLocacao() {}

    public ItemLocacao(BigDecimal valorDiaria, int dias, Locacao locacao, Veiculo veiculo) {
        this.valorDiaria = valorDiaria;
        this.dias = dias;
        this.locacao = locacao;
        this.veiculo = veiculo;
    }



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(BigDecimal valorDiaria) { this.valorDiaria = valorDiaria; }

    public int getDias() { return dias; }
    public void setDias(int dias) { this.dias = dias; }

    public Locacao getLocacao() { return locacao; }
    public void setLocacao(Locacao locacao) { this.locacao = locacao; }

    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
}