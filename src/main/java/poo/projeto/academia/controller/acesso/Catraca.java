package poo.projeto.academia.controller.acesso;

import java.time.LocalDateTime;
import poo.projeto.academia.controller.Agenda;
import poo.projeto.academia.controller.GerenciarClientes;
import poo.projeto.academia.model.Cliente;

/**
 * Catraca de entrada da academia.
 * <p>
 * Monta a cadeia de validadores (Chain of Responsibility) e só conhece
 * o primeiro elo. Para incluir uma regra nova, basta criar o validador
 * e ligá-lo na cadeia dentro do construtor.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see ValidadorAcesso
 */
public class Catraca {

    /** Primeiro elo da cadeia de validação. */
    private ValidadorAcesso primeiroValidador;

    /**
     * Cria a catraca e monta a cadeia: cadastro, mensalidade e agendamento.
     *
     * @param gerenciarClientes gerenciador com os clientes cadastrados
     * @param agenda            agenda com os agendamentos
     */
    public Catraca(GerenciarClientes gerenciarClientes, Agenda agenda) {
        ValidadorAcesso cadastro = new ValidadorCadastro(gerenciarClientes);
        ValidadorAcesso mensalidade = new ValidadorMensalidade();
        ValidadorAcesso agendamento = new ValidadorAgendamento(agenda);

        cadastro.definirProximo(mensalidade).definirProximo(agendamento);

        this.primeiroValidador = cadastro;
    }

    /**
     * Tenta registrar a entrada de um cliente agora.
     *
     * @param cliente cliente que quer entrar
     * @return resultado da validação, com o motivo
     */
    public ResultadoAcesso registrarEntrada(Cliente cliente) {
        SolicitacaoAcesso solicitacao = new SolicitacaoAcesso(cliente, LocalDateTime.now());
        return primeiroValidador.validar(solicitacao);
    }
}
