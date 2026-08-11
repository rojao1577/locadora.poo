package br.edu.ufape.poo.locadora.negocio.cadastro;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufape.poo.locadora.dados.CategoriaRepository;
import br.edu.ufape.poo.locadora.negocio.basica.Categoria;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaJaExisteException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaNaoEncontradaException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.NomeCategoriaInvalidoException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.ValorDiariaInvalidoException;


@Service
public class CadastroCategoria implements InterfaceCadastroCategoria {

    @Autowired
    private CategoriaRepository repositorioCategoria;

    @Override
    public <S extends Categoria> S cadastrarCategoria(S entity) {

        if (entity.getValorDiariaBase() == null ||
            entity.getValorDiariaBase().compareTo(BigDecimal.ZERO) < 0) {

            throw new ValorDiariaInvalidoException(
                "O valor da diária base deve ser informado e não pode ser negativo."
            );
        }

        if (entity.getNome() == null || entity.getNome().isBlank()) {
            throw new NomeCategoriaInvalidoException(
                "O nome da categoria deve ser informado."
            );
        }

        if (repositorioCategoria.findByNome(entity.getNome()).isPresent()) {
            throw new CategoriaJaExisteException(
                "Já existe uma categoria com esse nome."
            );
        }

        return repositorioCategoria.save(entity);
    }

    @Override
    public List<Categoria> listarCategorias() {
        return repositorioCategoria.findAll();
    }

    @Override
    public Optional<Categoria> buscarCategoriaPorId(Long id) {
        return repositorioCategoria.findById(id);
    }

    @Override
    public void removerCategoria(Long id) {

        Optional<Categoria> categoria = repositorioCategoria.findById(id);

        if (categoria.isEmpty()) {
            throw new CategoriaNaoEncontradaException(
                "Categoria não encontrada."
            );
        }

        repositorioCategoria.deleteById(id);
    }
}