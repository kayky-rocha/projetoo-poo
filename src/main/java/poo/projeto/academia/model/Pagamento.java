package poo.projeto.academia.model;

import poo.projeto.academia.enums.TipoCobranca;
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

    /** Cliente que realizou o pagamento. */
    private Cliente cliente;

    /** Forma de pagamento utilizada. */
    private TipoPagamento tipoPagamento;

    /** Forma de cobrança aplicada. */
    private TipoCobranca tipoCobranca;

    /** Valor pago pelo cliente. */
    private double valorPago;

    /** Data em que o pagamento foi realizado. */
    private LocalDate dataPagamento;

    /**
     * Cria um pagamento com as informações fornecidas.
     *
     * @param cliente       cliente que realizou o pagamento
     * @param tipoPagamento forma de pagamento utilizada
     * @param tipoCobranca  forma de cobrança aplicada
     * @param valorPago     valor pago pelo cliente
     * @param dataPagamento data em que o pagamento foi realizado
     */
    public Pagamento(Cliente cliente, TipoPagamento tipoPagamento, TipoCobranca tipoCobranca, double valorPago, LocalDate dataPagamento) {
        this.cliente = cliente;
        this.tipoPagamento = tipoPagamento;
        this.tipoCobranca = tipoCobranca;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        
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
    public TipoCobranca getTipoCobranca() {
        return tipoCobranca;
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
    * @return um mês após o pagamento se for mensalidade; {@code null} se for diária
    */
    public LocalDate calcularVencimento(){
        if (tipoCobranca == TipoCobranca.MENSALIDADE) {
            return dataPagamento.plusMonths(1);
        }
        return null;
    }
}

    
