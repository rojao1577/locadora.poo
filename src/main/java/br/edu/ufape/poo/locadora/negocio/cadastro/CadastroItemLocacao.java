package br.edu.ufape.poo.locadora.negocio.cadastro;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufape.poo.locadora.dados.ItemLocacaoRepository;
import br.edu.ufape.poo.locadora.negocio.basica.ItemLocacao;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.*;

@Service
public class CadastroItemLocacao implements InterfaceCadastroItemLocacao {

    @Autowired
    private ItemLocacaoRepository itemLocacaoRepository;

    @Override
    public ItemLocacao cadastrarItemLocacao(ItemLocacao itemLocacao) {

        if (itemLocacao.getVeiculo() == null) {
            throw new VeiculoItemObrigatorioException("O item de locação deve possuir um veículo associado.");
        }

        if (itemLocacao.getValorDiaria() == null || itemLocacao.getValorDiaria().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorDiariaInvalidoException("O valor da diária deve ser maior que zero.");
        }

        if (itemLocacao.getDias() <= 0) {
            throw new QuantidadeDiasInvalidaException("A quantidade de dias deve ser maior que zero.");
        }

        return itemLocacaoRepository.save(itemLocacao);
    }

    @Override
    public List<ItemLocacao> listarItensLocacao() {
        return itemLocacaoRepository.findAll();
    }

    @Override
    public ItemLocacao buscarItemLocacaoPorId(Long id) {
        return itemLocacaoRepository.findById(id).orElseThrow(
                () -> new ItemLocacaoNaoEncontradoException("Item de locação não encontrado.")
        );
    }

    @Override
    public void removerItemLocacao(Long id) {
        if (itemLocacaoRepository.findById(id).isEmpty()) {
            throw new ItemLocacaoNaoEncontradoException("Não é possível remover: Item de locação não encontrado.");
        }
        itemLocacaoRepository.deleteById(id);
    }
}