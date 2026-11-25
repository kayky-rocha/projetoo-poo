package poo.projeto.academia.persistencia;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Ensina o Gson a salvar e ler {@link LocalDateTime} no JSON.
 * <p>
 * No Java 17, o Gson não consegue acessar sozinho os campos internos das
 * classes de data. Este adaptador grava uma data com hora como texto (por exemplo,
 * {@code "2026-11-25T08:30"}) e, ao carregar, transforma o texto de volta em LocalDateTime.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see RepositorioJson
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

    /**
     * Cria o adaptador.
     */
    public LocalDateTimeAdapter() {
    }

    /**
     * Grava o valor no JSON como texto.
     *
     * @param saida escritor do JSON
     * @param valor valor a gravar; pode ser {@code null}
     * @throws IOException se der erro ao escrever
     */
    @Override
    public void write(JsonWriter saida, LocalDateTime valor) throws IOException {
        if (valor == null) {
            saida.nullValue();
        } else {
            saida.value(valor.toString());
        }
    }

    /**
     * Lê o texto do JSON e transforma em LocalDateTime.
     *
     * @param entrada leitor do JSON
     * @return o valor lido, ou {@code null} se estiver vazio
     * @throws IOException se der erro ao ler
     */
    @Override
    public LocalDateTime read(JsonReader entrada) throws IOException {
        if (entrada.peek() == JsonToken.NULL) {
            entrada.nextNull();
            return null;
        }
        return LocalDateTime.parse(entrada.nextString());
    }

    /**
     * Retorna o adaptador em texto.
     *
     * @return o tipo que este adaptador trata
     */
    @Override
    public String toString() {
        return "LocalDateTimeAdapter";
    }
}
