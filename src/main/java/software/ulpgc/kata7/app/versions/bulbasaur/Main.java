package software.ulpgc.kata7.app.versions.bulbasaur;

import software.ulpgc.kata7.app.io.CsvPokeParser;
import software.ulpgc.kata7.app.io.RemoteStore;
import software.ulpgc.kata7.app.view.MainFrame;
import software.ulpgc.kata7.architecture.model.Pokemon;
import software.ulpgc.kata7.architecture.model.Pokemon.Type;
import software.ulpgc.kata7.architecture.tasks.HistogramBuilder;
import software.ulpgc.kata7.architecture.viewmodel.Histogram;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        MainFrame.create()
                .display(histogramOf(pokemons()))
                .setVisible(true);
    }

    private static Histogram<Type> histogramOf(Stream<Pokemon> pokemons) {
        return HistogramBuilder.with(pokemons)
                .title("Pokemons per Type")
                .x("Types")
                .y("Count")
                .legend("Pokemons")
                .build(p -> p.types().stream());
    }

    private static Stream<Pokemon> pokemons() {
        return new RemoteStore(CsvPokeParser::parse).pokemons();
    }
}
