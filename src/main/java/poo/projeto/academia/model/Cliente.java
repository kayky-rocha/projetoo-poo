package poo.projeto.academia.model;

/**
 * Cliente da academia, que frequenta as salas de atividades
 * e pode comprar na lanchonete e na loja.
 * <p>
 * Além dos dados pessoais herdados de {@link Pessoa}, possui um
 * identificador próprio e o plano contratado.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Pessoa
 */
public class Cliente extends Pessoa {

    /** Identificador único do cliente no sistema. */
    private int id;

    /** Plano contratado pelo cliente (por exemplo, diária ou mensalidade). */
    private String plano;

    /**
     * Cria um cliente com dados pessoais, identificador e plano.
     *
     * @param nome     nome completo
     * @param cpf      CPF
     * @param telefone telefone de contato
     * @param email    endereço de e-mail
     * @param endereco endereço residencial
     * @param id       identificador único do cliente
     * @param plano    plano contratado
     */
    public Cliente(String nome, String cpf, String telefone, String email, String endereco, int id, String plano) {
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
     * Retorna o plano contratado pelo cliente.
     *
     * @return o plano
     */
    public String getPlano() {
        return plano;
    }

    /**
     * Altera o plano contratado pelo cliente.
     *
     * @param plano o novo plano
     */
    public void setPlano(String plano) {
        this.plano = plano;
    }
}
