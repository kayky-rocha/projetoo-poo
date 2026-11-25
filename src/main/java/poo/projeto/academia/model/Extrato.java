package poo.projeto.academia.model;

import java.time.LocalDateTime;
import poo.projeto.academia.enums.TipoPagamento;

/**
 * Comprovante de uma compra: venda da loja, pedido da lanchonete ou
 * pagamento de um agendamento.
 * <p>
 * É impresso na hora e, quando há cliente, fica salvo na lista de
 * extratos dele.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Venda
 * @see Cliente
 */
public class Extrato {

    /** Quantidade de extratos criados, usada para gerar o id. */
    private static int totalExtratos = 0;

    /** Identificador do extrato. */
    private int id;

    /** Data e hora da compra. */
    private LocalDateTime data;

    /**
     * Cliente da compra; {@code null} na venda de balcão.
     * <p>
     * É {@code transient} para não ser salvo no JSON: o cliente já guarda a
     * lista de extratos, e salvar os dois lados criaria uma referência circular.
     */
    private transient Cliente cliente;

    /** O que foi comprado, uma linha por item. */
    private String descricao;

    /** Valor total pago. */
    private double valorTotal;

    /** Forma de pagamento. */
    private TipoPagamento forma;

    /**
     * Sobe o contador para o maior id carregado do JSON.
     * <p>
     * Sem isso, o contador recomeça do 0 quando o programa abre, e um extrato
     * novo receberia o mesmo id de um extrato carregado. Só aumenta o contador, nunca diminui.
     *
     * @param maiorIdCarregado maior id entre os extratos carregados
     */
    public static void atualizarTotalExtratos(int maiorIdCarregado){
        if(maiorIdCarregado > totalExtratos){
            totalExtratos = maiorIdCarregado;
        }
    }

    /**
     * Cria um extrato.
     *
     * @param data       data e hora da compra
     * @param cliente    cliente da compra, ou {@code null}
     * @param descricao  o que foi comprado
     * @param valorTotal valor total pago
     * @param forma      forma de pagamento
     */
    public Extrato(LocalDateTime data, Cliente cliente, String descricao, double valorTotal, TipoPagamento forma) {
        totalExtratos++;
        this.id = totalExtratos;
        this.data = data;
        this.cliente = cliente;
        this.descricao = descricao;
        this.valorTotal = valorTotal;
        this.forma = forma;
    }

    /**
     * Imprime o extrato no console.
     */
    public void imprimir() {
        System.out.println(this);
    }

    /**
     * Retorna o identificador do extrato.
     *
     * @return o id
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna a data e hora da compra.
     *
     * @return a data e hora
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * Retorna o cliente da compra.
     *
     * @return o cliente, ou {@code null} na venda de balcão
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Altera o cliente do extrato.
     * <p>
     * Usado pela persistência em JSON para religar o objeto ao carregar os dados.
     *
     * @param cliente o cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna a descrição da compra.
     *
     * @return a descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o valor total.
     *
     * @return o valor total
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * Retorna a forma de pagamento.
     *
     * @return a forma de pagamento
     */
    public TipoPagamento getForma() {
        return forma;
    }

    /**
     * Retorna o extrato em texto, pronto para imprimir.
     *
     * @return o extrato completo
     */
    @Override
    public String toString() {
        String nomeCliente;
        if (cliente == null) {
            nomeCliente = "venda de balcão";
        } else {
            nomeCliente = cliente.getNome();
        }
        return "===== EXTRATO " + id + " =====\n"
                + "Data: " + data + "\n"
                + "Cliente: " + nomeCliente + "\n"
                + descricao
                + "Total: R$ " + String.format("%.2f", valorTotal) + "\n"
                + "Forma de pagamento: " + forma + "\n"
                + "=======================";
    }
}
