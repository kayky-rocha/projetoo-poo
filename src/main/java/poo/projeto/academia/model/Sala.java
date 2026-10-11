package poo.projeto.academia.model;

/**
 * Sala de exercícios da academia.
 * <p>
 * As 4 salas da academia são criadas uma única vez e armazenadas de forma
 * estática na classe {@link Sistema} (item 5 do trabalho).
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Sistema#getSalas()
 */
public class Sala {

    /** Número da sala (1 a 4). */
    private int numero;

    /** Tipo de atividade realizada na sala. */
    private TipoSala tipo;

    /** Quantidade máxima de alunos ao mesmo tempo na sala. */
    private int capacidade;

    /**
     * Cria uma sala.
     *
     * @param numero     número da sala
     * @param tipo       tipo de atividade
     * @param capacidade lotação máxima
     */
    public Sala(int numero, TipoSala tipo, int capacidade) {
        this.numero = numero;
        this.tipo = tipo;
        this.capacidade = capacidade;
    }

    /**
     * Retorna o número da sala.
     *
     * @return o número
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Altera o número da sala.
     *
     * @param numero o novo número
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Retorna o tipo de atividade da sala.
     *
     * @return o tipo
     */
    public TipoSala getTipo() {
        return tipo;
    }

    /**
     * Altera o tipo de atividade da sala.
     *
     * @param tipo o novo tipo
     */
    public void setTipo(TipoSala tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna a lotação máxima da sala.
     *
     * @return a capacidade
     */
    public int getCapacidade() {
        return capacidade;
    }

    /**
     * Altera a lotação máxima da sala.
     *
     * @param capacidade a nova capacidade
     */
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    /**
     * Retorna os dados da sala em formato de texto.
     *
     * @return número, tipo e capacidade
     */
    @Override
    public String toString() {
        return "Sala[numero=" + numero + ", tipo=" + tipo + ", capacidade=" + capacidade + "]";
    }
}
