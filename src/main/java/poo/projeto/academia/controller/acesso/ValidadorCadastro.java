package poo.projeto.academia.controller.acesso;

import poo.projeto.academia.controller.GerenciarClientes;
import poo.projeto.academia.model.Cliente;

/**
 * Elo da catraca que confere se o cliente está cadastrado na academia.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see ValidadorAcesso
 * @see GerenciarClientes
 */
public class ValidadorCadastro extends ValidadorAcesso {

    /** Gerenciador onde os clientes cadastrados são procurados. */
    private GerenciarClientes gerenciarClientes;

    /**
     * Cria o validador de cadastro.
     *
     * @param gerenciarClientes gerenciador com os clientes cadastrados
     */
    public ValidadorCadastro(GerenciarClientes gerenciarClientes) {
        this.gerenciarClientes = gerenciarClientes;
    }

    /**
     * Reprova se o cliente não existir ou não estiver cadastrado.
     *
     * @param solicitacao pedido de acesso
     * @return resultado da verificação de cadastro
     */
    @Override
    protected ResultadoAcesso verificar(SolicitacaoAcesso solicitacao) {
        Cliente cliente = solicitacao.getCliente();

        if (cliente == null) {
            return new ResultadoAcesso(false, "Cliente não informado.");
        }

        if (gerenciarClientes.buscarCliente(cliente.getId()) == null) {
            return new ResultadoAcesso(false, "Cliente não cadastrado.");
        }

        return new ResultadoAcesso(true, "Acesso liberado.");
    }
}
