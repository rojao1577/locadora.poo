package br.edu.ufape.poo.locadora.negocio.cadastro;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.ufape.poo.locadora.dados.CategoriaRepository;
import br.edu.ufape.poo.locadora.negocio.basica.Categoria;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaJaExisteException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaNaoEncontradaException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.NomeCategoriaInvalidoException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.ValorDiariaInvalidoException;

@ExtendWith(MockitoExtension.class)
class CadastroCategoriaTest {

    @Mock
    private CategoriaRepository repositorioCategoria;

    @InjectMocks
    private CadastroCategoria cadastroCategoria;

    @Test
    void deveRecusarCategoriaComValorDiariaNegativo() {

        Categoria categoria = new Categoria(
            "Hatch",
            new BigDecimal("-100")
        );

        assertThrows(
            ValorDiariaInvalidoException.class,
            () -> cadastroCategoria.cadastrarCategoria(categoria)
        );
    }

    @Test
    void deveCadastrarCategoriaComValorDiariaValido() {

        Categoria categoria = new Categoria(
            "Hatch",
            new BigDecimal("100")
        );

        cadastroCategoria.cadastrarCategoria(categoria);
    }

    @Test
    void deveRecusarCategoriaComNomeDuplicado() {

        Categoria categoriaExistente = new Categoria(
            "Hatch",
            new BigDecimal("100")
        );

        when(repositorioCategoria.findByNome("Hatch"))
            .thenReturn(Optional.of(categoriaExistente));

        Categoria novaCategoria = new Categoria(
            "Hatch",
            new BigDecimal("150")
        );

        assertThrows(
            CategoriaJaExisteException.class,
            () -> cadastroCategoria.cadastrarCategoria(novaCategoria)
        );
    }
    
    @Test
    void deveRecusarRemocaoDeCategoriaInexistente() {

        when(repositorioCategoria.findById(1L))
            .thenReturn(Optional.empty());

        assertThrows(
            CategoriaNaoEncontradaException.class,
            () -> cadastroCategoria.removerCategoria(1L)
        );
    }
    @Test
    void deveRecusarCategoriaSemNome() {

        Categoria categoria = new Categoria(
            "",
            new BigDecimal("100")
        );

        assertThrows(
            NomeCategoriaInvalidoException.class,
            () -> cadastroCategoria.cadastrarCategoria(categoria)
        );
    }
}