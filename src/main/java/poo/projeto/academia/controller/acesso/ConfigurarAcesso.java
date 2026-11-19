package poo.projeto.academia.controller.acesso;

import poo.projeto.academia.controller.GerenciarClientes;
import poo.projeto.academia.controller.Agenda;

/**
 * Monta as cadeias de validação de acesso.
 * <p>
 * Concentra a ordem dos elos em um único lugar. Para reordenar uma cadeia
 * ou encaixar uma regra nova, só esta classe precisa mudar.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see ValidadorAcesso
 * @see Catraca
 */
public class ConfigurarAcesso {
    
    /**
     * Monta a cadeia da entrada na academia: cadastro, mensalidade e agendamento.
     *
     * @param gerenciarClientes gerenciador usado para conferir o cadastro
     * @param agenda            agenda usada para conferir o agendamento do diarista
     * @return o primeiro elo da cadeia
     */
    public static ValidadorAcesso montarCadeiaEntrada(GerenciarClientes gerenciarClientes, Agenda agenda) {
        ValidadorAcesso cadastro = new ValidadorCadastro(gerenciarClientes);
        ValidadorAcesso mensalidade = new ValidadorMensalidade();
        ValidadorAcesso agendamento = new ValidadorAgendamento(agenda);

        cadastro.definirProximo(mensalidade).definirProximo(agendamento);
        return cadastro;
    }
    
}
