package br.edu.ufape.poo.locadora.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<FuncionarioDTOResponse>> listar() {
        List<FuncionarioDTOResponse> resposta = new ArrayList<>();

        List<Funcionario> listaFuncionarios = fachada.listarFuncionarios();

        for (Funcionario funcionario : listaFuncionarios) {
            resposta.add(conversor.entityToResponse(funcionario));
        }

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Funcionario funcionario = fachada.buscarFuncionarioPorId(id);
        return ResponseEntity.ok(conversor.entityToResponse(funcionario));
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody FuncionarioDTORequest dto) {
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