package software.ulpgc.kata7.app.io;

import software.ulpgc.kata7.architecture.io.Store;
import software.ulpgc.kata7.architecture.model.Pokemon;
import software.ulpgc.kata7.architecture.model.Pokemon.Type;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class DatabaseStore implements Store {
    private final Connection connection;

    public DatabaseStore(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Stream<Pokemon> pokemons() {
        try {
            return pokemonsIn(query());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Pokemon> pokemonsIn(ResultSet rs) {
        return Stream.generate(() -> pokemonIn(rs)).takeWhile(Objects::nonNull);
    }

    private Pokemon pokemonIn(ResultSet rs) {
        try {
            return rs.next() ? readPokemonFrom(rs) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Pokemon readPokemonFrom(ResultSet rs) throws SQLException {
        return new Pokemon(
                rs.getString(1),
                toTypes(rs.getString(2)),
                rs.getInt(3)
        );
    }

    private List<Type> toTypes(String string) {
        return Arrays.stream(string.split(",")).map(Type::valueOf).toList();
    }

    private ResultSet query() throws SQLException {
        return connection.createStatement().executeQuery("SELECT name, types, generation FROM pokemons");
    }
}
