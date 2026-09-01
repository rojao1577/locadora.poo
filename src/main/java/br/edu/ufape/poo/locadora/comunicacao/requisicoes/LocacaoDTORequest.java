package br.edu.ufape.poo.locadora.comunicacao.requisicoes;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class LocacaoDTORequest {

    @NotNull(message = "A data de locação (retirada) é obrigatória")
    private LocalDate dataLocacao;

    @NotNull(message = "A data de devolução prevista é obrigatória")
    @FutureOrPresent(message = "A data de devolução prevista não pode estar no passado")
    private LocalDate dataDevolucaoPrevista;

    @NotNull(message = "O ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "O ID do funcionário é obrigatório")
    private Long funcionarioId;

    @NotEmpty(message = "A locação deve conter ao menos o ID de um veículo")
    private List<Long> veiculosIds;

    public LocalDate getDataLocacao() { return dataLocacao; }
    public void setDataLocacao(LocalDate dataLocacao) { this.dataLocacao = dataLocacao; }

    public LocalDate getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }

    public List<Long> getVeiculosIds() { return veiculosIds; }
    public void setVeiculosIds(List<Long> veiculosIds) { this.veiculosIds = veiculosIds; }
}