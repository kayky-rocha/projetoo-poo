package poo.projeto.academia.model;

/**
 * Produto vendido na lojinha, com controle de estoque.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Produto
 */
public class ProdutoLoja extends Produto {

    /** Quantidade disponível em estoque. */
    private int estoque;

    /** Categoria do produto (por exemplo, suplemento ou vestuário). */
    private String categoria;

    /**
     * Cria um produto da lojinha.
     *
     * @param nome      nome do produto
     * @param preco     preço unitário
     * @param estoque   quantidade inicial em estoque
     * @param categoria categoria do produto
     * @throws IllegalArgumentException se o estoque for negativo
     */
    public ProdutoLoja(String nome, double preco, int estoque, String categoria) {
        super(nome, preco);
        if (estoque < 0) {
            throw new IllegalArgumentException("O estoque não pode ser negativo.");
        }
        this.estoque = estoque;
        this.categoria = categoria;
    }

    /**
     * Retorna o estoque atual.
     *
     * @return a quantidade em estoque
     */
    public int getEstoque() {
        return estoque;
    }

    /**
     * Retorna a categoria do produto.
     *
     * @return a categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Altera a categoria do produto.
     *
     * @param categoria a nova categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Verifica se há estoque suficiente para a quantidade desejada.
     *
     * @param quantidade a quantidade desejada
     * @return true se houver estoque suficiente, false caso contrário
     */
    public boolean temEstoque (int quantidade) {
        return quantidade > 0 && quantidade <= estoque; 
    }

    /**
     * Tira do estoque a quantidade especificada, quanto houver uma venda.
     *
     * @param quantidade a quantidade a ser reduzida
     * @throws IllegalArgumentException se não houver estoque suficiente
     */
    public void baixaEstoque(int quantidade) {
        if (temEstoque(quantidade)== false)
            throw new IllegalArgumentException("Estoque insuficiente para a quantidade solicitada.");
        estoque -= quantidade;
    }

    /**
     * Põe unidades no estoque, na reposição.
     *
     * @param quantidade quantidade reposta
     * @throws IllegalArgumentException se não for positiva
     */
    public void reporEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade a repor deve ser positiva.");
        }
        estoque += quantidade;
    }
    
}
