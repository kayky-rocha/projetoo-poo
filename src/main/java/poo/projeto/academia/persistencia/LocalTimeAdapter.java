package poo.projeto.academia.persistencia;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalTime;

/**
 * Ensina o Gson a salvar e ler {@link LocalTime} no JSON.
 * <p>
 * No Java 17, o Gson não consegue acessar sozinho os campos internos das
 * classes de data. Este adaptador grava um horário como texto (por exemplo,
 * {@code "08:30"}) e, ao carregar, transforma o texto de volta em LocalTime.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see RepositorioJson
 */
public class LocalTimeAdapter extends TypeAdapter<LocalTime> {

    /**
     * Cria o adaptador.
     */
    public LocalTimeAdapter() {
    }

    /**
     * Grava o valor no JSON como texto.
     *
     * @param saida escritor do JSON
     * @param valor valor a gravar; pode ser {@code null}
     * @throws IOException se der erro ao escrever
     */
    @Override
    public void write(JsonWriter saida, LocalTime valor) throws IOException {
        if (valor == null) {
            saida.nullValue();
        } else {
            saida.value(valor.toString());
        }
    }

    /**
     * Lê o texto do JSON e transforma em LocalTime.
     *
     * @param entrada leitor do JSON
     * @return o valor lido, ou {@code null} se estiver vazio
     * @throws IOException se der erro ao ler
     */
    @Override
    public LocalTime read(JsonReader entrada) throws IOException {
        if (entrada.peek() == JsonToken.NULL) {
            entrada.nextNull();
            return null;
        }
        return LocalTime.parse(entrada.nextString());
    }

    /**
     * Retorna o adaptador em texto.
     *
     * @return o tipo que este adaptador trata
     */
    @Override
    public String toString() {
        return "LocalTimeAdapter";
    }
}
