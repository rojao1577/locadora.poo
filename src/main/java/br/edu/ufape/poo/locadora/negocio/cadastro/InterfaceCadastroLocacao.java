package br.edu.ufape.poo.locadora.negocio.cadastro;

import br.edu.ufape.poo.locadora.negocio.basica.Locacao;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.ClienteInadimplenteException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.VeiculoIndisponivelException;

import java.time.LocalDate;
import java.util.List;

public interface InterfaceCadastroLocacao {

    Locacao registrarLocacao(Locacao locacao) throws ClienteInadimplenteException, VeiculoIndisponivelException;

    Locacao finalizarLocacao(Long idLocacao, LocalDate dataDevolucao);

    Locacao buscarLocacao(Long id);

    List<Locacao> listarLocacoes();

    void removerLocacao(Long id);
}