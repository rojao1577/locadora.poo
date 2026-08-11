package br.edu.ufape.poo.locadora.negocio.cadastro;

import br.edu.ufape.poo.locadora.negocio.basica.ItemLocacao;
import java.util.List;

public interface InterfaceCadastroItemLocacao {

    ItemLocacao cadastrarItemLocacao(ItemLocacao itemLocacao);

    List<ItemLocacao> listarItensLocacao();

    ItemLocacao buscarItemLocacaoPorId(Long id);

    void removerItemLocacao(Long id);
}