package poo.projeto.academia.model;

import java.time.LocalTime;

/**
 * Aula oferecida pela academia em uma das salas, em um horário fixo,
 * acompanhada por um instrutor.
 * <p>
 * O tipo da aula é o tipo da sala onde ela acontece. Os preços de diária e de
 * mensalidade são definidos por aula.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 */
public class Aula {

    /** Código único da aula. */
    private int codigo;

    /** Nome da aula, por exemplo "Spinning manhã". */
    private String nome;

    /** Horário de início da aula. */
    private LocalTime horario;

    /** Número de vagas da aula (limitado pela capacidade da sala). */
    private int capacidade;

    /** Preço cobrado por uma diária nesta aula. */
    private double valorDiaria;

    /** Preço cobrado por uma mensalidade nesta aula. */
    private double valorMensalidade;

    /** Sala onde a aula acontece. */
    private Sala sala;

    /** Funcionário que conduz a aula. */
    private Funcionario instrutor;

    /**
     * Cria uma aula. O número de vagas começa igual à capacidade da sala.
     *
     * @param codigo           código da aula
     * @param nome             nome da aula
     * @param sala             sala onde acontece
     * @param instrutor        instrutor responsável
     * @param horario          horário de início
     * @param valorDiaria      preço da diária
     * @param valorMensalidade preço da mensalidade
     */
    public Aula(int codigo, String nome, Sala sala, Funcionario instrutor, LocalTime horario,
            double valorDiaria, double valorMensalidade) {
        this.codigo = codigo;
        this.nome = nome;
        this.horario = horario;
        this.valorDiaria = valorDiaria;
        this.valorMensalidade = valorMensalidade;
        this.instrutor = instrutor;
        setSala(sala);
    }

    /**
     * Retorna o código da aula.
     *
     * @return o código
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Altera o código da aula.
     *
     * @param codigo o novo código
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna o nome da aula.
     *
     * @return o nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Altera o nome da aula.
     *
     * @param nome o novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o tipo da aula, que é o tipo da sala onde ela acontece.
     *
     * @return o tipo da aula, ou {@code null} se não houver sala
     */
    public TipoSala getTipoAula() {
        return sala == null ? null : sala.getTipo();
    }

    /**
     * Retorna o horário de início.
     *
     * @return o horário
     */
    public LocalTime getHorario() {
        return horario;
    }

    /**
     * Altera o horário de início.
     *
     * @param horario o novo horário
     */
    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    /**
     * Retorna o número de vagas da aula.
     *
     * @return a capacidade
     */
    public int getCapacidade() {
        return capacidade;
    }

    /**
     * Altera o número de vagas, sem ultrapassar a capacidade da sala.
     *
     * @param capacidade a nova capacidade
     * @throws IllegalArgumentException se for menor que 1 ou maior que a capacidade da sala
     */
    public void setCapacidade(int capacidade) {
        if (capacidade < 1 || (sala != null && capacidade > sala.getCapacidade())) {
            throw new IllegalArgumentException("Capacidade deve estar entre 1 e a capacidade da sala.");
        }
        this.capacidade = capacidade;
    }

    /**
     * Retorna o preço da diária.
     *
     * @return o preço da diária
     */
    public double getValorDiaria() {
        return valorDiaria;
    }

    /**
     * Altera o preço da diária.
     *
     * @param valorDiaria o novo preço
     */
    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    /**
     * Retorna o preço da mensalidade.
     *
     * @return o preço da mensalidade
     */
    public double getValorMensalidade() {
        return valorMensalidade;
    }

    /**
     * Altera o preço da mensalidade.
     *
     * @param valorMensalidade o novo preço
     */
    public void setValorMensalidade(double valorMensalidade) {
        this.valorMensalidade = valorMensalidade;
    }

    /**
     * Retorna o preço conforme a forma de cobrança.
     *
     * @param tipo diária ou mensalidade
     * @return o preço correspondente
     */
    public double getValor(TipoCobranca tipo) {
        return tipo == TipoCobranca.MENSALIDADE ? valorMensalidade : valorDiaria;
    }

    /**
     * Retorna a sala da aula.
     *
     * @return a sala
     */
    public Sala getSala() {
        return sala;
    }

    /**
     * Define a sala da aula e ajusta o número de vagas para a capacidade da sala.
     *
     * @param sala a nova sala
     */
    public void setSala(Sala sala) {
        this.sala = sala;
        if (sala != null) {
            this.capacidade = sala.getCapacidade();
        }
    }

    /**
     * Retorna o instrutor da aula.
     *
     * @return o instrutor
     */
    public Funcionario getInstrutor() {
        return instrutor;
    }

    /**
     * Define o instrutor da aula.
     *
     * @param instrutor o novo instrutor
     */
    public void setInstrutor(Funcionario instrutor) {
        this.instrutor = instrutor;
    }

    /**
     * Retorna os dados da aula em texto.
     *
     * @return representação da aula
     */
    @Override
    public String toString() {
        return "Aula[codigo=" + codigo + ", nome=" + nome + ", tipo=" + getTipoAula()
                + ", sala=" + (sala == null ? "-" : sala.getNumero()) + ", horario=" + horario
                + ", instrutor=" + (instrutor == null ? "-" : instrutor.getNome())
                + ", vagas=" + capacidade + ", diaria=R$ " + String.format("%.2f", valorDiaria)
                + ", mensalidade=R$ " + String.format("%.2f", valorMensalidade) + "]";
    }
}