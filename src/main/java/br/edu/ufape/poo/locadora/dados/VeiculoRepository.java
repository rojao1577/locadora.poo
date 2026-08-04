package br.edu.ufape.poo.locadora.dados;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}