package br.edu.ufape.poo.locadora.negocio.cadastro;

import java.util.List;

import br.edu.ufape.poo.locadora.negocio.basica.Cliente;

public interface InterfaceCadastroCliente {

    Cliente salvarCliente(Cliente novo) throws RegistroDuplicadoException;

    Cliente atualizarCliente(Cliente novo) throws RegistroDuplicadoException;

    List<Cliente> listarTodosClientes();

    Cliente procurarClientePorCpf(String cpf) throws RegistroInexistenteException;

    Cliente carregarCliente(Long id) throws RegistroInexistenteException;

    void apagarCliente(Cliente entity);

}