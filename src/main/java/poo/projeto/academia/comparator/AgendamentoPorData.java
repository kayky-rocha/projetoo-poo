package poo.projeto.academia.comparator;

import java.util.Comparator;
import poo.projeto.academia.model.Agendamento;

/**
 * Comparator que ordena os agendamentos pela data da aula. No empate, ordena pelo horário.
 * <p>
 * compara um campo por vez e, se der empate (zero),
 * passa para o próximo campo.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Agendamento
 */
public class AgendamentoPorData implements Comparator< Agendamento >
{
    /**
     * Cria o comparador por data.
     */
    public AgendamentoPorData()
    {
    }

    /**
     * Compara dois agendamentos pela data (ano, mês e dia) e, no empate, pelo horário.
     *
     * @param agendamento1 primeiro agendamento
     * @param agendamento2 segundo agendamento
     * @return numero negativo se o primeiro vem antes, zero se forem iguais,
     *         numero positivo se o primeiro vem depois
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

        // entao testa o mes
        if ( mesCompare != 0 )
            return mesCompare;

        int diaCompare = agendamento1.getData().getDayOfMonth() -
            agendamento2.getData().getDayOfMonth(); // compara o dia

        // entao testa o dia
        if ( diaCompare != 0 )
            return diaCompare;

        int horaCompare = agendamento1.getAula().getHorario().getHour() -
            agendamento2.getAula().getHorario().getHour(); // compara a hora

        // entao testa a hora
        if ( horaCompare != 0 )
            return horaCompare;

        int minutoCompare = agendamento1.getAula().getHorario().getMinute() -
            agendamento2.getAula().getHorario().getMinute(); // compara o minuto

        return minutoCompare; // retorna o resultado da comparação de minutos
    } // fim do metodo compare

    /**
     * Retorna em texto o critério de ordenação.
     *
     * @return o objeto em texto
     */
    @Override
    public String toString() {
        return "Ordena agendamentos por data (desempate: horário)";
    }
} // fim da classe AgendamentoPorData
