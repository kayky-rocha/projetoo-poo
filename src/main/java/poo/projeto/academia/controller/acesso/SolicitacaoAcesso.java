package poo.projeto.academia.controller.acesso;

import java.time.LocalDateTime;
import poo.projeto.academia.model.Cliente;

/**
 * Pedido de entrada de um cliente na academia.
 * <p>
 * É o objeto que percorre a cadeia de validadores. Guarda quem quer
 * entrar e em qual momento.
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

    /**
     * Cria uma solicitação de acesso.
     *
     * @param cliente  cliente que quer entrar
     * @param dataHora data e hora da tentativa
     */
    public SolicitacaoAcesso(Cliente cliente, LocalDateTime dataHora) {
        this.cliente = cliente;
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
}