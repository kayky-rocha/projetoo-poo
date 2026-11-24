package poo.projeto.academia.comparator;

import java.util.Comparator;
import poo.projeto.academia.model.Cliente;

/**
 * Comparator que ordena os clientes pelo id, do menor para o maior.
 * <p>
 * Como o id é único, não há empate.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see Cliente
 */
public class ClientePorId implements Comparator< Cliente >
{
    /**
     * Cria o comparador pelo id.
     */
    public ClientePorId()
    {
    }

    /**
     * Compara dois clientes pelo id.
     *
     * @param cliente1 primeiro cliente
     * @param cliente2 segundo cliente
     * @return numero negativo se o primeiro vem antes, zero se forem iguais,
     *         numero positivo se o primeiro vem depois
     */
    public int compare( Cliente cliente1, Cliente cliente2 )
    {
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
        return "Ordena clientes pelo id";
    }
} // fim da classe ClientePorId
