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

import br.edu.ufape.poo.locadora.comunicacao.requisicoes.FuncionarioDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.FuncionarioDTOResponse;
import br.edu.ufape.poo.locadora.conversor.FuncionarioConversor;
import br.edu.ufape.poo.locadora.negocio.basica.Funcionario;
import br.edu.ufape.poo.locadora.negocio.fachada.Fachada;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private FuncionarioConversor conversor;

    @GetMapping
    public List<FuncionarioDTOResponse> listar() {
        return ((List<Funcionario>) fachada.listarFuncionarios())
                .stream()
                .map(conversor::entityToResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Funcionario funcionario = fachada.buscarFuncionarioPorId(id);

        return ResponseEntity.ok(
                conversor.entityToResponse(funcionario)
        );
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(
            @Valid @RequestBody FuncionarioDTORequest dto
    ) {
        Funcionario funcionario = conversor.requestToEntity(dto);

        funcionario = fachada.cadastrarFuncionario(funcionario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(conversor.entityToResponse(funcionario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        fachada.removerFuncionario(id);
        return ResponseEntity.noContent().build();
    }
}