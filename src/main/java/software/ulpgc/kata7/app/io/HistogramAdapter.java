package software.ulpgc.kata7.app.io;

import software.ulpgc.kata7.app.pojos.HistogramPojo;
import software.ulpgc.kata7.architecture.viewmodel.Histogram;

import java.util.HashMap;
import java.util.Map;

public class HistogramAdapter {
    public static <T> HistogramPojo<T> toPojo(Histogram<T> histogram) {
        Map<T, Integer> data = new HashMap<>();
        histogram.forEach(b -> data.put(b, histogram.count(b)));

        return new HistogramPojo<>(
                histogram.title(),
                histogram.x(),
                histogram.y(),
                histogram.legend(),
                data
        );
    }
}
