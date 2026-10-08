package poo.projeto.academia.model;

/**
 * Funcionário da academia com acesso ao sistema.
 * <p>
 * Além dos dados pessoais herdados de {@link Pessoa}, guarda as informações
 * funcionais (cargo e salário) e as credenciais de login.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Pessoa
 * @see Administrador
 */
public class Funcionario extends Pessoa {

    /** Identificador único do funcionário no sistema. */
    private int id;

    /** Cargo ocupado na academia. */
    private String cargo;

    /** Salário mensal, em reais. */
    private double salario;

    /** Nome de usuário usado para entrar no sistema. */
    private String usuario;

    /** Senha usada para entrar no sistema. */
    private String senha;

    /**
     * Cria um funcionário com dados pessoais, funcionais e de acesso.
     *
     * @param nome     nome completo
     * @param cpf      CPF
     * @param telefone telefone de contato
     * @param email    endereço de e-mail
     * @param endereco endereço residencial
     * @param id       identificador único do funcionário
     * @param cargo    cargo ocupado
     * @param salario  salário mensal, em reais
     * @param usuario  nome de usuário para login
     * @param senha    senha para login
     */
    public Funcionario(String nome, String cpf, String telefone, String email, String endereco, int id, String cargo, double salario, String usuario, String senha) {
        super(nome, cpf, telefone, email);
        super.setEndereco(endereco);
        this.id = id;
        this.cargo = cargo;
        this.salario = salario;
        this.usuario = usuario;
        this.senha = senha;
    }

    /**
     * Retorna o identificador do funcionário.
     *
     * @return o identificador
     */
    public int getId() {
        return id;
    }

    /**
     * Altera o identificador do funcionário.
     *
     * @param id o novo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o cargo do funcionário.
     *
     * @return o cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Altera o cargo do funcionário.
     *
     * @param cargo o novo cargo
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Retorna o salário mensal do funcionário.
     *
     * @return o salário, em reais
     */
    public double getSalario() {
        return salario;
    }

    /**
     * Altera o salário mensal do funcionário.
     *
     * @param salario o novo salário, em reais
     */
    public void setSalario(double salario) {
        this.salario = salario;
    }

    /**
     * Retorna o nome de usuário de login.
     *
     * @return o nome de usuário
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Altera o nome de usuário de login.
     *
     * @param usuario o novo nome de usuário
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna a senha de login.
     *
     * @return a senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Altera a senha de login.
     *
     * @param senha a nova senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
