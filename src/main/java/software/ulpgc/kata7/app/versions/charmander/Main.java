package software.ulpgc.kata7.app.versions.charmander;

import software.ulpgc.kata7.app.io.CsvPokeParser;
import software.ulpgc.kata7.app.io.DatabaseRecorder;
import software.ulpgc.kata7.app.io.DatabaseStore;
import software.ulpgc.kata7.app.io.RemoteStore;
import software.ulpgc.kata7.app.view.MainFrame;
import software.ulpgc.kata7.architecture.model.Pokemon.Type;
import software.ulpgc.kata7.architecture.tasks.HistogramBuilder;
import software.ulpgc.kata7.architecture.viewmodel.Histogram;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static final File database = new File("pokemon.db");
    public static void main(String[] args) {
        try (Connection connection = openConnection()) {
            importDataFromRemoteIfRequired(connection);
            Histogram<Type> histogram = HistogramBuilder.with(new DatabaseStore(connection).pokemons())
                    .title("Pokemons per Type")
                    .x("Types")
                    .y("Count")
                    .legend("Pokemons")
                    .build(p -> p.types().stream());
            MainFrame.create()
                    .display(histogram)
                    .setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void importDataFromRemoteIfRequired(Connection connection) throws SQLException {
        if (database.length() > 0) return;
        new DatabaseRecorder(connection).put(new RemoteStore(CsvPokeParser::parse).pokemons());
    }

    private static Connection openConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database.getAbsolutePath());
        connection.setAutoCommit(false);
        return connection;
    }
}
