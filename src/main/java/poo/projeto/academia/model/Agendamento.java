package poo.projeto.academia.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import poo.projeto.academia.enums.StatusAgendamento;

/**
 * Agendamento de um cliente em uma aula, em uma data.
 * <p>
 * Todo agendamento nasce {@code PRELIMINAR}. Depois pode ser confirmado,
 * cancelado (com reembolso de metade do valor) ou realizado. Um
 * agendamento preliminar expira depois de 5 dias úteis.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see StatusAgendamento
 */
public class Agendamento {

    /** Prazo, em dias úteis, para confirmar um agendamento preliminar. */
    private static final int PRAZO_DIAS_UTEIS = 5;

    /** Código único do agendamento. */
    private int codigo;

    /** Cliente que agendou. */
    private Cliente cliente;

    /** Aula agendada. */
    private Aula aula;

    /** Data em que a aula vai acontecer. */
    private LocalDate data;

    /** Data em que o agendamento foi feito. */
    private LocalDate dataCriacao;

    /** Valor cobrado, em reais. */
    private double valor;

    /** Estado atual do agendamento. */
    private StatusAgendamento status;

    /**
     * Cria um agendamento preliminar. O valor vem da aula, de acordo
     * com o plano do cliente.
     *
     * @param codigo  código do agendamento
     * @param cliente cliente que agendou
     * @param aula    aula agendada
     * @param data    data da aula
     */
    public Agendamento(int codigo, Cliente cliente, Aula aula, LocalDate data) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.aula = aula;
        this.data = data;
        this.dataCriacao = LocalDate.now();
        this.valor = aula.getValor(cliente.getPlano());
        this.status = StatusAgendamento.PRELIMINAR;
    }

    /**
     * Confirma o agendamento. Só funciona se ele estiver preliminar.
     *
     * @return {@code true} se confirmou; {@code false} caso contrário
     */
    public boolean confirmar() {
        if (status != StatusAgendamento.PRELIMINAR) {
            return false;
        }
        status = StatusAgendamento.CONFIRMADO;
        return true;
    }

    /**
     * Cancela o agendamento e calcula o reembolso.
     *
     * @return valor a devolver ao cliente; 0 se não pôde cancelar
     */
    public double cancelar() {
        if (status == StatusAgendamento.CANCELADO || status == StatusAgendamento.REALIZADO) {
            return 0;
        }
        status = StatusAgendamento.CANCELADO;
        return calcularReembolso();
    }

    /**
     * Marca a aula como realizada. Só funciona se ele estiver confirmado.
     *
     * @return {@code true} se marcou; {@code false} caso contrário
     */
    public boolean realizar() {
        if (status != StatusAgendamento.CONFIRMADO) {
            return false;
        }
        status = StatusAgendamento.REALIZADO;
        return true;
    }

    /**
     * Calcula o reembolso em caso de cancelamento: metade do valor.
     *
     * @return valor do reembolso, em reais
     */
    public double calcularReembolso() {
        return valor / 2;
    }

    /**
     * Verifica se o agendamento preliminar passou do prazo de 5 dias úteis.
     *
     * @param hoje data usada como referência
     * @return {@code true} se está preliminar e passou do prazo
     */
    public boolean expirou(LocalDate hoje) {
        if (status != StatusAgendamento.PRELIMINAR) {
            return false;
        }

        int diasUteis = 0;
        LocalDate dia = dataCriacao.plusDays(1);

        while (dia.isAfter(hoje) == false) {
            if (dia.getDayOfWeek() != DayOfWeek.SATURDAY && dia.getDayOfWeek() != DayOfWeek.SUNDAY) {
                diasUteis = diasUteis + 1;
            }
            dia = dia.plusDays(1);
        }

        return diasUteis > PRAZO_DIAS_UTEIS;
    }

    /**
     * Retorna o código do agendamento.
     *
     * @return o código
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Retorna o cliente que agendou.
     *
     * @return o cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Retorna a aula agendada.
     *
     * @return a aula
     */
    public Aula getAula() {
        return aula;
    }

    /**
     * Retorna a data da aula.
     *
     * @return a data
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Retorna a data em que o agendamento foi feito.
     *
     * @return a data de criação
     */
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    /**
     * Altera a data em que o agendamento foi feito (útil em testes).
     *
     * @param dataCriacao a nova data de criação
     */
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    /**
     * Retorna o valor cobrado.
     *
     * @return o valor, em reais
     */
    public double getValor() {
        return valor;
    }

    /**
     * Retorna o estado atual do agendamento.
     *
     * @return o status
     */
    public StatusAgendamento getStatus() {
        return status;
    }
}
