package poo.projeto.academia.model;

import java.util.ArrayList;
import poo.projeto.academia.Sistema;
import poo.projeto.academia.enums.OrigemPagamento;
import poo.projeto.academia.enums.TipoPlano;

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
    private TipoPlano plano;

    /** Lista de pagamentos realizados pelo cliente. */
    private ArrayList<Pagamento> pagamentos = new ArrayList<>();

    /** Extratos das compras do cliente (loja, lanchonete e agendamentos). */
    private ArrayList<Extrato> extratos = new ArrayList<>();

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
    public Cliente(String nome, String cpf, String telefone, String email, String endereco, int id, TipoPlano plano) {
        super(nome, cpf, telefone, email);
        super.setEndereco(endereco);
        this.id = id;
        this.plano = plano;
        Sistema.contarCliente();
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
    public TipoPlano getPlano() {
        return plano;
    }

    /**
     * Altera a forma de cobrança contratada.
     *
     * @param plano o novo plano
     */
    public void setPlano(TipoPlano plano) {
        this.plano = plano;
    }

    /**
     * Registra um pagamento no histórico do cliente.
     *
     * @param pagamento pagamento a registrar
     * @throws IllegalArgumentException se for nulo ou pertencer a outro cliente
     */
    public void registrarPagamento(Pagamento pagamento) {
        if (pagamento == null) {
            throw new IllegalArgumentException("Pagamento não pode ser nulo.");
        }
        if (pagamento.getCliente() != this) {
            throw new IllegalArgumentException("Pagamento pertence a outro cliente.");
        }
        pagamentos.add(pagamento);
        }

     /**
     * Retorna o histórico de pagamentos do cliente.
     *
     * @return cópia da lista de pagamentos
     */
    public ArrayList<Pagamento> getPagamentos(){
        return new ArrayList<>(pagamentos);
    }

     /**
     * Busca o pagamento mais recente de uma forma de cobrança.
     *
     * @param tipo forma de cobrança a filtrar
     * @return o pagamento mais recente daquele tipo, ou {@code null} se não houver
     */
    public Pagamento ultimoPagamento(OrigemPagamento origem) {
        Pagamento ultimo = null;
        for (Pagamento p : pagamentos) {
            if (p.getOrigem() == origem
                && (ultimo == null || p.getDataPagamento().isAfter(ultimo.getDataPagamento()))) {
                    ultimo = p;
            }
        }
        return ultimo;
    }

    /**
     * Guarda um extrato no histórico do cliente.
     *
     * @param extrato extrato a guardar
     * @throws IllegalArgumentException se for nulo
     */
    public void adicionarExtrato(Extrato extrato) {
        if (extrato == null) {
            throw new IllegalArgumentException("Extrato não pode ser nulo.");
        }
        extratos.add(extrato);
    }

    /**
     * Retorna os extratos do cliente.
     *
     * @return cópia da lista de extratos
     */
    public ArrayList<Extrato> getExtratos() {
        return new ArrayList<>(extratos);
    }

    /**
     * Retorna o cliente em texto, com os dados pessoais herdados de Pessoa.
     *
     * @return o objeto em texto
     */
    @Override
    public String toString() {
        return "Cliente[id=" + id + ", plano=" + plano + ", " + super.toString()
                + ", pagamentos=" + pagamentos.size() + ", extratos=" + extratos.size() + "]";
    }
}
