package br.edu.ufape.poo.locadora.conversor;

import org.springframework.stereotype.Component;

import br.edu.ufape.poo.locadora.comunicacao.requisicoes.CategoriaDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.CategoriaDTOResponse;
import br.edu.ufape.poo.locadora.negocio.basica.Categoria;

@Component
public class CategoriaConversor {

    public Categoria requestToEntity(CategoriaDTORequest dto) {

        Categoria categoria = new Categoria();

        categoria.setNome(dto.nome());
        categoria.setValorDiariaBase(dto.valorDiariaBase());

        return categoria;
    }

    public CategoriaDTOResponse entityToResponse(Categoria categoria) {

        return new CategoriaDTOResponse(
            categoria.getId(),
            categoria.getNome(),
            categoria.getValorDiariaBase()
        );
    }
}