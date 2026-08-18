package br.edu.ufape.poo.locadora.negocio.fachada;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Nossos Imports Básicos
import br.edu.ufape.poo.locadora.negocio.basica.Categoria;
import br.edu.ufape.poo.locadora.negocio.basica.Cliente;
import br.edu.ufape.poo.locadora.negocio.basica.Funcionario;
import br.edu.ufape.poo.locadora.negocio.basica.Pagamento;
import br.edu.ufape.poo.locadora.negocio.basica.Veiculo;
import br.edu.ufape.poo.locadora.negocio.basica.ItemLocacao;
import br.edu.ufape.poo.locadora.negocio.basica.Locacao;

// Nossos Imports de Cadastro
import br.edu.ufape.poo.locadora.negocio.cadastro.CadastroCategoria;
import br.edu.ufape.poo.locadora.negocio.cadastro.CadastroCliente;
import br.edu.ufape.poo.locadora.negocio.cadastro.CadastroVeiculo;
import br.edu.ufape.poo.locadora.negocio.cadastro.InterfaceCadastroFuncionario;
import br.edu.ufape.poo.locadora.negocio.cadastro.InterfaceCadastroPagamento;
import br.edu.ufape.poo.locadora.negocio.cadastro.InterfaceCadastroItemLocacao;
import br.edu.ufape.poo.locadora.negocio.cadastro.InterfaceCadastroLocacao;

// Nossos Imports de Exceções
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaNaoEncontradaException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CategoriaPossuiVeiculosException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.ClienteInadimplenteException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.CpfInvalidoException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.RegistroDuplicadoException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.RegistroInexistenteException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.VeiculoIndisponivelException;
import br.edu.ufape.poo.locadora.negocio.cadastro.exception.VeiculoNaoEncontradoException;


@Service
public class Fachada {

    @Autowired
    private CadastroCategoria cadastroCategoria;

    @Autowired
    private CadastroVeiculo cadastroVeiculo;

    @Autowired
    private CadastroCliente cadastroCliente;

    @Autowired
    private InterfaceCadastroFuncionario cadastroFuncionario;

    @Autowired
    private InterfaceCadastroPagamento cadastroPagamento;

    @Autowired
    private InterfaceCadastroItemLocacao cadastroItemLocacao;

    @Autowired
    private InterfaceCadastroLocacao cadastroLocacao;

    // ==================== CATEGORIA ====================

    public Categoria cadastrarCategoria(Categoria categoria) {
        return cadastroCategoria.cadastrarCategoria(categoria);
    }

    public List<Categoria> listarCategorias() {
        return cadastroCategoria.listarCategorias();
    }

    public Optional<Categoria> buscarCategoriaPorId(Long id) {
        return cadastroCategoria.buscarCategoriaPorId(id);
    }

    public void removerCategoria(Long id) {
        Optional<Categoria> categoria = cadastroCategoria.buscarCategoriaPorId(id);
        if (categoria.isPresent() && cadastroVeiculo.existeVeiculoNaCategoria(categoria.get())) {
            throw new CategoriaPossuiVeiculosException("Não é possível remover uma categoria que possui veículos.");
        }
        cadastroCategoria.removerCategoria(id);
    }

    // ==================== VEICULO ====================

    public Veiculo cadastrarVeiculo(Veiculo veiculo, Long idCategoria) {

        Categoria categoria = cadastroCategoria.buscarCategoriaPorId(idCategoria)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(
                        "Categoria não encontrada."
                ));

        veiculo.setCategoria(categoria);

