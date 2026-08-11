package br.edu.ufape.poo.locadora.negocio.cadastro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ufape.poo.locadora.negocio.basica.Cliente;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CpfInvalidoException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.RegistroDuplicadoException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.RegistroInexistenteException;

@SpringBootTest
class CadastroClienteTest {

	@Autowired
	private CadastroCliente cadastroCliente;
	
	@Test
	void SalvarCliente() throws RegistroDuplicadoException, CpfInvalidoException{
		String cpf = "100.100.100-" + String.format("%02d",System.nanoTime() % 100);
		Cliente cliente = new Cliente("Cliente Teste", cpf, "81900000000", "Rua Teste", "teste@gmail.com", 500);
		
		Cliente salvo = cadastroCliente.salvarCliente(cliente);
		
		assertEquals(cpf, salvo.getCpf());
	}
	@Test
    void deveLancarExcecaoAoSalvarDoisClientesComMesmoCpf() throws RegistroDuplicadoException, CpfInvalidoException {
        String cpf = "200.200.200-" + String.format("%02d",System.nanoTime() % 100);

        Cliente c1 = new Cliente("Cliente 1", cpf, "81900000000",
            "Rua A", "c1@teste.com", 500);
        Cliente c2 = new Cliente("Cliente 2", cpf, "81911111111",
            "Rua B", "c2@teste.com", 600);

        cadastroCliente.salvarCliente(c1);

        assertThrows(RegistroDuplicadoException.class, () -> {
            cadastroCliente.salvarCliente(c2);
        });
    }

    @Test
    void deveRemoverClienteComSucesso() throws RegistroDuplicadoException, RegistroInexistenteException, CpfInvalidoException {
        String cpf = "300.300.300-" + String.format("%02d",System.nanoTime() % 100);
        Cliente cliente = new Cliente("Cliente Remover", cpf, "81900000000",
            "Rua C", "remover@email.com", 500);

        Cliente salvo = cadastroCliente.salvarCliente(cliente);
        cadastroCliente.apagarCliente(salvo);

        assertThrows(RegistroInexistenteException.class, () -> {
            cadastroCliente.procurarClientePorCpf(cpf);
        });
    }

    @Test
    void deveLancarExcecaoAoProcurarClienteInexistente() {
        assertThrows(RegistroInexistenteException.class, () -> {
            cadastroCliente.procurarClientePorCpf("999.999.999-99");
        });
    }

    @Test
    void deveAtualizarClienteComSucesso() throws RegistroDuplicadoException, CpfInvalidoException {
        String cpf = "400.400.400-" + String.format("%02d",System.nanoTime() % 100);
        Cliente cliente = new Cliente("Nome Antigo", cpf, "81900000000",
            "Rua Antiga", "antigo@email.com", 500);

        Cliente salvo = cadastroCliente.salvarCliente(cliente);
        salvo.setNome("Nome Novo");

        Cliente atualizado = cadastroCliente.atualizarCliente(salvo);

        assertEquals("Nome Novo", atualizado.getNome());
    }

    @Test
    void deveLancarExcecaoAoSalvarClienteComCpfInvalido() {
        Cliente cliente = new Cliente("Cliente Invalido", "123", "81900000000",
            "Rua Invalida", "invalido@email.com", 500);

        assertThrows(CpfInvalidoException.class, () -> {
            cadastroCliente.salvarCliente(cliente);
        });
    }
}
