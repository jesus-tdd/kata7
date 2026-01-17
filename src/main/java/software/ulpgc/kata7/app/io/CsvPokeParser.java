package software.ulpgc.kata7.app.io;

import software.ulpgc.kata7.architecture.model.Pokemon;
import software.ulpgc.kata7.architecture.model.Pokemon.Type;

import java.util.ArrayList;
import java.util.List;

public class CsvPokeParser {
    public static Pokemon parse(String s) {
        return parse(s.split(","));
    }

    private static Pokemon parse(String[] split) {
        return new Pokemon(removeMarks(split[1]), toTypes(List.of(split[3], split[4])), toInt(split[12]));
    }

    private static int toInt(String s) {
        return Integer.parseInt(s);
    }

    private static List<Type> toTypes(List<String> strings) {
        List<Type> list = new ArrayList<>();
        for (String s : strings) {
            if (s.equals("\" \"")) continue;
            list.add(toType(s));
        }
        return list;
    }

    private static Type toType(String s) {
        return Type.valueOf(removeMarks(s));
    }

    private static String removeMarks(String s) {
        return s.replaceAll("\"", "");
    }
}
