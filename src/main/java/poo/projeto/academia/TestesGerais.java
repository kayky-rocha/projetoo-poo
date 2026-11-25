package poo.projeto.academia;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import poo.projeto.academia.controller.Financeiro;
import poo.projeto.academia.controller.GerenciarFuncionarios;
import poo.projeto.academia.controller.Lanchonete;
import poo.projeto.academia.controller.Lojinha;
import poo.projeto.academia.controller.acesso.Catraca;
import poo.projeto.academia.controller.acesso.CatracaAula;
import poo.projeto.academia.controller.acesso.EventoCatraca;
import poo.projeto.academia.controller.acesso.ResultadoAcesso;
import poo.projeto.academia.enums.CategoriaLanchonete;
import poo.projeto.academia.enums.OrigemPagamento;
import poo.projeto.academia.enums.TipoEvento;
import poo.projeto.academia.enums.TipoPagamento;
import poo.projeto.academia.enums.TipoPlano;
import poo.projeto.academia.enums.TipoSala;
import poo.projeto.academia.model.Administrador;
import poo.projeto.academia.model.Agendamento;
import poo.projeto.academia.model.Aula;
import poo.projeto.academia.model.Balanco;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.model.Despesa;
import poo.projeto.academia.model.Extrato;
import poo.projeto.academia.model.Funcionario;
import poo.projeto.academia.model.ItemCardapio;
import poo.projeto.academia.model.NotaFiscal;
import poo.projeto.academia.model.Pagamento;
import poo.projeto.academia.model.ProdutoLoja;
import poo.projeto.academia.model.RegistroCatraca;
import poo.projeto.academia.model.VendaLanchonete;
import poo.projeto.academia.model.VendaLojinha;
import poo.projeto.academia.persistencia.PersistenciaSistema;

/**
 * Testes das partes do sistema que a Questão 21 não cobre (item 3 do Notion).
 * <p>
 * Cada método testa uma área e cria o seu próprio {@link Sistema}, para que um
 * teste não interfira no outro. O {@code Main} chama {@link #executarTodos()}.
 *
 * @author Aaron Araujo Agapito Guedes
 * @author Kayky Francisco Rodrigues da Rocha
 */
public class TestesGerais {

    /**
     * Construtor padrão. A classe só tem métodos estáticos.
     */
    public TestesGerais() {
    }

    /**
     * Roda todos os testes desta classe, na ordem.
     */
    public static void executarTodos() {
        testarLoginEPermissoes();
        testarGerenciarFuncionarios();
        testarCatraca();
        testarLojinhaELanchonete();
        testarFinanceiro();
        testarJson();
    }

    /**
     * Cria um sistema com um administrador e um funcionário comum cadastrados.
     * O primeiro administrador entra direto pelo GerenciarFuncionarios, antes do login.
     *
     * @return o sistema pronto, sem ninguém logado
     */
    private static Sistema criarSistema() {
        Sistema sistema = new Sistema();
        Administrador adm = new Administrador("Marta", "900", "3391", "marta@academia.com", "Rua M",
                0, "Gerente", 5000.0, "marta", "123", "adm123");
        sistema.getGerenciarFuncionarios().cadastrarFuncionario(adm);
        Funcionario recepcao = new Funcionario("Paulo", "901", "3392", "paulo@academia.com", "Rua P",
                0, "Recepcionista", 2000.0, "paulo", "456");
        sistema.getGerenciarFuncionarios().cadastrarFuncionario(recepcao);
        return sistema;
    }

    /**
     * Imprime o título de um teste.
     *
     * @param titulo texto do título
     */
    private static void titulo(String titulo) {
        System.out.println("\n========== " + titulo + " ==========");
    }

