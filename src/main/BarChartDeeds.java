package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler.ChartTheme;

/**
 * BarChartDeedsVsDeedsPrivateDataUniversities
 */
public class BarChartDeeds implements ExampleChart<CategoryChart> {
	String[] selectedUniversities;
	// to do small names for fit the chart
	String[] selectedUniversitiesSmallNames;
	Number[] selectedUniversitiesTotalDeeds;
	Number[] selectedUniversitiesDeedsWithPrivateData;

	public BarChartDeeds(List<String> selectedUniversities, List<Integer> selectedUniversitiesTotalDeeds,
			List<Integer> selectedUniversitiesDeedsWithPrivateData) {
		//this.selectedUniversities = (String[]) selectedUniversities.toArray();
		this.selectedUniversities = selectedUniversities.toArray(new String[0]);
		this.selectedUniversitiesTotalDeeds = (Integer[]) selectedUniversitiesTotalDeeds.toArray(new Integer[selectedUniversitiesTotalDeeds.size()]);
		this.selectedUniversitiesDeedsWithPrivateData = (Integer[]) selectedUniversitiesDeedsWithPrivateData.toArray(new Integer[selectedUniversitiesDeedsWithPrivateData.size()]);
	}

	@Override
	public CategoryChart getChart() {

		// Create Chart
		CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title(getClass().getSimpleName())
				.xAxisTitle("Ιδρύματα").yAxisTitle("Πράξεις").theme(ChartTheme.GGPlot2).build();

		// Customize Chart
		chart.getStyler().setPlotGridVerticalLinesVisible(false);

		// Series
		//chart.addSeries("total deeds", new ArrayList<>(Arrays.asList(new String[] { "ΠαΜακ", "Ιωαννίνων" })),
		chart.addSeries("total deeds", new ArrayList<>(Arrays.asList(this.selectedUniversities)),
				new ArrayList<Number>(Arrays.asList(this.selectedUniversitiesTotalDeeds)));
		chart.addSeries("deeds with private data",
				new ArrayList<>(Arrays.asList(this.selectedUniversities)),
				new ArrayList<Number>(Arrays.asList(this.selectedUniversitiesDeedsWithPrivateData)));

		return chart;
	}

	@Override
	public String getExampleChartName() {

		return (getClass().getSimpleName() + " - GGPlot2 Theme");
	}
}
