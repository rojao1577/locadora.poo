package br.edu.ufape.poo.locadora.negocio.fachada;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import br.edu.ufape.poo.locadora.negocio.basica.StatusVeiculo;
import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.VeiculoNaoEncontradoException;
import br.edu.ufape.poo.locadora.negocio.basica.Categoria;
import br.edu.ufape.poo.locadora.negocio.cadastro.CadastroCategoria;
import br.edu.ufape.poo.locadora.negocio.cadastro.CadastroVeiculo;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaPossuiVeiculosException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CpfInvalidoException;
import br.edu.ufape.poo.locadora.negocio.basica.Cliente;
import br.edu.ufape.poo.locadora.negocio.cadastro.CadastroCliente;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.RegistroDuplicadoException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.RegistroInexistenteException;

@ExtendWith(MockitoExtension.class)
class FachadaTest {

	@Mock
	private CadastroCliente cadastroCliente;
	
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
    
    @Test
    void deveEscolherVeiculoDisponivelQuandoExisteNaCategoria() {

        Categoria categoria = new Categoria("Hatch", new BigDecimal("100"));
        Veiculo veiculo = new Veiculo("TST0001", "Modelo Teste", "Marca Teste", 2024,
            StatusVeiculo.DISPONIVEL, categoria);

        when(cadastroCategoria.buscarCategoriaPorId(1L))
            .thenReturn(Optional.of(categoria));

        when(cadastroVeiculo.listarVeiculosDisponiveisPorCategoria(categoria))
            .thenReturn(List.of(veiculo));

        Veiculo escolhido = fachada.escolherVeiculoDisponivelPorCategoria(1L);

        assertEquals("TST0001", escolhido.getPlaca());
    }

    @Test
    void deveLancarExcecaoQuandoNaoHaVeiculoDisponivelNaCategoria() {

        Categoria categoria = new Categoria("Hatch", new BigDecimal("100"));

        when(cadastroCategoria.buscarCategoriaPorId(1L))
            .thenReturn(Optional.of(categoria));

        when(cadastroVeiculo.listarVeiculosDisponiveisPorCategoria(categoria))
            .thenReturn(List.of());

        assertThrows(
            VeiculoNaoEncontradoException.class,
            () -> fachada.escolherVeiculoDisponivelPorCategoria(1L)
        );
    }
    @Test
    void deveSalvarClienteComSucesso() throws RegistroDuplicadoException, CpfInvalidoException {

        Cliente cliente = new Cliente("João", "111.111.111-11", "81999999999",
            "Rua X", "joao@email.com", 600);

        when(cadastroCliente.salvarCliente(cliente))
            .thenReturn(cliente);

        Cliente salvo = fachada.salvarCliente(cliente);

        assertEquals("João", salvo.getNome());
        verify(cadastroCliente).salvarCliente(cliente);
    }

    @Test
    void deveLancarExcecaoAoSalvarClienteComCpfDuplicado() throws RegistroDuplicadoException, CpfInvalidoException {

        Cliente cliente = new Cliente("Maria", "222.222.222-22", "81988887777",
            "Rua Y", "maria@email.com", 500);

        when(cadastroCliente.salvarCliente(cliente))
            .thenThrow(new RegistroDuplicadoException("CPF já cadastrado."));

        assertThrows(RegistroDuplicadoException.class, () -> {
            fachada.salvarCliente(cliente);
        });
    }

    @Test
    void deveProcurarClientePorCpfComSucesso() throws RegistroInexistenteException {

        Cliente cliente = new Cliente("Pedro", "333.333.333-33", "81977776666",
            "Rua Z", "pedro@email.com", 700);

        when(cadastroCliente.procurarClientePorCpf("333.333.333-33"))
            .thenReturn(cliente);

        Cliente encontrado = fachada.procurarClientePorCpf("333.333.333-33");

        assertEquals("Pedro", encontrado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExiste() throws RegistroInexistenteException {

        when(cadastroCliente.procurarClientePorCpf("000.000.000-00"))
            .thenThrow(new RegistroInexistenteException("Cliente não encontrado."));

        assertThrows(RegistroInexistenteException.class, () -> {
            fachada.procurarClientePorCpf("000.000.000-00");
        });
    }
}