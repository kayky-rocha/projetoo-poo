package poo.projeto.academia;

import java.time.LocalDate;
import poo.projeto.academia.controller.Agenda;
import poo.projeto.academia.controller.Financeiro;
import poo.projeto.academia.controller.GerenciarClientes;
import poo.projeto.academia.controller.GerenciarFuncionarios;
import poo.projeto.academia.controller.Lanchonete;
import poo.projeto.academia.controller.Lojinha;
import poo.projeto.academia.controller.acesso.Catraca;
import poo.projeto.academia.controller.acesso.CatracaAula;
import poo.projeto.academia.controller.acesso.ConfigurarAcesso;
import poo.projeto.academia.enums.OrigemPagamento;
import poo.projeto.academia.enums.TipoPagamento;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.model.Extrato;
import poo.projeto.academia.model.Pagamento;
import poo.projeto.academia.model.Venda;

/**
 * Classe central do sistema da academia.
 * <p>
 * Cria e guarda os gerenciadores, a agenda, as catracas, a lojinha, a
 * lanchonete e o financeiro. Também é por ela que as receitas chegam ao
 * financeiro: diárias, mensalidades e vendas.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 */
public class Sistema {

    /** Valor da diária para treinar na academia. */
    public static final double VALOR_DIARIA = 30.0;

    /** Valor da mensalidade. */
    public static final double VALOR_MENSALIDADE = 150.0;

    /** Cadastro de clientes. */
    private GerenciarClientes gerenciarClientes;

    /** Cadastro de funcionários e administradores. */
    private GerenciarFuncionarios gerenciarFuncionarios;

    /** Agenda das aulas. */
    private Agenda agenda;

    /** Catraca de entrada da academia. */
    private Catraca catraca;

    /** Catraca das salas de aula. */
    private CatracaAula catracaAula;

    /** Lojinha da academia. */
    private Lojinha lojinha;

    /** Lanchonete da academia. */
    private Lanchonete lanchonete;

    /** Controle de receitas e despesas. */
    private Financeiro financeiro;

    /**
     * Cria o sistema com todas as partes ligadas.
     */
    public Sistema() {
        gerenciarClientes = new GerenciarClientes();
        gerenciarFuncionarios = new GerenciarFuncionarios();
        agenda = new Agenda();
        lojinha = new Lojinha();
        lanchonete = new Lanchonete();
        financeiro = new Financeiro();
        catraca = new Catraca(ConfigurarAcesso.montarCadeiaEntrada(gerenciarClientes), gerenciarClientes);
        catracaAula = new CatracaAula(ConfigurarAcesso.montarCadeiaAula(gerenciarClientes, agenda), agenda);
    }

    /**
     * Registra o pagamento da diária de hoje de um cliente.
     * <p>
     * O pagamento fica no cliente (é o que a catraca confere) e no financeiro.
     *
     * @param cliente cliente que paga a diária
     * @param forma   forma de pagamento
     * @return o pagamento criado
     */
    public Pagamento registrarDiaria(Cliente cliente, TipoPagamento forma) {
        Pagamento pagamento = new Pagamento(cliente, forma, OrigemPagamento.DIARIA,
                VALOR_DIARIA, LocalDate.now());
        cliente.registrarPagamento(pagamento);
        financeiro.registrarPagamento(pagamento);
        return pagamento;
    }

    /**
     * Registra o pagamento da mensalidade de um cliente, com a data de hoje.
     *
     * @param cliente cliente que paga a mensalidade
     * @param forma   forma de pagamento
     * @return o pagamento criado
     */
    public Pagamento registrarMensalidade(Cliente cliente, TipoPagamento forma) {
        Pagamento pagamento = new Pagamento(cliente, forma, OrigemPagamento.MENSALIDADE,
                VALOR_MENSALIDADE, LocalDate.now());
        cliente.registrarPagamento(pagamento);
        financeiro.registrarPagamento(pagamento);
        return pagamento;
    }

    /**
     * Finaliza uma venda da lojinha ou da lanchonete e registra a receita
     * no financeiro.
     *
     * @param venda venda a finalizar
     * @param forma forma de pagamento
     * @return o extrato da venda
     */
    public Extrato finalizarVenda(Venda venda, TipoPagamento forma) {
        Extrato extrato = venda.finalizar(forma);
        financeiro.registrarPagamento(venda.getPagamento());
        return extrato;
    }

    /**
     * Retorna o cadastro de clientes.
     *
     * @return o gerenciador de clientes
     */
    public GerenciarClientes getGerenciarClientes() {
        return gerenciarClientes;
    }

    /**
     * Retorna o cadastro de funcionários.
     *
     * @return o gerenciador de funcionários
     */
    public GerenciarFuncionarios getGerenciarFuncionarios() {
        return gerenciarFuncionarios;
    }

    /**
     * Retorna a agenda.
     *
     * @return a agenda
     */
    public Agenda getAgenda() {
        return agenda;
    }

    /**
     * Retorna a catraca de entrada.
     *
     * @return a catraca
     */
    public Catraca getCatraca() {
        return catraca;
    }

    /**
     * Retorna a catraca das aulas.
     *
     * @return a catraca das aulas
     */
    public CatracaAula getCatracaAula() {
        return catracaAula;
    }

    /**
     * Retorna a lojinha.
     *
     * @return a lojinha
     */
    public Lojinha getLojinha() {
        return lojinha;
    }

    /**
     * Retorna a lanchonete.
     *
     * @return a lanchonete
     */
    public Lanchonete getLanchonete() {
        return lanchonete;
    }

    /**
     * Retorna o financeiro.
     *
     * @return o financeiro
     */
    public Financeiro getFinanceiro() {
        return financeiro;
    }

    /**
     * Retorna o sistema em texto.
     *
     * @return resumo das partes do sistema
     */
    @Override
    public String toString() {
        return "Sistema da academia | " + gerenciarClientes.listarClientes().size() + " cliente(s) | "
                + lojinha + " | " + lanchonete + " | " + financeiro;
    }
}
