package br.edu.ufape.poo.locadora.comunicacao.resposta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class LocacaoDTOResponse {
    private Long id;
    private LocalDate dataLocacao;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private BigDecimal valorTotal;
    private Long clienteId;
    private Long funcionarioId;
    private List<Long> veiculosIds;

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

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }

    public List<Long> getVeiculosIds() { return veiculosIds; }
    public void setVeiculosIds(List<Long> veiculosIds) { this.veiculosIds = veiculosIds; }
}