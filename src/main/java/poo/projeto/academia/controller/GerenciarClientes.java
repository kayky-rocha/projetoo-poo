package poo.projeto.academia.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.model.Extrato;

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
     * Atualiza os dados de um cliente existente.
     * <p>
     * Altera o próprio objeto cadastrado, mantendo o id e o histórico
     * de pagamentos.
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
        Cliente existente = clientes.get(indice);
        existente.setNome(novosDados.getNome());
        existente.setCpf(novosDados.getCpf());
        existente.setTelefone(novosDados.getTelefone());
        existente.setEmail(novosDados.getEmail());
        existente.setEndereco(novosDados.getEndereco());
        existente.setPlano(novosDados.getPlano());
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
     * Lista os clientes em uma ordem escolhida.
     * <p>
     * Usa o {@link Collections#sort} com um objeto Comparator, como
     * {@code new ClientePorNome()}. A lista original não muda.
     *
     * @param comparador define a ordem da lista
     * @return cópia da lista de clientes, já ordenada
     */
    public ArrayList<Cliente> listarClientes(Comparator<Cliente> comparador) {
        ArrayList<Cliente> lista = new ArrayList<>(clientes);
        Collections.sort(lista, comparador);
        return lista;
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

    /**
     * Troca a lista de clientes pela que foi carregada do JSON.
     * <p>
     * O próximo id passa a ser o maior id da lista mais 1, para não repetir.
     *
     * @param lista clientes carregados
     */
    public void restaurar(ArrayList<Cliente> lista) {
        clientes = lista;
        proximoId = 1;
        for (Cliente c : clientes) {
            if (c.getId() >= proximoId) {
                proximoId = c.getId() + 1;
            }
            for (Extrato e : c.getExtratos()){
                Extrato.atualizarTotalExtratos(e.getId());
            }
        }
    }

    /**
     * Retorna o cadastro de clientes em texto.
     *
     * @return o objeto em texto
     */
    @Override
    public String toString() {
        return "GerenciarClientes[clientes=" + clientes.size() + ", proximoId=" + proximoId + "]";
    }
}
