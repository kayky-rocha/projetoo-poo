package poo.projeto.academia.controller;

import java.util.ArrayList;
import java.util.Objects;
import poo.projeto.academia.model.Funcionario;

/**
 * Gerencia o cadastro de funcionários e administradores.
 * <p>
 * Armazena todos na mesma lista, já que {@code Administrador} é um
 * {@code Funcionario}. Atribui o id no cadastro e impede nomes de
 * usuário repetidos.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Funcionario
 */
public class GerenciarFuncionarios {

    /** Funcionários e administradores cadastrados. */
    private ArrayList<Funcionario> funcionarios;

    /** Próximo id a ser atribuído a um funcionário. */
    private int proximoId;

    /**
     * Cria o gerenciador com a lista vazia.
     */
    public GerenciarFuncionarios() {
        this.funcionarios = new ArrayList<>();
        this.proximoId = 1;
    }

    /**
     * Cadastra um funcionário e define o seu id.
     *
     * @param funcionario funcionário a ser cadastrado
     * @throws IllegalArgumentException se for nulo ou o usuário já existir
     */
    public void cadastrarFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário nulo.");
        }
        if (usuarioEmUso(funcionario.getUsuario(), -1)) {
            throw new IllegalArgumentException("Usuário já cadastrado.");
        }
        funcionario.setId(proximoId++);
        funcionarios.add(funcionario);
    }

    /**
     * Substitui os dados de um funcionário, mantendo o seu id.
     *
     * @param id         id do funcionário a editar
     * @param novosDados funcionário com os dados atualizados
     * @return {@code true} se editou; {@code false} se não encontrou
     * @throws IllegalArgumentException se o usuário novo já pertencer a outro funcionário
     */
    public boolean editarFuncionario(int id, Funcionario novosDados) {
        int indice = indiceDe(id);
        if (indice == -1 || novosDados == null) {
            return false;
        }
        if (usuarioEmUso(novosDados.getUsuario(), id)) {
            throw new IllegalArgumentException("Usuário já cadastrado.");
        }
        Funcionario existente = funcionarios.get(indice);
        existente.setNome(novosDados.getNome());
        existente.setUsuario(novosDados.getUsuario());
        existente.setSenha(novosDados.getSenha());
        existente.setCargo(novosDados.getCargo());
        existente.setSalario(novosDados.getSalario());
        existente.setCpf(novosDados.getCpf());
        existente.setTelefone(novosDados.getTelefone());
        existente.setEmail(novosDados.getEmail());
        existente.setEndereco(novosDados.getEndereco());
        return true;
    }

    /**
     * Remove um funcionário pelo id.
     *
     * @param id id do funcionário a remover
     * @return {@code true} se removeu; {@code false} se não encontrou
     */
    public boolean removerFuncionario(int id) {
        int indice = indiceDe(id);
        if (indice == -1) {
            return false;
        }
        funcionarios.remove(indice);
        return true;
    }

    /**
     * Busca um funcionário pelo nome de usuário (usado no login).
     *
     * @param usuario nome de usuário
     * @return o funcionário, ou {@code null} se não existir
     */
    public Funcionario buscarFuncionario(String usuario) {
        for (Funcionario f : funcionarios) {
            if (Objects.equals(f.getUsuario(), usuario)) {
                return f;
            }
        }
        return null;
    }

    /**
     * Lista todos os funcionários cadastrados.
     *
     * @return cópia da lista de funcionários
     */
    public ArrayList<Funcionario> listarFuncionarios() {
        return new ArrayList<>(funcionarios);
    }

    /**
     * Procura a posição de um funcionário na lista.
     *
     * @param id id do funcionário
     * @return índice na lista, ou -1 se não encontrou
     */
    private int indiceDe(int id) {
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Verifica se um nome de usuário já pertence a algum funcionário.
     *
     * @param usuario    usuário a verificar
     * @param idIgnorado id do funcionário a desconsiderar (usado na edição)
     * @return {@code true} se o usuário já estiver em uso
     */
    private boolean usuarioEmUso(String usuario, int idIgnorado) {
        for (Funcionario f : funcionarios) {
            if (f.getId() != idIgnorado && Objects.equals(f.getUsuario(), usuario)) {
                return true;
            }
        }
        return false;
    }
}