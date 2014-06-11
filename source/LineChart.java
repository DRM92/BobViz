import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import java.io.File;
import javax.swing.JOptionPane;

/**
 * @author 	D. Sherratt
 * @file 	LineChart.java
 * @date	29.03.2013
 * @version     1.0
 * @see         Wicked Cool Java  By: Brian D. Eubanks, chapter 6 -creating jfreecharts
 * 
 * \brief LineChart class that creates a Line Chart
 */
public class LineChart extends Visualization {
	
	/**
	*	Constructor used requiring a dataset
	*
	*	@param the dataset that the line chart will represent
	*/
	public LineChart(DataSet inputdataset) {
		super.setDataset(inputdataset);
	}
	
	/**
	*	Method that creates the line chart
	*
	*	@return the chart for display
	*/
	public JFreeChart MakeChart() {
		
		DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
		
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
            nextValue1 = (Comparable<Object>) column1Data[i];
            
			Double nextValue2 = null;
			
			boolean addThis = true;
			
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
				lineDataset.addValue( nextValue2, column2Header, nextValue1 );
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
			lineDataset = new DefaultCategoryDataset(); //Reset
			JOptionPane.showMessageDialog(null, 
				"Your selected y-axis has data in the wrong format" + "\n" +
				"The following data needs to be a number in order to be" + 
				" represented."
					+ errors);
		}
		
		 JFreeChart chart = ChartFactory.createLineChart( 
                        super.getHeader(),
						super.getxAxis(),
						super.getyAxis(),
						lineDataset,
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
	* Unit test used to test LineChart class.
	*/
	public static void main(String args[]){
		DataSet testdataset = new DataSet();
		File inputfile = new File("test.csv");
		testdataset.BuildDataSet(inputfile);
		
		System.out.println("LineChart:: LineChart()");
		LineChart testLineChart=new LineChart(testdataset);
		System.out.println("LineChart::LineChart()- Test Passed");
		
		System.out.println("LineChart:: MakeChart()");
		testLineChart.MakeChart();
		System.out.println("LineChart::MakeChart()- Test Passed");
	}
}