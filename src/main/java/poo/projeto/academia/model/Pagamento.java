package poo.projeto.academia.model;

import poo.projeto.academia.enums.OrigemPagamento;
import poo.projeto.academia.enums.TipoPlano;
import poo.projeto.academia.enums.TipoPagamento;
import java.time.LocalDate;

/**
 * Representa um pagamento realizado por um cliente.
 * <p>
 * Possui informações sobre o cliente, a forma de pagamento,
 * o valor pago e a data do pagamento.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 */
public class Pagamento {

    /** Código único do pagamento. É definido pelo Financeiro ao registrar. */
    private int id;

    /** Cliente que realizou o pagamento. */
    private Cliente cliente;

    /** Forma de pagamento utilizada. */
    private TipoPagamento tipoPagamento;

    /** Forma de cobrança aplicada. */
    private TipoPlano tipoPlano;

    /** De onde vem o pagamento (aula, diária, mensalidade, loja etc.). */
    private OrigemPagamento origem;

    /** Valor pago pelo cliente. */
    private double valorPago;

    /** Data em que o pagamento foi realizado. */
    private LocalDate dataPagamento;

    /**
     * Cria um pagamento com as informações fornecidas.
     *
     * @param cliente       cliente que realizou o pagamento; pode ser nulo apenas para {@code LOJA} e {@code LANCHONETE}
     * @param tipoPagamento forma de pagamento utilizada
     * @param origem        de onde vem o pagamento
     * @param valorPago     valor pago; negativo para {@code CANCELAMENTO} e positivo para as demais origens
     * @param dataPagamento data em que o pagamento foi realizado
     * @throws IllegalArgumentException se algum dado obrigatório faltar ou o sinal do valor não combinar com a origem
     */
    public Pagamento(Cliente cliente, TipoPagamento tipoPagamento, OrigemPagamento origem, double valorPago, LocalDate dataPagamento) {
        if (tipoPagamento == null || origem == null || dataPagamento == null){
            throw new IllegalArgumentException("Forma de pagamento, origem e data são obrigatórias.");
        }
        if (cliente == null && origem != OrigemPagamento.LOJA && origem != OrigemPagamento.LANCHONETE) {
            throw new IllegalArgumentException("Esta origem exige um cliente.");
        }
        if (origem == OrigemPagamento.CANCELAMENTO && valorPago >= 0) {
            throw new IllegalArgumentException("O valor de um cancelamento deve ser negativo.");
        }
        if (origem != OrigemPagamento.CANCELAMENTO && valorPago <= 0) {
            throw new IllegalArgumentException("O valor do pagamento deve ser positivo.");
        }

        this.cliente = cliente;
        this.tipoPagamento = tipoPagamento;
        this.origem = origem;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        
    }

    /**
     * Retorna o id do pagamento.
     *
     * @return o id, ou 0 se o Financeiro ainda não o definiu
     */
    public int getId() {
        return id;
    }

    /**
     * Define o id do pagamento. Chamado pelo Financeiro ao registrá-lo.
     *
     * @param id o novo id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o cliente que realizou o pagamento.
     *
     * @return o cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Retorna a forma de pagamento utilizada.
     *
     * @return a forma de pagamento
     */
    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    /**
     * Retorna a forma de cobrança aplicada.
     *
     * @return a forma de cobrança
     */
    public TipoPlano getTipoPlano() {
        return tipoPlano;
    }

     /**
     * Retorna a origem do pagamento.
     *
     * @return a origem
     */
    public OrigemPagamento getOrigem() {
        return origem;
    }

    /**
     * Retorna o valor pago pelo cliente.
     *
     * @return o valor pago
     */
    public double getValorPago() {
        return valorPago;
    }

    /**
     * Retorna a data em que o pagamento foi realizado.
     *
     * @return a data do pagamento
     */
    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    /**
     * Calcula a data de vencimento do pagamento.
     *
     * @return um mês após o pagamento se for mensalidade; {@code null} nas demais origens
     */
    public LocalDate calcularVencimento(){
        if (origem == OrigemPagamento.MENSALIDADE) {
            return dataPagamento.plusMonths(1);
        }
        return null;
    }

    /**
     * Descreve o pagamento em texto.
     *
     * @return código, origem, forma, valor, data e nome do cliente
     */
    @Override
    public String toString() {
        return "Pagamento[id=" + id + ", origem=" + origem + ", forma=" + tipoPagamento
                + ", valor=R$ " + String.format("%.2f", valorPago) + ", data=" + dataPagamento
                + ", cliente=" + (cliente == null ? "-" : cliente.getNome()) + "]";
    }
}


    
