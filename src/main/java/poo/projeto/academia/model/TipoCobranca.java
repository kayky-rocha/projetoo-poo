package poo.projeto.academia.model;

/**
 * Formas de cobrança de um agendamento: diária (foco nos turistas)
 * ou mensalidade.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 */
public enum TipoCobranca {

    /** Acesso a uma aula em um único dia. */
    DIARIA("Diária"),

    /** Acesso à aula durante um mês a partir da data de início. */
    MENSALIDADE("Mensalidade");

    /** Nome legível da forma de cobrança. */
    private final String descricao;

    /**
     * Cria a forma de cobrança com seu nome legível.
     *
     * @param descricao nome exibido ao usuário
     */
    TipoCobranca(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o nome legível da forma de cobrança.
     *
     * @return a descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o nome legível da forma de cobrança.
     *
     * @return a descrição
     */
    @Override
    public String toString() {
        return descricao;
    }
}