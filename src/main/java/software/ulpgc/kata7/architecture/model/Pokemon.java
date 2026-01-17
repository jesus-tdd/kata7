package software.ulpgc.kata7.architecture.model;

import java.util.List;

public record Pokemon(String name, List<Type> types, int generation) {
    public enum Type {
        Normal, Fire, Water, Grass, Flying, Fighting, Poison, Electric, Ground,
        Rock, Psychic, Ice, Bug, Ghost, Steel, Dragon, Dark, Fairy
    }
}
