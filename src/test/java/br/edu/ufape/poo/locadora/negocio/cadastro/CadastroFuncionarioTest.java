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

import br.edu.ufape.poo.locadora.dados.FuncionarioRepository;
import br.edu.ufape.poo.locadora.negocio.basica.Funcionario;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.*;

@ExtendWith(MockitoExtension.class)
public class CadastroFuncionarioTest {

    @Mock
    private FuncionarioRepository repositorio;

    @InjectMocks
    private CadastroFuncionario cadastroFuncionario;

    @Test
    public void deveCadastrarComSucesso() {
        Funcionario f = new Funcionario();
        f.setCpf("12345678901");
        f.setCargo("Atendente");
        f.setSalario(new BigDecimal("1500.00"));

        when(repositorio.findByCpf("12345678901")).thenReturn(Optional.empty());
        when(repositorio.save(f)).thenReturn(f);

        assertDoesNotThrow(() -> cadastroFuncionario.cadastrarFuncionario(f));
        verify(repositorio, times(1)).save(f);
    }

    @Test
    public void deveLancarExcecaoCpfInvalido() {
        Funcionario f = new Funcionario();
        f.setCpf("123"); // CPF inválido (menos de 11 dígitos)

        assertThrows(CpfFuncionarioInvalidoException.class, () -> cadastroFuncionario.cadastrarFuncionario(f));
    }

    @Test
    public void deveLancarExcecaoCpfDuplicado() {
        Funcionario f = new Funcionario();
        f.setCpf("12345678901");
        f.setCargo("Gerente");
        f.setSalario(new BigDecimal("3000.00"));

        // Simula que o banco já tem alguém com esse CPF
        when(repositorio.findByCpf("12345678901")).thenReturn(Optional.of(new Funcionario()));

        assertThrows(FuncionarioJaExisteException.class, () -> cadastroFuncionario.cadastrarFuncionario(f));
    }

    @Test
    public void deveLancarExcecaoSalarioNegativo() {
        Funcionario f = new Funcionario();
        f.setCpf("12345678901");
        f.setCargo("Mecânico");
        f.setSalario(new BigDecimal("-100.00"));

        assertThrows(SalarioFuncionarioInvalidoException.class, () -> cadastroFuncionario.cadastrarFuncionario(f));
    }

    @Test
    public void deveLancarExcecaoRemoverInexistente() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThrows(FuncionarioNaoEncontradoException.class, () -> cadastroFuncionario.removerFuncionario(99L));
    }
}