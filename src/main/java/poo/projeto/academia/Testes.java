package poo.projeto.academia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.time.LocalDate;
import java.time.LocalTime;
import poo.projeto.academia.comparator.ClientePorId;
import poo.projeto.academia.comparator.ClientePorNome;
import poo.projeto.academia.comparator.ClientePorPlano;
import poo.projeto.academia.controller.GerenciarClientes;
import poo.projeto.academia.enums.TipoPlano;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.enums.CategoriaLanchonete;
import poo.projeto.academia.enums.TipoPagamento;
import poo.projeto.academia.enums.TipoSala;
import poo.projeto.academia.model.Administrador;
import poo.projeto.academia.model.Agendamento;
import poo.projeto.academia.model.Aula;
import poo.projeto.academia.model.ItemCardapio;
import poo.projeto.academia.model.NotaFiscal;
import poo.projeto.academia.model.ProdutoLoja;
import poo.projeto.academia.model.VendaLanchonete;
import poo.projeto.academia.model.VendaLojinha;

/**
 * Testes do sistema, um método por questão do enunciado.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 */
public class Testes {

    /**
     * Construtor padrão. A classe só tem métodos estáticos.
     */
    public Testes() {
    }
    /**
     * Cria um gerenciador com três clientes fora de ordem, usado nas questões 18 a 20.
     *
     * @return gerenciador com clientes cadastrados
     */
    private static GerenciarClientes clientesDeTeste() {
        GerenciarClientes g = new GerenciarClientes();
        g.cadastrarCliente(new Cliente("Carlos", "201", "3401", "carlos@email.com", "Rua 1", 0, TipoPlano.MENSALIDADE));
        g.cadastrarCliente(new Cliente("Ana", "202", "3402", "ana@email.com", "Rua 2", 0, TipoPlano.DIARIA));
        g.cadastrarCliente(new Cliente("Bruno", "203", "3403", "bruno@email.com", "Rua 3", 0, TipoPlano.MENSALIDADE));
        return g;
    }

    /**
     * Questão 18: Iterator e foreach.
     * <p>
     * O foreach é a forma curta do mesmo Iterator: o compilador troca o
     * foreach por um while com hasNext e next.
     */
    public static void questao18() {
        // Questao 18 {
        System.out.println("\n=== QUESTÃO 18 ===");
        ArrayList<Cliente> lista = clientesDeTeste().listarClientes();
        System.out.println("Com Iterator:");
        Iterator<Cliente> iterator = lista.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("Com foreach:");
        for (Cliente c : lista) {
            System.out.println(c);
        }
        // }
    }

    /**
     * Questão 19: Collections.sort com dois critérios diferentes.
     */
    public static void questao19() {
        // Questao 19 {
        System.out.println("\n=== QUESTÃO 19 ===");
        GerenciarClientes g = clientesDeTeste();
        System.out.println("Por nome:");
        for (Cliente c : g.listarClientes(new ClientePorNome())) {
            System.out.println(c.getNome());
        }
        System.out.println("Por plano:");
        for (Cliente c : g.listarClientes(new ClientePorPlano())) {
            System.out.println(c.getNome() + " - " + c.getPlano());
        }
        // }
    }

    /**
     * Questão 20: encontrar (Iterator) comparado com Collections.binarySearch.
     * <p>
     * O binarySearch exige a lista ordenada pelo mesmo comparator. Quando não
     * acha, devolve um número negativo.
     */
    public static void questao20() {
        // Questao 20 {
        System.out.println("\n=== QUESTÃO 20 ===");
        GerenciarClientes g = clientesDeTeste();
        Cliente chave = new Cliente("", "x", "", "", "", 3, TipoPlano.DIARIA);
        System.out.println("encontrar id 3: " + g.encontrar(chave, new ClientePorId()));

        ArrayList<Cliente> ordenada = g.listarClientes(new ClientePorId());
        int pos = Collections.binarySearch(ordenada, chave, new ClientePorId());
        System.out.println("binarySearch id 3: posição " + pos + " -> " + ordenada.get(pos).getNome());

        Cliente inexistente = new Cliente("", "y", "", "", "", 9, TipoPlano.DIARIA);
        System.out.println("encontrar id 9: " + g.encontrar(inexistente, new ClientePorId()));
        System.out.println("binarySearch id 9: " + Collections.binarySearch(ordenada, inexistente, new ClientePorId()));
        // }
    }

