package br.edu.ufape.poo.locadora.comunicacao;

import br.edu.ufape.poo.locadora.negocio.basica.ItemLocacao;
import br.edu.ufape.poo.locadora.negocio.cadastro.CadastroItemLocacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-locacao")
public class ItemLocacaoController {

    @Autowired
    private CadastroItemLocacao cadastroItemLocacao;

    @PostMapping
    public ItemLocacao salvar(@RequestBody ItemLocacao item) {
        return cadastroItemLocacao.salvar(item);
    }

    @GetMapping
    public List<ItemLocacao> listarTodos() {
        return cadastroItemLocacao.listarTodos();
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        cadastroItemLocacao.remover(id);
    }
}