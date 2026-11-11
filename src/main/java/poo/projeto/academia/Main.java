package poo.projeto.academia;

import java.time.LocalDate;
import java.time.LocalTime;
import poo.projeto.academia.controller.Agenda;
import poo.projeto.academia.controller.GerenciarClientes;
import poo.projeto.academia.controller.acesso.Catraca;
import poo.projeto.academia.controller.acesso.ResultadoAcesso;
import poo.projeto.academia.enums.TipoCobranca;
import poo.projeto.academia.enums.TipoPagamento;
import poo.projeto.academia.enums.TipoSala;
import poo.projeto.academia.model.Agendamento;
import poo.projeto.academia.model.Aula;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.model.Funcionario;
import poo.projeto.academia.model.Pagamento;
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
        Agenda agenda = new Agenda();

        Funcionario instrutor = new Funcionario("Rita", "900", "3390", "rita@email.com", "Rua Z",
                0, "Instrutora", 2500.0, "rita", "123");
        Sala sala = new Sala(1, TipoSala.SPINNING, 10);
        Aula spinning = new Aula(1, "Spinning", sala, instrutor, LocalTime.of(8, 0), 30.0, 150.0);

        Cliente emDia = new Cliente("Ana", "111", "3399", "ana@email.com", "Rua A", 0, TipoCobranca.MENSALIDADE);
        gerenciarClientes.cadastrarCliente(emDia);
        pagarMensalidade(emDia, hoje.minusDays(10));

        Cliente vencido = new Cliente("Bruno", "222", "3398", "bruno@email.com", "Rua B", 0, TipoCobranca.MENSALIDADE);
        gerenciarClientes.cadastrarCliente(vencido);
        pagarMensalidade(vencido, hoje.minusMonths(2));

        Cliente semPagamento = new Cliente("Fábio", "666", "3394", "fabio@email.com", "Rua F", 0, TipoCobranca.MENSALIDADE);
        gerenciarClientes.cadastrarCliente(semPagamento);

        Cliente diarista = new Cliente("Carla", "333", "3397", "carla@email.com", "Rua C", 0, TipoCobranca.DIARIA);
        gerenciarClientes.cadastrarCliente(diarista);

        Cliente semConfirmar = new Cliente("Eva", "555", "3395", "eva@email.com", "Rua E", 0, TipoCobranca.DIARIA);
        gerenciarClientes.cadastrarCliente(semConfirmar);

        Cliente naoCadastrado = new Cliente("Davi", "444", "3396", "davi@email.com", "Rua D", 99, TipoCobranca.DIARIA);

        Agendamento agAna = agenda.agendar(emDia, spinning, hoje);
        agAna.confirmar();

        Agendamento agCarla = agenda.agendar(diarista, spinning, hoje);
        agCarla.confirmar();

        agenda.agendar(semConfirmar, spinning, hoje);

        Catraca catraca = new Catraca(gerenciarClientes, agenda);

        testar(catraca, emDia);
        testar(catraca, vencido);
        testar(catraca, semPagamento);
        testar(catraca, diarista);
        testar(catraca, semConfirmar);
        testar(catraca, naoCadastrado);
    }

    /**
     * Registra no cliente um pagamento de mensalidade.
     *
     * @param cliente       cliente que paga
     * @param dataPagamento data em que a mensalidade foi paga
     */
    private static void pagarMensalidade(Cliente cliente, LocalDate dataPagamento) {
        Pagamento pagamento = new Pagamento(cliente, TipoPagamento.PIX,
                TipoCobranca.MENSALIDADE, 150.0, dataPagamento);
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
}