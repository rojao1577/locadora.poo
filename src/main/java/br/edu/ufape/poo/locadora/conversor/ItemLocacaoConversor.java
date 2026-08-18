package br.edu.ufape.poo.locadora.conversor;

import org.springframework.stereotype.Component;

import br.edu.ufape.poo.locadora.comunicacao.requisicoes.ItemLocacaoDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.ItemLocacaoDTOResponse;
import br.edu.ufape.poo.locadora.negocio.basica.ItemLocacao;
import br.edu.ufape.poo.locadora.negocio.basica.Locacao;
import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;


@Component
public class ItemLocacaoConversor {

	public ItemLocacao requestToEntity(ItemLocacaoDTORequest dto, Locacao locacao,Veiculo veiculo) {
		return new ItemLocacao(
				dto.valorDiaria(),
				dto.dias(),
				locacao,
				veiculo
				);
	}
	
	public ItemLocacaoDTOResponse entityToResponse(ItemLocacao item) {
		return new ItemLocacaoDTOResponse(
				item.getId(),
				item.getValorDiaria(),
				item.getDias(),
				item.getLocacao().getId(),
				item.getVeiculo().getPlaca()
				);
	}
}
