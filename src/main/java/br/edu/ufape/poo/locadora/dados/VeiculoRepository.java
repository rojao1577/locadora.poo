package br.edu.ufape.poo.locadora.dados;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufape.poo.locadora.negocio.basica.Categoria;
import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    boolean existsByCategoria(Categoria categoria);

    Optional<Veiculo> findByPlaca(String placa);
}