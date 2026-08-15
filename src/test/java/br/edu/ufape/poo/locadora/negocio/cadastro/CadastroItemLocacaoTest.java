package br.edu.ufape.poo.locadora.negocio.cadastro;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.ufape.poo.locadora.dados.ItemLocacaoRepository;
import br.edu.ufape.poo.locadora.negocio.basica.ItemLocacao;
import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.*;

@ExtendWith(MockitoExtension.class)
public class CadastroItemLocacaoTest {

    @Mock
    private ItemLocacaoRepository repositorio;

    @InjectMocks
    private CadastroItemLocacao cadastroItemLocacao;

    @Test
    public void deveCadastrarComSucesso() {
        ItemLocacao item = new ItemLocacao();
        item.setVeiculo(new Veiculo());
        item.setValorDiaria(new BigDecimal("100.00"));
        item.setDias(3);

        when(repositorio.save(item)).thenReturn(item);

        assertDoesNotThrow(() -> cadastroItemLocacao.cadastrarItemLocacao(item));
        verify(repositorio, times(1)).save(item);
    }

    @Test
    public void deveLancarExcecaoVeiculoNulo() {
        ItemLocacao item = new ItemLocacao();
        item.setVeiculo(null);
        item.setValorDiaria(new BigDecimal("100.00"));
        item.setDias(3);

        assertThrows(VeiculoItemObrigatorioException.class, () -> cadastroItemLocacao.cadastrarItemLocacao(item));
    }

    @Test
    public void deveLancarExcecaoValorDiariaInvalido() {
        ItemLocacao item = new ItemLocacao();
        item.setVeiculo(new Veiculo());
        item.setValorDiaria(BigDecimal.ZERO);
        item.setDias(3);

        assertThrows(ValorDiariaInvalidoException.class, () -> cadastroItemLocacao.cadastrarItemLocacao(item));
    }

    @Test
    public void deveLancarExcecaoDiasInvaliados() {
        ItemLocacao item = new ItemLocacao();
        item.setVeiculo(new Veiculo());
        item.setValorDiaria(new BigDecimal("100.00"));
        item.setDias(0);

        assertThrows(QuantidadeDiasInvalidaException.class, () -> cadastroItemLocacao.cadastrarItemLocacao(item));
    }

    @Test
    public void deveLancarExcecaoRemoverInexistente() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ItemLocacaoNaoEncontradoException.class, () -> cadastroItemLocacao.removerItemLocacao(99L));
    }
}