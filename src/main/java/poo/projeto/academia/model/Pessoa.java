package poo.projeto.academia.model;

/**
 * Classe base abstrata para qualquer pessoa cadastrada no sistema da academia.
 * <p>
 * Reúne os dados pessoais e de contato comuns a clientes e funcionários.
 * Não pode ser instanciada diretamente; use uma das subclasses concretas.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Cliente
 * @see Funcionario
 */
public abstract class Pessoa {

    /** Nome completo da pessoa. */
    private String nome;

    /** CPF da pessoa. */
    private String cpf;

    /** Telefone de contato. */
    private String telefone;

    /** Endereço de e-mail. */
    private String email;

    /** Endereço residencial. Não é obrigatório na criação do objeto. */
    private String endereco;

    /**
     * Cria uma pessoa com os dados pessoais obrigatórios.
     * O endereço pode ser definido depois com {@link #setEndereco(String)}.
     *
     * @param nome     nome completo
     * @param cpf      CPF
     * @param telefone telefone de contato
     * @param email    endereço de e-mail
     */
    public Pessoa(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    /**
     * Retorna o nome completo da pessoa.
     *
     * @return o nome completo
     */
    public String getNome() {
        return nome;
    }

    /**
     * Altera o nome completo da pessoa.
     *
     * @param nome o novo nome completo
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o CPF da pessoa.
     *
     * @return o CPF
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Altera o CPF da pessoa.
     *
     * @param cpf o novo CPF
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Retorna o telefone de contato.
     *
     * @return o telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Altera o telefone de contato.
     *
     * @param telefone o novo telefone
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna o endereço de e-mail.
     *
     * @return o e-mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Altera o endereço de e-mail.
     *
     * @param email o novo e-mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna o endereço residencial.
     *
     * @return o endereço, ou {@code null} se ainda não tiver sido informado
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define ou altera o endereço residencial.
     *
     * @param endereco o novo endereço
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
