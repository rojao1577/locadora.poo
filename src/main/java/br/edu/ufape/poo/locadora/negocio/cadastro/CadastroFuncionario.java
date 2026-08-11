package br.edu.ufape.poo.locadora.negocio.cadastro;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufape.poo.locadora.dados.FuncionarioRepository;
import br.edu.ufape.poo.locadora.negocio.basica.Funcionario;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.*;

@Service
public class CadastroFuncionario implements InterfaceCadastroFuncionario {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Funcionario cadastrarFuncionario(Funcionario funcionario) {

        if (funcionario.getCpf() == null || funcionario.getCpf().isBlank() || !funcionario.validarCpf()) {
            throw new CpfFuncionarioInvalidoException("O CPF informado é nulo, vazio ou inválido.");
        }


        if (funcionario.getCargo() == null || funcionario.getCargo().isBlank()) {
            throw new CargoFuncionarioInvalidoException("O cargo do funcionário não pode ser vazio.");
        }


        if (funcionario.getSalario() == null || funcionario.getSalario().doubleValue() < 0) {
            throw new SalarioFuncionarioInvalidoException("O salário não pode ser nulo ou negativo.");
        }


        Optional<Funcionario> existente = funcionarioRepository.findByCpf(funcionario.getCpf());
        if (existente.isPresent()) {
            throw new FuncionarioJaExisteException("Já existe um funcionário cadastrado com este CPF.");
        }

        return funcionarioRepository.save(funcionario);
    }

    @Override
    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();
    }

    @Override
    public Funcionario buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id).orElseThrow(
                () -> new FuncionarioNaoEncontradoException("Funcionário não encontrado.")
        );
    }

    @Override
    public void removerFuncionario(Long id) {

        if (funcionarioRepository.findById(id).isEmpty()) {
            throw new FuncionarioNaoEncontradoException("Não é possível remover: funcionário não encontrado.");
        }
        funcionarioRepository.deleteById(id);
    }
}