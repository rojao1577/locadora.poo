package br.edu.ufape.poo.locadora.negocio.basica;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataLocacao;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private BigDecimal valorTotal;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @OneToMany(mappedBy = "locacao", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos = new ArrayList<>();

    @OneToMany(mappedBy = "locacao", cascade = CascadeType.ALL)
    private List<ItemLocacao> itens = new ArrayList<>();

    public Locacao() {
        super();
        this.valorTotal = new BigDecimal("0.0");
    }

    public BigDecimal calcularMulta() {
        BigDecimal multa = new BigDecimal("0.0");
        if (dataDevolucaoReal != null && dataDevolucaoPrevista != null) {
            if (dataDevolucaoReal.isAfter(dataDevolucaoPrevista)) {
                long dias = ChronoUnit.DAYS.between(dataDevolucaoPrevista, dataDevolucaoReal);
                BigDecimal diasAtraso = new BigDecimal(dias);
                BigDecimal valorMultaPorDia = new BigDecimal("50.0");
                multa = valorMultaPorDia.multiply(diasAtraso);
            }
        }
        return multa;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDataLocacao() { return dataLocacao; }
    public void setDataLocacao(LocalDate dataLocacao) { this.dataLocacao = dataLocacao; }

    public LocalDate getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }

    public LocalDate getDataDevolucaoReal() { return dataDevolucaoReal; }
    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) { this.dataDevolucaoReal = dataDevolucaoReal; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public List<Pagamento> getPagamentos() { return pagamentos; }
    public void setPagamentos(List<Pagamento> pagamentos) { this.pagamentos = pagamentos; }

    public List<ItemLocacao> getItens() { return itens; }
    public void setItens(List<ItemLocacao> itens) { this.itens = itens; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Locacao)) return false;
        Locacao locacao = (Locacao) o;
        return id != null && id.equals(locacao.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Locacao{" +
                "id=" + id +
                ", dataLocacao=" + dataLocacao +
                ", valorTotal=" + valorTotal +
                '}';
    }
}