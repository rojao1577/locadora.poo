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
import java.time.temporal.ChronoUnit;
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

        if (locacao.getDataLocacao() == null) {
            locacao.setDataLocacao(LocalDate.now());
        }

        BigDecimal valorTotalCalculado = BigDecimal.ZERO;

        long dias = 1;
        if (locacao.getDataLocacao() != null && locacao.getDataDevolucaoPrevista() != null) {
            dias = ChronoUnit.DAYS.between(locacao.getDataLocacao(), locacao.getDataDevolucaoPrevista());
            if (dias <= 0) {
                dias = 1;
            }
        }

        if (locacao.getItens() != null) {
            for (ItemLocacao item : locacao.getItens()) {
                if (item.getVeiculo().getStatus() != StatusVeiculo.DISPONIVEL) {
                    throw new VeiculoIndisponivelException(item.getVeiculo().getPlaca());
                }
                item.getVeiculo().setStatus(StatusVeiculo.ALUGADO);
                item.setLocacao(locacao);

                BigDecimal diaria = item.getVeiculo().getCategoria().getValorDiariaBase();
                item.setValorDiaria(diaria);
                item.setDias((int) dias);
                BigDecimal totalItem = diaria.multiply(new BigDecimal(dias));
                valorTotalCalculado = valorTotalCalculado.add(totalItem);
            }
        }

        locacao.setValorTotal(valorTotalCalculado);

        return locacaoRepository.save(locacao);
    }

    @Override
    public Locacao finalizarLocacao(Long idLocacao, LocalDate dataDevolucao) {
        Locacao locacao = buscarLocacao(idLocacao);
        locacao.setDataDevolucaoReal(dataDevolucao);

        BigDecimal novoValorTotal = BigDecimal.ZERO;


        long diasReais = 1;
        if (locacao.getDataLocacao() != null && locacao.getDataDevolucaoReal() != null) {
            diasReais = ChronoUnit.DAYS.between(locacao.getDataLocacao(), locacao.getDataDevolucaoReal());
            if (diasReais <= 0) {
                diasReais = 1;
            }
        }

        if (locacao.getItens() != null) {
            for (ItemLocacao item : locacao.getItens()) {
                item.getVeiculo().setStatus(StatusVeiculo.DISPONIVEL);

                BigDecimal diaria = item.getValorDiaria();
                if (diaria == null) {
                    diaria = item.getVeiculo().getCategoria().getValorDiariaBase();
                }

                item.setDias((int) diasReais);


                BigDecimal totalItem = diaria.multiply(new BigDecimal(diasReais));
                novoValorTotal = novoValorTotal.add(totalItem);
            }
        }

        BigDecimal multa = locacao.calcularMulta();

        locacao.setValorTotal(novoValorTotal.add(multa));

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

    @Override
    public void removerLocacao(Long id) {

        Locacao locacao = buscarLocacao(id);

        if (locacao.getItens() != null) {
            for (ItemLocacao item : locacao.getItens()) {
                if (item.getVeiculo() != null) {
                    item.getVeiculo().setStatus(StatusVeiculo.DISPONIVEL);
                }
            }

            locacaoRepository.save(locacao);
        }

        locacaoRepository.deleteById(id);
    }
}