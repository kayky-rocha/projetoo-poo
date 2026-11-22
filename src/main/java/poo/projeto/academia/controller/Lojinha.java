package poo.projeto.academia.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.model.ProdutoLoja;
import poo.projeto.academia.model.VendaLojinha;

/**
 * Subsistema da lojinha: cadastro de produtos, estoque e vendas.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see ProdutoLoja
 * @see VendaLojinha
 */
public class Lojinha {

    /** Produtos à venda. */
    private ArrayList<ProdutoLoja> produtos = new ArrayList<>();

    /** Vendas abertas na lojinha. */
    private ArrayList<VendaLojinha> vendas = new ArrayList<>();

    /**
     * Cadastra um produto na lojinha.
     *
     * @param produto produto a cadastrar
     * @throws IllegalArgumentException se o produto for nulo
     */
    public void cadastrarProduto(ProdutoLoja produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }
        produtos.add(produto);
    }

    /**
     * Busca um produto pelo id.
     *
     * @param id identificador do produto
     * @return o produto, ou {@code null} se não existir
     */
    public ProdutoLoja buscarProduto(int id) {
        for (ProdutoLoja p : produtos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Verifica se há estoque de um produto para a quantidade pedida.
     *
     * @param idProduto  identificador do produto
     * @param quantidade quantidade desejada
     * @return {@code true} se o produto existe e tem estoque suficiente
     */
    public boolean verificarEstoque(int idProduto, int quantidade) {
        ProdutoLoja produto = buscarProduto(idProduto);
        if (produto == null) {
            return false;
        }
        return produto.temEstoque(quantidade);
    }

    /**
     * Repõe o estoque de um produto.
     *
     * @param idProduto  identificador do produto
     * @param quantidade quantidade a repor
     * @return {@code true} se o produto existe
     */
    public boolean reporEstoque(int idProduto, int quantidade) {
        ProdutoLoja produto = buscarProduto(idProduto);
        if (produto == null) {
            return false;
        }
        produto.reporEstoque(quantidade);
        return true;
    }

    /**
     * Abre uma venda nova na lojinha.
     *
     * @param cliente cliente da venda, ou {@code null} na venda de balcão
     * @return a venda aberta, para adicionar itens e finalizar
     */
    public VendaLojinha vender(Cliente cliente) {
        VendaLojinha venda = new VendaLojinha(cliente);
        vendas.add(venda);
        return venda;
    }

    /**
     * Lista os produtos cadastrados.
     *
     * @return cópia da lista de produtos
     */
    public ArrayList<ProdutoLoja> listarProdutos() {
        return new ArrayList<>(produtos);
    }

    /**
     * Lista as vendas finalizadas de um dia.
     *
     * @param data dia do relatório
     * @return vendas finalizadas nesse dia
     */
    public ArrayList<VendaLojinha> relatorioVendas(LocalDate data) {
        ArrayList<VendaLojinha> resultado = new ArrayList<>();
        for (VendaLojinha v : vendas) {
            if (v.isFinalizada() && v.getData().toLocalDate().equals(data)) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    /**
     * Lista as vendas finalizadas de um mês.
     *
     * @param ano ano do relatório
     * @param mes mês do relatório (1 a 12)
     * @return vendas finalizadas nesse mês
     */
    public ArrayList<VendaLojinha> relatorioVendasMes(int ano, int mes) {
        ArrayList<VendaLojinha> resultado = new ArrayList<>();
        for (VendaLojinha v : vendas) {
            if (v.isFinalizada() && v.getData().getYear() == ano
                    && v.getData().getMonthValue() == mes) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    /**
     * Retorna a lojinha em texto.
     *
     * @return quantidade de produtos e de vendas
     */
    @Override
    public String toString() {
        return "Lojinha: " + produtos.size() + " produto(s), " + vendas.size() + " venda(s)";
    }
}
