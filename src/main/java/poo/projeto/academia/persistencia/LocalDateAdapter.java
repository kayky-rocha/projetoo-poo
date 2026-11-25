package poo.projeto.academia.persistencia;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Ensina o Gson a salvar e ler {@link LocalDate} no JSON.
 * <p>
 * No Java 17, o Gson não consegue acessar sozinho os campos internos das
 * classes de data. Este adaptador grava uma data como texto (por exemplo,
 * {@code "2026-11-25"}) e, ao carregar, transforma o texto de volta em LocalDate.
 *
 * @author Kayky Francisco Rodrigues da Rocha
 * @author Aaron Araujo Agapito Guedes
 * @see RepositorioJson
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    /**
     * Cria o adaptador.
     */
    public LocalDateAdapter() {
    }

    /**
     * Grava o valor no JSON como texto.
     *
     * @param saida escritor do JSON
     * @param valor valor a gravar; pode ser {@code null}
     * @throws IOException se der erro ao escrever
     */
    @Override
    public void write(JsonWriter saida, LocalDate valor) throws IOException {
        if (valor == null) {
            saida.nullValue();
        } else {
            saida.value(valor.toString());
        }
    }

    /**
     * Lê o texto do JSON e transforma em LocalDate.
     *
     * @param entrada leitor do JSON
     * @return o valor lido, ou {@code null} se estiver vazio
     * @throws IOException se der erro ao ler
     */
    @Override
    public LocalDate read(JsonReader entrada) throws IOException {
        if (entrada.peek() == JsonToken.NULL) {
            entrada.nextNull();
            return null;
        }
        return LocalDate.parse(entrada.nextString());
    }

    /**
     * Retorna o adaptador em texto.
     *
     * @return o tipo que este adaptador trata
     */
    @Override
    public String toString() {
        return "LocalDateAdapter";
    }
}
