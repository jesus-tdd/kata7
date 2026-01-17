package software.ulpgc.kata7.app.pojos;

import java.util.Map;

public record HistogramPojo<T>(String title, String x, String y, String legend, Map<T, Integer> data) {
}
