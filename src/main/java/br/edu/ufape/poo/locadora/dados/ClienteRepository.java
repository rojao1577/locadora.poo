package br.edu.ufape.poo.locadora.dados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufape.poo.locadora.negocio.basica.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Cliente findByCpf(String cpf);
}
