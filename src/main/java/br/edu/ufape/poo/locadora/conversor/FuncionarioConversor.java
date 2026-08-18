package br.edu.ufape.poo.locadora.conversor;

import br.edu.ufape.poo.locadora.comunicacao.requisicoes.FuncionarioDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.FuncionarioDTOResponse;
import br.edu.ufape.poo.locadora.negocio.basica.Funcionario;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioConversor {

    public Funcionario requestToEntity(FuncionarioDTORequest dto) {
        Funcionario entity = new Funcionario();
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());
        return entity;
    }

    public FuncionarioDTOResponse entityToResponse(Funcionario entity) {
        return new FuncionarioDTOResponse(
                entity.getId(),
                entity.getNome(),
                entity.getCpf()
        );
    }
}