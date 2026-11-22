package poo.projeto.academia.model;

import java.time.LocalDate;

/**
 * Gasto da academia, como conta de luz, salário ou manutenção.
 * <p>
 * Entra no balanço mensal do lado das saídas.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Balanco
 */
public class Despesa {

    /** Código da despesa, gerado pelo Financeiro. */
    private int codigo;

    /** Data da despesa. */
    private LocalDate data;

    /** Descrição do gasto. */
    private String descricao;

    /** Valor gasto. */
    private double valor;

    /**
     * Cria uma despesa.
     *
     * @param codigo    código da despesa
     * @param data      data da despesa
     * @param descricao descrição do gasto
     * @param valor     valor gasto
     * @throws IllegalArgumentException se faltar a descrição ou o valor não for positivo
     */
    public Despesa(int codigo, LocalDate data, String descricao, double valor) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição da despesa é obrigatória.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da despesa deve ser positivo.");
        }
        this.codigo = codigo;
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
    }

    /**
     * Retorna o código da despesa.
     *
     * @return o código
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Retorna a data da despesa.
     *
     * @return a data
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Retorna a descrição da despesa.
     *
     * @return a descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o valor da despesa.
     *
     * @return o valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * Retorna a despesa em texto.
     *
     * @return código, data, descrição e valor
     */
    @Override
    public String toString() {
        return "Despesa " + codigo + " | " + data + " | " + descricao
                + " | R$ " + String.format("%.2f", valor);
    }
}
