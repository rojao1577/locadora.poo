package br.edu.ufape.poo.locadora.conversor;

import br.edu.ufape.poo.locadora.comunicacao.requisicoes.LocacaoDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.LocacaoDTOResponse;
import br.edu.ufape.poo.locadora.negocio.basica.ItemLocacao;
import br.edu.ufape.poo.locadora.negocio.basica.Locacao;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocacaoConversor {

    public Locacao toEntity(LocacaoDTORequest request) {
        Locacao locacao = new Locacao();

        locacao.setDataLocacao(request.getDataLocacao());
        locacao.setDataDevolucaoPrevista(request.getDataDevolucaoPrevista());

        return locacao;
    }

    public LocacaoDTOResponse toResponse(Locacao locacao) {
        LocacaoDTOResponse response = new LocacaoDTOResponse();
        response.setId(locacao.getId());
        response.setDataLocacao(locacao.getDataLocacao());
        response.setDataDevolucaoPrevista(locacao.getDataDevolucaoPrevista());
        response.setDataDevolucaoReal(locacao.getDataDevolucaoReal());
        response.setValorTotal(locacao.getValorTotal());

        if (locacao.getCliente() != null) response.setClienteId(locacao.getCliente().getId());
        if (locacao.getFuncionario() != null) response.setFuncionarioId(locacao.getFuncionario().getId());

        if (locacao.getItens() != null) {
            List<Long> veiculosIds = new ArrayList<>();
            for (ItemLocacao item : locacao.getItens()) {
                if (item.getVeiculo() != null) {
                    veiculosIds.add(item.getVeiculo().getId());
                }
            }
            response.setVeiculosIds(veiculosIds);
        }

        return response;
    }
}