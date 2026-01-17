package software.ulpgc.kata7.architecture.tasks;

import software.ulpgc.kata7.architecture.model.Pokemon;
import software.ulpgc.kata7.architecture.viewmodel.Histogram;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class HistogramBuilder {
    private final Stream<Pokemon> pokemons;
    private final Map<String, String> labels;

    public static HistogramBuilder with(Stream<Pokemon> pokemons) {
        return new HistogramBuilder(pokemons);
    }

    private HistogramBuilder(Stream<Pokemon> pokemons) {
        this.pokemons = pokemons;
        labels = new HashMap<>();
    }

    public <T> Histogram<T> build(Function<Pokemon, Stream<T>> binarize) {
        Histogram<T> histogram = new Histogram<>(labels);
        pokemons.flatMap(binarize).forEach(histogram::put);
        return histogram;
    }

    public HistogramBuilder title(String label) {
        labels.put("title", label);
        return this;
    }

    public HistogramBuilder x(String label) {
        labels.put("x", label);
        return this;
    }

    public HistogramBuilder y(String label) {
        labels.put("y", label);
        return this;
    }

    public HistogramBuilder legend(String label) {
        labels.put("legend", label);
        return this;
    }
}
