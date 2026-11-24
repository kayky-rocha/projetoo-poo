package poo.projeto.academia.comparator;

import java.util.Comparator;
import poo.projeto.academia.model.Cliente;

/**
 * Comparator que ordena os clientes pelo nome, em ordem alfabética. No empate, ordena pelo id.
 * <p>
 * compara um campo por vez e, se der empate (zero),
 * passa para o próximo campo.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Cliente
 */
public class ClientePorNome implements Comparator< Cliente >
{
    /**
     * Cria o comparador pelo nome.
     */
    public ClientePorNome()
    {
    }

    /**
     * Compara dois clientes pelo nome e, no empate, pelo id.
     *
     * @param cliente1 primeiro cliente
     * @param cliente2 segundo cliente
     * @return numero negativo se o primeiro vem antes, zero se forem iguais,
     *         numero positivo se o primeiro vem depois
     */
    public int compare( Cliente cliente1, Cliente cliente2 )
    {
        int nomeCompare = cliente1.getNome().compareTo(
            cliente2.getNome() ); // compara o nome

        // testa o nome primeiro
        if ( nomeCompare != 0 )
            return nomeCompare;

        int idCompare = cliente1.getId() - cliente2.getId(); // compara o id

        return idCompare; // retorna o resultado da comparação de id
    } // fim do metodo compare

    /**
     * Retorna em texto o critério de ordenação.
     *
     * @return o objeto em texto
     */
    @Override
    public String toString() {
        return "Ordena clientes pelo nome (desempate: id)";
    }
} // fim da classe ClientePorNome
