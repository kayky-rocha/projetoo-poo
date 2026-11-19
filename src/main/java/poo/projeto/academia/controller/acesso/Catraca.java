package poo.projeto.academia.controller.acesso;

import java.time.LocalDateTime;
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
     * Cria a catraca com a cadeia já montada.
     *
     * @param primeiroValidador primeiro elo da cadeia
     * @throws IllegalArgumentException se o validador for nulo
     */
    public Catraca(ValidadorAcesso primeiroValidador) {
        if (primeiroValidador == null) {
            throw new IllegalArgumentException("Validador nulo.");
        }
        this.primeiroValidador = primeiroValidador;
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
