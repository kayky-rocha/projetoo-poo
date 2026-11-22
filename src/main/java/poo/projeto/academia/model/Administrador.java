package poo.projeto.academia.model;

/**
 * Funcionário com permissões administrativas no sistema da academia.
 * <p>
 * Possui uma senha administrativa adicional, exigida para operações
 * restritas, como gestão de funcionários e lançamento de despesas.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Funcionario
 */
public class Administrador extends Funcionario {

    /** Senha extra usada para autorizar operações administrativas. */
    private String senhaAdm;

    /**
     * Cria um administrador com dados pessoais, funcionais e de acesso.
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
     * @param senhaAdm senha administrativa
     */
    public Administrador(String nome, String cpf, String telefone, String email, String endereco, int id, String cargo, double salario, String usuario, String senha, String senhaAdm) {
        super(nome, cpf, telefone, email, endereco, id, cargo, salario, usuario, senha);
        this.senhaAdm = senhaAdm;
    }

    /**
     * Verifica se a senha informada corresponde à senha administrativa.
     *
     * @param senhaDigitada senha fornecida pelo usuário
     * @return {@code true} se a senha estiver correta; {@code false} caso contrário
     */
    public boolean validarSenhaAdm(String senhaDigitada) {
        return this.senhaAdm.equals(senhaDigitada);
    }

    /**
     * Troca a senha administrativa. Só funciona se a senha atual estiver certa.
     *
     * @param senhaAtual senha administrativa atual
     * @param novaSenha  nova senha administrativa
     * @return {@code true} se trocou; {@code false} se a senha atual estiver errada
     *         ou a nova estiver vazia
     */
    public boolean alterarSenhaAdm(String senhaAtual, String novaSenha) {
        if (validarSenhaAdm(senhaAtual) == false) {
            return false;
        }
        if (novaSenha == null || novaSenha.isBlank()) {
            return false;
        }
        this.senhaAdm = novaSenha;
        return true;
    }

    /**
     * Retorna o administrador em texto, com os dados herdados de Funcionario. As senhas não aparecem.
     *
     * @return o objeto em texto
     */
    @Override
    public String toString() {
        return "Administrador -> " + super.toString();
    }
}