    /**
     * Login e permissões: senha errada, funcionário comum barrado nas funções
     * do administrador, administrador liberado e logout.
     */
    public static void testarLoginEPermissoes() {
        titulo("LOGIN E PERMISSOES");
        Sistema sistema = criarSistema();

        // 1. Sem login, nada funciona
        try {
            sistema.registrarDiaria(new Cliente("Teste", "1", "1", "t@t.com", "Rua", 0, TipoPlano.DIARIA),
                    TipoPagamento.PIX);
        } catch (IllegalStateException e) {
            System.out.println("Sem login: " + e.getMessage());
        }

        // 2. Senha errada e usuário que não existe
        System.out.println("Login com senha errada: " + sistema.login("paulo", "000"));
        System.out.println("Login de usuário inexistente: " + sistema.login("joao", "123"));

        // 3. Funcionário comum: cadastra cliente, mas não gera balanço
        System.out.println("Login do Paulo: " + sistema.login("paulo", "456"));
        System.out.println("Está logado? " + sistema.estaLogado());
        System.out.println("É administrador? " + sistema.isAdministradorLogado());
        Cliente ana = new Cliente("Ana", "111", "3399", "ana@email.com", "Rua A", 0, TipoPlano.MENSALIDADE);
        sistema.cadastrarCliente(ana);
        System.out.println("Paulo cadastrou: " + ana.getNome() + " (id " + ana.getId() + ")");
        try {
            sistema.gerarBalancoMensal(2026, 11);
        } catch (IllegalStateException e) {
            System.out.println("Paulo tentou gerar o balanço: " + e.getMessage());
        }
        try {
            sistema.cadastrarFuncionario(new Funcionario("Rui", "902", "3393", "rui@academia.com", "Rua R",
                    0, "Instrutor", 2500.0, "rui", "789"));
        } catch (IllegalStateException e) {
            System.out.println("Paulo tentou cadastrar funcionário: " + e.getMessage());
        }

        // 4. Logout e login do administrador
        sistema.logout();
        System.out.println("Depois do logout, está logado? " + sistema.estaLogado());
        System.out.println("Login da Marta: " + sistema.login("marta", "123"));
        System.out.println("É administrador? " + sistema.isAdministradorLogado());
        System.out.println("Usuário logado: " + sistema.getUsuarioLogado().getNome());
        Balanco balanco = sistema.gerarBalancoMensal(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        System.out.println("Marta gerou o balanço. Saldo: " + balanco.getSaldo());

        // 5. Alterar a senha do administrador
        int idMarta = sistema.getUsuarioLogado().getId();
        System.out.println("Alterar senha com a senha atual errada: "
                + sistema.alterarSenhaAdm(idMarta, "errada", "nova123"));
        System.out.println("Alterar senha com a senha atual certa: "
                + sistema.alterarSenhaAdm(idMarta, "adm123", "nova123"));
        Administrador marta = (Administrador) sistema.getUsuarioLogado();
        System.out.println("A senha nova vale? " + marta.validarSenhaAdm("nova123"));
        sistema.logout();
    }

    /**
     * Gerenciar funcionários: cadastrar, usuário repetido, buscar, editar,
     * listar e remover, sempre pelo Sistema com o administrador logado.
     */
    public static void testarGerenciarFuncionarios() {
        titulo("GERENCIAR FUNCIONARIOS");
        Sistema sistema = criarSistema();
        sistema.login("marta", "123");
        GerenciarFuncionarios gerenciar = sistema.getGerenciarFuncionarios();

        // 1. Cadastrar
        Funcionario rui = new Funcionario("Rui", "902", "3393", "rui@academia.com", "Rua R",
                0, "Instrutor", 2500.0, "rui", "789");
        sistema.cadastrarFuncionario(rui);
        System.out.println("Cadastrado: " + rui.getNome() + " (id " + rui.getId() + ")");

        // 2. Usuário repetido
        try {
            sistema.cadastrarFuncionario(new Funcionario("Outro Rui", "903", "3390", "o@academia.com", "Rua O",
                    0, "Instrutor", 2500.0, "rui", "000"));
        } catch (IllegalArgumentException e) {
            System.out.println("Usuário repetido: " + e.getMessage());
        }

        // 3. Buscar pelo usuário e pelo id
        System.out.println("Buscar pelo usuário 'rui': " + gerenciar.buscarFuncionario("rui").getNome());
        System.out.println("Buscar pelo id " + rui.getId() + ": " + gerenciar.buscarFuncionarioPorId(rui.getId()).getNome());

        // 4. Editar o cargo e o salário
        Funcionario novosDados = new Funcionario("Rui", "902", "3393", "rui@academia.com", "Rua R",
                0, "Coordenador", 3200.0, "rui", "789");
        System.out.println("Editou? " + sistema.editarFuncionario(rui.getId(), novosDados));
        System.out.println("Cargo novo: " + rui.getCargo() + ", salário: " + rui.getSalario());
        System.out.println("Editar id que não existe: " + sistema.editarFuncionario(99, novosDados));

        // 5. Listar
        System.out.println("Funcionários cadastrados:");
        for (Funcionario f : gerenciar.listarFuncionarios()) {
            System.out.println("  " + f.getId() + " - " + f.getNome() + " (" + f.getCargo() + ")");
        }

        // 6. Remover: o administrador não remove a si mesmo
        int idMarta = sistema.getUsuarioLogado().getId();
        System.out.println("Marta remover a si mesma: " + sistema.removerFuncionario(idMarta));
        System.out.println("Remover o Rui: " + sistema.removerFuncionario(rui.getId()));
        System.out.println("Remover de novo: " + sistema.removerFuncionario(rui.getId()));
        System.out.println("Total de funcionários: " + gerenciar.listarFuncionarios().size());
        sistema.logout();
    }

    /**
     * Catraca: entrada, saída, eventos da catraca externa (receberEvento),
     * CatracaAula e os relatórios diário, mensal e anual.
     */
    public static void testarCatraca() {
        titulo("CATRACA");
        Sistema sistema = criarSistema();
        sistema.login("paulo", "456");
        Catraca catraca = sistema.getCatraca();

        Cliente bia = new Cliente("Bia", "211", "3381", "bia@email.com", "Rua B", 0, TipoPlano.MENSALIDADE);
        Cliente caio = new Cliente("Caio", "212", "3382", "caio@email.com", "Rua C", 0, TipoPlano.DIARIA);
        sistema.cadastrarCliente(bia);
        sistema.cadastrarCliente(caio);
        sistema.registrarMensalidade(bia, TipoPagamento.PIX);
        sistema.registrarDiaria(caio, TipoPagamento.DINHEIRO);

        // 1. Entrada e saída direto na catraca
        LocalDateTime hoje8h = LocalDate.now().atTime(8, 0);
        System.out.println("Bia entra: " + catraca.registrarEntrada(bia, hoje8h).getMotivo());
        System.out.println("Bia entra de novo: " + catraca.registrarEntrada(bia, hoje8h.plusMinutes(5)).getMotivo());
        System.out.println("Bia sai: " + catraca.registrarSaida(bia, hoje8h.plusHours(1)));
        System.out.println("Bia sai de novo: " + catraca.registrarSaida(bia, hoje8h.plusHours(2)));

        // 2. Catraca externa avisando o sistema (receberEvento)
        EventoCatraca entrada = new EventoCatraca(caio.getId(), TipoEvento.ENTRADA, hoje8h.plusHours(2));
        EventoCatraca saida = new EventoCatraca(caio.getId(), TipoEvento.SAIDA, hoje8h.plusHours(3));
        EventoCatraca desconhecido = new EventoCatraca(99, TipoEvento.ENTRADA, hoje8h.plusHours(3));
        System.out.println("Evento de entrada do Caio: " + catraca.receberEvento(entrada).getMotivo());
        System.out.println("Evento de saída do Caio: " + catraca.receberEvento(saida).getMotivo());
        System.out.println("Evento de saída sem entrada: " + catraca.receberEvento(saida).getMotivo());
        System.out.println("Evento de cliente id 99: " + catraca.receberEvento(desconhecido).getMotivo());

        // 3. Registros de outros dias, para os relatórios mensal e anual
        LocalDateTime mesPassado = LocalDate.now().minusMonths(1).atTime(18, 0);
        catraca.registrarEntrada(bia, mesPassado);
        catraca.registrarSaida(bia, mesPassado.plusMinutes(90));

        // 4. Relatórios
        LocalDate hoje = LocalDate.now();
        ArrayList<RegistroCatraca> diario = catraca.relatorioDiario(hoje);
        ArrayList<RegistroCatraca> mensal = catraca.relatorioMensal(hoje.getYear(), hoje.getMonthValue());
        ArrayList<RegistroCatraca> anual = catraca.relatorioAnual(hoje.getYear());
        System.out.println("Relatório diário (" + diario.size() + " registros):");
        for (RegistroCatraca r : diario) {
            System.out.println("  " + r);
        }
        System.out.println("Relatório mensal: " + mensal.size() + " registros");
        System.out.println("Relatório anual: " + anual.size() + " registros");

        // 5. CatracaAula: só entra quem tem agendamento confirmado naquela aula
        CatracaAula catracaAula = sistema.getCatracaAula();
        Aula pilates = new Aula(1, "Pilates", Sistema.buscarSala(TipoSala.PILATES), null,
                LocalTime.of(19, 0), 40.0, 25.0);
        Agendamento agendamento = sistema.getAgenda().agendar(bia, pilates, hoje);
        LocalDateTime horaDaAula = hoje.atTime(19, 0);
        System.out.println("Bia na aula sem confirmar: "
                + catracaAula.registrarEntrada(bia, pilates, horaDaAula).getMotivo());
        sistema.confirmarAgendamento(agendamento.getId(), TipoPagamento.CREDITO);
        System.out.println("Bia na aula confirmada: "
                + catracaAula.registrarEntrada(bia, pilates, horaDaAula).getMotivo());
        System.out.println("Status do agendamento: " + agendamento.getStatus());
        System.out.println("Bia tenta entrar de novo: "
                + catracaAula.registrarEntrada(bia, pilates, horaDaAula).getMotivo());
        System.out.println("Caio na aula sem agendamento: "
                + catracaAula.registrarEntrada(caio, pilates, horaDaAula).getMotivo());
        sistema.logout();
    }

    /**
     * Lojinha e lanchonete: cadastro, estoque, vendas com e sem cliente,
     * erros esperados e relatórios de vendas do dia e do mês.
     */
    public static void testarLojinhaELanchonete() {
        titulo("LOJINHA E LANCHONETE");
        Sistema sistema = criarSistema();
        sistema.login("paulo", "456");
        Lojinha lojinha = sistema.getLojinha();
        Lanchonete lanchonete = sistema.getLanchonete();

        Cliente dani = new Cliente("Dani", "311", "3371", "dani@email.com", "Rua D", 0, TipoPlano.MENSALIDADE);
        sistema.cadastrarCliente(dani);

        // 1. Lojinha: cadastro e estoque
        ProdutoLoja luva = new ProdutoLoja("Luva de treino", 45.0, 3, "Acessório");
        ProdutoLoja garrafa = new ProdutoLoja("Garrafa 1L", 30.0, 10, "Acessório");
        lojinha.cadastrarProduto(luva);
        lojinha.cadastrarProduto(garrafa);
        System.out.println("Produtos: " + lojinha.listarProdutos().size());
        System.out.println("Tem 2 luvas? " + lojinha.verificarEstoque(luva.getId(), 2));
        System.out.println("Tem 5 luvas? " + lojinha.verificarEstoque(luva.getId(), 5));
        System.out.println("Produto id 99 existe? " + lojinha.verificarEstoque(99, 1));

        // 2. Venda com cliente
        VendaLojinha venda = lojinha.vender(dani);
        venda.adicionarItem(luva, 2);
        venda.adicionarItem(garrafa, 1);
        System.out.println("Total da venda: " + venda.total());
        Extrato extrato = sistema.finalizarVenda(venda, TipoPagamento.DEBITO);
        extrato.imprimir();
        System.out.println("Estoque de luvas depois da venda: " + luva.getEstoque());

        // 3. Estoque insuficiente e reposição
        VendaLojinha outra = lojinha.vender(null);
        try {
            outra.adicionarItem(luva, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Estoque insuficiente: " + e.getMessage());
        }
        System.out.println("Repor 4 luvas: " + lojinha.reporEstoque(luva.getId(), 4));
        System.out.println("Estoque de luvas depois de repor: " + luva.getEstoque());
        outra.adicionarItem(luva, 5);
        sistema.finalizarVenda(outra, TipoPagamento.DINHEIRO);
        System.out.println("Venda de balcão finalizada: " + outra.total());

        // 4. Lanchonete: cardápio, item indisponível e pedido
        ItemCardapio suco = new ItemCardapio("Suco de laranja", 8.0, CategoriaLanchonete.BEBIDA);
        ItemCardapio sanduiche = new ItemCardapio("Sanduíche natural", 15.0, CategoriaLanchonete.LANCHE);
        lanchonete.cadastrarItem(suco);
        lanchonete.cadastrarItem(sanduiche);
        sanduiche.setDisponivel(false);
        VendaLanchonete pedido = lanchonete.registrarPedido(dani);
        pedido.adicionarItem(suco, 2);
        try {
            pedido.adicionarItem(sanduiche, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Item indisponível: " + e.getMessage());
        }
        Extrato extratoPedido = sistema.finalizarVenda(pedido, TipoPagamento.PIX);
        extratoPedido.imprimir();

        // 5. Relatórios de vendas (só as finalizadas)
        LocalDate hoje = LocalDate.now();
        System.out.println("Vendas da lojinha hoje: " + lojinha.relatorioVendas(hoje).size());
        System.out.println("Vendas da lojinha no mês: "
                + lojinha.relatorioVendasMes(hoje.getYear(), hoje.getMonthValue()).size());
        System.out.println("Pedidos da lanchonete hoje: " + lanchonete.relatorioVendas(hoje).size());
        System.out.println("Pedidos da lanchonete no mês: "
                + lanchonete.relatorioVendasMes(hoje.getYear(), hoje.getMonthValue()).size());
        System.out.println("Compras no histórico da Dani: " + dani.getPagamentos().size());
        System.out.println("Extratos da Dani: " + dani.getExtratos().size());
        sistema.logout();
    }

    /**
     * Financeiro: pagamentos de várias origens, cancelamento negativo, notas
     * fiscais, despesas, relatórios de vendas e o balanço mensal.
     */
    public static void testarFinanceiro() {
        titulo("FINANCEIRO, DESPESAS E BALANCO");
        Sistema sistema = criarSistema();
        sistema.login("paulo", "456");
        Financeiro financeiro = sistema.getFinanceiro();
        LocalDate hoje = LocalDate.now();

        Cliente edu = new Cliente("Edu", "411", "3361", "edu@email.com", "Rua E", 0, TipoPlano.DIARIA);
        Cliente fia = new Cliente("Fia", "412", "3362", "fia@email.com", "Rua F", 0, TipoPlano.MENSALIDADE);
        sistema.cadastrarCliente(edu);
        sistema.cadastrarCliente(fia);

        // 1. Receitas: diária, mensalidade, aula confirmada e aula cancelada (50%)
        Pagamento diaria = sistema.registrarDiaria(edu, TipoPagamento.DINHEIRO);
        sistema.registrarMensalidade(fia, TipoPagamento.PIX);
        Aula spinning = new Aula(2, "Spinning", Sistema.buscarSala(TipoSala.SPINNING), null,
                LocalTime.of(7, 0), 40.0, 20.0);
        Agendamento aula = sistema.getAgenda().agendar(fia, spinning, hoje.plusDays(1));
        sistema.confirmarAgendamento(aula.getId(), TipoPagamento.CREDITO);
        double reembolso = sistema.cancelarAgendamento(aula.getId());
        System.out.println("Reembolso do cancelamento: " + reembolso);

        // 2. Buscar pagamento e somar
        System.out.println("Pagamento da diária: " + financeiro.buscarPagamento(diaria.getId()));
        ArrayList<Pagamento> doDia = financeiro.relatorioVendasDia(hoje);
        System.out.println("Pagamentos de hoje: " + doDia.size() + ", total líquido: " + financeiro.somar(doDia));
        System.out.println("Pagamentos do mês: "
                + financeiro.relatorioVendasMes(hoje.getYear(), hoje.getMonthValue()).size());
        System.out.println("Total de AULA no mês: "
                + financeiro.totalPorOrigem(OrigemPagamento.AULA, hoje.getYear(), hoje.getMonthValue()));
        System.out.println("Total de CANCELAMENTO no mês: "
                + financeiro.totalPorOrigem(OrigemPagamento.CANCELAMENTO, hoje.getYear(), hoje.getMonthValue()));

        // 3. Notas fiscais: uma por pagamento
        System.out.println("Notas emitidas: " + financeiro.listarNotas().size());
        for (int id : fia.getIdsNotas()) {
            NotaFiscal nota = financeiro.buscarNota(id);
            System.out.println("  Nota da Fia: " + nota);
        }

        // 4. Despesas
        Despesa luz = sistema.lancarDespesa("Conta de luz", 400.0);
        sistema.lancarDespesa("Material de limpeza", 80.0);
        System.out.println("Despesa lançada: " + luz);
        System.out.println("Despesas: " + financeiro.listarDespesas().size());

        // 5. Balanço do mês (só o administrador)
        sistema.logout();
        sistema.login("marta", "123");
        Balanco balanco = sistema.gerarBalancoMensal(hoje.getYear(), hoje.getMonthValue());
        System.out.println(balanco);
        System.out.println("Receita de MENSALIDADE: " + balanco.getReceita(OrigemPagamento.MENSALIDADE));
        System.out.println("Média diária: " + balanco.mediaDiariaReceita());
        System.out.println("Valor médio por pagamento: " + balanco.ticketMedio());
        sistema.logout();
    }

    /**
     * Persistência em JSON: salva um sistema, carrega em outro e confere os
     * dados. Depois cria um produto, uma venda e um extrato novos e confere
     * que os ids não repetem os que vieram do JSON.
     */
    public static void testarJson() {
        titulo("SALVAR E CARREGAR JSON");
        String pasta = "dados-teste";

        // 1. Monta um sistema com dados e salva
        Sistema original = criarSistema();
        original.login("paulo", "456");
        Cliente gil = new Cliente("Gil", "511", "3351", "gil@email.com", "Rua G", 0, TipoPlano.MENSALIDADE);
        original.cadastrarCliente(gil);
        original.registrarMensalidade(gil, TipoPagamento.PIX);
        ProdutoLoja toalha = new ProdutoLoja("Toalha", 25.0, 6, "Acessório");
        original.getLojinha().cadastrarProduto(toalha);
        VendaLojinha venda = original.getLojinha().vender(gil);
        venda.adicionarItem(toalha, 1);
        original.finalizarVenda(venda, TipoPagamento.DINHEIRO);
        original.getCatraca().registrarEntrada(gil);

        PersistenciaSistema persistencia = new PersistenciaSistema(pasta);
        try {
            persistencia.salvarTudo(original);
            System.out.println("Sistema salvo na pasta " + pasta);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
            return;
        }

        // 2. Carrega em um sistema novo, como se o programa abrisse de novo
        Sistema carregado = new Sistema();
        try {
            persistencia.carregarTudo(carregado);
        } catch (IOException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
            return;
        }
        Cliente gilCarregado = carregado.getGerenciarClientes().buscarCliente(gil.getId());
        System.out.println("Clientes carregados: " + carregado.getGerenciarClientes().listarClientes().size());
        System.out.println("Cliente carregado: " + gilCarregado.getNome()
                + ", pagamentos: " + gilCarregado.getPagamentos().size()
                + ", notas: " + gilCarregado.getIdsNotas().size());
        System.out.println("Funcionários carregados: " + carregado.getGerenciarFuncionarios().listarFuncionarios().size());
        System.out.println("O login funciona depois de carregar? " + carregado.login("paulo", "456"));
        System.out.println("Estoque da toalha carregado: "
                + carregado.getLojinha().buscarProduto(toalha.getId()).getEstoque());
        System.out.println("Pagamentos carregados: " + carregado.getFinanceiro().listarPagamentos().size());
        System.out.println("Notas carregadas: " + carregado.getFinanceiro().listarNotas().size());
        System.out.println("Registros da catraca carregados: " + carregado.getCatraca().listarRegistros().size());

        // 3. Teste dos ids: os novos não podem repetir os carregados
        int maiorProduto = 0;
        for (ProdutoLoja p : carregado.getLojinha().listarProdutos()) {
            if (p.getId() > maiorProduto) {
                maiorProduto = p.getId();
            }
        }
        int maiorVenda = 0;
        for (VendaLojinha v : carregado.getLojinha().listarVendas()) {
            if (v.getId() > maiorVenda) {
                maiorVenda = v.getId();
            }
        }
        int maiorExtrato = 0;
        for (Extrato e : gilCarregado.getExtratos()) {
            if (e.getId() > maiorExtrato) {
                maiorExtrato = e.getId();
            }
        }

        ProdutoLoja novoProduto = new ProdutoLoja("Corda de pular", 20.0, 4, "Acessório");
        carregado.getLojinha().cadastrarProduto(novoProduto);
        VendaLojinha novaVenda = carregado.getLojinha().vender(gilCarregado);
        novaVenda.adicionarItem(novoProduto, 1);
        Extrato novoExtrato = carregado.finalizarVenda(novaVenda, TipoPagamento.PIX);

        System.out.println("Produto: maior id carregado " + maiorProduto + ", id novo " + novoProduto.getId()
                + " -> " + (novoProduto.getId() > maiorProduto ? "OK, não repetiu" : "ERRO, repetiu"));
        System.out.println("Venda: maior id carregado " + maiorVenda + ", id novo " + novaVenda.getId()
                + " -> " + (novaVenda.getId() > maiorVenda ? "OK, não repetiu" : "ERRO, repetiu"));
        System.out.println("Extrato: maior id carregado " + maiorExtrato + ", id novo " + novoExtrato.getId()
                + " -> " + (novoExtrato.getId() > maiorExtrato ? "OK, não repetiu" : "ERRO, repetiu"));
        System.out.println("buscarProduto(" + novoProduto.getId() + ") devolve: "
                + carregado.getLojinha().buscarProduto(novoProduto.getId()).getNome());
        carregado.logout();
    }

    /**
     * Retorna em texto o que esta classe faz.
     *
     * @return o objeto em texto
     */
    @Override
    public String toString() {
        return "TestesGerais: testes das áreas que a Questão 21 não cobre";
    }
}
