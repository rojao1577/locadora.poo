package br.edu.ufape.poo.locadora.negocio.cadastro;

import java.util.List;
import java.util.Optional;

import br.edu.ufape.poo.locadora.negocio.basica.Categoria;

public interface InterfaceCadastroCategoria {

    <S extends Categoria> S cadastrarCategoria(S entity);

    List<Categoria> listarCategorias();

    Optional<Categoria> buscarCategoriaPorId(Long id);

    void removerCategoria(Long id);
}