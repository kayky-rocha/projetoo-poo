package poo.projeto.academia.model;

import poo.projeto.academia.enums.OrigemPagamento;

/**
 * Pedido da lanchonete da academia.
 * <p>
 * Só aceita itens do cardápio que estejam disponíveis. Cada pedido
 * recebe um número, chamado no balcão.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Venda
 * @see ItemCardapio
 */
public class VendaLanchonete extends Venda {

    /** Quantidade de pedidos criados, usada para gerar o número. */
    private static int totalPedidos = 0;

    /** Número do pedido. */
    private int numeroPedido;

    /**
     * Cria um pedido da lanchonete.
     *
     * @param cliente cliente do pedido, ou {@code null} na venda de balcão
     */
    public VendaLanchonete(Cliente cliente) {
        super(cliente);
        totalPedidos++;
        this.numeroPedido = totalPedidos;
    }

    /**
     * Aceita só itens do cardápio que estejam disponíveis.
     *
     * @param produto    produto a conferir
     * @param quantidade quantidade total desse item no pedido
     * @throws IllegalArgumentException se não for item do cardápio ou estiver indisponível
     */
    @Override
    protected void conferirItem(Produto produto, int quantidade) {
        if (!(produto instanceof ItemCardapio)) {
            throw new IllegalArgumentException("A lanchonete só vende itens do cardápio.");
        }
        ItemCardapio item = (ItemCardapio) produto;
        if (item.isDisponivel() == false) {
            throw new IllegalArgumentException(produto.getNome() + " está indisponível.");
        }
    }

    /**
     * A receita da lanchonete tem origem LANCHONETE.
     *
     * @return {@link OrigemPagamento#LANCHONETE}
     */
    @Override
    public OrigemPagamento getOrigem() {
        return OrigemPagamento.LANCHONETE;
    }

    /**
     * Retorna o número do pedido.
     *
     * @return o número do pedido
     */
    public int getNumeroPedido() {
        return numeroPedido;
    }

    /**
     * Retorna o pedido em texto.
     *
     * @return número do pedido e dados da venda
     */
    @Override
    public String toString() {
        return "Pedido " + numeroPedido + " | " + super.toString();
    }
}
