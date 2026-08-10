package br.edu.ufape.poo.locadora.negocio.cadastro;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufape.poo.locadora.dados.VeiculoRepository;
import br.edu.ufape.poo.locadora.negocio.basica.StatusVeiculo;
import br.edu.ufape.poo.locadora.negocio.basica.Categoria;
import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaVeiculoObrigatoriaException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.PlacaVeiculoInvalidaException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.StatusVeiculoObrigatorioException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.VeiculoJaExisteException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.VeiculoNaoEncontradoException;

@Service
public class CadastroVeiculo implements InterfaceCadastroVeiculo {

    @Autowired
    private VeiculoRepository repositorioVeiculo;

    @Override
    public <S extends Veiculo> S cadastrarVeiculo(S entity) {

        if (entity.getCategoria() == null) {
            throw new CategoriaVeiculoObrigatoriaException(
                "O veículo deve possuir uma categoria."
            );
        }

        if (entity.getStatus() == null) {
            throw new StatusVeiculoObrigatorioException(
                "O veículo deve possuir um status."
            );
        }

        if (entity.getPlaca() == null || entity.getPlaca().isBlank()) {
            throw new PlacaVeiculoInvalidaException(
                "A placa do veículo deve ser informada."
            );
        }

        if (repositorioVeiculo.findByPlaca(entity.getPlaca()).isPresent()) {
            throw new VeiculoJaExisteException(
                "Já existe um veículo com essa placa."
            );
        }

        return repositorioVeiculo.save(entity);
    }

    @Override
    public List<Veiculo> listarVeiculos() {
        return repositorioVeiculo.findAll();
    }

    @Override
    public Optional<Veiculo> buscarVeiculoPorId(Long id) {
        return repositorioVeiculo.findById(id);
    }

    @Override
    public void removerVeiculo(Long id) {

        if (repositorioVeiculo.findById(id).isEmpty()) {
            throw new VeiculoNaoEncontradoException(
                "Veículo não encontrado."
            );
        }

        repositorioVeiculo.deleteById(id);
    }
    @Override
    public boolean existeVeiculoNaCategoria(Categoria categoria) {
        return repositorioVeiculo.existsByCategoria(categoria);
    }
    
    @Override
    public List<Veiculo> listarVeiculosDisponiveisPorCategoria(Categoria categoria) {
        return repositorioVeiculo.findByCategoriaAndStatus(categoria, StatusVeiculo.DISPONIVEL);
    }
}