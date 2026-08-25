package br.edu.ufape.poo.locadora.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ufape.poo.locadora.comunicacao.requisicoes.PagamentoDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.PagamentoDTOResponse;
import br.edu.ufape.poo.locadora.conversor.PagamentoConversor;
import br.edu.ufape.poo.locadora.negocio.basica.Pagamento;
import br.edu.ufape.poo.locadora.negocio.fachada.Fachada;

// Olha aqui as exceções exatas da sua lista!
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.PagamentoNaoEncontradoException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.RegistroDuplicadoException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.RegistroInexistenteException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pagamentos")
@CrossOrigin(origins = "http://localhost:3000")
public class PagamentoController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private PagamentoConversor conversor;

    @GetMapping
    public List<PagamentoDTOResponse> listar() {
        return ((List<Pagamento>) fachada.listarPagamentos())
                .stream()
                .map(conversor::entityToResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {

            Pagamento pagamento = fachada.buscarPagamentoPorId(id);

            return ResponseEntity.ok(
                    conversor.entityToResponse(pagamento)
            );

        } catch (PagamentoNaoEncontradoException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(
            @Valid @RequestBody PagamentoDTORequest dto
    ) {
        Pagamento pagamento = conversor.requestToEntity(dto);

        br.edu.ufape.poo.locadora.negocio.basica.Locacao locacao = fachada.buscarLocacaoPorId(dto.idLocacao());
        pagamento.setLocacao(locacao);

        pagamento = fachada.cadastrarPagamento(pagamento);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(conversor.entityToResponse(pagamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            fachada.removerPagamento(id);
            return ResponseEntity.noContent().build();

        } catch (PagamentoNaoEncontradoException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}