package br.edu.ufape.poo.locadora.conversor;

import org.springframework.stereotype.Component;

import br.edu.ufape.poo.locadora.comunicacao.requisicoes.ClienteDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.ClienteDTOResponse;
import br.edu.ufape.poo.locadora.negocio.basica.Cliente;

@Component
public class ClienteConversor {
	public Cliente requestToEntity(ClienteDTORequest dto) {
		return new Cliente(
				dto.nome(),
				dto.cpf(),
				dto.telefone(),
				dto.endereco(),
				dto.email(),
				dto.scoreCredito()
			);
	}
	public ClienteDTOResponse entityToResponse(Cliente cliente) {
		return new ClienteDTOResponse(
				cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEndereco(),
                cliente.getEmail(),
                cliente.getScoreCredito()
                );
	}
	
}
