package br.edu.ufape.poo.locadora.negocio.basica;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pagamento {

    private Long id;
    private BigDecimal valor;
    private LocalDate dataPagamento;
    private String formaPagamento;

    public Pagamento (){
        super ();
    }

    public Pagamento (BigDecimal valor, LocalDate dataPagamento, String formaPagamento){
        super ();
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.formaPagamento = formaPagamento;
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
}
