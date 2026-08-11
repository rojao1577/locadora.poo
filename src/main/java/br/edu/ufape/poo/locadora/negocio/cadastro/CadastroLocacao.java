package br.edu.ufape.poo.locadora.negocio.cadastro;

import br.edu.ufape.poo.locadora.dados.LocacaoRepository;
import br.edu.ufape.poo.locadora.negocio.basica.ItemLocacao;
import br.edu.ufape.poo.locadora.negocio.basica.Locacao;
import br.edu.ufape.poo.locadora.negocio.basica.StatusVeiculo;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.ClienteInadimplenteException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.VeiculoIndisponivelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CadastroLocacao implements InterfaceCadastroLocacao {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Override
    public Locacao registrarLocacao(Locacao locacao) throws ClienteInadimplenteException, VeiculoIndisponivelException {
        if (locacao.getCliente() != null && locacao.getCliente().verificarInadimplencia()) {
            throw new ClienteInadimplenteException(locacao.getCliente().getCpf());
        }

        if (locacao.getItens() != null) {
            for (ItemLocacao item : locacao.getItens()) {
                if (item.getVeiculo().getStatus() != StatusVeiculo.DISPONIVEL) {
                    throw new VeiculoIndisponivelException(item.getVeiculo().getPlaca());
                }
                item.getVeiculo().setStatus(StatusVeiculo.ALUGADO);
                item.setLocacao(locacao);
            }
        }

        locacao.setDataLocacao(LocalDate.now());

        return locacaoRepository.save(locacao);
    }

    @Override
    public Locacao finalizarLocacao(Long idLocacao, LocalDate dataDevolucao) {
        Locacao locacao = buscarLocacao(idLocacao);

        locacao.setDataDevolucaoReal(dataDevolucao);

        BigDecimal multa = locacao.calcularMulta();
        if (locacao.getValorTotal() == null) {
            locacao.setValorTotal(new BigDecimal("0.0"));
        }
        locacao.setValorTotal(locacao.getValorTotal().add(multa));

        if (locacao.getItens() != null) {
            for (ItemLocacao item : locacao.getItens()) {
                item.getVeiculo().setStatus(StatusVeiculo.DISPONIVEL);
            }
        }

        return locacaoRepository.save(locacao);
    }

    @Override
    public Locacao buscarLocacao(Long id) {
        return locacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locação não encontrada com o ID: " + id));
    }

    @Override
    public List<Locacao> listarLocacoes() {
        return locacaoRepository.findAll();
    }
}