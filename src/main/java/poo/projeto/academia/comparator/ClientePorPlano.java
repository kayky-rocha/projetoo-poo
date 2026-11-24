package poo.projeto.academia.comparator;

import java.util.Comparator;
import poo.projeto.academia.model.Cliente;

/**
 * Comparator que ordena os clientes pelo plano (primeiro DIARIA, depois
 * MENSALIDADE). No empate, ordena pelo nome.
 * <p>
 * compara um campo por vez e, se der empate (zero),
 * passa para o próximo campo.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Cliente
 */
public class ClientePorPlano implements Comparator< Cliente >
{
    /**
     * Cria o comparador pelo plano.
     */
    public ClientePorPlano()
    {
    }

    /**
     * Compara dois clientes pelo plano e, no empate, pelo nome.
     *
     * @param cliente1 primeiro cliente
     * @param cliente2 segundo cliente
     * @return numero negativo se o primeiro vem antes, zero se forem iguais,
     *         numero positivo se o primeiro vem depois
     */
    public int compare( Cliente cliente1, Cliente cliente2 )
    {
        int planoCompare = cliente1.getPlano().compareTo(
            cliente2.getPlano() ); // compara o plano

        // testa o plano primeiro
        if ( planoCompare != 0 )
            return planoCompare;

        int nomeCompare = cliente1.getNome().compareTo(
            cliente2.getNome() ); // compara o nome

        return nomeCompare; // retorna o resultado da comparação de nomes
    } // fim do metodo compare

    /**
     * Retorna em texto o critério de ordenação.
     *
     * @return o objeto em texto
     */
    @Override
    public String toString() {
        return "Ordena clientes pelo plano (desempate: nome)";
    }
} // fim da classe ClientePorPlano
