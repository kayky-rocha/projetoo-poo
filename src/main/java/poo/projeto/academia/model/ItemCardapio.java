package poo.projeto.academia.model;

import poo.projeto.academia.enums.CategoriaLanchonete;

/**
 * Item do cardápio da lanchonete. Não tem estoque: ou está disponível, ou não.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Produto
 */
public class ItemCardapio extends Produto {

    /** Categoria do item no cardápio. */
    private CategoriaLanchonete categoria;

    /** Indica se o item pode ser pedido agora. */
    private boolean disponivel;

    /**
     * Cria um item do cardápio, já disponível.
     *
     * @param nome      nome do item
     * @param preco     preço unitário
     * @param categoria categoria do item
     * @throws IllegalArgumentException se a categoria for nula
     */
    public ItemCardapio(String nome, double preco, CategoriaLanchonete categoria) {
        super(nome, preco);
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria é obrigatória.");
        }
        this.categoria = categoria;
        this.disponivel = true;
    }

    /**
     * Retorna a categoria do item.
     *
     * @return a categoria
     */
    public CategoriaLanchonete getCategoria() {
        return categoria;
    }

    /**
     * Informa se o item pode ser pedido.
     *
     * @return {@code true} se estiver disponível
     */
    public boolean isDisponivel() {
        return disponivel;
    }

    /**
     * Liga ou desliga a disponibilidade do item.
     *
     * @param disponivel nova disponibilidade
     */
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
    
}
