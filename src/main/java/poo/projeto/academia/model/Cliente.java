package poo.projeto.academia.model;

import java.time.LocalDate;
import poo.projeto.academia.enums.TipoCobranca;

/**
 * cliente da academia, que frequenta as salas de atividades
 * e pode comprar na lanchonete e na loja.
 * <p>
 * possui um identificador próprio, a forma de cobrança contratada
 * e, para mensalistas, a data de vencimento da mensalidade.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Pessoa
 */
public class Cliente extends Pessoa {

    /** Identificador único do cliente no sistema. */
    private int id;

    /** Forma de cobrança contratada (diária ou mensalidade). */
    private TipoCobranca plano;

    /** Data de vencimento da mensalidade; {@code null} para diaristas. */
    private LocalDate dataVencimento;

    /**
     * Cria um cliente com dados pessoais, identificador e plano.
     *
     * @param nome     nome completo
     * @param cpf      CPF
     * @param telefone telefone de contato
     * @param email    endereço de e-mail
     * @param endereco endereço residencial
     * @param id       identificador único do cliente
     * @param plano    forma de cobrança contratada
     */
    public Cliente(String nome, String cpf, String telefone, String email, String endereco, int id, TipoCobranca plano) {
        super(nome, cpf, telefone, email);
        super.setEndereco(endereco);
        this.id = id;
        this.plano = plano;
    }

    /**
     * Retorna o identificador do cliente.
     *
     * @return o identificador
     */
    public int getId() {
        return id;
    }

    /**
     * Altera o identificador do cliente.
     *
     * @param id o novo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna a forma de cobrança contratada.
     *
     * @return o plano
     */
    public TipoCobranca getPlano() {
        return plano;
    }

    /**
     * Altera a forma de cobrança contratada.
     *
     * @param plano o novo plano
     */
    public void setPlano(TipoCobranca plano) {
        this.plano = plano;
    }

    /**
     * Retorna a data de vencimento da mensalidade.
     *
     * @return a data de vencimento, ou {@code null} se não houver
     */
    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    /**
     * Altera a data de vencimento da mensalidade.
     *
     * @param dataVencimento a nova data de vencimento
     */
    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
