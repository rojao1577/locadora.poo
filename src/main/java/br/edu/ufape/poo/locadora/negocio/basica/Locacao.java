package br.edu.ufape.poo.locadora.negocio.basica;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class Locacao {

    private Long id;
    private LocalDate dataLocacao;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private BigDecimal valorTotal;

    private Cliente cliente;
    private Funcionario funcionario;
    private List<Pagamento> pagamentos = new ArrayList<>();

    public Locacao (){
        super ();
        this.valorTotal = new BigDecimal("0.0");

    }
    public void registrar (){
        this.dataLocacao = LocalDate.now();

    }

    public  BigDecimal calcularMulta(){
        BigDecimal multa = new BigDecimal("0.0");

        if (dataDevolucaoReal != null && dataDevolucaoPrevista != null ){
            if (dataDevolucaoPrevista.isAfter(dataDevolucaoPrevista)){
                long dias = ChronoUnit.DAYS.between(dataDevolucaoPrevista, dataDevolucaoReal);
                BigDecimal diasAtraso = new BigDecimal(dias);
                BigDecimal valorMultaPorDia = new BigDecimal("50.0");

                multa = valorMultaPorDia.multiply(diasAtraso);

            }
        }
        return multa;
    }

    public void finalizarLocacao(){
        this.dataDevolucaoReal = LocalDate.now();
        BigDecimal subtotal = new BigDecimal("0.0");

        BigDecimal multa = calcularMulta();
        this.valorTotal = subtotal.add(multa);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(LocalDate dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }
}
