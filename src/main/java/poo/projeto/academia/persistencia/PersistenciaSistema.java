package poo.projeto.academia.persistencia;

import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import poo.projeto.academia.Sistema;
import poo.projeto.academia.model.Administrador;
import poo.projeto.academia.model.Agendamento;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.model.Despesa;
import poo.projeto.academia.model.Extrato;
import poo.projeto.academia.model.Funcionario;
import poo.projeto.academia.model.ItemCardapio;
import poo.projeto.academia.model.ItemVenda;
import poo.projeto.academia.model.Pagamento;
import poo.projeto.academia.model.ProdutoLoja;
import poo.projeto.academia.model.RegistroCatraca;
import poo.projeto.academia.model.VendaLanchonete;
import poo.projeto.academia.model.VendaLojinha;
import poo.projeto.academia.model.NotaFiscal;

/**
 * Salva e carrega todos os dados do sistema em arquivos JSON.
 * <p>
 * Cada tipo de dado vai para um arquivo próprio dentro da pasta escolhida:
 * clientes, funcionários, administradores, agendamentos, registros da catraca,
 * produtos (com o estoque), cardápio, vendas da lojinha, pedidos da lanchonete,
 * pagamentos, despesas e notas fiscais. Funcionários e administradores ficam em arquivos
 * separados para o administrador continuar sendo administrador ao carregar.
 * <p>
 * Ao carregar, o JSON cria cópias separadas dos objetos (por exemplo, o
 * cliente dentro de um agendamento vira outro objeto). Por isso o método
 * {@link #carregarTudo(Sistema)} religa cada referência ao objeto certo,
 * procurando pelo id.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see RepositorioJson
 * @see Sistema
 */
public class PersistenciaSistema {

    /** Pasta onde ficam os arquivos JSON. */
    private String pasta;

    /** Repositório que lê e grava os arquivos. */
    private RepositorioJson repositorio;

    /**
     * Cria a persistência usando uma pasta.
     *
     * @param pasta pasta dos arquivos JSON, por exemplo {@code "dados"}
     */
    public PersistenciaSistema(String pasta) {
        this.pasta = pasta;
        this.repositorio = new RepositorioJson();
    }

    /**
     * Monta o caminho de um arquivo dentro da pasta.
     *
     * @param nome nome do arquivo
     * @return caminho completo do arquivo
     */
    private String caminho(String nome) {
        return pasta + File.separator + nome;
    }

    /**
     * Salva todos os dados do sistema, cada tipo em um arquivo JSON.
     *
     * @param sistema sistema com os dados
     * @throws IOException se não conseguir gravar algum arquivo
     */
    public void salvarTudo(Sistema sistema) throws IOException {
        new File(pasta).mkdirs();

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        ArrayList<Administrador> administradores = new ArrayList<>();
        for (Funcionario f : sistema.getGerenciarFuncionarios().listarFuncionarios()) {
            if (f instanceof Administrador) {
                administradores.add((Administrador) f);
            } else {
                funcionarios.add(f);
            }
        }

        repositorio.salvar(caminho("clientes.json"), sistema.getGerenciarClientes().listarClientes());
        repositorio.salvar(caminho("funcionarios.json"), funcionarios);
        repositorio.salvar(caminho("administradores.json"), administradores);
        repositorio.salvar(caminho("agendamentos.json"), sistema.getAgenda().listarAgendamentos());
        repositorio.salvar(caminho("catraca.json"), sistema.getCatraca().listarRegistros());
        repositorio.salvar(caminho("produtos.json"), sistema.getLojinha().listarProdutos());
        repositorio.salvar(caminho("cardapio.json"), sistema.getLanchonete().listarCardapio());
        repositorio.salvar(caminho("vendas-lojinha.json"), sistema.getLojinha().listarVendas());
        repositorio.salvar(caminho("vendas-lanchonete.json"), sistema.getLanchonete().listarPedidos());
        repositorio.salvar(caminho("pagamentos.json"), sistema.getFinanceiro().listarPagamentos());
        repositorio.salvar(caminho("despesas.json"), sistema.getFinanceiro().listarDespesas());
        repositorio.salvar(caminho("notas.json"), sistema.getFinanceiro().listarNotas());
    }

