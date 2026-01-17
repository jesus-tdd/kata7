package software.ulpgc.kata7.architecture.viewmodel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Histogram<T> implements Iterable<T> {
    private final Map<T, Integer> map;
    private final Map<String, String> labels;

    public Histogram(Map<String, String> labels) {
        this.labels = labels;
        map = new HashMap<>();
    }

    @Override
    public Iterator<T> iterator() {
        return map.keySet().iterator();
    }

    public void put(T bin) {
        map.put(bin, count(bin) + 1);
    }

    public Integer count(T bin) {
        return map.getOrDefault(bin, 0);
    }

    public int size() {
        return map.size();
    }

    public String title() {
        return labels.get("title");
    }

    public String x() {
        return labels.get("x");
    }

    public String y() {
        return labels.get("y");
    }

    public String legend() {
        return labels.get("legend");
    }
}
