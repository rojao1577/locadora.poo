package br.edu.ufape.poo.locadora.dados;

import br.edu.ufape.poo.locadora.negocio.basica.ItemLocacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemLocacaoRepository extends JpaRepository<ItemLocacao, Long> {
    List<ItemLocacao> findByLocacaoId(Long locacaoId);
}