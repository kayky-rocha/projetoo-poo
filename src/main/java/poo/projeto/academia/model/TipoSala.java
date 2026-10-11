package poo.projeto.academia.model;

/**
 * Tipos de sala de exercícios da academia, conforme o enunciado:
 * spinning, musculação, fit dance e pilates.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 */
public enum TipoSala {

    /** Sala de spinning (bicicletas). */
    SPINNING("Spinning"),

    /** Sala de musculação. */
    MUSCULACAO("Musculação"),

    /** Sala de fit dance. */
    FIT_DANCE("Fit Dance"),

    /** Sala de pilates. */
    PILATES("Pilates");

    /** Nome legível do tipo de sala. */
    private final String descricao;

    /**
     * Cria o tipo de sala com seu nome legível.
     *
     * @param descricao nome exibido ao usuário
     */
    TipoSala(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o nome legível do tipo de sala.
     *
     * @return a descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o nome legível do tipo de sala.
     *
     * @return a descrição
     */
    @Override
    public String toString() {
        return descricao;
    }
}