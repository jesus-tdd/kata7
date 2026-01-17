package software.ulpgc.kata7.app.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import software.ulpgc.kata7.architecture.viewmodel.Histogram;

import javax.swing.*;
import java.awt.*;

import static org.jfree.chart.axis.CategoryLabelPositions.UP_90;

public class MainFrame extends JFrame {

    public static MainFrame create() {
        return new MainFrame();
    }

    private MainFrame() throws HeadlessException {
        this.setTitle("Pokemons per type");
        this.setSize(900, 800);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public <T> MainFrame display(Histogram<T> histogram) {
        this.getContentPane().add(chartPanelWith(histogram));
        return this;
    }

    private <T> ChartPanel chartPanelWith(Histogram<T> histogram) {
        return new ChartPanel(chartOf(histogram));
    }

    private <T> JFreeChart chartOf(Histogram<T> histogram) {
        JFreeChart chart = ChartFactory.createBarChart(
                histogram.title(),
                histogram.x(),
                histogram.y(),
                datasetOf(histogram)
        );
        chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(UP_90);
        return chart;
    }

    private <T> DefaultCategoryDataset datasetOf(Histogram<T> histogram) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        histogram.forEach(b -> dataset.addValue(histogram.count(b), histogram.legend(), b.toString()));
        return dataset;
    }
}
