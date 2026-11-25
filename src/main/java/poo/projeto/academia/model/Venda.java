package poo.projeto.academia.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import poo.projeto.academia.enums.OrigemPagamento;
import poo.projeto.academia.enums.TipoPagamento;

/**
 * Base comum das vendas da loja e da lanchonete.
 * <p>
 * Guarda os itens da venda, calcula o total e, ao finalizar, gera o
 * {@link Pagamento} e o {@link Extrato}. Cada subclasse diz só o que muda:
 * quais produtos aceita e qual é a origem do pagamento.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see VendaLojinha
 * @see VendaLanchonete
 */
public abstract class Venda {

    /** Quantidade de vendas criadas, usada para gerar o id. */
    private static int totalVendas = 0;

    /** Identificador da venda. */
    private int id;

    /** Data e hora da venda. */
    private LocalDateTime data;

    /** Cliente da venda; {@code null} na venda de balcão. */
    private Cliente cliente;

    /** Itens da venda. */
    private ArrayList<ItemVenda> itens = new ArrayList<>();

    /** Pagamento gerado ao finalizar; {@code null} antes disso. */
    private Pagamento pagamento;

    /** Extrato gerado ao finalizar; {@code null} antes disso. */
    private Extrato extrato;

    /**
     * Cria uma venda vazia.
     *
     * @param cliente cliente da venda, ou {@code null} na venda de balcão
     */
    public Venda(Cliente cliente) {
        totalVendas++;
        this.id = totalVendas;
        this.data = LocalDateTime.now();
        this.cliente = cliente;
    }

    /**
     * Confere se o produto pode entrar nesta venda.
     *
     * @param produto    produto a conferir
     * @param quantidade quantidade total desse produto na venda
     * @throws IllegalArgumentException se o produto não puder ser vendido
     */
    protected abstract void conferirItem(Produto produto, int quantidade);

    /**
     * Diz de onde vem a receita desta venda (loja ou lanchonete).
     *
     * @return a origem do pagamento
     */
    public abstract OrigemPagamento getOrigem();

    /**
     * Ação extra feita ao finalizar. A loja usa para dar baixa no estoque.
     */
    protected void aoFinalizar() {
    }

    /**
     * Adiciona um produto à venda. Se o produto já estiver na venda,
     * soma a quantidade.
     *
     * @param produto    produto vendido
     * @param quantidade quantidade
     * @throws IllegalStateException    se a venda já foi finalizada
     * @throws IllegalArgumentException se o produto não puder ser vendido
     */
    public void adicionarItem(Produto produto, int quantidade) {
        if (isFinalizada()) {
            throw new IllegalStateException("A venda já foi finalizada.");
        }
        if (produto == null || quantidade <= 0) {
            throw new IllegalArgumentException("Produto e quantidade positiva são obrigatórios.");
        }

        ItemVenda existente = buscarItem(produto);
        int quantidadeTotal = quantidade;
        if (existente != null) {
            quantidadeTotal = existente.getQuantidade() + quantidade;
        }

        conferirItem(produto, quantidadeTotal);

        if (existente != null) {
            existente.somarQuantidade(quantidade);
        } else {
            itens.add(new ItemVenda(produto, quantidade));
        }
    }

    /**
     * Soma os subtotais dos itens.
     *
     * @return o valor total da venda
     */
    public double total() {
        double soma = 0;
        for (ItemVenda item : itens) {
            soma += item.subtotal();
        }
        return soma;
    }

    /**
     * Finaliza a venda: gera o pagamento e o extrato. Se houver cliente,
     * o extrato fica salvo nele.
     *
     * @param forma forma de pagamento
     * @return o extrato da venda
     * @throws IllegalStateException se a venda já foi finalizada ou está vazia
     */
    public Extrato finalizar(TipoPagamento forma) {
        if (isFinalizada()) {
            throw new IllegalStateException("A venda já foi finalizada.");
        }
        if (itens.isEmpty()) {
            throw new IllegalStateException("A venda não tem itens.");
        }

        aoFinalizar();

        pagamento = new Pagamento(cliente, forma, getOrigem(), total(), data.toLocalDate());
        extrato = new Extrato(data, cliente, montarDescricao(), total(), forma);

        if (cliente != null) {
            cliente.adicionarExtrato(extrato);
        }
        return extrato;
    }

    /**
     * Diz se a venda já foi finalizada.
     *
     * @return {@code true} se já tem pagamento
     */
    public boolean isFinalizada() {
        return pagamento != null;
    }

    /**
     * Procura um produto entre os itens da venda.
     *
     * @param produto produto procurado
     * @return o item, ou {@code null} se o produto não está na venda
     */
    private ItemVenda buscarItem(Produto produto) {
        for (ItemVenda item : itens) {
            if (item.getProduto().getId() == produto.getId()) {
                return item;
            }
        }
        return null;
    }

    /**
     * Monta o texto com uma linha por item, usado no extrato.
     *
     * @return a descrição dos itens
     */
    private String montarDescricao() {
        String texto = "";
        for (ItemVenda item : itens) {
            texto += item.getQuantidade() + "x " + item.getProduto().getNome()
                    + " (R$ " + String.format("%.2f", item.getPrecoUnitario()) + ") = R$ "
                    + String.format("%.2f", item.subtotal()) + "\n";
        }
        return texto;
    }

    /**
     * Retorna o identificador da venda.
     *
     * @return o id
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna a data e hora da venda.
     *
     * @return a data e hora
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * Retorna o cliente da venda.
     *
     * @return o cliente, ou {@code null} na venda de balcão
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Altera o cliente da venda.
     * <p>
     * Usado pela persistência em JSON para religar o objeto ao carregar os dados.
     *
     * @param cliente o cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna os itens da venda.
     *
     * @return cópia da lista de itens
     */
    public ArrayList<ItemVenda> getItens() {
        return new ArrayList<>(itens);
    }

    /**
     * Retorna o pagamento da venda.
     *
     * @return o pagamento, ou {@code null} se ainda não foi finalizada
     */
    public Pagamento getPagamento() {
        return pagamento;
    }

    /**
     * Altera o pagamento da venda.
     * <p>
     * Usado pela persistência em JSON para religar o objeto ao carregar os dados.
     *
     * @param pagamento o pagamento
     */
    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    /**
     * Retorna o extrato da venda.
     *
     * @return o extrato, ou {@code null} se ainda não foi finalizada
     */
    public Extrato getExtrato() {
        return extrato;
    }

    /**
     * Retorna a venda em texto.
     *
     * @return id, origem, data, quantidade de itens e total
     */
    @Override
    public String toString() {
        return "Venda " + id + " (" + getOrigem() + ") | " + data.toLocalDate()
                + " | " + itens.size() + " item(ns) | R$ " + String.format("%.2f", total());
    }
}
