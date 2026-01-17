package software.ulpgc.kata7.app.io;

import software.ulpgc.kata7.architecture.io.Recorder;
import software.ulpgc.kata7.architecture.model.Pokemon;
import software.ulpgc.kata7.architecture.model.Pokemon.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

public class DatabaseRecorder implements Recorder {
    private final Connection connection;
    private final PreparedStatement statement;

    public DatabaseRecorder(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists(connection);
        statement = connection.prepareStatement("INSERT INTO pokemons (name, types, generation) VALUES (?, ?, ?)");
    }

    private static void createTableIfNotExists(Connection connection) throws SQLException {
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS pokemons (name TEXT, types TEXT, generation INTEGER)");
    }

    @Override
    public void put(Stream<Pokemon> pokemons) {
        try {
            pokemons.forEach(this::record);
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void record(Pokemon pokemon) {
        try {
            write(pokemon);
            executeBatchIfRequired();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(Pokemon pokemon) throws SQLException {
        this.statement.setString(1, pokemon.name());
        this.statement.setString(2, toTypesString(pokemon.types()));
        this.statement.setInt(3, pokemon.generation());
        statement.addBatch();
    }

    private int count = 0;
    private void executeBatchIfRequired() throws SQLException {
        if (++count % 1000 == 0) statement.executeBatch();
    }

    private String toTypesString(List<Type> types) {
        return String.join(",", toStringList(types));
    }

    private List<String> toStringList(List<Type> types) {
        return types.stream().map(Type::name).toList();
    }
}
