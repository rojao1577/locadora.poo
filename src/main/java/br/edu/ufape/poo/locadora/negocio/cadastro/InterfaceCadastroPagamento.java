package br.edu.ufape.poo.locadora.negocio.cadastro;

import br.edu.ufape.poo.locadora.negocio.basica.Pagamento;
import java.util.List;

public interface InterfaceCadastroPagamento {

    Pagamento cadastrarPagamento(Pagamento pagamento);

    List<Pagamento> listarPagamentos();

    Pagamento buscarPagamentoPorId(Long id);

    void removerPagamento(Long id);
}