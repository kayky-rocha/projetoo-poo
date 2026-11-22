package poo.projeto.academia.controller.acesso;

import java.time.LocalDate;
import poo.projeto.academia.controller.Agenda;
import poo.projeto.academia.model.Aula;
import poo.projeto.academia.model.Cliente;

/**
 * Elo da cadeia da aula que confere se o cliente tem agendamento
 * confirmado para aquela aula no dia.
 * <p>
 * Vale para todos os clientes, inclusive o mensalista: para entrar em uma
 * aula (Pilates, Spinning etc.) é preciso ter agendado e confirmado.
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
     * Reprova quem não tem agendamento confirmado na aula hoje.
     *
     * @param solicitacao pedido de acesso, com a aula
     * @return resultado da verificação de agendamento
     */
    @Override
    protected ResultadoAcesso verificar(SolicitacaoAcesso solicitacao) {
        Cliente cliente = solicitacao.getCliente();
        Aula aula = solicitacao.getAula();

        if (aula == null) {
            return new ResultadoAcesso(false, "Aula não informada.");
        }

        LocalDate hoje = solicitacao.getDataHora().toLocalDate();

        if (agenda.temAgendamentoConfirmado(cliente, aula, hoje) == false) {
            return new ResultadoAcesso(false, "Sem agendamento confirmado para esta aula hoje.");
        }

        return new ResultadoAcesso(true, "Acesso liberado.");
    }

    /**
     * Retorna o validador em texto.
     *
     * @return o objeto em texto
     */
    @Override
    public String toString() {
        return "ValidadorAgendamento[confere o agendamento confirmado na aula, " + super.toString() + "]";
    }
}
