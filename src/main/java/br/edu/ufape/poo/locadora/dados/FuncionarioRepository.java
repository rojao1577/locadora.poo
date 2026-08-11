package br.edu.ufape.poo.locadora.dados;
import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ufape.poo.locadora.negocio.basica.Funcionario;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByCpf(String cpf);
}
