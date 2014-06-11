import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import java.io.File;
import javax.swing.JOptionPane;

/**
 * @author 	K. Zheng
 * @file 	AreaChart.java
 * @date	30.03.2013
 * @version     1.0
 * @see         Wicked Cool Java  By: Brian D. Eubanks, chapter 6 -creating jfreecharts
 * 
 * \brief AreaChart class that creates a Area Chart
 */
public class AreaChart extends Visualization {	

	/**
	*	Constructor that reads in a DataSet to initialize
	*
	* @param DataSet with information to be visualized
	*/
	public AreaChart(DataSet inputdataset) {
		super.setDataset(inputdataset);
	}
	
	/**
    * Method that creates the area chart
	*
    * @return the chart for display
    */
	public JFreeChart MakeChart() {
		
		DefaultCategoryDataset areaDataset = new DefaultCategoryDataset();
		
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
            
			Comparable<Object> nextValue1;
			//First value can have be any comparable object.
            
            nextValue1 = (Comparable<Object>) column1Data[i];    
			Double nextValue2 = null;
			boolean addThis = true;
			//Second value can only be integer, double or boolean.
			
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
				areaDataset.addValue( nextValue2, column2Header, nextValue1 );
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
			areaDataset = new DefaultCategoryDataset(); //Reset
			JOptionPane.showMessageDialog(null, 
				"Your selected y-axis has data in the wrong format" + "\n" +
				"The following data needs to be a number in order to be" + 
				" represented."
					+ errors);
		}
		
		JFreeChart chart = ChartFactory.createAreaChart( 
                        super.getHeader(),
						super.getxAxis(),
						super.getyAxis(),
						areaDataset,
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
	* Unit test used to test AreaChart class.
	*/
		public static void main(String args[]){
		DataSet testdataset = new DataSet();
		File inputfile = new File("test.csv");
		testdataset.BuildDataSet(inputfile);
		
		System.out.println("AreaChart:: AreaChart()");
		AreaChart testAreaChart = new AreaChart(testdataset);
		System.out.println("AreaChart::BarChart()- Test Passed");
		
		System.out.println("AreaChart:: MakeChart()");
		testAreaChart.MakeChart();
		System.out.println("AreaChart::MakeChart()- Test Passed");
	}
	
}

/*end AreaChart class */