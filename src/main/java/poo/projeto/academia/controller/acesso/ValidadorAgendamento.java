package poo.projeto.academia.controller.acesso;

import java.time.LocalDate;
import poo.projeto.academia.controller.Agenda;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.enums.TipoCobranca;

/**
 * Elo da catraca que confere se o diarista tem agendamento confirmado
 * para o dia da entrada.
 * <p>
 * A diária é cobrada na confirmação do agendamento, então ele é o
 * comprovante do diarista. Mensalistas não precisam de agendamento e
 * passam direto.
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
     * Reprova diaristas sem agendamento confirmado hoje.
     *
     * @param solicitacao pedido de acesso
     * @return resultado da verificação de agendamento
     */
    @Override
    protected ResultadoAcesso verificar(SolicitacaoAcesso solicitacao) {
        Cliente cliente = solicitacao.getCliente();
        
        if (cliente.getPlano() == TipoCobranca.MENSALIDADE) {
            return new ResultadoAcesso(true, "Acesso liberado.");
        }

        LocalDate hoje = solicitacao.getDataHora().toLocalDate();

        if (agenda.temAgendamentoConfirmado(cliente, hoje) == false) {
            return new ResultadoAcesso(false, "Sem agendamento confirmado para hoje.");
        }

        return new ResultadoAcesso(true, "Acesso liberado.");
    }
}
