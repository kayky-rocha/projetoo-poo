package poo.projeto.academia.controller.acesso;

import java.time.LocalDate;
import poo.projeto.academia.enums.OrigemPagamento;
import poo.projeto.academia.enums.TipoPlano;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.model.Pagamento;


/**
 * Elo da catraca que confere se o diarista pagou a diária de hoje.
 * <p>
 * A diária dá direito a treinar na academia. Mensalistas não pagam
 * diária, então passam direto para o próximo elo da cadeia. O pagamento de
 * uma aula não substitui a diária.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see ValidadorAcesso
 * @see OrigemPagamento#DIARIA
 */
public class ValidadorDiaria extends ValidadorAcesso {

    /**
     * Cria o validador de diária.
     */
    public ValidadorDiaria() {
        super();
    }

    /**
     * Reprova diaristas que não pagaram a diária na data da tentativa de acesso.
     *
     * @param solicitacao pedido de acesso
     * @return resultado da verificação da diária
     */
    @Override
    protected ResultadoAcesso verificar(SolicitacaoAcesso solicitacao) {
        Cliente cliente = solicitacao.getCliente();

        if (cliente.getPlano() == TipoPlano.MENSALIDADE) {
            return new ResultadoAcesso(true, "Acesso liberado.");
        }

        LocalDate hoje = solicitacao.getDataHora().toLocalDate();
        Pagamento ultima = cliente.ultimoPagamento(OrigemPagamento.DIARIA);

        if (ultima == null || ultima.getDataPagamento().equals(hoje) == false){
            return new ResultadoAcesso(false, "Diária não paga para hoje.");
        }

        return new ResultadoAcesso(true, "Acesso liberado.");
    }
 
}
