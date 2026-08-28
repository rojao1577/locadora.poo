package br.edu.ufape.poo.locadora.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ufape.poo.locadora.comunicacao.requisicoes.ItemLocacaoDTORequest;
import br.edu.ufape.poo.locadora.comunicacao.resposta.ItemLocacaoDTOResponse;
import br.edu.ufape.poo.locadora.conversor.ItemLocacaoConversor;
import br.edu.ufape.poo.locadora.negocio.basica.ItemLocacao;
import br.edu.ufape.poo.locadora.negocio.basica.Locacao;
import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;
import br.edu.ufape.poo.locadora.negocio.fachada.Fachada;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/itenslocacao")
public class ItemLocacaoController {
	
	@Autowired
	private Fachada fachada;
	
	@Autowired
	private ItemLocacaoConversor conversor;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarItemLocacaoPorId(@PathVariable Long id) {
		ItemLocacao item = fachada.buscarItemLocacaoPorId(id);
		return ResponseEntity.ok(conversor.entityToResponse(item));
	}
	
	@GetMapping("/")
	public List<ItemLocacaoDTOResponse> listarItensLocacao() {
		return fachada.listarItensLocacao()
				.stream().map(conversor::entityToResponse).toList();
	}
	
	@PostMapping("/")
	public ResponseEntity<?> cadastrarItemLocacao(@RequestBody @Valid ItemLocacaoDTORequest itemRequest) {
		Locacao locacao = fachada.buscarLocacaoPorId(itemRequest.locacaoId());
		
		Veiculo veiculo = fachada.buscarVeiculoPorId(itemRequest.veiculoId())
				.orElseThrow(() -> new RuntimeException("veiculo nao encontrado."));
		
		ItemLocacao novo = conversor.requestToEntity(itemRequest, locacao, veiculo);
		ItemLocacao salvo = fachada.cadastrarItemLocacao(novo);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(conversor.entityToResponse(salvo));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerItemLocacao(@PathVariable Long id) {
		fachada.removerItemLocacao(id);
		return ResponseEntity.ok("Item de locaco removido com sucesso.");
	}
}
