package br.edu.ufape.poo.locadora.dados;

import br.edu.ufape.poo.locadora.negocio.basica.ItemLocacao;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface ItemLocacaoRepository extends JpaRepository<ItemLocacao, Long> {
    List<ItemLocacao> findByLocacaoId(Long locacaoId);
}