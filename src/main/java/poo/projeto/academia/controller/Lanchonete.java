package poo.projeto.academia.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.model.ItemCardapio;
import poo.projeto.academia.model.VendaLanchonete;

/**
 * Subsistema da lanchonete: cardápio e pedidos.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see ItemCardapio
 * @see VendaLanchonete
 */
public class Lanchonete {

    /**
     * Cria a lanchonete com o cardápio vazio.
     */
    public Lanchonete() {
    }

    /** Itens do cardápio. */
    private ArrayList<ItemCardapio> cardapio = new ArrayList<>();

    /** Pedidos abertos na lanchonete. */
    private ArrayList<VendaLanchonete> vendas = new ArrayList<>();

    /**
     * Cadastra um item no cardápio.
     *
     * @param item item a cadastrar
     * @throws IllegalArgumentException se o item for nulo
     */
    public void cadastrarItem(ItemCardapio item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo.");
        }
        cardapio.add(item);
    }

    /**
     * Busca um item do cardápio pelo id.
     *
     * @param id identificador do item
     * @return o item, ou {@code null} se não existir
     */
    public ItemCardapio buscarItem(int id) {
        for (ItemCardapio i : cardapio) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    /**
     * Abre um pedido novo na lanchonete.
     *
     * @param cliente cliente do pedido, ou {@code null} na venda de balcão
     * @return o pedido aberto, para adicionar itens e finalizar
     */
    public VendaLanchonete registrarPedido(Cliente cliente) {
        VendaLanchonete pedido = new VendaLanchonete(cliente);
        vendas.add(pedido);
        return pedido;
    }

    /**
     * Lista o cardápio.
     *
     * @return cópia da lista de itens
     */
    public ArrayList<ItemCardapio> listarCardapio() {
        return new ArrayList<>(cardapio);
    }

    /**
     * Lista os pedidos finalizados de um dia.
     *
     * @param data dia do relatório
     * @return pedidos finalizados nesse dia
     */
    public ArrayList<VendaLanchonete> relatorioVendas(LocalDate data) {
        ArrayList<VendaLanchonete> resultado = new ArrayList<>();
        for (VendaLanchonete v : vendas) {
            if (v.isFinalizada() && v.getData().toLocalDate().equals(data)) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    /**
     * Lista os pedidos finalizados de um mês.
     *
     * @param ano ano do relatório
     * @param mes mês do relatório (1 a 12)
     * @return pedidos finalizados nesse mês
     */
    public ArrayList<VendaLanchonete> relatorioVendasMes(int ano, int mes) {
        ArrayList<VendaLanchonete> resultado = new ArrayList<>();
        for (VendaLanchonete v : vendas) {
            if (v.isFinalizada() && v.getData().getYear() == ano
                    && v.getData().getMonthValue() == mes) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    /**
     * Lista todos os pedidos da lanchonete, finalizados ou não.
     *
     * @return cópia da lista de pedidos
     */
    public ArrayList<VendaLanchonete> listarPedidos() {
        return new ArrayList<>(vendas);
    }

    /**
     * Troca o cardápio e os pedidos pelos que foram carregados do JSON.
     *
     * @param listaCardapio itens do cardápio carregados
     * @param listaPedidos  pedidos carregados
     */
    public void restaurar(ArrayList<ItemCardapio> listaCardapio, ArrayList<VendaLanchonete> listaPedidos) {
        cardapio = listaCardapio;
        vendas = listaPedidos;
    }

    /**
     * Retorna a lanchonete em texto.
     *
     * @return quantidade de itens no cardápio e de pedidos
     */
    @Override
    public String toString() {
        return "Lanchonete: " + cardapio.size() + " item(ns) no cardápio, " + vendas.size() + " pedido(s)";
    }
}
