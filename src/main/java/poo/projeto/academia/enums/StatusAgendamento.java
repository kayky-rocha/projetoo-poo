package poo.projeto.academia.enums;

/**
 * Os estados de um agendamento, conforme o diagrama de estados.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 */
public enum StatusAgendamento {
    /** Criado, mas ainda não pago. */
    PRELIMINAR,
    /** Pago e garantido. */
    CONFIRMADO,
    /** Cancelado pelo cliente ou expirado. */
    CANCELADO,
    /** A aula já aconteceu. */
    REALIZADO
}
