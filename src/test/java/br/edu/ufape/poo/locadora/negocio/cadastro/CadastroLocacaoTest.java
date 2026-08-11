package br.edu.ufape.poo.locadora.negocio.cadastro;

import br.edu.ufape.poo.locadora.negocio.basica.Cliente;
import br.edu.ufape.poo.locadora.negocio.basica.Locacao;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.ClienteInadimplenteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CadastroLocacaoTest {

    @Autowired
    private InterfaceCadastroLocacao cadastroLocacao;

    @Test
    public void naoDevePermitirLocacaoParaClienteInadimplente() {
        Cliente clienteInadimplente = new Cliente();
        clienteInadimplente.setCpf("12345678900");
        clienteInadimplente.setScoreCredito(100);

        Locacao locacao = new Locacao();
        locacao.setCliente(clienteInadimplente);

        Exception exception = assertThrows(ClienteInadimplenteException.class, () -> {
            cadastroLocacao.registrarLocacao(locacao);
        });

        assertTrue(exception.getMessage().contains("possui inadimplências"));
    }
}