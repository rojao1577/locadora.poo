package br.edu.ufape.poo.locadora.controlador;

import br.edu.ufape.poo.locadora.conversor.LocacaoConversor;
import br.edu.ufape.poo.locadora.comunicacao.requisicoes.LocacaoDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.LocacaoDTOResponse;
import br.edu.ufape.poo.locadora.negocio.fachada.Fachada;
import br.edu.ufape.poo.locadora.negocio.basica.Locacao;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.ClienteInadimplenteException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.VeiculoIndisponivelException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/locacoes")
public class LocacaoController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private LocacaoConversor conversor;

    @PostMapping
    public ResponseEntity<?> registrarLocacao(@Valid @RequestBody LocacaoDTORequest request) {
        try {
            Locacao locacao = conversor.toEntity(request);
            Locacao locacaoSalva = fachada.registrarLocacao(
                    locacao,
                    request.getClienteId(),
                    request.getFuncionarioId(),
                    request.getVeiculosIds()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(conversor.toResponse(locacaoSalva));

        } catch (ClienteInadimplenteException | VeiculoIndisponivelException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (br.edu.ufape.poo.locadora.negocio.cadastro.exception.RegistroInexistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarLocacao(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        try {
            LocalDate dataDevolucao = LocalDate.parse(payload.get("dataDevolucao"));
            Locacao locacaoFinalizada = fachada.finalizarLocacao(id, dataDevolucao);
            return ResponseEntity.ok(conversor.toResponse(locacaoFinalizada));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}