        return cadastroVeiculo.cadastrarVeiculo(veiculo);
    }

    public List<Veiculo> listarVeiculos() {
        return cadastroVeiculo.listarVeiculos();
    }

    public Optional<Veiculo> buscarVeiculoPorId(Long id) {
        return cadastroVeiculo.buscarVeiculoPorId(id);
    }

    public void removerVeiculo(Long id) {
        cadastroVeiculo.removerVeiculo(id);
    }

    public Veiculo escolherVeiculoDisponivelPorCategoria(Long categoriaId) {
        Categoria categoria = cadastroCategoria.buscarCategoriaPorId(categoriaId)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria nao encontrada."));

        List<Veiculo> disponiveis = cadastroVeiculo.listarVeiculosDisponiveisPorCategoria(categoria);

        if(disponiveis.isEmpty()) {
            throw new VeiculoNaoEncontradoException("Nao ha veiculos disponiveis nessa categoria no momento.");
        }
        return disponiveis.get(0);
    }

    // ==================== CLIENTE ====================

    public Cliente salvarCliente(Cliente cliente) throws RegistroDuplicadoException, CpfInvalidoException {
        return cadastroCliente.salvarCliente(cliente);
    }

    public Cliente atualizarCliente(Cliente cliente) throws RegistroDuplicadoException, CpfInvalidoException {
        return cadastroCliente.atualizarCliente(cliente);
    }

    public List<Cliente> listarTodosClientes() {
        return cadastroCliente.listarTodosClientes();
    }

    public Cliente procurarClientePorCpf(String cpf) throws RegistroInexistenteException {
        return cadastroCliente.procurarClientePorCpf(cpf);
    }

    public Cliente carregarCliente(Long id) throws RegistroInexistenteException {
        return cadastroCliente.carregarCliente(id);
    }

    public void apagarCliente(Cliente entity) {
        cadastroCliente.apagarCliente(entity);
    }

    // ==================== FUNCIONARIO ====================

    public Funcionario cadastrarFuncionario(Funcionario funcionario) {
        return cadastroFuncionario.cadastrarFuncionario(funcionario);
    }

    public List<Funcionarios> listarFuncionarios() {
        return cadastroFuncionario.listarFuncionarios();
    }

    public Funcionario buscarFuncionarioPorId(Long id) {
        return cadastroFuncionario.buscarFuncionarioPorId(id);
    }

    public void removerFuncionario(Long id) {
        cadastroFuncionario.removerFuncionario(id);
    }

    // ==================== PAGAMENTO ====================

    public Pagamento cadastrarPagamento(Pagamento pagamento) {
        return cadastroPagamento.cadastrarPagamento(pagamento);
    }

    public List<Pagamento> listarPagamentos() {
        return cadastroPagamento.listarPagamentos();
    }

    public Pagamento buscarPagamentoPorId(Long id) {
        return cadastroPagamento.buscarPagamentoPorId(id);
    }

    public void removerPagamento(Long id) {
        cadastroPagamento.removerPagamento(id);
    }

    // ==================== ITEM LOCACAO ====================

    public ItemLocacao cadastrarItemLocacao(ItemLocacao itemLocacao) {
        return cadastroItemLocacao.cadastrarItemLocacao(itemLocacao);
    }

    public List<ItemLocacao> listarItensLocacao() {
        return cadastroItemLocacao.listarItensLocacao();
    }

    public ItemLocacao buscarItemLocacaoPorId(Long id) {
        return cadastroItemLocacao.buscarItemLocacaoPorId(id);
    }

    public void removerItemLocacao(Long id) {
        cadastroItemLocacao.removerItemLocacao(id);
    }

    // ==================== LOCACAO ===================

    public Locacao buscarLocacaoPorId(Long id) {
        return cadastroLocacao.buscarLocacao(id);
    }

    public Locacao registrarLocacao(Locacao locacao, Long clienteId, Long funcionarioId, List<Long> veiculosIds) throws ClienteInadimplenteException, VeiculoIndisponivelException, RegistroInexistenteException {

        Cliente cliente = cadastroCliente.carregarCliente(clienteId);
        Funcionario funcionario = cadastroFuncionario.buscarFuncionarioPorId(funcionarioId);

        locacao.setCliente(cliente);
        locacao.setFuncionario(funcionario);

        List<ItemLocacao> itens = new ArrayList<>();
        for (Long veiculoId : veiculosIds) {
            Veiculo veiculo = cadastroVeiculo.buscarVeiculoPorId(veiculoId)
                    .orElseThrow(() -> new RuntimeException("Veículo com ID " + veiculoId + " não encontrado no sistema."));

            ItemLocacao item = new ItemLocacao();
            item.setVeiculo(veiculo);
            item.setLocacao(locacao);
            itens.add(item);
        }
        locacao.setItens(itens);

        return cadastroLocacao.registrarLocacao(locacao);
    }

    public Locacao finalizarLocacao(Long idLocacao, LocalDate dataDevolucao) {
        return cadastroLocacao.finalizarLocacao(idLocacao, dataDevolucao);
    }
}