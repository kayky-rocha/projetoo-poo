package poo.projeto.academia.comparator;

import java.util.Comparator;
import poo.projeto.academia.model.Agendamento;

/**
 * Comparator que ordena os agendamentos pela data da aula. No empate, ordena pelo horário.
 * <p>
 * Segue o modelo da classe TimeComparator do livro Java: Como Programar
 * (Deitel, Figura 20.8): compara um campo por vez e, se der empate (zero),
 * passa para o próximo campo.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Agendamento
 */
public class AgendamentoPorData implements Comparator< Agendamento >
{
    /**
     * Compara dois agendamentos pela data (ano, mês e dia) e, no empate, pelo horário.
     *
     * @param agendamento1 primeiro agendamento
     * @param agendamento2 segundo agendamento
     * @return número negativo se o primeiro vem antes, zero se forem iguais,
     *         número positivo se o primeiro vem depois
     */
    public int compare( Agendamento agendamento1, Agendamento agendamento2 )
    {
        int anoCompare = agendamento1.getData().getYear() -
            agendamento2.getData().getYear(); // compara o ano

        // testa o ano primeiro
        if ( anoCompare != 0 )
            return anoCompare;

        int mesCompare = agendamento1.getData().getMonthValue() -
            agendamento2.getData().getMonthValue(); // compara o mês

        // então testa o mês
        if ( mesCompare != 0 )
            return mesCompare;

        int diaCompare = agendamento1.getData().getDayOfMonth() -
            agendamento2.getData().getDayOfMonth(); // compara o dia

        // então testa o dia
        if ( diaCompare != 0 )
            return diaCompare;

        int horaCompare = agendamento1.getAula().getHorario().getHour() -
            agendamento2.getAula().getHorario().getHour(); // compara a hora

        // então testa a hora
        if ( horaCompare != 0 )
            return horaCompare;

        int minutoCompare = agendamento1.getAula().getHorario().getMinute() -
            agendamento2.getAula().getHorario().getMinute(); // compara o minuto

        return minutoCompare; // retorna o resultado da comparação de minutos
    } // fim do método compare
} // fim da classe AgendamentoPorData
