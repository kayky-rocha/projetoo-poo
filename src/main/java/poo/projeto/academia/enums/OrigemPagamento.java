package poo.projeto.academia.enums;

/**
 * De onde vem um pagamento. O Financeiro usa a origem para saber
 * de onde sai cada receita.
 * <p>
 * {@link #AULA} e {@link #DIARIA} são independentes: pagar a aula dá direito
 * à aula, e a diária dá direito a treinar na academia.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see TipoPlano
 * @see TipoPagamento
 */
public enum OrigemPagamento {
    /** Pagamento de um agendamento de aula, como Pilates ou Spinning. */
    AULA,
    /** Diária de quem entra para treinar na academia. */
    DIARIA,
    /** Mensalidade de um cliente mensalista. */
    MENSALIDADE,
    /** Venda na loja de materiais. */
    LOJA,
    /** Venda na lanchonete. */
    LANCHONETE,
    /** Reembolso de um agendamento cancelado. É um valor negativo. */
    CANCELAMENTO
}
