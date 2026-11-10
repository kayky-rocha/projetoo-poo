package poo.projeto.academia;

import java.time.LocalDate;
import poo.projeto.academia.controller.GerenciarClientes;
import poo.projeto.academia.controller.acesso.Catraca;
import poo.projeto.academia.controller.acesso.ResultadoAcesso;
import poo.projeto.academia.enums.TipoCobranca;
import poo.projeto.academia.model.Cliente;

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
     * Inicia a aplicação. Por enquanto, testa a catraca em quatro cenários.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        GerenciarClientes gerenciarClientes = new GerenciarClientes();

        Cliente emDia = new Cliente("Ana", "111", "3399", "ana@email.com", "Rua A", 0, TipoCobranca.MENSALIDADE);
        emDia.setDataVencimento(LocalDate.now().plusDays(10));
        gerenciarClientes.cadastrarCliente(emDia);

        Cliente vencido = new Cliente("Bruno", "222", "3398", "bruno@email.com", "Rua B", 0, TipoCobranca.MENSALIDADE);
        vencido.setDataVencimento(LocalDate.now().minusDays(3));
        gerenciarClientes.cadastrarCliente(vencido);

        Cliente diarista = new Cliente("Carla", "333", "3397", "carla@email.com", "Rua C", 0, TipoCobranca.DIARIA);
        gerenciarClientes.cadastrarCliente(diarista);

        Cliente naoCadastrado = new Cliente("Davi", "444", "3396", "davi@email.com", "Rua D", 99, TipoCobranca.DIARIA);

        Catraca catraca = new Catraca(gerenciarClientes);

        testar(catraca, emDia);
        testar(catraca, vencido);
        testar(catraca, diarista);
        testar(catraca, naoCadastrado);
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
