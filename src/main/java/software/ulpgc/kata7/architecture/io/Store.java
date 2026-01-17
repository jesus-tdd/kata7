package software.ulpgc.kata7.architecture.io;

import software.ulpgc.kata7.architecture.model.Pokemon;

import java.util.stream.Stream;

public interface Store {
    Stream<Pokemon> pokemons();
}
