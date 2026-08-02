package br.edu.ufape.poo.locadora.dados;
import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ufape.poo.locadora.negocio.basica.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}
