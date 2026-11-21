package poo.projeto.academia.controller.acesso;

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
    
}
