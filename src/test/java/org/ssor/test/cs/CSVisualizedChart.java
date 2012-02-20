package org.ssor.test.cs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class CSVisualizedChart extends ApplicationFrame {

	static List<Record> recoder = new LinkedList<Record>();

	// Corresponding to column number
	public static int numberOfRound = 0;

	static int currentRound = 0;
	// Number of lines
	static final int division = 3;

	static int numberOfCompletedNode = 0;

	public static boolean executable = true;

	public static final Object mutualLock = new Byte[0];
	
	private static final String title = "Composite service's performance comparison";

	/**
	 * Creates a new demo.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public CSVisualizedChart(final String title) {
		super(title);
		final CategoryDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(900, 570));
		setContentPane(chartPanel);
	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return The dataset.
	 */
	private CategoryDataset createDataset() {

		// create the dataset...
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		//System.out.print("number: " + recoder.size() + "\n");
		for (Record record : recoder)
			dataset.addValue(record.getTimeUsage(), record.getName(), record
					.getNumberOfThread());
		/*
		 * // row keys... final String series1 = "Node #1"; final String series2 =
		 * "Node #2"; final String series3 = "Node #3"; // column keys... final
		 * String type1 = "20"; final String type2 = "50"; final String type3 =
		 * "100"; final String type4 = "150";
		 *  // create the dataset... final DefaultCategoryDataset dataset = new
		 * DefaultCategoryDataset();
		 * 
		 * dataset.addValue(914.0, series1, type1); dataset.addValue(1371.0,
		 * series1, type2); dataset.addValue(3.0, series1, type3);
		 * dataset.addValue(5.0, series1, type4);
		 * 
		 * dataset.addValue(1171.8, series2, type1); dataset.addValue(1682.6,
		 * series2, type2); dataset.addValue(6.0, series2, type3);
		 * dataset.addValue(8.0, series2, type4);
		 * 
		 * dataset.addValue(1109.8, series3, type1); dataset.addValue(1530.0,
		 * series3, type2); dataset.addValue(2.0, series3, type3);
		 * dataset.addValue(3.0, series3, type4);
		 */
		return dataset;

	}

	/**
	 * Creates a sample chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return The chart.
	 */
	private JFreeChart createChart(final CategoryDataset dataset) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createLineChart(
				title, // chart title
				"Non-redundant complexity", // domain
																	// axis
																	// label
				"Time taken (ms)", // range
																		// axis
																		// label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);
		chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 3));
	    Shape[] arrayOfShape = new Shape[3];
	    int[] arrayOfInt1 = { -3, 3, -3 };
	    int[] arrayOfInt2 = { -3, 0, 3 };
	    arrayOfShape[0] = new Polygon(arrayOfInt1, arrayOfInt2, 3);
	    arrayOfShape[1] = new Rectangle2D.Double(-2.0D, -3.0D, 3.0D, 8.0D);
	    arrayOfInt1 = new int[] { -3, 3, 3 };
	    arrayOfInt2 = new int[] { 0, -3, 3 };
	    arrayOfShape[2] = new Polygon(arrayOfInt1, arrayOfInt2, 3);
	   // arrayOfShape[3] = new Rectangle2D.Double(-6.0D, -6.0D, 6.0D, 6.0D);
	    DefaultDrawingSupplier localDefaultDrawingSupplier = new DefaultDrawingSupplier(DefaultDrawingSupplier.DEFAULT_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, arrayOfShape);
	  

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		// final StandardLegend legend = (StandardLegend) chart.getLegend();
		// legend.setDisplaySeriesShapes(true);
		// legend.setShapeScaleX(1.5);
		// legend.setShapeScaleY(1.5);
		// legend.setDisplaySeriesLines(true);

		chart.setBackgroundPaint(Color.white);

		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinePaint(Color.black);
	    LineAndShapeRenderer localLineAndShapeRenderer = (LineAndShapeRenderer)plot.getRenderer();
	    localLineAndShapeRenderer.setBaseShapesVisible(true);
	    //localLineAndShapeRenderer.setBaseItemLabelsVisible(true);
	    localLineAndShapeRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		// customise the range axis...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);

		// ****************************************************************************
		// * JFREECHART DEVELOPER GUIDE *
		// * The JFreeChart Developer Guide, written by David Gilbert, is
		// available *
		// * to purchase from Object Refinery Limited: *
		// * *
		// * http://www.object-refinery.com/jfreechart/guide.html *
		// * *
		// * Sales are used to provide funding for the JFreeChart project -
		// please *
		// * support us so that we can continue developing free software. *
		// ****************************************************************************

		// customise the renderer...
		final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
				.getRenderer();
		// renderer.setDrawShapes(true);
		renderer.setSeriesPaint(0, Color.red);
		renderer.setSeriesPaint(1, Color.blue);
		//renderer.setSeriesPaint(2, Color.orange);
		renderer.setSeriesPaint(2, Color.magenta);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f,
				new float[] { 1.0f, 1.0f }, 10.0f));
		renderer.setSeriesStroke(1, new BasicStroke(2.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f,
				new float[] { 1.0f, 1.0f }, 10.0f));
		renderer.setSeriesStroke(2, new BasicStroke(2.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f,
				new float[] { 1.0f, 1.0f }, 10.0f));

		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;
	}

	private static class Record {
		private String name;
		private double timeUsage;
		private String numberOfThread;

		public Record(String name, double timeUsage, String numberOfThread) {
			super();
			this.name = name;
			this.timeUsage = timeUsage;
			this.numberOfThread = numberOfThread;
		}

		public String getName() {
			return name;
		}

		public double getTimeUsage() {
			return timeUsage;
		}

		public String getNumberOfThread() {
			return numberOfThread;
		}
	}

	/**
	 * Starting point for the demonstration application.
	 * 672  890
	 * @param args
	 *            ignored.
	 */
	public static void main(final String[] args) {
		
		recoder.add(new Record("composite service", 7470.0, "0"));
		recoder.add(new Record("composite service", 7788.0, "203"));
		recoder.add(new Record("composite service", 10321.0, "485"));
		recoder.add(new Record("composite service", 12888.0, "672"));
		recoder.add(new Record("composite service", 13206.0, "890"));
	
		recoder.add(new Record("traditional UB", 4250.0, "0"));
		recoder.add(new Record("traditional UB", 7546.0, "203"));
		recoder.add(new Record("traditional UB", 12262.0, "485"));
		recoder.add(new Record("traditional UB", 15529.0, "672"));
		recoder.add(new Record("traditional UB", 18771.0, "890"));

		final CSVisualizedChart demo = new CSVisualizedChart(title);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

	public synchronized static boolean record(String name, double timeUsage,
			String numberOfThread) {

		recoder.add(new Record(name, timeUsage, String.valueOf(Integer.parseInt(numberOfThread) * division)));
		numberOfCompletedNode++;
		if (numberOfCompletedNode % division == 0) {
			currentRound++;
			if (currentRound == numberOfRound) {
				main(null);
				return false;
			} else {
				executable = true;
				synchronized (mutualLock) {
					mutualLock.notifyAll();
				}

				return true;
			}
		}

		return false;
	}

}
