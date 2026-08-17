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

import br.edu.ufape.poo.locadora.comunicacao.requisicoes.VeiculoDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.VeiculoDTOResponse;
import br.edu.ufape.poo.locadora.conversor.VeiculoConversor;
import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaNaoEncontradaException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.VeiculoJaExisteException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.VeiculoNaoEncontradoException;
import br.edu.ufape.poo.locadora.negocio.fachada.Fachada;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private VeiculoConversor conversor;

    @GetMapping
    public List<VeiculoDTOResponse> listar() {

        return fachada.listarVeiculos()
            .stream()
            .map(conversor::entityToResponse)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        try {

            Veiculo veiculo = fachada.buscarVeiculoPorId(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(
                    "Veículo não encontrado."
                ));

            return ResponseEntity.ok(
                conversor.entityToResponse(veiculo)
            );

        } catch (VeiculoNaoEncontradoException e) {

            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(
        @Valid @RequestBody VeiculoDTORequest dto
    ) {

        try {

            Veiculo veiculo = conversor.requestToEntity(dto);

            veiculo = fachada.cadastrarVeiculo(
                veiculo,
                dto.idCategoria()
            );

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(conversor.entityToResponse(veiculo));

        } catch (VeiculoJaExisteException e) {

            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());

        } catch (CategoriaNaoEncontradaException e) {

            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {

        try {

            fachada.removerVeiculo(id);

            return ResponseEntity.noContent().build();

        } catch (VeiculoNaoEncontradoException e) {

            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }
}