    /**
     * Carrega todos os dados dos arquivos JSON para dentro do sistema.
     * <p>
     * Se um arquivo não existir, aquela parte começa vazia.
     *
     * @param sistema sistema que vai receber os dados
     * @throws IOException se não conseguir ler algum arquivo
     */
    public void carregarTudo(Sistema sistema) throws IOException {
        // 1. Clientes, com os pagamentos e extratos religados ao próprio cliente
        Type tipoClientes = new TypeToken<ArrayList<Cliente>>() { }.getType();
        ArrayList<Cliente> clientes = carregarLista("clientes.json", tipoClientes);
        for (Cliente c : clientes) {
            for (Pagamento p : c.getPagamentos()) {
                p.setCliente(c);
            }
            for (Extrato e : c.getExtratos()) {
                e.setCliente(c);
            }
        }
        sistema.getGerenciarClientes().restaurar(clientes);

        // 2. Funcionários e administradores juntos na mesma lista
        Type tipoFuncionarios = new TypeToken<ArrayList<Funcionario>>() { }.getType();
        Type tipoAdministradores = new TypeToken<ArrayList<Administrador>>() { }.getType();
        ArrayList<Funcionario> funcionarios = carregarLista("funcionarios.json", tipoFuncionarios);
        ArrayList<Administrador> administradores = carregarLista("administradores.json", tipoAdministradores);
        funcionarios.addAll(administradores);
        sistema.getGerenciarFuncionarios().restaurar(funcionarios);

        // 3. Pagamentos: usa o mesmo objeto que está no cliente, procurando pelo id
        Type tipoPagamentos = new TypeToken<ArrayList<Pagamento>>() { }.getType();
        Type tipoDespesas = new TypeToken<ArrayList<Despesa>>() { }.getType();
        ArrayList<Pagamento> pagamentosLidos = carregarLista("pagamentos.json", tipoPagamentos);
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        for (Pagamento p : pagamentosLidos) {
            Pagamento doCliente = buscarPagamentoNosClientes(clientes, p.getId());
            if (doCliente != null) {
                pagamentos.add(doCliente);
            } else {
                pagamentos.add(p);
            }
        }
        ArrayList<Despesa> despesas = carregarLista("despesas.json", tipoDespesas);
        sistema.getFinanceiro().restaurar(pagamentos, despesas);
        Type tipoNotas = new TypeToken<ArrayList<NotaFiscal>>() { }.getType();
        ArrayList<NotaFiscal> notas = carregarLista("notas.json", tipoNotas);
        sistema.getFinanceiro().restaurarNotas(notas);

        // 4. Agendamentos, religados ao cliente e ao pagamento
        Type tipoAgendamentos = new TypeToken<ArrayList<Agendamento>>() { }.getType();
        ArrayList<Agendamento> agendamentos = carregarLista("agendamentos.json", tipoAgendamentos);
        for (Agendamento a : agendamentos) {
            a.setCliente(religarCliente(sistema, a.getCliente()));
            if (a.getPagamento() != null) {
                a.setPagamento(religarPagamento(sistema, a.getPagamento()));
            }
        }
        sistema.getAgenda().restaurar(agendamentos);

        // 5. Registros da catraca, religados ao cliente
        Type tipoRegistros = new TypeToken<ArrayList<RegistroCatraca>>() { }.getType();
        ArrayList<RegistroCatraca> registros = carregarLista("catraca.json", tipoRegistros);
        for (RegistroCatraca r : registros) {
            r.setCliente(religarCliente(sistema, r.getCliente()));
        }
        sistema.getCatraca().restaurarRegistros(registros);

        // 6. Lojinha: produtos (com estoque) e vendas
        Type tipoProdutos = new TypeToken<ArrayList<ProdutoLoja>>() { }.getType();
        Type tipoVendasLojinha = new TypeToken<ArrayList<VendaLojinha>>() { }.getType();
        ArrayList<ProdutoLoja> produtos = carregarLista("produtos.json", tipoProdutos);
        ArrayList<VendaLojinha> vendasLojinha = carregarLista("vendas-lojinha.json", tipoVendasLojinha);
        sistema.getLojinha().restaurar(produtos, vendasLojinha);
        for (VendaLojinha v : vendasLojinha) {
            if (v.getCliente() != null) {
                v.setCliente(religarCliente(sistema, v.getCliente()));
            }
            if (v.getPagamento() != null) {
                v.setPagamento(religarPagamento(sistema, v.getPagamento()));
            }
            for (ItemVenda item : v.getItens()) {
                ProdutoLoja produto = sistema.getLojinha().buscarProduto(item.getProduto().getId());
                if (produto != null) {
                    item.setProduto(produto);
                }
            }
        }

        // 7. Lanchonete: cardápio e pedidos
        Type tipoCardapio = new TypeToken<ArrayList<ItemCardapio>>() { }.getType();
        Type tipoPedidos = new TypeToken<ArrayList<VendaLanchonete>>() { }.getType();
        ArrayList<ItemCardapio> cardapio = carregarLista("cardapio.json", tipoCardapio);
        ArrayList<VendaLanchonete> pedidos = carregarLista("vendas-lanchonete.json", tipoPedidos);
        sistema.getLanchonete().restaurar(cardapio, pedidos);
        for (VendaLanchonete v : pedidos) {
            if (v.getCliente() != null) {
                v.setCliente(religarCliente(sistema, v.getCliente()));
            }
            if (v.getPagamento() != null) {
                v.setPagamento(religarPagamento(sistema, v.getPagamento()));
            }
            for (ItemVenda item : v.getItens()) {
                ItemCardapio itemCardapio = sistema.getLanchonete().buscarItem(item.getProduto().getId());
                if (itemCardapio != null) {
                    item.setProduto(itemCardapio);
                }
            }
        }
    }

