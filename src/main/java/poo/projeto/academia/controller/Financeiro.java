package poo.projeto.academia.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import poo.projeto.academia.enums.OrigemPagamento;
import poo.projeto.academia.model.Balanco;
import poo.projeto.academia.model.Despesa;
import poo.projeto.academia.model.Pagamento;

/**
 * Controle financeiro da academia.
 * <p>
 * Toda receita é um {@link Pagamento}: diárias, mensalidades, aulas,
 * vendas da loja e da lanchonete. O reembolso de um cancelamento entra
 * como um Pagamento de valor negativo. Os gastos ficam em {@link Despesa}.
 * Com isso, emite os relatórios de vendas e o balanço mensal.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Pagamento
 * @see Despesa
 * @see Balanco
 */
public class Financeiro {

    /** Todos os pagamentos recebidos. */
    private ArrayList<Pagamento> pagamentos = new ArrayList<>();

    /** Todas as despesas lançadas. */
    private ArrayList<Despesa> despesas = new ArrayList<>();

    /** Próximo código de pagamento. */
    private int proximoCodigo = 1;

    /** Próximo código de despesa. */
    private int proximoCodigoDespesa = 1;

    /**
     * Registra um pagamento e gera o código dele.
     *
     * @param pagamento pagamento a registrar
     * @return o código gerado
     * @throws IllegalArgumentException se o pagamento for nulo
     */
    public int registrarPagamento(Pagamento pagamento) {
        if (pagamento == null) {
            throw new IllegalArgumentException("Pagamento não pode ser nulo.");
        }
        pagamento.setId(proximoCodigo);
        proximoCodigo++;
        pagamentos.add(pagamento);
        return pagamento.getId();
    }

    /**
     * Busca um pagamento pelo código.
     *
     * @param codigo código do pagamento
     * @return o pagamento, ou {@code null} se não existir
     */
    public Pagamento buscarPagamento(int codigo) {
        for (Pagamento p : pagamentos) {
            if (p.getId() == codigo) {
                return p;
            }
        }
        return null;
    }

    /**
     * Lança uma despesa.
     *
     * @param descricao descrição do gasto
     * @param valor     valor gasto
     * @param data      data do gasto
     * @return a despesa criada
     */
    public Despesa registrarDespesa(String descricao, double valor, LocalDate data) {
        Despesa despesa = new Despesa(proximoCodigoDespesa, data, descricao, valor);
        proximoCodigoDespesa++;
        despesas.add(despesa);
        return despesa;
    }

    /**
     * Soma o que entrou de uma origem em um mês.
     *
     * @param origem origem procurada
     * @param ano    ano
     * @param mes    mês (1 a 12)
     * @return a soma dos pagamentos dessa origem no mês
     */
    public double totalPorOrigem(OrigemPagamento origem, int ano, int mes) {
        double soma = 0;
        for (Pagamento p : relatorioVendasMes(ano, mes)) {
            if (p.getOrigem() == origem) {
                soma += p.getValorPago();
            }
        }
        return soma;
    }

    /**
     * Lista os pagamentos de um dia (relatório de vendas do dia).
     *
     * @param data dia do relatório
     * @return pagamentos desse dia
     */
    public ArrayList<Pagamento> relatorioVendasDia(LocalDate data) {
        ArrayList<Pagamento> resultado = new ArrayList<>();
        for (Pagamento p : pagamentos) {
            if (p.getDataPagamento().equals(data)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /**
     * Lista os pagamentos de um mês (relatório de vendas do mês).
     *
     * @param ano ano do relatório
     * @param mes mês do relatório (1 a 12)
     * @return pagamentos desse mês
     */
    public ArrayList<Pagamento> relatorioVendasMes(int ano, int mes) {
        ArrayList<Pagamento> resultado = new ArrayList<>();
        for (Pagamento p : pagamentos) {
            if (p.getDataPagamento().getYear() == ano && p.getDataPagamento().getMonthValue() == mes) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /**
     * Soma os valores de uma lista de pagamentos.
     *
     * @param lista pagamentos a somar
     * @return a soma dos valores
     */
    public double somar(ArrayList<Pagamento> lista) {
        double soma = 0;
        for (Pagamento p : lista) {
            soma += p.getValorPago();
        }
        return soma;
    }

    /**
     * Gera o balanço de um mês.
     *
     * @param ano ano do balanço
     * @param mes mês do balanço (1 a 12)
     * @return o balanço com receitas por origem, despesas, saldo e médias
     */
    public Balanco gerarBalancoMensal(int ano, int mes) {
        HashMap<OrigemPagamento, Double> receitaPorOrigem = new HashMap<>();
        for (OrigemPagamento origem : OrigemPagamento.values()) {
            receitaPorOrigem.put(origem, totalPorOrigem(origem, ano, mes));
        }

        double totalDespesas = 0;
        for (Despesa d : despesas) {
            if (d.getData().getYear() == ano && d.getData().getMonthValue() == mes) {
                totalDespesas += d.getValor();
            }
        }

        int quantidade = relatorioVendasMes(ano, mes).size();
        return new Balanco(ano, mes, receitaPorOrigem, totalDespesas, quantidade);
    }

    /**
     * Lista todos os pagamentos.
     *
     * @return cópia da lista de pagamentos
     */
    public ArrayList<Pagamento> listarPagamentos() {
        return new ArrayList<>(pagamentos);
    }

    /**
     * Lista todas as despesas.
     *
     * @return cópia da lista de despesas
     */
    public ArrayList<Despesa> listarDespesas() {
        return new ArrayList<>(despesas);
    }

    /**
     * Retorna o financeiro em texto.
     *
     * @return quantidade de pagamentos e de despesas
     */
    @Override
    public String toString() {
        return "Financeiro: " + pagamentos.size() + " pagamento(s), " + despesas.size() + " despesa(s)";
    }
}
