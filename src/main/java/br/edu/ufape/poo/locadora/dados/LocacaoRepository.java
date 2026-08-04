package br.edu.ufape.poo.locadora.dados;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufape.poo.locadora.negocio.basica.Locacao;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {

}
