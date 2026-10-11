package poo.projeto.academia.controller;

import java.util.ArrayList;
import java.util.Objects;
import poo.projeto.academia.model.Cliente;

/**
 * Gerencia o cadastro de clientes da academia.
 * <p>
 * Armazena os clientes em memória, atribui o id de cada um no
 * cadastro e impede CPFs repetidos.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Cliente
 */
public class GerenciarClientes {

    /** Clientes cadastrados no sistema. */
    private ArrayList<Cliente> clientes;

    /** Próximo id a ser atribuído a um cliente. */
    private int proximoId;

    /**
     * Cria o gerenciador com a lista vazia.
     */
    public GerenciarClientes() {
        this.clientes = new ArrayList<>();
        this.proximoId = 1;
    }

    /**
     * Cadastra um novo cliente e define o seu id.
     *
     * @param cliente cliente a ser cadastrado
     * @throws IllegalArgumentException se o cliente for nulo ou o CPF já estiver cadastrado
     */
    public void cadastrarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nulo.");
        }
        if (cpfEmUso(cliente.getCpf(), -1)) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        cliente.setId(proximoId++);
        clientes.add(cliente);
    }

    /**
     * Substitui os dados de um cliente existente, mantendo o seu id.
     *
     * @param id         id do cliente a editar
     * @param novosDados cliente com os dados atualizados
     * @return {@code true} se editou; {@code false} se não encontrou
     * @throws IllegalArgumentException se o CPF novo já pertencer a outro cliente
     */
    public boolean editarCliente(int id, Cliente novosDados) {
        int indice = indiceDe(id);
        if (indice == -1 || novosDados == null) {
            return false;
        }
        if (cpfEmUso(novosDados.getCpf(), id)) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        novosDados.setId(id);
        clientes.set(indice, novosDados);
        return true;
    }

    /**
     * Remove um cliente pelo id.
     *
     * @param id id do cliente a remover
     * @return {@code true} se removeu; {@code false} se não encontrou
     */
    public boolean removerCliente(int id) {
        int indice = indiceDe(id);
        if (indice == -1) {
            return false;
        }
        clientes.remove(indice);
        return true;
    }

    /**
     * Busca um cliente pelo id.
     *
     * @param id id do cliente
     * @return o cliente, ou {@code null} se não existir
     */
    public Cliente buscarCliente(int id) {
        int indice = indiceDe(id);
        return indice == -1 ? null : clientes.get(indice);
    }

    /**
     * Lista todos os clientes cadastrados.
     *
     * @return cópia da lista de clientes
     */
    public ArrayList<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    /**
     * Procura a posição de um cliente na lista.
     *
     * @param id id do cliente
     * @return índice na lista, ou -1 se não encontrou
     */
    private int indiceDe(int id) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Verifica se um CPF já pertence a algum cliente.
     *
     * @param cpf        CPF a verificar
     * @param idIgnorado id do cliente a desconsiderar (usado na edição)
     * @return {@code true} se o CPF já estiver em uso
     */
    private boolean cpfEmUso(String cpf, int idIgnorado) {
        for (Cliente c : clientes) {
            if (c.getId() != idIgnorado && Objects.equals(c.getCpf(), cpf)) {
                return true;
            }
        }
        return false;
    }
}