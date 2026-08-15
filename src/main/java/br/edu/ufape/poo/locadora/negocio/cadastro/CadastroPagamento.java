package br.edu.ufape.poo.locadora.negocio.cadastro;

import java.math.BigDecimal;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufape.poo.locadora.dados.PagamentoRepository;
import br.edu.ufape.poo.locadora.negocio.basica.Pagamento;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.*;

@Service
public class CadastroPagamento implements InterfaceCadastroPagamento {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Override
    public Pagamento cadastrarPagamento(Pagamento pagamento) {

        if (pagamento.getValor() == null || pagamento.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorPagamentoInvalidoException("O valor do pagamento deve ser maior que zero.");
        }

        if (pagamento.getFormaPagamento() == null || pagamento.getFormaPagamento().isBlank()) {
            throw new FormaPagamentoInvalidaException("A forma de pagamento deve ser informada.");
        }

        if (pagamento.getDataPagamento() == null) {
            throw new DataPagamentoInvalidaException("A data do pagamento não pode ser nula.");
        }

        if (pagamento.getLocacao() == null) {
            throw new LocacaoPagamentoObrigatoriaException("O pagamento deve estar vinculado a uma locação.");
        }

        return pagamentoRepository.save(pagamento);
    }

    @Override
    public List<Pagamento> listarPagamentos() {
        return pagamentoRepository.findAll();
    }

    @Override
    public Pagamento buscarPagamentoPorId(Long id) {
        return pagamentoRepository.findById(id).orElseThrow(
                () -> new PagamentoNaoEncontradoException("Pagamento não encontrado.")
        );
    }

    @Override
    public void removerPagamento(Long id) {
        if (pagamentoRepository.findById(id).isEmpty()) {
            throw new PagamentoNaoEncontradoException("Não é possível remover: Pagamento não encontrado.");
        }
        pagamentoRepository.deleteById(id);
    }
}