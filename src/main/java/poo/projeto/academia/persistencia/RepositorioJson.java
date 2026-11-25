package poo.projeto.academia.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Lê e grava arquivos JSON usando a biblioteca Gson.
 * <p>
 * Os arquivos são abertos com try-with-resources, que fecha o arquivo
 * sozinho no fim, mesmo se acontecer um erro no meio.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see PersistenciaSistema
 */
public class RepositorioJson {

    /** Gson configurado com os adaptadores de data e hora. */
    private Gson gson;

    /**
     * Cria o repositório com o Gson já configurado.
     */
    public RepositorioJson() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .setPrettyPrinting()
                .create();
    }

    /**
     * Grava os dados em um arquivo JSON.
     *
     * @param caminho caminho do arquivo, por exemplo {@code "dados/clientes.json"}
     * @param dados   objeto ou lista a gravar
     * @throws IOException se não conseguir gravar o arquivo
     */
    public void salvar(String caminho, Object dados) throws IOException {
        try (FileWriter escritor = new FileWriter(caminho)) {
            gson.toJson(dados, escritor);
        }
    }

    /**
     * Lê um arquivo JSON e transforma de volta em objeto.
     *
     * @param <T>     tipo do objeto lido
     * @param caminho caminho do arquivo
     * @param tipo    tipo do objeto, por exemplo uma lista de clientes
     * @return o objeto lido, ou {@code null} se o arquivo não existir
     * @throws IOException se não conseguir ler o arquivo
     */
    public <T> T carregar(String caminho, Type tipo) throws IOException {
        File arquivo = new File(caminho);
        if (arquivo.exists() == false) {
            return null;
        }
        try (FileReader leitor = new FileReader(arquivo)) {
            return gson.fromJson(leitor, tipo);
        }
    }

    /**
     * Retorna o repositório em texto.
     *
     * @return descrição do repositório
     */
    @Override
    public String toString() {
        return "RepositorioJson (Gson com adaptadores de data e hora)";
    }
}
