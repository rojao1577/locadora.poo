package br.edu.ufape.poo.locadora.dados;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufape.poo.locadora.negocio.basica.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNome(String nome);

}