     /**
     * Questão 21: atendimento de dez clientes, do cadastro ao fim.
     * <p>
     * Cada cliente passa por um processo diferente e termina com a emissão
     * de uma nota fiscal ou com o cancelamento da matrícula.
     */
    public static void questao21() {
        // Questao 21 {
        System.out.println("\n=== QUESTÃO 21 ===");

        Sistema sistema = new Sistema();
        sistema.getGerenciarFuncionarios().cadastrarFuncionario(new Administrador("Marta", "900", "3390",
                "marta@email.com", "Rua M", 0, "Gerente", 3000.0, "marta", "123", "adm123"));
        sistema.login("marta", "123");
        ProdutoLoja whey = new ProdutoLoja("Whey 900g", 120.0, 5, "Suplemento");
        ItemCardapio suco = new ItemCardapio("Suco de laranja", 8.0, CategoriaLanchonete.BEBIDA);
        sistema.getLojinha().cadastrarProduto(whey);
        sistema.getLanchonete().cadastrarItem(suco);
        Aula spinning = new Aula(1, "Spinning", Sistema.buscarSala(TipoSala.SPINNING), sistema.getUsuarioLogado(),
                LocalTime.of(18, 0), 30.0, 150.0);
        
        Cliente ana = new Cliente("Ana", "101", "3301", "ana@email.com", "Rua A", 0, TipoPlano.MENSALIDADE);
        Cliente bruno = new Cliente("Bruno", "102", "3302", "bruno@email.com", "Rua B", 0, TipoPlano.MENSALIDADE);
        Cliente carla = new Cliente("Carla", "103", "3303", "carla@email.com", "Rua C", 0, TipoPlano.MENSALIDADE);
        Cliente davi = new Cliente("Davi", "104", "3304", "davi@email.com", "Rua D", 0, TipoPlano.DIARIA);
        Cliente eva = new Cliente("Eva", "105", "3305", "eva@email.com", "Rua E", 0, TipoPlano.DIARIA);
        Cliente fabio = new Cliente("Fábio", "106", "3306", "fabio@email.com", "Rua F", 0, TipoPlano.DIARIA);
        Cliente gabi = new Cliente("Gabi", "107", "3307", "gabi@email.com", "Rua G", 0, TipoPlano.MENSALIDADE);
        Cliente hugo = new Cliente("Hugo", "108", "3308", "hugo@email.com", "Rua H", 0, TipoPlano.DIARIA);
        Cliente iara = new Cliente("Iara", "109", "3309", "iara@email.com", "Rua I", 0, TipoPlano.MENSALIDADE);
        Cliente joao = new Cliente("João", "110", "3310", "joao@email.com", "Rua J", 0, TipoPlano.MENSALIDADE);
        sistema.cadastrarCliente(ana);
        sistema.cadastrarCliente(bruno);
        sistema.cadastrarCliente(carla);
        sistema.cadastrarCliente(davi);
        sistema.cadastrarCliente(eva);
        sistema.cadastrarCliente(fabio);
        sistema.cadastrarCliente(gabi);
        sistema.cadastrarCliente(hugo);
        sistema.cadastrarCliente(iara);
        sistema.cadastrarCliente(joao);
        System.out.println("Clientes cadastrados: " + sistema.getGerenciarClientes().listarClientes().size());

        // 1. Ana: mensalidade, entra e sai. Termina com a nota fiscal.
        sistema.registrarMensalidade(ana, TipoPagamento.PIX);
        System.out.println("Ana entra: " + sistema.getCatraca().registrarEntrada(ana).getMotivo());
        System.out.println("Ana sai: " + sistema.getCatraca().registrarSaida(ana));
        imprimirUltimaNota(sistema, ana);

        // 2. Bruno: com a mensalidade vencida (simulada) não cancela. Termina com a nota.
        sistema.registrarMensalidade(bruno, TipoPagamento.CREDITO);
        System.out.println("Bruno entra: " + sistema.getCatraca().registrarEntrada(bruno).getMotivo());
        try {
            sistema.cancelarMatricula(bruno, LocalDate.now().plusMonths(2));
        } catch (IllegalArgumentException e) {
            System.out.println("Bruno, mensalidade vencida: " + e.getMessage());
        }
        imprimirUltimaNota(sistema, bruno);

        // 3. Carla: não pagou, é barrada e não cancela. Paga e cancela a matrícula.
        System.out.println("Carla entra sem pagar: " + sistema.getCatraca().registrarEntrada(carla).getMotivo());
        try {
            sistema.cancelarMatricula(carla);
        } catch (IllegalArgumentException e) {
            System.out.println("Carla, com pendência: " + e.getMessage());
        }
        sistema.registrarMensalidade(carla, TipoPagamento.DEBITO);
        sistema.cancelarMatricula(carla);
        System.out.println("Carla cancelou a matrícula. Ativa? " + carla.isAtivo());
        System.out.println("Carla entra depois de cancelar: " + sistema.getCatraca().registrarEntrada(carla).getMotivo());

        // 4. Davi: diária paga, entra. Termina com a nota.
        sistema.registrarDiaria(davi, TipoPagamento.DINHEIRO);
        System.out.println("Davi entra: " + sistema.getCatraca().registrarEntrada(davi).getMotivo());
        imprimirUltimaNota(sistema, davi);

        // 5. Eva: tenta entrar sem a diária, é barrada, paga e entra. Termina com a nota.
        System.out.println("Eva entra sem pagar: " + sistema.getCatraca().registrarEntrada(eva).getMotivo());
        sistema.registrarDiaria(eva, TipoPagamento.PIX);
        System.out.println("Eva entra depois de pagar: " + sistema.getCatraca().registrarEntrada(eva).getMotivo());
        imprimirUltimaNota(sistema, eva);

        // 6. Fábio: diária, agenda e confirma a aula, entra na aula. Termina com a nota da aula.
        sistema.registrarDiaria(fabio, TipoPagamento.DEBITO);
        Agendamento agFabio = sistema.getAgenda().agendar(fabio, spinning, LocalDate.now());
        System.out.println("Fábio agendou. Status: " + agFabio.getStatus());
        sistema.confirmarAgendamento(agFabio.getId(), TipoPagamento.PIX);
        System.out.println("Fábio na aula: " + sistema.getCatracaAula().registrarEntrada(fabio, spinning).getMotivo());
        imprimirUltimaNota(sistema, fabio);

        // 7. Gabi: confirma a aula, cancela (devolve 50%) e cancela a matrícula.
        sistema.registrarMensalidade(gabi, TipoPagamento.PIX);
        Agendamento agGabi = sistema.getAgenda().agendar(gabi, spinning, LocalDate.now());
        sistema.confirmarAgendamento(agGabi.getId(), TipoPagamento.PIX);
        double reembolso = sistema.cancelarAgendamento(agGabi.getId());
        System.out.println("Gabi cancelou a aula e recebeu R$ " + reembolso);
        imprimirUltimaNota(sistema, gabi);
        sistema.cancelarMatricula(gabi);
        System.out.println("Gabi cancelou a aula e recebeu R$ " + String.format("%.2f", reembolso));

        // 8. Hugo: compra na lojinha (baixa no estoque) e na lanchonete. Termina com a nota.
        System.out.println("Estoque do whey antes: " + whey.getEstoque());
        VendaLojinha compra = sistema.getLojinha().vender(hugo);
        compra.adicionarItem(whey, 2);
        sistema.finalizarVenda(compra, TipoPagamento.CREDITO);
        System.out.println("Estoque do whey depois de vender 2: " + whey.getEstoque());
        VendaLojinha grande = sistema.getLojinha().vender(hugo);
        try {
            grande.adicionarItem(whey, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Hugo tentou 10 wheys: " + e.getMessage());
        }
        VendaLanchonete pedido = sistema.getLanchonete().registrarPedido(hugo);
        pedido.adicionarItem(suco, 1);
        sistema.finalizarVenda(pedido, TipoPagamento.DINHEIRO);
        imprimirUltimaNota(sistema, hugo);

        // 9. Iara: cancela a matrícula, é barrada e volta. Termina com a nota.
        sistema.registrarMensalidade(iara, TipoPagamento.PIX);
        sistema.cancelarMatricula(iara);
        System.out.println("Iara cancelada tenta entrar: " + sistema.getCatraca().registrarEntrada(iara).getMotivo());
        iara.reativarMatricula();
        System.out.println("Iara reativada entra: " + sistema.getCatraca().registrarEntrada(iara).getMotivo());
        imprimirUltimaNota(sistema, iara);

        // 10. João: com pendência não cancela. Paga e cancela. O cadastro continua guardado.
        try {
            sistema.cancelarMatricula(joao);
        } catch (IllegalArgumentException e) {
            System.out.println("João, com pendência: " + e.getMessage());
        }
        sistema.registrarMensalidade(joao, TipoPagamento.DEBITO);
        sistema.cancelarMatricula(joao);
        System.out.println("João cancelou a matrícula. Ativo? " + joao.isAtivo());
        System.out.println("João continua cadastrado? "
                + (sistema.getGerenciarClientes().buscarCliente(joao.getId()) != null));

        System.out.println(sistema.getFinanceiro());
        // }
    }

    /**
     * Imprime a nota fiscal mais recente de um cliente.
     *
     * @param sistema sistema com o financeiro
     * @param cliente cliente da nota
     */
    private static void imprimirUltimaNota(Sistema sistema, Cliente cliente) {
        ArrayList<Integer> ids = cliente.getIdsNotas();
        NotaFiscal nota = sistema.getFinanceiro().buscarNota(ids.get(ids.size() - 1));
        System.out.println("  Nota de " + cliente.getNome() + ": " + nota);
    }
}
