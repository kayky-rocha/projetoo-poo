package poo.projeto.academia;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import poo.projeto.academia.model.Administrador;
import poo.projeto.academia.model.Agendamento;
import poo.projeto.academia.model.Balanco;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.model.Despesa;
import poo.projeto.academia.model.Extrato;
import poo.projeto.academia.model.Funcionario;
import poo.projeto.academia.model.Pagamento;
import poo.projeto.academia.model.Venda;

/**
 * Classe central do sistema da academia.
 * <p>
 * Cria e guarda os gerenciadores, a agenda, as catracas, a lojinha, a
 * lanchonete e o financeiro. Também é por ela que as receitas chegam ao
 * financeiro: diárias, mensalidades e vendas.
 * <p>
 * O uso é feito por colaboradores e administradores, com login. Qualquer
 * funcionário logado atende clientes, agenda aulas, vende e lança despesas.
 * Só o administrador cadastra, edita e remove funcionários, altera a senha
 * de um administrador e gera o balanço mensal.
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

    /** Funcionário que está usando o sistema; {@code null} se ninguém fez login. */
    private Funcionario usuarioLogado;

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
     * Faz o login de um colaborador ou administrador pelo usuário e senha.
     *
     * @param usuario nome de usuário
     * @param senha   senha de login
     * @return {@code true} se o usuário existe e a senha está certa
     */
    public boolean login(String usuario, String senha) {
        Funcionario funcionario = gerenciarFuncionarios.buscarFuncionario(usuario);
        if (funcionario == null || funcionario.getSenha().equals(senha) == false) {
            return false;
        }
        usuarioLogado = funcionario;
        return true;
    }

    /**
     * Encerra a sessão do funcionário logado.
     */
    public void logout() {
        usuarioLogado = null;
    }

    /**
     * Diz se há alguém logado.
     *
     * @return {@code true} se algum funcionário fez login
     */
    public boolean estaLogado() {
        return usuarioLogado != null;
    }

    /**
     * Diz se quem está logado é um administrador.
     *
     * @return {@code true} se o usuário logado é um {@link Administrador}
     */
    public boolean isAdministradorLogado() {
        return usuarioLogado instanceof Administrador;
    }

    /**
     * Retorna o funcionário logado.
     *
     * @return o funcionário, ou {@code null} se ninguém fez login
     */
    public Funcionario getUsuarioLogado() {
        return usuarioLogado;
    }

    /**
     * Impede a operação se ninguém fez login.
     *
     * @throws IllegalStateException se ninguém fez login
     */
    private void exigirLogin() {
        if (usuarioLogado == null) {
            throw new IllegalStateException("Faça login para usar o sistema.");
        }
    }

    /**
     * Impede a operação se quem está logado não for administrador.
     *
     * @throws IllegalStateException se ninguém fez login ou se não for administrador
     */
    private void exigirAdministrador() {
        exigirLogin();
        if (isAdministradorLogado() == false) {
            throw new IllegalStateException("Apenas o administrador pode fazer isso.");
        }
    }

    /**
     * Cadastra um cliente. Qualquer funcionário logado pode.
     *
     * @param cliente cliente a cadastrar
     * @throws IllegalStateException se ninguém fez login
     */
    public void cadastrarCliente(Cliente cliente) {
        exigirLogin();
        gerenciarClientes.cadastrarCliente(cliente);
    }

    /**
     * Edita os dados de um cliente. Qualquer funcionário logado pode.
     *
     * @param id         identificador do cliente
     * @param novosDados novos dados
     * @return {@code true} se editou
     * @throws IllegalStateException se ninguém fez login
     */
    public boolean editarCliente(int id, Cliente novosDados) {
        exigirLogin();
        return gerenciarClientes.editarCliente(id, novosDados);
    }

    /**
     * Remove um cliente. Qualquer funcionário logado pode.
     *
     * @param id identificador do cliente
     * @return {@code true} se removeu
     * @throws IllegalStateException se ninguém fez login
     */
    public boolean removerCliente(int id) {
        exigirLogin();
        return gerenciarClientes.removerCliente(id);
    }

    /**
     * Lança uma despesa. Qualquer funcionário logado pode.
     *
     * @param descricao descrição do gasto
     * @param valor     valor gasto
     * @return a despesa criada, com a data de hoje
     * @throws IllegalStateException se ninguém fez login
     */
    public Despesa lancarDespesa(String descricao, double valor) {
        exigirLogin();
        return financeiro.registrarDespesa(descricao, valor, LocalDate.now());
    }

    /**
     * Cadastra um colaborador ou administrador. Só o administrador pode.
     *
     * @param funcionario funcionário a cadastrar
     * @throws IllegalStateException se quem está logado não for administrador
     */
    public void cadastrarFuncionario(Funcionario funcionario) {
        exigirAdministrador();
        gerenciarFuncionarios.cadastrarFuncionario(funcionario);
    }

    /**
     * Edita os dados de um funcionário. Só o administrador pode.
     *
     * @param id         identificador do funcionário
     * @param novosDados novos dados
     * @return {@code true} se editou
     * @throws IllegalStateException se quem está logado não for administrador
     */
    public boolean editarFuncionario(int id, Funcionario novosDados) {
        exigirAdministrador();
        return gerenciarFuncionarios.editarFuncionario(id, novosDados);
    }

    /**
     * Remove um funcionário. Só o administrador pode, e ele não pode remover
     * a si mesmo enquanto está logado.
     *
     * @param id identificador do funcionário
     * @return {@code true} se removeu
     * @throws IllegalStateException se quem está logado não for administrador
     */
    public boolean removerFuncionario(int id) {
        exigirAdministrador();
        if (usuarioLogado.getId() == id) {
            return false;
        }
        return gerenciarFuncionarios.removerFuncionario(id);
    }

    /**
     * Altera a senha administrativa de um administrador. Só o administrador pode.
     *
     * @param idAdministrador identificador do administrador
     * @param senhaAtual      senha administrativa atual
     * @param novaSenha       nova senha administrativa
     * @return {@code true} se trocou; {@code false} se o id não for de um
     *         administrador ou a senha atual estiver errada
     * @throws IllegalStateException se quem está logado não for administrador
     */
    public boolean alterarSenhaAdm(int idAdministrador, String senhaAtual, String novaSenha) {
        exigirAdministrador();
        Funcionario funcionario = gerenciarFuncionarios.buscarFuncionarioPorId(idAdministrador);
        if (!(funcionario instanceof Administrador)) {
            return false;
        }
        Administrador administrador = (Administrador) funcionario;
        return administrador.alterarSenhaAdm(senhaAtual, novaSenha);
    }

    /**
     * Gera o balanço de um mês. Só o administrador pode.
     *
     * @param ano ano do balanço
     * @param mes mês do balanço (1 a 12)
     * @return o balanço
     * @throws IllegalStateException se quem está logado não for administrador
     */
    public Balanco gerarBalancoMensal(int ano, int mes) {
        exigirAdministrador();
        return financeiro.gerarBalancoMensal(ano, mes);
    }

    /**
     * Registra o pagamento da diária de hoje de um cliente.
     * <p>
     * O pagamento fica no cliente (é o que a catraca confere) e no financeiro.
     *
     * @param cliente cliente que paga a diária
     * @param forma   forma de pagamento
     * @return o pagamento criado
     * @throws IllegalStateException se ninguém fez login
     */
    public Pagamento registrarDiaria(Cliente cliente, TipoPagamento forma) {
        exigirLogin();
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
     * @throws IllegalStateException se ninguém fez login
     */
    public Pagamento registrarMensalidade(Cliente cliente, TipoPagamento forma) {
        exigirLogin();
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
     * @throws IllegalStateException se ninguém fez login
     */
    public Extrato finalizarVenda(Venda venda, TipoPagamento forma) {
        exigirLogin();
        Extrato extrato = venda.finalizar(forma);
        financeiro.registrarPagamento(venda.getPagamento());
        return extrato;
    }

    /**
     * Confirma um agendamento: cria o pagamento da aula (AULA), registra
     * esse pagamento no cliente e no financeiro e gera o extrato.
     *
     * @param codigo código do agendamento
     * @param forma  forma de pagamento
     * @return o extrato do agendamento, ou {@code null} se não pôde confirmar
     * @throws IllegalStateException se ninguém fez login
     */
    public Extrato confirmarAgendamento(int codigo, TipoPagamento forma) {
        exigirLogin();
        Agendamento agendamento = agenda.buscarAgendamento(codigo);
        if (agendamento == null || agendamento.confirmar(forma) == false) {
            return null;
        }

        Cliente cliente = agendamento.getCliente();
        Pagamento pagamento = agendamento.getPagamento();
        cliente.registrarPagamento(pagamento);
        financeiro.registrarPagamento(pagamento);

        String descricao = "Aula " + agendamento.getAula().getNome() + " em " + agendamento.getData()
                + " às " + agendamento.getAula().getHorario() + "\n";
        Extrato extrato = new Extrato(LocalDateTime.now(), cliente, descricao,
                pagamento.getValorPago(), forma);
        cliente.adicionarExtrato(extrato);
        return extrato;
    }

    /**
     * Cancela um agendamento. Se ele estava confirmado, devolve 50% e
     * registra o reembolso no financeiro como um pagamento CANCELAMENTO
     * de valor negativo. A academia fica com os outros 50%.
     *
     * @param codigo código do agendamento
     * @return valor devolvido ao cliente; 0 se era preliminar ou não pôde cancelar
     * @throws IllegalStateException se ninguém fez login
     */
    public double cancelarAgendamento(int codigo) {
        exigirLogin();
        Agendamento agendamento = agenda.buscarAgendamento(codigo);
        if (agendamento == null) {
            return 0;
        }

        double reembolso = agenda.cancelar(codigo);

        if (reembolso > 0) {
            Cliente cliente = agendamento.getCliente();
            TipoPagamento forma = agendamento.getPagamento().getTipoPagamento();
            Pagamento cancelamento = new Pagamento(cliente, forma, OrigemPagamento.CANCELAMENTO,
                    -reembolso, LocalDate.now());
            cliente.registrarPagamento(cancelamento);
            financeiro.registrarPagamento(cancelamento);
        }
        return reembolso;
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
        String logado = "ninguém logado";
        if (usuarioLogado != null) {
            logado = "logado: " + usuarioLogado.getUsuario();
        }
        return "Sistema da academia | " + logado + " | " + gerenciarClientes.listarClientes().size() + " cliente(s) | "
                + lojinha + " | " + lanchonete + " | " + financeiro;
    }
}
