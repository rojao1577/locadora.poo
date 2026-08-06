package br.edu.ufape.poo.locadora.negocio.cadastro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufape.poo.locadora.dados.ClienteRepository;
import br.edu.ufape.poo.locadora.negocio.basica.Cliente;

@Service
public class CadastroCliente implements InterfaceCadastroCliente {

    @Autowired
    private ClienteRepository repositorioCliente;

    @Override
    public Cliente salvarCliente(Cliente novo) throws RegistroDuplicadoException {
        if (repositorioCliente.findByCpf(novo.getCpf()) == null) {
            return repositorioCliente.save(novo);
        } else {
            throw new RegistroDuplicadoException(
                "Não é possível cadastrar mais de um cliente com o mesmo CPF. Verifique o CPF informado.");
        }
    }

    @Override
    public Cliente atualizarCliente(Cliente novo) throws RegistroDuplicadoException {
        Cliente existente = repositorioCliente.findByCpf(novo.getCpf());
        if (existente == null || existente.getId().equals(novo.getId())) {
            return repositorioCliente.save(novo);
        } else {
            throw new RegistroDuplicadoException(
                "Não é possível cadastrar mais de um cliente com o mesmo CPF. Verifique o CPF informado.");
        }
    }

    @Override
    public List<Cliente> listarTodosClientes() {
        return repositorioCliente.findAll();
    }

    @Override
    public Cliente procurarClientePorCpf(String cpf) throws RegistroInexistenteException {
        Cliente procurado = repositorioCliente.findByCpf(cpf);
        if (procurado != null) {
            return procurado;
        }
        throw new RegistroInexistenteException("Não existe cliente com o CPF " + cpf);
    }

    @Override
    public Cliente carregarCliente(Long id) throws RegistroInexistenteException {
        return repositorioCliente.findById(id).orElseThrow(
            () -> new RegistroInexistenteException("Não existe cliente com o id = " + id));
    }

    @Override
    public void apagarCliente(Cliente entity) {
        repositorioCliente.delete(entity);
    }
}