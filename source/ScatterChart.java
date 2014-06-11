import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import java.io.File;
import javax.swing.JOptionPane;

/**
 * @author 	D. Sherratt, X. Zhao, K. Zheng
 * @file 	ScatterChart.java
 * @date	01.04.2013
 * @version     1.0
 * @see         Wicked Cool Java  By: Brian D. Eubanks, chapter 6 -creating jfreecharts
 * 
 * \brief ScatterChart class that creates a Scatter Chart
 */
 public class ScatterChart extends Visualization {
	
	/**
	* Constructor that reads in a DataSet to initialize
	*
	* @param DataSet with information to be visualized
	*/
	public ScatterChart(DataSet inputdataset) {
		super.setDataset(inputdataset);
	}
	
	/**
    * Method that creates the scatter chart
	*
    * @return the chart for display
    */
	public JFreeChart MakeChart() {
		
		XYSeries scatterDataset = new XYSeries(super.getDataset().
				GetAttributeName(super.getAttribute2()));
		
		Object[] column1Data = super.getDataset().GetColumnData(
			super.getAttribute1());
		Object[] column2Data = super.getDataset().GetColumnData(
			super.getAttribute2());
		
		String column2Header = super.getDataset().GetAttributeName(
			super.getAttribute2());
		
		boolean addDataset = true;
		String errors = "";
		int errorCounter = 0;
		
		for (int i=0; i<super.getDataset().GetNoOfEntrys();i++) {	
           
            Double nextValue1 = null;
			Double nextValue2 = null;
			
			boolean addThis = true;
			
			try {
				int intNextValue1 = Integer.parseInt(column1Data[i].toString());
				nextValue1 = (Double) ((double) intNextValue1);
			} catch (NumberFormatException nfe) {
				try {
					double doubleNextValue1 = Double.parseDouble(
							column1Data[i].toString());
					nextValue1 = (Double) doubleNextValue1;
				} catch (NumberFormatException nfe2) {
					String strNextValue2 = column2Data[i].toString();
					if (strNextValue2.equalsIgnoreCase("True")) {
						nextValue1 = TRUE;
					} else if (strNextValue2.equalsIgnoreCase("False")) {
						nextValue1 = FALSE;
					} else {
						addThis = false;
					}
				}
			} catch (Exception e) {
				addThis = false;
			}
			
			try {
				int intNextValue2 = Integer.parseInt(column2Data[i].toString());
				nextValue2 = (Double) ((double) intNextValue2);
			} catch (NumberFormatException nfe) {
				try {
					double doubleNextValue2 = Double.parseDouble(
							column2Data[i].toString());
					nextValue2 = (Double) doubleNextValue2;
				} catch (NumberFormatException nfe2) {
					String strNextValue2 = column2Data[i].toString();
					if (strNextValue2.equalsIgnoreCase("True")) {
						nextValue2 = TRUE;
					} else if (strNextValue2.equalsIgnoreCase("False")) {
						nextValue2 = FALSE;
					} else {
						addThis = false;
					}
				}
			} catch (Exception e) {
				addThis = false;
			}
			
			if (addThis == true) {
				scatterDataset.add( nextValue1, nextValue2 );
			} else {
				addDataset = false;
				if (errorCounter < MAX_ERROR_LENGTH) {
					errors = errors + "\n"
						+ column1Data[i].toString()
						+ ", " + column2Data[i].toString();
						errorCounter++;
					}
			}
        }
		
		if (addDataset == false) {
			scatterDataset = new XYSeries(super.getDataset().
				GetAttributeName(super.getAttribute2()));
			JOptionPane.showMessageDialog(null, 
				"Your selected x-axis, y-axis and value has data in the " + 
				"wrong format" + "\n" +
				"The following data needs to be a number in order to be" + 
				" represented."
					+ errors);
		}
		
		XYSeriesCollection chartDataset = new XYSeriesCollection();
		chartDataset.addSeries(scatterDataset);
		
		 JFreeChart chart = ChartFactory.createScatterPlot( 
                        super.getHeader(),
						super.getxAxis(),
						super.getyAxis(),
						chartDataset,
						PlotOrientation.VERTICAL,
                        true, //include legend
                        true,
                        false );
        return chart;
	}
	
	private final Double TRUE = 1.0;
	private final Double FALSE = 0.0;
	private final int MAX_ERROR_LENGTH = 5;
	
	/**
	* Unit test used to test ScatterChart class.
	*/
	public static void main(String args[]){
		DataSet testdataset = new DataSet();
		File inputfile = new File("test.csv");
		testdataset.BuildDataSet(inputfile);
		
		System.out.println("ScatterChart:: ScatterChart()");
		ScatterChart testChart = new ScatterChart(testdataset);
		System.out.println("ScatterChart:: ScatterChart()- Test Passed");
		
		System.out.println("ScatterChart:: MakeChart()");
		testChart.MakeChart();
		System.out.println("ScatterChart:: MakeChart()- Test Passed");
	}
 }