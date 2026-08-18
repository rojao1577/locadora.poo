package br.edu.ufape.poo.locadora.conversor;

import br.edu.ufape.poo.locadora.comunicacao.requisicoes.PagamentoDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.PagamentoDTOResponse;
import br.edu.ufape.poo.locadora.negocio.basica.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class PagamentoConversor {

    public Pagamento requestToEntity(PagamentoDTORequest dto) {
        Pagamento entity = new Pagamento();
        entity.setValor(dto.valor());
        entity.setFormaPagamento(dto.formaPagamento());
        entity.setDataPagamento(dto.dataPagamento());
        return entity;
    }

    public PagamentoDTOResponse entityToResponse(Pagamento entity) {
        Long idLocacao = (entity.getLocacao() != null) ? entity.getLocacao().getId() : null;
        return new PagamentoDTOResponse(
                entity.getId(),
                entity.getValor(),
                entity.getFormaPagamento(),
                entity.getDataPagamento(),
                idLocacao
        );
    }
}