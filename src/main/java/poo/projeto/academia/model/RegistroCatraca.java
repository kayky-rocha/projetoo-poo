package poo.projeto.academia.model;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Marcação de uma passagem do cliente pela catraca.
 * <p>
 * Guarda a hora em que o cliente entrou e, quando ele sai, a hora da saída.
 * Enquanto a saída estiver vazia ({@code null}), o cliente está na academia.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Cliente
 */
public class RegistroCatraca {

    /** Cliente que passou pela catraca. */
    private Cliente cliente;

    /** Data e hora da entrada. */
    private LocalDateTime entrada;

    /** Data e hora da saída; {@code null} enquanto o cliente está dentro. */
    private LocalDateTime saida;

    /**
     * Cria o registro de uma entrada.
     *
     * @param cliente cliente que entrou
     * @param entrada data e hora da entrada
     */
    public RegistroCatraca(Cliente cliente, LocalDateTime entrada) {
        this.cliente = cliente;
        this.entrada = entrada;
        this.saida = null;
    }

    /**
     * Marca a saída do cliente.
     *
     * @param saida data e hora da saída
     */
    public void registrarSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    /**
     * Diz se o cliente ainda está na academia, ou seja, se não saiu.
     *
     * @return {@code true} se a saída ainda não foi marcada
     */
    public boolean estaAberto() {
        return saida == null;
    }

    /**
     * Calcula quanto tempo o cliente ficou na academia.
     *
     * @return tempo entre a entrada e a saída; zero se ainda não saiu
     */
    public Duration duracao() {
        if (saida == null) {
            return Duration.ZERO;
        }
        return Duration.between(entrada, saida);
    }

    /**
     * Retorna o cliente do registro.
     *
     * @return o cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Retorna a data e hora da entrada.
     *
     * @return a entrada
     */
    public LocalDateTime getEntrada() {
        return entrada;
    }

    /**
     * Retorna a data e hora da saída.
     *
     * @return a saída, ou {@code null} se o cliente ainda está dentro
     */
    public LocalDateTime getSaida() {
        return saida;
    }

    /**
     * Retorna o registro em texto.
     *
     * @return nome do cliente, entrada, saída e duração em minutos
     */
    @Override
    public String toString() {
        String textoSaida;
        if (saida == null) {
            textoSaida = "ainda na academia";
        } else {
            textoSaida = saida.toString();
        }
        return cliente.getNome() + " | entrada: " + entrada + " | saída: " + textoSaida
                + " | " + duracao().toMinutes() + " min";
    }
}
