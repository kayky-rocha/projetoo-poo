package poo.projeto.academia.controller.acesso;

import poo.projeto.academia.controller.Agenda;
import poo.projeto.academia.controller.GerenciarClientes;


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
     * Cria o configurador. Os métodos são estáticos, então não é preciso criar objetos desta classe.
     */
    public ConfigurarAcesso() {
    }

    /**
     * Monta a cadeia da entrada na academia: cadastro, mensalidade e diária.
     * <p>
     * O agendamento de aula não faz parte desta cadeia. Ele é conferido
     * na cadeia da aula.
     *
     * @param gerenciarClientes gerenciador usado para conferir o cadastro
     * @return o primeiro elo da cadeia
     */
    public static ValidadorAcesso montarCadeiaEntrada(GerenciarClientes gerenciarClientes) {
        ValidadorAcesso cadastro = new ValidadorCadastro(gerenciarClientes);
        ValidadorAcesso mensalidade = new ValidadorMensalidade();
        ValidadorAcesso diaria = new ValidadorDiaria();
        

        cadastro.definirProximo(mensalidade).definirProximo(diaria);
        return cadastro;
    }

    /**
     * Monta a cadeia da entrada em uma aula: cadastro e agendamento.
     * <p>
     * Vale para todos os clientes, inclusive o mensalista: só entra na
     * aula quem tem agendamento confirmado nela no dia.
     *
     * @param gerenciarClientes gerenciador usado para conferir o cadastro
     * @param agenda            agenda usada para conferir o agendamento
     * @return o primeiro elo da cadeia
     */
    public static ValidadorAcesso montarCadeiaAula(GerenciarClientes gerenciarClientes, Agenda agenda) {
        ValidadorAcesso cadastro = new ValidadorCadastro(gerenciarClientes);
        ValidadorAcesso agendamento = new ValidadorAgendamento(agenda);

        cadastro.definirProximo(agendamento);
        return cadastro;
    }

    /**
     * Retorna em texto o que esta classe faz.
     *
     * @return o objeto em texto
     */
    @Override
    public String toString() {
        return "ConfigurarAcesso: monta a cadeia de entrada e a cadeia da aula";
    }
}
