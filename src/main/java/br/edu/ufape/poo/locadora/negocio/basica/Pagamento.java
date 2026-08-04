package br.edu.ufape.poo.locadora.negocio.basica;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pagamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private BigDecimal valor;
    private LocalDate dataPagamento;
    private String formaPagamento;
    
    @ManyToOne
    @JoinColumn(name = "locacao_id")
    private Locacao locacao;

    public Pagamento (){
        super ();
    }

    public Pagamento (BigDecimal valor, LocalDate dataPagamento, String formaPagamento, Locacao locacao){
        super ();
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.formaPagamento = formaPagamento;
        this.locacao = locacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    
    @Override
    public boolean equals(Object o) {
    	if (this == o) return true;
    	if (!(o instanceof Pagamento)) return false;
    	Pagamento pagamento = (Pagamento) o;
    	return id != null && id.equals(pagamento.id);
    }
    
    @Override
    public int hashCode() {
    	return getClass().hashCode();
    }
    
    @Override
    public String toString() {
    	return "Pagamento{" +
    			"id=" + id +
    			", valor=" + valor +
    			", dataPagamento=" + dataPagamento +
    			", formaPagamento=" + formaPagamento + '\'' +
    			'}';
    }
}
