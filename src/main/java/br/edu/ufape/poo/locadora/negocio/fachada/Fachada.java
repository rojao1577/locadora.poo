package br.edu.ufape.poo.locadora.negocio.fachada;

import java.util.List;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaPossuiVeiculosException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufape.poo.locadora.negocio.basica.Categoria;
import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;
import br.edu.ufape.poo.locadora.negocio.cadastro.CadastroCategoria;
import br.edu.ufape.poo.locadora.negocio.cadastro.CadastroVeiculo;

@Service
public class Fachada {

    @Autowired
    private CadastroCategoria cadastroCategoria;

    @Autowired
    private CadastroVeiculo cadastroVeiculo;

    // CATEGORIA

    public Categoria cadastrarCategoria(Categoria categoria) {
        return cadastroCategoria.cadastrarCategoria(categoria);
    }

    public List<Categoria> listarCategorias() {
        return cadastroCategoria.listarCategorias();
    }

    public Optional<Categoria> buscarCategoriaPorId(Long id) {
        return cadastroCategoria.buscarCategoriaPorId(id);
    }
    
    public void removerCategoria(Long id) {

        Optional<Categoria> categoria =
            cadastroCategoria.buscarCategoriaPorId(id);

        if (categoria.isPresent() &&
            cadastroVeiculo.existeVeiculoNaCategoria(categoria.get())) {

            throw new CategoriaPossuiVeiculosException(
                "Não é possível remover uma categoria que possui veículos."
            );
        }

        cadastroCategoria.removerCategoria(id);
    }

    // VEICULO

    public Veiculo cadastrarVeiculo(Veiculo veiculo) {
        return cadastroVeiculo.cadastrarVeiculo(veiculo);
    }

    public List<Veiculo> listarVeiculos() {
        return cadastroVeiculo.listarVeiculos();
    }

    public Optional<Veiculo> buscarVeiculoPorId(Long id) {
        return cadastroVeiculo.buscarVeiculoPorId(id);
    }

    public void removerVeiculo(Long id) {
        cadastroVeiculo.removerVeiculo(id);
    }
}