package software.ulpgc.kata7.app.io;

import software.ulpgc.kata7.architecture.io.Store;
import software.ulpgc.kata7.architecture.model.Pokemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Function;
import java.util.stream.Stream;

public class RemoteStore implements Store {
    private final Function<String, Pokemon> deserialize;

    public RemoteStore(Function<String, Pokemon> deserialize) {
        this.deserialize = deserialize;
    }

    @Override
    public Stream<Pokemon> pokemons() {
        try {
            return pokemonsFrom(new URL("https://raw.githubusercontent.com/lgreski/pokemonData/refs/heads/master/Pokemon.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Pokemon> pokemonsFrom(URL url) throws IOException {
        return pokemonsFrom(url.openConnection());
    }

    private Stream<Pokemon> pokemonsFrom(URLConnection connection) throws IOException {
        return pokemonsFrom(connection.getInputStream());
    }

    private Stream<Pokemon> pokemonsFrom(InputStream inputStream) {
        return pokemonsFrom(toReader(inputStream)).onClose(() -> close(inputStream));
    }

    private void close(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Pokemon> pokemonsFrom(BufferedReader reader) {
        return reader.lines().skip(1).map(deserialize);
    }

    private BufferedReader toReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
