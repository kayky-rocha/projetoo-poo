package poo.projeto.academia.controller.acesso;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import poo.projeto.academia.controller.GerenciarClientes;
import poo.projeto.academia.enums.TipoEvento;
import poo.projeto.academia.model.Cliente;
import poo.projeto.academia.model.RegistroCatraca;

/**
 * Catraca de entrada da academia.
 * <p>
 * Só conhece o primeiro elo da cadeia de validadores (Chain of
 * Responsibility), que é montada pelo {@link ConfigurarAcesso}.
 * Guarda em uma lista as marcações de entrada e saída de cada cliente
 * e emite os relatórios diário, mensal e anual.
 * <p>
 * A catraca externa avisa o sistema pelo método
 * {@link #receberEvento(EventoCatraca)}.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see ValidadorAcesso
 * @see RegistroCatraca
 */
public class Catraca {

    /** Primeiro elo da cadeia de validação. */
    private ValidadorAcesso primeiroValidador;

    /** Cadastro usado para achar o cliente pelo id que vem da catraca externa. */
    private GerenciarClientes gerenciarClientes;

    /** Marcações de entrada e saída. */
    private ArrayList<RegistroCatraca> registros = new ArrayList<>();

    /**
     * Cria a catraca com a cadeia já montada.
     *
     * @param primeiroValidador primeiro elo da cadeia
     * @throws IllegalArgumentException se o validador for nulo
     */
    public Catraca(ValidadorAcesso primeiroValidador) {
        if (primeiroValidador == null) {
            throw new IllegalArgumentException("Validador nulo.");
        }
        this.primeiroValidador = primeiroValidador;
    }

    /**
     * Cria a catraca com a cadeia já montada e o cadastro de clientes,
     * necessário para receber os eventos da catraca externa.
     *
     * @param primeiroValidador primeiro elo da cadeia
     * @param gerenciarClientes cadastro de clientes
     * @throws IllegalArgumentException se o validador for nulo
     */
    public Catraca(ValidadorAcesso primeiroValidador, GerenciarClientes gerenciarClientes) {
        this(primeiroValidador);
        this.gerenciarClientes = gerenciarClientes;
    }

    /**
     * Tenta registrar a entrada do cliente agora.
     *
     * @param cliente cliente que quer entrar
     * @return resultado da validação
     */
    public ResultadoAcesso registrarEntrada(Cliente cliente) {
        return registrarEntrada(cliente, LocalDateTime.now());
    }

    /**
     * Tenta registrar a entrada do cliente em uma data e hora.
     * <p>
     * Passa o pedido pela cadeia. Se for liberado, cria um registro de entrada.
     *
     * @param cliente  cliente que quer entrar
     * @param dataHora data e hora da tentativa
     * @return resultado da validação
     */
    public ResultadoAcesso registrarEntrada(Cliente cliente, LocalDateTime dataHora) {
        if (cliente != null && buscarRegistroAberto(cliente) != null) {
            return new ResultadoAcesso(false, "Cliente já está na academia.");
        }

        SolicitacaoAcesso solicitacao = new SolicitacaoAcesso(cliente, dataHora);
        ResultadoAcesso resultado = primeiroValidador.validar(solicitacao);

        if (resultado.isLiberado()) {
            registros.add(new RegistroCatraca(cliente, dataHora));
        }
        return resultado;
    }

    /**
     * Registra a saída do cliente agora.
     *
     * @param cliente cliente que está saindo
     * @return {@code true} se havia uma entrada aberta para fechar
     */
    public boolean registrarSaida(Cliente cliente) {
        return registrarSaida(cliente, LocalDateTime.now());
    }

    /**
     * Registra a saída do cliente em uma data e hora.
     *
     * @param cliente  cliente que está saindo
     * @param dataHora data e hora da saída
     * @return {@code true} se havia uma entrada aberta para fechar
     */
    public boolean registrarSaida(Cliente cliente, LocalDateTime dataHora) {
        RegistroCatraca registro = buscarRegistroAberto(cliente);

        if (registro == null) {
            return false;
        }
        registro.registrarSaida(dataHora);
        return true;
    }

    /**
     * Recebe um aviso da catraca externa e atualiza os registros.
     * <p>
     * Acha o cliente pelo id e, conforme o tipo do evento, registra a
     * entrada (passando pela cadeia) ou a saída.
     *
     * @param evento aviso enviado pela catraca externa
     * @return resultado da passagem
     */
    public ResultadoAcesso receberEvento(EventoCatraca evento) {
        if (gerenciarClientes == null) {
            return new ResultadoAcesso(false, "Catraca sem cadastro de clientes.");
        }

        Cliente cliente = gerenciarClientes.buscarCliente(evento.getIdCliente());

        if (cliente == null) {
            return new ResultadoAcesso(false, "Cliente não cadastrado.");
        }

        if (evento.getTipo() == TipoEvento.ENTRADA) {
            return registrarEntrada(cliente, evento.getDataHora());
        }

        if (registrarSaida(cliente, evento.getDataHora())) {
            return new ResultadoAcesso(true, "Saída registrada.");
        }
        return new ResultadoAcesso(false, "Cliente não está na academia.");
    }

    /**
     * Lista as entradas de um dia.
     *
     * @param data dia do relatório
     * @return registros com entrada nesse dia
     */
    public ArrayList<RegistroCatraca> relatorioDiario(LocalDate data) {
        ArrayList<RegistroCatraca> resultado = new ArrayList<>();
        for (RegistroCatraca r : registros) {
            if (r.getEntrada().toLocalDate().equals(data)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    /**
     * Lista as entradas de um mês.
     *
     * @param ano ano do relatório
     * @param mes mês do relatório (1 a 12)
     * @return registros com entrada nesse mês
     */
    public ArrayList<RegistroCatraca> relatorioMensal(int ano, int mes) {
        ArrayList<RegistroCatraca> resultado = new ArrayList<>();
        for (RegistroCatraca r : registros) {
            if (r.getEntrada().getYear() == ano && r.getEntrada().getMonthValue() == mes) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    /**
     * Lista as entradas de um ano.
     *
     * @param ano ano do relatório
     * @return registros com entrada nesse ano
     */
    public ArrayList<RegistroCatraca> relatorioAnual(int ano) {
        ArrayList<RegistroCatraca> resultado = new ArrayList<>();
        for (RegistroCatraca r : registros) {
            if (r.getEntrada().getYear() == ano) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    /**
     * Lista todas as marcações.
     *
     * @return cópia da lista de registros
     */
    public ArrayList<RegistroCatraca> listarRegistros() {
        return new ArrayList<>(registros);
    }

    /**
     * Procura a entrada do cliente que ainda não tem saída.
     *
     * @param cliente cliente procurado
     * @return o registro aberto, ou {@code null} se o cliente não está dentro
     */
    private RegistroCatraca buscarRegistroAberto(Cliente cliente) {
        for (RegistroCatraca r : registros) {
            if (r.getCliente().getId() == cliente.getId() && r.estaAberto()) {
                return r;
            }
        }
        return null;
    }
}
