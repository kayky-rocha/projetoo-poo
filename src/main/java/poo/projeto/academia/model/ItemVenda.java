package poo.projeto.academia.model;

/**
 * Uma linha da venda: um produto, a quantidade e o preço no momento da venda.
 * <p>
 * O preço unitário é copiado do produto na criação. Se o produto mudar de
 * preço depois, a venda antiga não muda.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Venda
 */
public class ItemVenda {

    /** Produto vendido. */
    private Produto produto;

    /** Quantidade vendida. */
    private int quantidade;

    /** Preço unitário no momento da venda. */
    private double precoUnitario;

    /**
     * Cria um item de venda.
     *
     * @param produto    produto vendido
     * @param quantidade quantidade vendida
     * @throws IllegalArgumentException se o produto for nulo ou a quantidade não for positiva
     */
    public ItemVenda(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto é obrigatório.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser positiva.");
        }
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }

    /**
     * Retorna o produto vendido.
     *
     * @return o produto
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Altera o produto do item.
     * <p>
     * Usado pela persistência em JSON para religar o objeto ao carregar os dados.
     *
     * @param produto o produto
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * Retorna a quantidade vendida.
     *
     * @return a quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Retorna o preço unitário no momento da venda.
     *
     * @return o preço unitário
     */
    public double getPrecoUnitario() {
        return precoUnitario;
    }

    /**
     * Soma mais unidades ao item, quando o mesmo produto é adicionado de novo.
     *
     * @param quantidade unidades a somar
     */
    void somarQuantidade(int quantidade) {
        this.quantidade += quantidade;
    }

    /**
     * Calcula o valor da linha.
     *
     * @return preço unitário vezes quantidade
     */
    public double subtotal() {
        return precoUnitario * quantidade;
    }

    /**
     * Retorna o item da venda em texto.
     *
     * @return o objeto em texto
     */
    @Override
    public String toString() {
        return "ItemVenda[produto=" + produto.getNome() + ", quantidade=" + quantidade
                + ", precoUnitario=R$ " + String.format("%.2f", precoUnitario)
                + ", subtotal=R$ " + String.format("%.2f", subtotal()) + "]";
    }
}
