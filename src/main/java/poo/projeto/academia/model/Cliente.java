package poo.projeto.academia.model;

public class Cliente extends Pessoa {
    private int id;
    private String plano;
    

    public Cliente(String nome, String cpf, String telefone, String email, String endereco, int id, String plano) {
        super(nome, cpf, telefone, email);
        super.setEndereco(endereco);
        this.id = id;
        this.plano = plano;
    }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlano() {
            return plano;
        }

        public void setPlano(String plano) {
            this.plano = plano;
        }

}
