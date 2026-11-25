package poo.projeto.academia.model;

/**
 * Base de tudo o que se vende: produtos da loja e itens da lanchonete.
 * <p>
 * Cada produto criado aumenta o contador estático {@link #getTotalProdutos()},
 * que o Sistema usa para contar as instâncias.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see ProdutoLoja
 * @see ItemCardapio
 */
public class Produto {
    
    /** Quantidade de produtos criados até agora. */
    private static int totalProdutos = 0;

    /** Identificador único do produto. */
    private int id;

    /** Nome do produto. */
    private String nome;

    /** Preço unitário. */
    private double preco;

    /**
     * Cria um produto e aumenta o contador de instâncias.
     *
     * @param nome  nome do produto
     * @param preco preço unitário
     * @throws IllegalArgumentException se o nome for vazio ou o preço não for positivo
     */
    protected Produto(String nome, double preco) {
        
        validarNome(nome);
        validarPreco(preco);
        totalProdutos++;
        this.id = totalProdutos;
        this.nome = nome;
        this.preco = preco;

    }

    /**
     * Retorna quantos produtos já foram criados.
     *
     * @return o total de instâncias
     */
    public static int getTotalProdutos() {
        return totalProdutos;
    }

    /**
     * Sobe o contador para o maior id carregado do JSON.
     * <p>
     * Sem isso, o contador recomeça do 0 quando o programa abre, e um produto
     * novo receberia o mesmo id de um produto carregado. Só aumenta o contador, nunca diminui.
     *
     * @param maiorIdCarregado maior id entre os produtos carregados
     */
    public static void atualizarTotalProdutos(int maiorIdCarregado){
        if (maiorIdCarregado > totalProdutos){
            totalProdutos = maiorIdCarregado;
        }
    }

    /**
     * Retorna o identificador do produto.
     *
     * @return o identificador
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o nome do produto.
     *
     * @return o nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Altera o nome do produto.
     *
     * @param nome o novo nome
     * @throws IllegalArgumentException se for nulo ou vazio
     */
    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    /**
     * Retorna o preço unitário.
     *
     * @return o preço
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Confere se o nome do produto foi informado.
     *
     * @param nome nome do produto
     * @throws IllegalArgumentException se o nome for nulo ou vazio
     */
    private static void validarNome(String nome) {
        if (nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome do produto é obrigatório.");
        }
    }

    /**
     * Confere se o preço do produto é maior que zero.
     *
     * @param preco preço unitário
     * @throws IllegalArgumentException se o preço for zero ou negativo
     */
    private static void validarPreco(double preco) {
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço do produto deve ser positivo.");
        }
    }

    /**
     * Retorna os dados básicos do produto em texto. As subclasses usam este texto com {@code super.toString()}.
     *
     * @return o objeto em texto
     */
    @Override
    public String toString() {
        return "id=" + id + ", nome=" + nome + ", preco=R$ " + String.format("%.2f", preco);
    }
}
