package poo.projeto.academia.controller.acesso;

/**
 * Elo base da cadeia de validação da catraca (Chain of Responsibility).
 * <p>
 * Cada subclasse implementa uma única regra em {@link #verificar}.
 * O método {@link #validar} roda a regra e, se ela aprovar, passa a
 * solicitação para o próximo elo. Se reprovar, a cadeia para ali.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see SolicitacaoAcesso
 * @see ResultadoAcesso
 */
public abstract class ValidadorAcesso {

    /** Próximo elo da cadeia; {@code null} se este for o último. */
    private ValidadorAcesso proximo;

    /**
     * Cria um elo sem próximo. O próximo é ligado depois com
     * {@link #definirProximo}.
     */
    public ValidadorAcesso() {
        this.proximo = null;
    }

    /**
     * Define o próximo elo da cadeia.
     * <p>
     * Retorna o elo recebido para permitir montar a cadeia em sequência:
     * {@code a.definirProximo(b).definirProximo(c);}
     *
     * @param proximo elo que vem depois deste
     * @return o próprio elo recebido
     */
    public ValidadorAcesso definirProximo(ValidadorAcesso proximo) {
        this.proximo = proximo;
        return proximo;
    }

    /**
     * Valida a solicitação neste elo e, se aprovada, nos seguintes.
     *
     * @param solicitacao pedido de acesso
     * @return o resultado do primeiro elo que reprovar, ou do último elo
     */
    public ResultadoAcesso validar(SolicitacaoAcesso solicitacao) {
        ResultadoAcesso resultado = verificar(solicitacao);

        if (resultado.isLiberado() == false) {
            return resultado;
        }

        if (proximo == null) {
            return resultado;
        }

        return proximo.validar(solicitacao);
    }

    /**
     * Aplica a regra específica deste elo.
     *
     * @param solicitacao pedido de acesso
     * @return resultado da verificação desta regra
     */
    protected abstract ResultadoAcesso verificar(SolicitacaoAcesso solicitacao);
}
