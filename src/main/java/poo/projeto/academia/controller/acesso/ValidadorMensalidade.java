package poo.projeto.academia.controller.acesso;

import java.time.LocalDate;
import poo.projeto.academia.enums.TipoCobranca;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.model.Pagamento;

/**
 * Elo da catraca que confere se a mensalidade do cliente está em dia.
 * <p>
 * Usa o pagamento de mensalidade mais recente do cliente e compara o
 * seu vencimento com a data da tentativa de acesso. Diaristas não pagam
 * mensalidade, então passam direto para o próximo elo da cadeia.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see ValidadorAcesso
 * @see Pagamento
 */
public class ValidadorMensalidade extends ValidadorAcesso {

    /**
     * Cria o validador de mensalidade.
     */
    public ValidadorMensalidade() {
        super();
    }

    /**
     * Reprova mensalistas sem pagamento registrado ou com a mensalidade vencida.
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

        Pagamento ultimo = cliente.ultimoPagamento(TipoCobranca.MENSALIDADE);

        if (ultimo == null) {
            return new ResultadoAcesso(false, "Mensalidade não paga.");
        }
        
        LocalDate vencimento = ultimo.calcularVencimento();
        LocalDate hoje = solicitacao.getDataHora().toLocalDate();

        if(vencimento.isBefore(hoje)) {
            return new ResultadoAcesso(false, "Mensalidade vencida em: " + vencimento + ".");
        }

        return new ResultadoAcesso(true, "Acesso liberado.");
    }
}
