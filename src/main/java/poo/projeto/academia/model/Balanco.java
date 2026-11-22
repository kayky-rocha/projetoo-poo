package poo.projeto.academia.model;

import java.time.YearMonth;
import java.util.HashMap;
import poo.projeto.academia.enums.OrigemPagamento;

/**
 * Balanço de um mês, com estatísticas básicas.
 * <p>
 * Mostra quanto entrou (separado pela origem: diárias, mensalidades,
 * aulas, loja, lanchonete e cancelamentos), quanto saiu, o saldo e a
 * média de receita por dia.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Despesa
 * @see Pagamento
 */
public class Balanco {

    /** Ano do balanço. */
    private int ano;

    /** Mês do balanço (1 a 12). */
    private int mes;

    /** Soma das receitas do mês. */
    private double totalReceitas;

    /** Soma das despesas do mês. */
    private double totalDespesas;

    /** Receitas menos despesas. */
    private double saldo;

    /** Quantidade de pagamentos recebidos no mês. */
    private int quantidadePagamentos;

    /** Quanto entrou de cada origem. */
    private HashMap<OrigemPagamento, Double> receitaPorOrigem;

    /**
     * Cria o balanço de um mês.
     *
     * @param ano                  ano do balanço
     * @param mes                  mês do balanço (1 a 12)
     * @param receitaPorOrigem     quanto entrou de cada origem
     * @param totalDespesas        soma das despesas do mês
     * @param quantidadePagamentos quantidade de pagamentos do mês
     */
    public Balanco(int ano, int mes, HashMap<OrigemPagamento, Double> receitaPorOrigem,
            double totalDespesas, int quantidadePagamentos) {
        this.ano = ano;
        this.mes = mes;
        this.receitaPorOrigem = receitaPorOrigem;
        this.totalDespesas = totalDespesas;
        this.quantidadePagamentos = quantidadePagamentos;

        this.totalReceitas = 0;
        for (OrigemPagamento origem : OrigemPagamento.values()) {
            this.totalReceitas += getReceita(origem);
        }
        this.saldo = totalReceitas - totalDespesas;
    }

    /**
     * Retorna quanto entrou de uma origem no mês.
     *
     * @param origem origem procurada
     * @return a receita dessa origem; 0 se não houve
     */
    public double getReceita(OrigemPagamento origem) {
        Double valor = receitaPorOrigem.get(origem);
        if (valor == null) {
            return 0;
        }
        return valor;
    }

    /**
     * Calcula a receita média por dia do mês.
     *
     * @return total de receitas dividido pelos dias do mês
     */
    public double mediaDiariaReceita() {
        int dias = YearMonth.of(ano, mes).lengthOfMonth();
        return totalReceitas / dias;
    }

    /**
     * Calcula o valor médio de cada pagamento.
     *
     * @return total de receitas dividido pela quantidade de pagamentos; 0 se não houve
     */
    public double ticketMedio() {
        if (quantidadePagamentos == 0) {
            return 0;
        }
        return totalReceitas / quantidadePagamentos;
    }

    /**
     * Retorna o ano do balanço.
     *
     * @return o ano
     */
    public int getAno() {
        return ano;
    }

    /**
     * Retorna o mês do balanço.
     *
     * @return o mês (1 a 12)
     */
    public int getMes() {
        return mes;
    }

    /**
     * Retorna a soma das receitas.
     *
     * @return o total de receitas
     */
    public double getTotalReceitas() {
        return totalReceitas;
    }

    /**
     * Retorna a soma das despesas.
     *
     * @return o total de despesas
     */
    public double getTotalDespesas() {
        return totalDespesas;
    }

    /**
     * Retorna o saldo do mês.
     *
     * @return receitas menos despesas
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Retorna a quantidade de pagamentos do mês.
     *
     * @return a quantidade de pagamentos
     */
    public int getQuantidadePagamentos() {
        return quantidadePagamentos;
    }

    /**
     * Retorna o balanço em texto, pronto para imprimir.
     *
     * @return o balanço completo
     */
    @Override
    public String toString() {
        String texto = "===== BALANÇO " + String.format("%02d", mes) + "/" + ano + " =====\n";
        for (OrigemPagamento origem : OrigemPagamento.values()) {
            texto += origem + ": R$ " + String.format("%.2f", getReceita(origem)) + "\n";
        }
        texto += "Total de receitas: R$ " + String.format("%.2f", totalReceitas) + "\n"
                + "Total de despesas: R$ " + String.format("%.2f", totalDespesas) + "\n"
                + "Saldo: R$ " + String.format("%.2f", saldo) + "\n"
                + "Pagamentos no mês: " + quantidadePagamentos + "\n"
                + "Média de receita por dia: R$ " + String.format("%.2f", mediaDiariaReceita()) + "\n"
                + "Valor médio por pagamento: R$ " + String.format("%.2f", ticketMedio()) + "\n"
                + "=============================";
        return texto;
    }
}
