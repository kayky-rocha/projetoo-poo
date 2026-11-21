package poo.projeto.academia.comparator;

import java.util.Comparator;
import poo.projeto.academia.model.Agendamento;

/**
 * Comparator que ordena os agendamentos pela data da aula.
 * <p>
 * Se dois agendamentos forem no mesmo dia, desempata pelo horário da aula.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Agendamento
 */
public class AgendamentoPorData implements Comparator<Agendamento> {

    /**
     * Compara dois agendamentos pela data e, no empate, pelo horário.
     *
     * @param agendamento1 primeiro agendamento
     * @param agendamento2 segundo agendamento
     * @return número negativo se o primeiro vem antes, zero se forem iguais,
     *         número positivo se o primeiro vem depois
     */
    @Override
    public int compare(Agendamento agendamento1, Agendamento agendamento2) {
        int dataCompare = agendamento1.getData().compareTo(agendamento2.getData()); // compara a data

        // testa a data primeiro
        if (dataCompare != 0) {
            return dataCompare;
        }

        int horarioCompare = agendamento1.getAula().getHorario()
                .compareTo(agendamento2.getAula().getHorario()); // compara o horário

        return horarioCompare;
    }
}
