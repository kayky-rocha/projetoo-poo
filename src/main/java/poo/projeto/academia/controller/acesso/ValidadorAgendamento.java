package poo.projeto.academia.controller.acesso;

import java.time.LocalDate;
import poo.projeto.academia.controller.Agenda;
import poo.projeto.academia.model.Cliente;

/**
 * Elo da catraca que confere se o cliente tem agendamento confirmado
 * para o dia da entrada.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see ValidadorAcesso
 * @see Agenda
 */
public class ValidadorAgendamento extends ValidadorAcesso {

    /** Agenda onde os agendamentos são procurados. */
    private Agenda agenda;

    /**
     * Cria o validador de agendamento.
     *
     * @param agenda agenda com os agendamentos
     */
    public ValidadorAgendamento(Agenda agenda) {
        this.agenda = agenda;
    }

    /**
     * Reprova se o cliente não tiver agendamento confirmado hoje.
     *
     * @param solicitacao pedido de acesso
     * @return resultado da verificação de agendamento
     */
    @Override
    protected ResultadoAcesso verificar(SolicitacaoAcesso solicitacao) {
        Cliente cliente = solicitacao.getCliente();
        LocalDate hoje = solicitacao.getDataHora().toLocalDate();

        if (agenda.temAgendamentoConfirmado(cliente, hoje) == false) {
            return new ResultadoAcesso(false, "Sem agendamento confirmado para hoje.");
        }

        return new ResultadoAcesso(true, "Acesso liberado.");
    }
}
