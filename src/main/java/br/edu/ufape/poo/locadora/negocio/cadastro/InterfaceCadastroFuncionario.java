package br.edu.ufape.poo.locadora.negocio.cadastro;

import br.edu.ufape.poo.locadora.negocio.basica.Funcionario;
import java.util.List;

public interface InterfaceCadastroFuncionario {

    Funcionario cadastrarFuncionario(Funcionario funcionario);

    List<Funcionario> listarFuncionarios();

    Funcionario buscarFuncionarioPorId(Long id);

    void removerFuncionario(Long id);
}