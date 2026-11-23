package poo.projeto.academia;

import java.time.LocalDate;
import poo.projeto.academia.controller.GerenciarClientes;
import poo.projeto.academia.controller.acesso.Catraca;
import poo.projeto.academia.controller.acesso.ConfigurarAcesso;
import poo.projeto.academia.controller.acesso.ValidadorAcesso;
import poo.projeto.academia.controller.acesso.ResultadoAcesso;
import poo.projeto.academia.enums.OrigemPagamento;
import poo.projeto.academia.enums.TipoPlano;
import poo.projeto.academia.enums.CategoriaLanchonete;
import poo.projeto.academia.enums.TipoPagamento;
import poo.projeto.academia.enums.TipoSala;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.model.ItemCardapio;
import poo.projeto.academia.model.Pagamento;
import poo.projeto.academia.model.ProdutoLoja;
import poo.projeto.academia.model.Sala;

/**
 * Ponto de entrada do Sistema de Gerenciamento de Academia.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 */
public class Main {

    /**
     * Construtor padrão. A classe é usada apenas pelo método {@link #main(String[])}.
     */
    public Main() {
    }

    /**
     * Inicia a aplicação. Por enquanto, testa a catraca em seis cenários.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        LocalDate hoje = LocalDate.now();

        GerenciarClientes gerenciarClientes = new GerenciarClientes();

        Cliente emDia = new Cliente("Ana", "111", "3399", "ana@email.com", "Rua A", 0, TipoPlano.MENSALIDADE);
        gerenciarClientes.cadastrarCliente(emDia);
        pagarMensalidade(emDia, hoje.minusDays(10));

        Cliente vencido = new Cliente("Bruno", "222", "3398", "bruno@email.com", "Rua B", 0, TipoPlano.MENSALIDADE);
        gerenciarClientes.cadastrarCliente(vencido);
        pagarMensalidade(vencido, hoje.minusMonths(2));

        Cliente semPagamento = new Cliente("Fábio", "666", "3394", "fabio@email.com", "Rua F", 0, TipoPlano.MENSALIDADE);
        gerenciarClientes.cadastrarCliente(semPagamento);

        Cliente diarista = new Cliente("Carla", "333", "3397", "carla@email.com", "Rua C", 0, TipoPlano.DIARIA);
        gerenciarClientes.cadastrarCliente(diarista);

        Cliente semConfirmar = new Cliente("Eva", "555", "3395", "eva@email.com", "Rua E", 0, TipoPlano.DIARIA);
        gerenciarClientes.cadastrarCliente(semConfirmar);

        Cliente naoCadastrado = new Cliente("Davi", "444", "3396", "davi@email.com", "Rua D", 99, TipoPlano.DIARIA);

        Pagamento diaria = new Pagamento(diarista, TipoPagamento.DINHEIRO,
                OrigemPagamento.DIARIA, 30.0, hoje);
        diarista.registrarPagamento(diaria);

        ValidadorAcesso cadeia = ConfigurarAcesso.montarCadeiaEntrada(gerenciarClientes);
        Catraca catraca = new Catraca(cadeia);

        testar(catraca, emDia);
        testar(catraca, vencido);
        testar(catraca, semPagamento);
        testar(catraca, diarista);
        testar(catraca, semConfirmar);
        testar(catraca, naoCadastrado);

        testarSalas();
        testarContadores();
    }

    /**
     * Testa as 4 salas estáticas do Sistema.
     */
    private static void testarSalas(){
        System.out.println("\n=== Salas ===");
        for (Sala sala : Sistema.getSalas()){
            System.out.println(sala);
        }
        System.out.println("Sala de pilates: " + Sistema.buscarSala(TipoSala.PILATES));
    }

    /**
     * Testa os contadores estáticos de Cliente e o método de classe que conta instâncias.
     */
    private static void testarContadores() {
        System.out.println("\n=== CONTADORES ===");
        new ProdutoLoja("Whey 900g", 120.0, 5, "Suplemento");
        new ItemCardapio("Suco de laranja", 8.0, CategoriaLanchonete.BEBIDA);

        System.out.println("private static (via get): " + Sistema.getTotalClientes());
        System.out.println("protected static (direto, mesmo pacote): " + Sistema.totalClientesProtegido);
        System.out.println(Sistema.contarInstancias());

        try {
            Sistema.setTotalClientes(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
    }

    /**
     * Registra no cliente um pagamento de mensalidade.
     *
     * @param cliente       cliente que paga
     * @param dataPagamento data em que a mensalidade foi paga
     */
    private static void pagarMensalidade(Cliente cliente, LocalDate dataPagamento) {
        Pagamento pagamento = new Pagamento(cliente, TipoPagamento.PIX,
                OrigemPagamento.MENSALIDADE, 150.0, dataPagamento);
        cliente.registrarPagamento(pagamento);
    }

    /**
     * Passa um cliente pela catraca e imprime o resultado.
     *
     * @param catraca catraca usada no teste
     * @param cliente cliente que tenta entrar
     */
    private static void testar(Catraca catraca, Cliente cliente) {
        ResultadoAcesso resultado = catraca.registrarEntrada(cliente);
        System.out.println(cliente.getNome() + " -> " + resultado.getMotivo());
    }

    /**
     * Retorna em texto o que esta classe faz.
     *
     * @return o objeto em texto
     */
    @Override
    public String toString() {
        return "Main: ponto de entrada do sistema da academia";
    }
}
