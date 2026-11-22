package poo.projeto.academia.model;

import poo.projeto.academia.enums.OrigemPagamento;

/**
 * Venda da lojinha da academia.
 * <p>
 * Só aceita produtos da loja e confere o estoque. Ao finalizar, dá baixa
 * no estoque de cada produto vendido.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Venda
 * @see ProdutoLoja
 */
public class VendaLojinha extends Venda {

    /**
     * Cria uma venda da lojinha.
     *
     * @param cliente cliente da venda, ou {@code null} na venda de balcão
     */
    public VendaLojinha(Cliente cliente) {
        super(cliente);
    }

    /**
     * Aceita só produtos da loja com estoque suficiente.
     *
     * @param produto    produto a conferir
     * @param quantidade quantidade total desse produto na venda
     * @throws IllegalArgumentException se não for produto da loja ou faltar estoque
     */
    @Override
    protected void conferirItem(Produto produto, int quantidade) {
        if (!(produto instanceof ProdutoLoja)) {
            throw new IllegalArgumentException("A lojinha só vende produtos da loja.");
        }
        ProdutoLoja produtoLoja = (ProdutoLoja) produto;
        if (produtoLoja.temEstoque(quantidade) == false) {
            throw new IllegalArgumentException("Estoque insuficiente de " + produto.getNome() + ".");
        }
    }

    /**
     * Dá baixa no estoque de cada item vendido.
     */
    @Override
    protected void aoFinalizar() {
        for (ItemVenda item : getItens()) {
            ProdutoLoja produtoLoja = (ProdutoLoja) item.getProduto();
            produtoLoja.baixaEstoque(item.getQuantidade());
        }
    }

    /**
     * A receita da lojinha tem origem LOJA.
     *
     * @return {@link OrigemPagamento#LOJA}
     */
    @Override
    public OrigemPagamento getOrigem() {
        return OrigemPagamento.LOJA;
    }
}
