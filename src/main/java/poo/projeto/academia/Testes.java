package poo.projeto.academia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import poo.projeto.academia.comparator.ClientePorId;
import poo.projeto.academia.comparator.ClientePorNome;
import poo.projeto.academia.comparator.ClientePorPlano;
import poo.projeto.academia.controller.GerenciarClientes;
import poo.projeto.academia.enums.TipoPlano;
import poo.projeto.academia.model.Cliente;

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
}
