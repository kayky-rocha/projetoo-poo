package poo.projeto.academia.comparator;

import java.util.Comparator;
import poo.projeto.academia.model.Agendamento;

/**
 * Comparator que ordena os agendamentos pelo horário da aula.
 * <p>
 * Se duas aulas forem no mesmo horário, desempata pela data.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Agendamento
 */
public class AgendamentoPorHorario implements Comparator<Agendamento> {

    /**
     * Compara dois agendamentos pelo horário e, no empate, pela data.
     *
     * @param agendamento1 primeiro agendamento
     * @param agendamento2 segundo agendamento
     * @return número negativo se o primeiro vem antes, zero se forem iguais,
     *         número positivo se o primeiro vem depois
     */
    @Override
    public int compare(Agendamento agendamento1, Agendamento agendamento2) {
        int horarioCompare = agendamento1.getAula().getHorario()
                .compareTo(agendamento2.getAula().getHorario()); // compara o horário

        // testa o horário primeiro
        if (horarioCompare != 0) {
            return horarioCompare;
        }

        int dataCompare = agendamento1.getData().compareTo(agendamento2.getData()); // compara a data

        return dataCompare;
    }
}
