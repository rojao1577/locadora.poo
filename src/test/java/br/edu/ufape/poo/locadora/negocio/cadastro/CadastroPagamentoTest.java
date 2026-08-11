package br.edu.ufape.poo.locadora.negocio.cadastro;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.ufape.poo.locadora.dados.PagamentoRepository;
import br.edu.ufape.poo.locadora.negocio.basica.Pagamento;
import br.edu.ufape.poo.locadora.negocio.basica.Locacao;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.*;

@ExtendWith(MockitoExtension.class)
public class CadastroPagamentoTest {

    @Mock
    private PagamentoRepository repositorio;

    @InjectMocks
    private CadastroPagamento cadastroPagamento;

    @Test
    public void deveCadastrarComSucesso() {
        Pagamento p = new Pagamento();
        p.setValor(new BigDecimal("150.00"));
        p.setFormaPagamento("PIX");
        p.setDataPagamento(LocalDate.now());
        p.setLocacao(new Locacao());

        when(repositorio.save(p)).thenReturn(p);

        assertDoesNotThrow(() -> cadastroPagamento.cadastrarPagamento(p));
        verify(repositorio, times(1)).save(p);
    }

    @Test
    public void deveLancarExcecaoValorZeradoOuNegativo() {
        Pagamento p = new Pagamento();
        p.setValor(BigDecimal.ZERO);
        p.setFormaPagamento("Cartão");
        p.setDataPagamento(LocalDate.now());
        p.setLocacao(new Locacao());

        assertThrows(ValorPagamentoInvalidoException.class, () -> cadastroPagamento.cadastrarPagamento(p));
    }

    @Test
    public void deveLancarExcecaoFormaPagamentoVazia() {
        Pagamento p = new Pagamento();
        p.setValor(new BigDecimal("50.00"));
        p.setFormaPagamento("");
        p.setDataPagamento(LocalDate.now());
        p.setLocacao(new Locacao());

        assertThrows(FormaPagamentoInvalidaException.class, () -> cadastroPagamento.cadastrarPagamento(p));
    }

    @Test
    public void deveLancarExcecaoLocacaoNula() {
        Pagamento p = new Pagamento();
        p.setValor(new BigDecimal("50.00"));
        p.setFormaPagamento("Dinheiro");
        p.setDataPagamento(LocalDate.now());
        p.setLocacao(null);

        assertThrows(LocacaoPagamentoObrigatoriaException.class, () -> cadastroPagamento.cadastrarPagamento(p));
    }

    @Test
    public void deveLancarExcecaoRemoverInexistente() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PagamentoNaoEncontradoException.class, () -> cadastroPagamento.removerPagamento(99L));
    }
}