package br.edu.ufape.poo.locadora.comunicacao;

import br.edu.ufape.poo.locadora.negocio.basica.ItemLocacao;
import br.edu.ufape.poo.locadora.negocio.fachada.Fachada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-locacao")
public class ItemLocacaoController {

    @Autowired
    private Fachada fachada;

    @PostMapping
    public ItemLocacao salvar(@RequestBody ItemLocacao item) {

        return fachada.cadastrarItemLocacao(item);
    }

    @GetMapping
    public List<ItemLocacao> listarTodos() {

        return fachada.listarItensLocacao();
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {

        fachada.removerItemLocacao(id);
    }
}