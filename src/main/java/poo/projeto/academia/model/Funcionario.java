package poo.projeto.academia.model;

public class Funcionario extends Pessoa {
    private int id;
    private String cargo;
    private double salario;
    private String usuario;
    private String senha;

    public Funcionario(String nome, String cpf, String telefone, String email, String endereco, int id, String cargo, double salario, String usuario, String senha) {
        super(nome, cpf, telefone, email);
        super.setEndereco(endereco);
        this.id = id;
        this.cargo = cargo;
        this.salario = salario;
        this.usuario = usuario;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
}
