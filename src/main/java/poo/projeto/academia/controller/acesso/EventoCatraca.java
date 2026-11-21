package poo.projeto.academia.controller.acesso;

import java.time.LocalDateTime;
import poo.projeto.academia.enums.TipoEvento;

/**
 * Aviso que a catraca externa envia ao sistema a cada passagem.
 * <p>
 * A catraca externa só conhece o número (id) do cliente, o tipo da
 * passagem (entrada ou saída) e a hora. O sistema recebe esse aviso
 * pelo método {@link Catraca#receberEvento(EventoCatraca)}.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Catraca
 */
public class EventoCatraca {

    /** Identificador do cliente que passou pela catraca. */
    private int idCliente;

    /** Tipo da passagem: entrada ou saída. */
    private TipoEvento tipo;

    /** Data e hora da passagem. */
    private LocalDateTime dataHora;

    /**
     * Cria um evento da catraca externa.
     *
     * @param idCliente identificador do cliente
     * @param tipo      entrada ou saída
     * @param dataHora  data e hora da passagem
     */
    public EventoCatraca(int idCliente, TipoEvento tipo, LocalDateTime dataHora) {
        this.idCliente = idCliente;
        this.tipo = tipo;
        this.dataHora = dataHora;
    }

    /**
     * Retorna o identificador do cliente.
     *
     * @return o id do cliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Retorna o tipo da passagem.
     *
     * @return entrada ou saída
     */
    public TipoEvento getTipo() {
        return tipo;
    }

    /**
     * Retorna a data e hora da passagem.
     *
     * @return a data e hora
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Retorna o evento em texto.
     *
     * @return tipo, id do cliente e data e hora
     */
    @Override
    public String toString() {
        return tipo + " do cliente " + idCliente + " em " + dataHora;
    }
}
