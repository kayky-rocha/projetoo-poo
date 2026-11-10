package poo.projeto.academia.controller.acesso;

/**
 * Resultado de uma tentativa de acesso pela catraca.
 * <p>
 * Informa se o acesso foi liberado e o motivo da decisão, para que a
 * catraca possa mostrar por que negou a entrada.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see ValidadorAcesso
 */
public class ResultadoAcesso {

    /** Indica se o acesso foi liberado. */
    private boolean liberado;

    /** Motivo da liberação ou da recusa. */
    private String motivo;

    /**
     * Cria um resultado de acesso.
     *
     * @param liberado {@code true} se o acesso foi liberado
     * @param motivo   motivo da decisão
     */
    public ResultadoAcesso(boolean liberado, String motivo) {
        this.liberado = liberado;
        this.motivo = motivo;
    }

    /**
     * Informa se o acesso foi liberado.
     *
     * @return {@code true} se liberado; {@code false} se negado
     */
    public boolean isLiberado() {
        return liberado;
    }

    /**
     * Retorna o motivo da decisão.
     *
     * @return o motivo
     */
    public String getMotivo() {
        return motivo;
    }
}