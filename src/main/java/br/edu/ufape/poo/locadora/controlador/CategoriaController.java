package br.edu.ufape.poo.locadora.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufape.poo.locadora.comunicacao.requisicoes.CategoriaDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.CategoriaDTOResponse;
import br.edu.ufape.poo.locadora.conversor.CategoriaConversor;
import br.edu.ufape.poo.locadora.negocio.basica.Categoria;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaJaExisteException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaNaoEncontradaException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaPossuiVeiculosException;
import br.edu.ufape.poo.locadora.negocio.fachada.Fachada;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private CategoriaConversor conversor;

    @GetMapping
    public List<CategoriaDTOResponse> listar() {

        return fachada.listarCategorias()
            .stream()
            .map(conversor::entityToResponse)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        try {

            Categoria categoria = fachada.buscarCategoriaPorId(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(
                    "Categoria não encontrada."
                ));

            return ResponseEntity.ok(
                conversor.entityToResponse(categoria)
            );

        } catch (CategoriaNaoEncontradaException e) {

            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(
        @Valid @RequestBody CategoriaDTORequest dto
    ) {

        try {

            Categoria categoria = conversor.requestToEntity(dto);

            categoria = fachada.cadastrarCategoria(categoria);

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(conversor.entityToResponse(categoria));

        } catch (CategoriaJaExisteException e) {

            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {

        try {

            fachada.removerCategoria(id);

            return ResponseEntity.noContent().build();

        } catch (CategoriaNaoEncontradaException e) {

            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());

        } catch (CategoriaPossuiVeiculosException e) {

            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
        }
    }
}