package poo.projeto.academia.controller.acesso;

import java.time.LocalDate;
import poo.projeto.academia.enums.TipoCobranca;
import poo.projeto.academia.model.Cliente;

/**
 * Elo da catraca que confere se a mensalidade do cliente está em dia.
 * <p>
 * Diaristas não pagam mensalidade, então passam direto para o próximo
 * elo da cadeia.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see ValidadorAcesso
 */
public class ValidadorMensalidade extends ValidadorAcesso {

    /**
     * Cria o validador de mensalidade.
     */
    public ValidadorMensalidade() {
        super();
    }

    /**
     * Reprova mensalistas sem vencimento ou com a mensalidade vencida.
     *
     * @param solicitacao pedido de acesso
     * @return resultado da verificação da mensalidade
     */
    @Override
    protected ResultadoAcesso verificar(SolicitacaoAcesso solicitacao) {
        Cliente cliente = solicitacao.getCliente();

        if (cliente.getPlano() != TipoCobranca.MENSALIDADE) {
            return new ResultadoAcesso(true, "Acesso liberado.");
        }

        LocalDate vencimento = cliente.getDataVencimento();
        LocalDate hoje = solicitacao.getDataHora().toLocalDate();

        if (vencimento == null) {
            return new ResultadoAcesso(false, "Mensalidade sem data de vencimento.");
        }

        if (vencimento.isBefore(hoje)) {
            return new ResultadoAcesso(false, "Mensalidade vencida em " + vencimento + ".");
        }

        return new ResultadoAcesso(true, "Acesso liberado.");
    }
}
