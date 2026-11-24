package poo.projeto.academia.comparator;

import java.util.Comparator;
import poo.projeto.academia.model.Agendamento;

/**
 * Comparator que ordena os agendamentos pelo horário da aula. No empate, ordena pela data.
 * <p>
 * compara um campo por vez e, se der empate (zero),
 * passa para o próximo campo.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Agendamento
 */
public class AgendamentoPorHorario implements Comparator< Agendamento >
{
    /**
     * Cria o comparador por horário.
     */
    public AgendamentoPorHorario()
    {
    }

    /**
     * Compara dois agendamentos pelo horário (hora e minuto) e se empatar, pela data
     *
     * @param agendamento1 primeiro agendamento
     * @param agendamento2 segundo agendamento
     * @return número negativo se o primeiro vem antes, zero se forem iguais,
     *         número positivo se o primeiro vem depois
     */
    public int compare( Agendamento agendamento1, Agendamento agendamento2 )
    {
        int horaCompare = agendamento1.getAula().getHorario().getHour() -
            agendamento2.getAula().getHorario().getHour(); // compara a hora

        // testa a hora primeiro
        if ( horaCompare != 0 )
            return horaCompare;

        int minutoCompare = agendamento1.getAula().getHorario().getMinute() -
            agendamento2.getAula().getHorario().getMinute(); // compara o minuto

        // entao testa o minuto
        if ( minutoCompare != 0 )
            return minutoCompare;

        int anoCompare = agendamento1.getData().getYear() -
            agendamento2.getData().getYear(); // compara o ano

        // entao testa o ano
        if ( anoCompare != 0 )
            return anoCompare;

        int mesCompare = agendamento1.getData().getMonthValue() -
            agendamento2.getData().getMonthValue(); // compara o mês

        // entao testa o mês
        if ( mesCompare != 0 )
            return mesCompare;

        int diaCompare = agendamento1.getData().getDayOfMonth() -
            agendamento2.getData().getDayOfMonth(); // compara o dia

        return diaCompare; // retorna o resultado da comparação de dias
    } // fim do metodo compare

    /**
     * Retorna em texto o critério de ordenação.
     *
     * @return o objeto em texto
     */
    @Override
    public String toString() {
        return "Ordena agendamentos por horário (desempate: data)";
    }
} // fim da classe AgendamentoPorHorario