    /**
     * Carrega uma lista de um arquivo. Se o arquivo não existir, devolve uma lista vazia.
     *
     * @param <T>  tipo dos itens da lista
     * @param nome nome do arquivo
     * @param tipo tipo da lista
     * @return a lista carregada, ou uma lista vazia
     * @throws IOException se não conseguir ler o arquivo
     */
    private <T> ArrayList<T> carregarLista(String nome, Type tipo) throws IOException {
        ArrayList<T> lista = repositorio.carregar(caminho(nome), tipo);
        if (lista == null) {
            return new ArrayList<>();
        }
        return lista;
    }

    /**
     * Troca a cópia do cliente pelo cliente cadastrado com o mesmo id.
     *
     * @param sistema sistema com o cadastro de clientes
     * @param copia   cópia do cliente lida do JSON
     * @return o cliente cadastrado, ou a própria cópia se ele não existir mais
     */
    private Cliente religarCliente(Sistema sistema, Cliente copia) {
        Cliente cadastrado = sistema.getGerenciarClientes().buscarCliente(copia.getId());
        if (cadastrado == null) {
            return copia;
        }
        return cadastrado;
    }

    /**
     * Troca a cópia do pagamento pelo pagamento do financeiro com o mesmo id.
     *
     * @param sistema sistema com o financeiro
     * @param copia   cópia do pagamento lida do JSON
     * @return o pagamento do financeiro, ou a própria cópia se não for encontrado
     */
    private Pagamento religarPagamento(Sistema sistema, Pagamento copia) {
        Pagamento registrado = sistema.getFinanceiro().buscarPagamento(copia.getId());
        if (registrado == null) {
            return copia;
        }
        return registrado;
    }

    /**
     * Procura, nos pagamentos dos clientes, um pagamento com o id informado.
     *
     * @param clientes clientes carregados
     * @param id       id do pagamento; ids menores que 1 não são procurados
     * @return o pagamento do cliente, ou {@code null} se não achar
     */
    private Pagamento buscarPagamentoNosClientes(ArrayList<Cliente> clientes, int id) {
        if (id < 1) {
            return null;
        }
        for (Cliente c : clientes) {
            for (Pagamento p : c.getPagamentos()) {
                if (p.getId() == id) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * Retorna a persistência em texto.
     *
     * @return a pasta usada
     */
    @Override
    public String toString() {
        return "PersistenciaSistema[pasta=" + pasta + "]";
    }
}
