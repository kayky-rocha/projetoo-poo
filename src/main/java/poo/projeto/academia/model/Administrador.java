package poo.projeto.academia.model;

public class Administrador extends Funcionario {
    private String senhaAdm;

    public Administrador(String nome, String cpf, String telefone, String email, String endereco, int id, String cargo, double salario, String usuario, String senha, String senhaAdm) {
        super(nome, cpf, telefone, email, endereco, id, cargo, salario, usuario, senha);
        this.senhaAdm = senhaAdm;
    }

    public boolean validarSenhaAdm(String senhaDigitada) {
        return this.senhaAdm.equals(senhaDigitada);
    }
}
