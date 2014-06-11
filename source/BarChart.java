import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.io.File;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JOptionPane;

/**
 * @author 	Lewis Radcliffe, Laurence Tsang & Joshua Reynolds
 * @file 	BarChart.java
 * @date	28.02.13
 * @version     2.1
 * @see         http://www.jfree.org/jfreechart/api/javadoc/index.html , Wicked 
 *				Cool Java  By: Brian D. Eubanks, chapter 6 -creating jfreecharts
 * 
 * \brief BarChart class creates the Bar Chart.
 */

public class BarChart extends Visualization {
	/**
	*	Constructor that reads in a DataSet to initialize
	*
	* @param DataSet with information to be visualized
	*/
	public BarChart(DataSet inputdataset) {
		super.setDataset(inputdataset);
	}
	
    /**
    * Method that creates the bar chart
	*
    * @return the chart for display
    */
    public JFreeChart MakeChart() {	
		
		Object[] column1Data = super.getDataset().GetColumnData(
			super.getAttribute1());
		Object[] column2Data = super.getDataset().GetColumnData(
			super.getAttribute2());
        
		boolean addDataset = true;
		String errors = "";
		int errorCounter = 0;
		
		DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        for (int i=0; i<super.getDataset().GetNoOfEntrys();i++) {	
		
			Double nextValue2 = null;
            boolean addThis = true;
			
			Comparable<Object> nextValue1;
           
            nextValue1 = (Comparable<Object>) column1Data[i];
			
			try {
				int intNextValue2 = Integer.parseInt(column2Data[i].toString());
				nextValue2 = (Double) ((double) intNextValue2);
			} catch (NumberFormatException nfe) {
				try {
					double doubleNextValue2 = Double.parseDouble(column2Data[i]
						.toString());
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
				barDataset.addValue(nextValue2, nextValue1, nextValue1);
			} else {
				addDataset = false;
				if (errorCounter < MAX_ERROR_LENGTH) {
					errors = errors + "\n"
						+ column2Data[i].toString();
						errorCounter++;
				}
			}
        }
        
		if (addDataset == false) {
			barDataset = new DefaultCategoryDataset(); //Reset
			JOptionPane.showMessageDialog(null, 
				"Your selected y-axis has data in the wrong format" + "\n" +
				"The following data needs to be a number in order to be" + 
				" represented."
					+ errors);
		}
		
        JFreeChart chart = ChartFactory.createBarChart( 
                        super.getHeader(),
                        super.getxAxis(),
                        super.getyAxis(),
						barDataset,
                        PlotOrientation.VERTICAL, true, false, false);
        return chart;
    }
	
	private final Double TRUE = 1.0;
	private final Double FALSE = 0.0;
	private final int MAX_ERROR_LENGTH = 5;

	/**
	* Unit test used to test BarChart class.
	*/
	public static void main(String args[]){
		DataSet testdataset = new DataSet();
		File inputfile = new File("test.csv");
		testdataset.BuildDataSet(inputfile);
		
		System.out.println("BarChart:: BarChart()");
		BarChart testBarChart=new BarChart(testdataset);
		System.out.println("BarChart::BarChart()- Test Passed");
		
		System.out.println("BarChart:: MakeChart()");
		testBarChart.MakeChart();
		System.out.println("BarChart::MakeChart()- Test Passed");
	}

} /* end BarChart class */
