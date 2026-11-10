package poo.projeto.academia.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import poo.projeto.academia.enums.StatusAgendamento;
import poo.projeto.academia.model.Agendamento;
import poo.projeto.academia.model.Aula;
import poo.projeto.academia.model.Cliente;

/**
 * Gerencia os agendamentos da academia.
 * <p>
 * Cria agendamentos conferindo se ainda há vaga na aula naquela data,
 * busca agendamentos e diz se um cliente tem agendamento confirmado
 * em um dia (usado pela catraca).
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Agendamento
 */
public class Agenda {

    /** Agendamentos registrados. */
    private ArrayList<Agendamento> agendamentos;

    /** Próximo código a ser atribuído a um agendamento. */
    private int proximoCodigo;

    /**
     * Cria a agenda vazia.
     */
    public Agenda() {
        this.agendamentos = new ArrayList<>();
        this.proximoCodigo = 1;
    }

    /**
     * Cria um agendamento preliminar, se houver vaga.
     *
     * @param cliente cliente que quer agendar
     * @param aula    aula desejada
     * @param data    data da aula
     * @return o agendamento criado
     * @throws IllegalArgumentException se faltar algum dado ou a aula estiver lotada
     */
    public Agendamento agendar(Cliente cliente, Aula aula, LocalDate data) {
        if (cliente == null || aula == null || data == null) {
            throw new IllegalArgumentException("Dados do agendamento incompletos.");
        }
        if (temVaga(aula, data) == false) {
            throw new IllegalArgumentException("Aula lotada nesta data.");
        }

        Agendamento agendamento = new Agendamento(proximoCodigo, cliente, aula, data);
        proximoCodigo = proximoCodigo + 1;
        agendamentos.add(agendamento);
        return agendamento;
    }

    /**
     * Verifica se ainda há vaga em uma aula numa data.
     * Agendamentos cancelados não ocupam vaga.
     *
     * @param aula aula a verificar
     * @param data data da aula
     * @return {@code true} se ainda há vaga
     */
    public boolean temVaga(Aula aula, LocalDate data) {
        int ocupadas = 0;
        for (Agendamento a : agendamentos) {
            if (a.getAula() == aula && a.getData().equals(data)
                    && a.getStatus() != StatusAgendamento.CANCELADO) {
                ocupadas = ocupadas + 1;
            }
        }
        return ocupadas < aula.getCapacidade();
    }

    /**
     * Busca um agendamento pelo código.
     *
     * @param codigo código do agendamento
     * @return o agendamento, ou {@code null} se não existir
     */
    public Agendamento buscarAgendamento(int codigo) {
        for (Agendamento a : agendamentos) {
            if (a.getCodigo() == codigo) {
                return a;
            }
        }
        return null;
    }

    /**
     * Lista os agendamentos de um cliente.
     *
     * @param cliente cliente procurado
     * @return lista com os agendamentos dele
     */
    public ArrayList<Agendamento> listarPorCliente(Cliente cliente) {
        ArrayList<Agendamento> resultado = new ArrayList<>();
        for (Agendamento a : agendamentos) {
            if (a.getCliente().getId() == cliente.getId()) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    /**
     * Diz se o cliente tem algum agendamento confirmado na data.
     *
     * @param cliente cliente procurado
     * @param data    dia a verificar
     * @return {@code true} se tiver agendamento confirmado nesse dia
     */
    public boolean temAgendamentoConfirmado(Cliente cliente, LocalDate data) {
        for (Agendamento a : listarPorCliente(cliente)) {
            if (a.getData().equals(data) && a.getStatus() == StatusAgendamento.CONFIRMADO) {
                return true;
            }
        }
        return false;
    }

    /**
     * Cancela os agendamentos preliminares que passaram do prazo.
     *
     * @param hoje data usada como referência
     * @return quantos agendamentos foram cancelados
     */
    public int cancelarExpirados(LocalDate hoje) {
        int cancelados = 0;
        for (Agendamento a : agendamentos) {
            if (a.expirou(hoje)) {
                a.cancelar();
                cancelados = cancelados + 1;
            }
        }
        return cancelados;
    }

    /**
     * Lista todos os agendamentos.
     *
     * @return cópia da lista de agendamentos
     */
    public ArrayList<Agendamento> listarAgendamentos() {
        return new ArrayList<>(agendamentos);
    }
}
