package br.edu.ufape.poo.locadora.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ufape.poo.locadora.comunicacao.requisicoes.ClienteDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.ClienteDTOResponse;
import br.edu.ufape.poo.locadora.conversor.ClienteConversor;
import br.edu.ufape.poo.locadora.negocio.basica.Cliente;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CpfInvalidoException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.RegistroDuplicadoException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.RegistroInexistenteException;
import br.edu.ufape.poo.locadora.negocio.fachada.Fachada;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private ClienteConversor conversor;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarClientePorId(@PathVariable Long id) {
        try {
            Cliente c = fachada.carregarCliente(id);
            return ResponseEntity.ok(conversor.entityToResponse(c));
        } catch (RegistroInexistenteException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/")
    public List<ClienteDTOResponse> listarClientes() {
        return fachada.listarTodosClientes()
                .stream().map(conversor::entityToResponse).toList();
    }

    @PostMapping("/")
    public ResponseEntity<?> cadastrarCliente(@RequestBody @Valid ClienteDTORequest clienteRequest) {
        Cliente novo = conversor.requestToEntity(clienteRequest);
        try {
            Cliente salvo = fachada.salvarCliente(novo);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(conversor.entityToResponse(salvo));
        } catch (RegistroDuplicadoException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (CpfInvalidoException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterarCliente(@RequestBody @Valid ClienteDTORequest clienteRequest,
                                             @PathVariable Long id) {
        try {
            Cliente existente = fachada.carregarCliente(id);
            existente.setNome(clienteRequest.nome());
            existente.setCpf(clienteRequest.cpf());
            existente.setTelefone(clienteRequest.telefone());
            existente.setEndereco(clienteRequest.endereco());
            existente.setEmail(clienteRequest.email());
            existente.setScoreCredito(clienteRequest.scoreCredito());

            Cliente atualizado = fachada.atualizarCliente(existente);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(conversor.entityToResponse(atualizado));
        } catch (RegistroInexistenteException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (RegistroDuplicadoException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (CpfInvalidoException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerCliente(@PathVariable Long id) {
        try {
            Cliente existente = fachada.carregarCliente(id);
            fachada.apagarCliente(existente);
            return ResponseEntity.ok("Cliente removido com sucesso.");
        } catch (RegistroInexistenteException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}