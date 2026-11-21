package poo.projeto.academia.comparator;

import java.util.Comparator;
import poo.projeto.academia.model.Agendamento;

/**
 * Comparator que ordena os agendamentos pelo nome do cliente, em ordem alfabética.
 * <p>
 * Se for o mesmo nome, desempata pela data da aula.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Agendamento
 */
public class AgendamentoPorCliente implements Comparator<Agendamento> {

    /**
     * Compara dois agendamentos pelo nome do cliente e, no empate, pela data.
     *
     * @param agendamento1 primeiro agendamento
     * @param agendamento2 segundo agendamento
     * @return número negativo se o primeiro vem antes, zero se forem iguais,
     *         número positivo se o primeiro vem depois
     */
    @Override
    public int compare(Agendamento agendamento1, Agendamento agendamento2) {
        int nomeCompare = agendamento1.getCliente().getNome()
                .compareTo(agendamento2.getCliente().getNome()); // compara o nome

        // testa o nome primeiro
        if (nomeCompare != 0) {
            return nomeCompare;
        }

        int dataCompare = agendamento1.getData().compareTo(agendamento2.getData()); // compara a data

        return dataCompare;
    }
}
