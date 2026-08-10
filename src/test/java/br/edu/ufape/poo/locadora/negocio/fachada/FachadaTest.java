package br.edu.ufape.poo.locadora.negocio.fachada;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.ufape.poo.locadora.negocio.basica.Categoria;
import br.edu.ufape.poo.locadora.negocio.cadastro.CadastroCategoria;
import br.edu.ufape.poo.locadora.negocio.cadastro.CadastroVeiculo;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaPossuiVeiculosException;

@ExtendWith(MockitoExtension.class)
class FachadaTest {

    @Mock
    private CadastroCategoria cadastroCategoria;

    @Mock
    private CadastroVeiculo cadastroVeiculo;

    @InjectMocks
    private Fachada fachada;

    @Test
    void deveRecusarRemocaoDeCategoriaQuePossuiVeiculos() {

        Categoria categoria = new Categoria(
            "Hatch",
            new BigDecimal("100")
        );

        when(cadastroCategoria.buscarCategoriaPorId(1L))
            .thenReturn(Optional.of(categoria));

        when(cadastroVeiculo.existeVeiculoNaCategoria(categoria))
            .thenReturn(true);

        assertThrows(
            CategoriaPossuiVeiculosException.class,
            () -> fachada.removerCategoria(1L)
        );
    }
    
    @Test
    void deveRemoverCategoriaSemVeiculos() {

        Categoria categoria = new Categoria(
            "Hatch",
            new BigDecimal("100")
        );

        when(cadastroCategoria.buscarCategoriaPorId(1L))
            .thenReturn(Optional.of(categoria));

        when(cadastroVeiculo.existeVeiculoNaCategoria(categoria))
            .thenReturn(false);

        fachada.removerCategoria(1L);

        verify(cadastroCategoria).removerCategoria(1L);
    }
}