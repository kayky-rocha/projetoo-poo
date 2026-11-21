package poo.projeto.academia.controller.acesso;

import java.time.LocalDateTime;
import poo.projeto.academia.model.Aula;
import poo.projeto.academia.model.Cliente;

/**
 * Pedido de entrada de um cliente na academia ou em uma aula.
 * <p>
 * É o objeto que percorre a cadeia de validadores. Guarda quem quer
 * entrar, em qual momento e, na entrada de uma aula, qual é a aula.
 * Na entrada da academia não há aula, e o campo fica {@code null}.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see ValidadorAcesso
 */
public class SolicitacaoAcesso {

    /** Cliente que quer entrar. */
    private Cliente cliente;

    /** Data e hora da tentativa de entrada. */
    private LocalDateTime dataHora;

    /** Aula em que o cliente quer entrar; {@code null} na entrada da academia. */
    private Aula aula;

    /**
     * Cria uma solicitação de acesso.
     *
     * @param cliente  cliente que quer entrar
     * @param dataHora data e hora da tentativa
     */
    public SolicitacaoAcesso(Cliente cliente, LocalDateTime dataHora) {
        this.cliente = cliente;
        this.dataHora = dataHora;
        this.aula = null;
    }

    /**
     * Cria uma solicitação de acesso a uma aula.
     *
     * @param cliente  cliente que quer entrar
     * @param aula     aula em que o cliente quer entrar
     * @param dataHora data e hora da tentativa
     */
    public SolicitacaoAcesso(Cliente cliente, Aula aula, LocalDateTime dataHora) {
        this.cliente = cliente;
        this.aula = aula;
        this.dataHora = dataHora;
    }

    /**
     * Retorna o cliente da solicitação.
     *
     * @return o cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Retorna a data e hora da tentativa.
     *
     * @return a data e hora
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Retorna a aula da solicitação.
     *
     * @return a aula, ou {@code null} se for a entrada da academia
     */
    public Aula getAula() {
        return aula;
    }
}
