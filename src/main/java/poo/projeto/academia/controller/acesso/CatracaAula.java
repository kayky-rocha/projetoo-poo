package poo.projeto.academia.controller.acesso;

import java.time.LocalDateTime;
import poo.projeto.academia.controller.Agenda;
import poo.projeto.academia.model.Agendamento;
import poo.projeto.academia.model.Aula;
import poo.projeto.academia.model.Cliente;

/**
 * Catraca da sala de aula (Pilates, Spinning etc.).
 * <p>
 * É separada da catraca de entrada e tem a sua própria cadeia, montada por
 * {@link ConfigurarAcesso#montarCadeiaAula}. Quando libera a entrada, marca o
 * agendamento como realizado, para que a mesma aula não libere duas vezes.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Catraca
 * @see ValidadorAgendamento
 */
public class CatracaAula {

    /** Primeiro elo da cadeia da aula. */
    private ValidadorAcesso primeiroValidador;

    /** Agenda usada para marcar o agendamento como realizado. */
    private Agenda agenda;

    /**
     * Cria a catraca da aula com a cadeia já montada.
     *
     * @param primeiroValidador primeiro elo da cadeia da aula
     * @param agenda            agenda com os agendamentos
     * @throws IllegalArgumentException se o validador for nulo
     */
    public CatracaAula(ValidadorAcesso primeiroValidador, Agenda agenda) {
        if (primeiroValidador == null) {
            throw new IllegalArgumentException("Validador nulo.");
        }
        this.primeiroValidador = primeiroValidador;
        this.agenda = agenda;
    }

    /**
     * Tenta registrar a entrada do cliente na aula agora.
     *
     * @param cliente cliente que quer entrar
     * @param aula    aula em que quer entrar
     * @return resultado da validação
     */
    public ResultadoAcesso registrarEntrada(Cliente cliente, Aula aula) {
        return registrarEntrada(cliente, aula, LocalDateTime.now());
    }

    /**
     * Tenta registrar a entrada do cliente na aula em uma data e hora.
     * <p>
     * Passa o pedido pela cadeia da aula. Se for liberado, chama
     * {@link Agendamento#realizar()} no agendamento daquela aula.
     *
     * @param cliente  cliente que quer entrar
     * @param aula     aula em que quer entrar
     * @param dataHora data e hora da tentativa
     * @return resultado da validação
     */
    public ResultadoAcesso registrarEntrada(Cliente cliente, Aula aula, LocalDateTime dataHora) {
        SolicitacaoAcesso solicitacao = new SolicitacaoAcesso(cliente, aula, dataHora);
        ResultadoAcesso resultado = primeiroValidador.validar(solicitacao);

        if (resultado.isLiberado()) {
            Agendamento agendamento = agenda.buscarAgendamentoConfirmado(cliente, aula, dataHora.toLocalDate());
            if (agendamento != null) {
                agendamento.realizar();
            }
        }
        return resultado;
    }
}
