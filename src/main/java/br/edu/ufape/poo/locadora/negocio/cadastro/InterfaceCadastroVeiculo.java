package br.edu.ufape.poo.locadora.negocio.cadastro;

import java.util.List;
import java.util.Optional;

import br.edu.ufape.poo.locadora.negocio.basica.Categoria;
import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;

public interface InterfaceCadastroVeiculo {

    <S extends Veiculo> S cadastrarVeiculo(S entity);

    List<Veiculo> listarVeiculos();

    Optional<Veiculo> buscarVeiculoPorId(Long id);

    void removerVeiculo(Long id);

    boolean existeVeiculoNaCategoria(Categoria categoria);
}