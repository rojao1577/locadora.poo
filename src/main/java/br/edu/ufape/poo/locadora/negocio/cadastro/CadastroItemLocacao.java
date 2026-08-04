package br.edu.ufape.poo.locadora.negocio.cadastro;

import br.edu.ufape.poo.locadora.negocio.basica.ItemLocacao;
import br.edu.ufape.poo.locadora.dados.ItemLocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroItemLocacao {

    @Autowired
    private ItemLocacaoRepository repository;

    public ItemLocacao salvar(ItemLocacao item) {
        return repository.save(item);
    }

    public List<ItemLocacao> listarTodos() {
        return repository.findAll();
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }
}