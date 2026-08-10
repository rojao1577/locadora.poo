package br.edu.ufape.poo.locadora.negocio.cadastro;

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

import br.edu.ufape.poo.locadora.dados.VeiculoRepository;
import br.edu.ufape.poo.locadora.negocio.basica.Categoria;
import br.edu.ufape.poo.locadora.negocio.basica.StatusVeiculo;
import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaVeiculoObrigatoriaException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.PlacaVeiculoInvalidaException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.StatusVeiculoObrigatorioException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.VeiculoJaExisteException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.VeiculoNaoEncontradoException;

@ExtendWith(MockitoExtension.class)
class CadastroVeiculoTest {

    @Mock
    private VeiculoRepository repositorioVeiculo;

    @InjectMocks
    private CadastroVeiculo cadastroVeiculo;

    @Test
    void deveRecusarVeiculoSemCategoria() {

        Veiculo veiculo = new Veiculo(
            "ABC1D23",
            "Onix",
            "Chevrolet",
            2025,
            StatusVeiculo.DISPONIVEL,
            null
        );

        assertThrows(
            CategoriaVeiculoObrigatoriaException.class,
            () -> cadastroVeiculo.cadastrarVeiculo(veiculo)
        );
    }

    @Test
    void deveRecusarVeiculoSemStatus() {

        Categoria categoria = new Categoria(
            "Hatch",
            new BigDecimal("100")
        );

        Veiculo veiculo = new Veiculo(
            "ABC1D23",
            "Onix",
            "Chevrolet",
            2025,
            null,
            categoria
        );

        assertThrows(
            StatusVeiculoObrigatorioException.class,
            () -> cadastroVeiculo.cadastrarVeiculo(veiculo)
        );
    }

    @Test
    void deveRecusarVeiculoComPlacaDuplicada() {

        Categoria categoria = new Categoria(
            "Hatch",
            new BigDecimal("100")
        );

        Veiculo veiculoExistente = new Veiculo(
            "ABC1D23",
            "Onix",
            "Chevrolet",
            2025,
            StatusVeiculo.DISPONIVEL,
            categoria
        );

        when(repositorioVeiculo.findByPlaca("ABC1D23"))
            .thenReturn(Optional.of(veiculoExistente));

        Veiculo novoVeiculo = new Veiculo(
            "ABC1D23",
            "Polo",
            "Volkswagen",
            2026,
            StatusVeiculo.DISPONIVEL,
            categoria
        );

        assertThrows(
            VeiculoJaExisteException.class,
            () -> cadastroVeiculo.cadastrarVeiculo(novoVeiculo)
        );
    }

    @Test
    void deveCadastrarVeiculoValido() {

        Categoria categoria = new Categoria(
            "Hatch",
            new BigDecimal("100")
        );

        Veiculo veiculo = new Veiculo(
            "ABC1D23",
            "Onix",
            "Chevrolet",
            2025,
            StatusVeiculo.DISPONIVEL,
            categoria
        );

        when(repositorioVeiculo.findByPlaca("ABC1D23"))
            .thenReturn(Optional.empty());

        cadastroVeiculo.cadastrarVeiculo(veiculo);

        verify(repositorioVeiculo).save(veiculo);
    }

    @Test
    void deveRecusarRemocaoDeVeiculoInexistente() {

        when(repositorioVeiculo.findById(1L))
            .thenReturn(Optional.empty());

        assertThrows(
            VeiculoNaoEncontradoException.class,
            () -> cadastroVeiculo.removerVeiculo(1L)
        );
    }
    @Test
    void deveRecusarVeiculoSemPlaca() {

        Categoria categoria = new Categoria(
            "Hatch",
            new BigDecimal("100")
        );

        Veiculo veiculo = new Veiculo(
            "",
            "Onix",
            "Chevrolet",
            2025,
            StatusVeiculo.DISPONIVEL,
            categoria
        );

        assertThrows(
            PlacaVeiculoInvalidaException.class,
            () -> cadastroVeiculo.cadastrarVeiculo(veiculo)
        );
    }
}