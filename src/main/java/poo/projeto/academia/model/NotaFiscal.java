package poo.projeto.academia.model;

import java.time.LocalDate;
import poo.projeto.academia.enums.OrigemPagamento;

/**
 * Nota fiscal de um pagamento.
 * <p>
 * É emitida na hora em que o pagamento é feito. Fica guardada no
 * {@code Financeiro}, e o cliente guarda só o id dela.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Pagamento
 * @see Cliente
 */
public class NotaFiscal {

    /** Quantidade de notas criadas, usada para gerar o id. */
    private static int totalNotas = 0;

    /** Identificador da nota. */
    private int id;

    /** Id do pagamento que gerou a nota. */
    private int idPagamento;

    /** Id do cliente da nota; 0 quando não há cliente. */
    private int idCliente;

    /** Data do pagamento. */
    private LocalDate data;

    /** Valor da nota; negativo no cancelamento. */
    private double valor;

    /** De onde veio o pagamento. */
    private OrigemPagamento origem;

     /**
     * Cria uma nota fiscal.
     *
     * @param idPagamento id do pagamento
     * @param idCliente   id do cliente, ou 0 se não houver
     * @param data        data do pagamento
     * @param valor       valor pago
     * @param origem      origem do pagamento
     * @throws IllegalArgumentException se a data ou a origem forem nulas
     */
    public NotaFiscal(int idPagamento, int idCliente, LocalDate data, double valor, OrigemPagamento origem){
        if (data == null || origem == null){
            throw new IllegalArgumentException("Data e origem são obrigatórias.");
        }
        totalNotas++;
        this.id = totalNotas;
        this.idPagamento = idPagamento;
        this.idCliente = idCliente;
        this.data = data;
        this.valor = valor;
        this.origem = origem;
    }

    /**
     * Sobe o contador até o maior id carregado do JSON, para que uma nota
     * nova não repita um id já existente.
     *
     * @param maiorIdCarregado maior id entre as notas carregadas
     */
    public static void atualizarTotalNotas(int maiorIdCarregado){
        if(maiorIdCarregado > totalNotas){
            totalNotas = maiorIdCarregado;
        }
    }

     /**
     * Retorna o identificador da nota.
     *
     * @return o id
     */
    public int getId(){
        return id;
    }

    /**
     * Retorna o id do pagamento que gerou a nota.
     *
     * @return o id do pagamento
     */
    public int getIdPagamento(){
        return idPagamento;
    }

    /**
     * Retorna o id do cliente da nota.
     *
     * @return o id do cliente, ou 0 se não houver
     */
    public int getIdCliente(){
        return idCliente;
    }

    /**
     * Retorna a data do pagamento.
     *
     * @return a data
     */
    public LocalDate getData(){
        return data;
    }

    /**
     * Retorna o valor da nota.
     *
     * @return o valor; negativo no cancelamento
     */
    public double getValor(){
        return valor;
    }

    /**
     * Retorna a origem do pagamento.
     *
     * @return a origem
     */
    public OrigemPagamento getOrigem(){
        return origem;
    }

    /**
     * Descreve a nota em texto.
     *
     * @return id, origem, data, valor e ids do pagamento e do cliente
     */
    @Override
    public String toString() {
        return "NotaFiscal #" + id + " | " + origem + " | " + data
                + " | R$ " + String.format("%.2f", valor)
                + " | pagamento " + idPagamento
                + " | cliente " + (idCliente == 0 ? "balcão" : idCliente);
    }
}
    

