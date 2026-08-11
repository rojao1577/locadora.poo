package br.edu.ufape.poo.locadora.negocio.cadastro;

import java.util.List;

import br.edu.ufape.poo.locadora.negocio.basica.Cliente;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CpfInvalidoException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.RegistroDuplicadoException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.RegistroInexistenteException;

public interface InterfaceCadastroCliente {

    Cliente salvarCliente(Cliente novo) throws RegistroDuplicadoException, CpfInvalidoException;

    Cliente atualizarCliente(Cliente novo) throws RegistroDuplicadoException, CpfInvalidoException;

    List<Cliente> listarTodosClientes();

    Cliente procurarClientePorCpf(String cpf) throws RegistroInexistenteException;

    Cliente carregarCliente(Long id) throws RegistroInexistenteException;

    void apagarCliente(Cliente entity);

}