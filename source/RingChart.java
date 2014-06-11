import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import java.io.File;
import javax.swing.JOptionPane;

/**
 * @author 	X. Zhao (Anna)
 * @file 	RingChart.java
 * @date	29.03.2013
 * @version     1.0
 * @see         Wicked Cool Java  By: Brian D. Eubanks, chapter 6 -creating jfreecharts
 * 
 * \brief RingChart class that creates a ring Chart
 */
 
public class RingChart extends Visualization {
	
	/**
	*	Constructor requiring a dataset
	*
	*	@param the dataset that the ring chart will represent
	*/
	public RingChart(DataSet inputdataset) {
		super.setDataset(inputdataset);
	}
	
	/**
    * Method that creates the Ring Chart
    *
    * @return the chart for display
    */
    public JFreeChart MakeChart() {	
		DefaultPieDataset  chartDataSet = new DefaultPieDataset();
		
		Object[] column1Data = 
		         super.getDataset().GetColumnData(super.getAttribute1());
		Object[] column2Data =
            	 super.getDataset().GetColumnData(super.getAttribute2());
		
		boolean addDataset = true;
		String errors = "";
		int errorCounter = 0;
		
		 for (int i=0; i<super.getDataset().GetNoOfEntrys();i++) {	
            
			Comparable<Object> nextValue1;
           
            nextValue1 = (Comparable<Object>) column1Data[i];
            
			Double nextValue2 = null;
			
			boolean addThis = true;
			
			try {
				int intNextValue2 = 
				           Integer.parseInt(column2Data[i].toString());
				nextValue2 = (Double) ((double) intNextValue2);
			} catch (NumberFormatException nfe) {
				try {
					double doubleNextValue2 = 
					       Double.parseDouble(column2Data[i].toString());
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
				chartDataSet.setValue( nextValue1, nextValue2 );
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
			chartDataSet = new DefaultPieDataset(); //Reset
			JOptionPane.showMessageDialog(null, 
				"Your selected y-axis has data in the wrong format" + "\n" +
				"The following data needs to be a number in order to be" + 
				" represented."
					+ errors);
		}
		
		JFreeChart chart = ChartFactory.createRingChart( 
                        super.getHeader(),
                        chartDataSet,
                        true, //include legend
                        true,
                        false );
        return chart;
	}
	
	private final Double TRUE = 1.0;
	private final Double FALSE = 0.0;
	private final int MAX_ERROR_LENGTH = 5;
	
	public static void main(String args[]){
		//Creating dataset to test RingChart class with.
		DataSet testdataset = new DataSet();
		File inputfile = new File("test.csv");
		testdataset.BuildDataSet(inputfile);
		
		//Entering the dataset into the RingChart class.
		System.out.println("RingChart:: RingChart()");
		RingChart testRingChart=new RingChart(testdataset);
		System.out.println("RingChart::RingChart()- Test Passed");
		
		//Creating the chart.
		System.out.println("RingChart:: MakeChart()");
		testRingChart.MakeChart();
		System.out.println("RingChart::MakeChart()- Test Passed");
	}
	
 }