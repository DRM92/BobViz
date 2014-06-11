import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYZDataset;
import java.io.File;
import javax.swing.JOptionPane;

/**
 * @author 	D. Sherratt
 * @file 	BubbleChart.java
 * @date	30.03.2013
 * @version     1.0
 * @see         Wicked Cool Java  By: Brian D. Eubanks, chapter 6 -creating 
 *				jfreecharts
 * 
 * \brief AreaChart class that creates a Area Chart
 */
public class BubbleChart extends Visualization {
	/**
	*	Constructor that reads in a DataSet to initialize
	*
	* @param DataSet with information to be visualized
	*/
	public BubbleChart(DataSet inputdataset) {
		super.setDataset(inputdataset);
	}
	
	/**
	* Method that allows you to set the third attribute of the visualization
	*
	* @param attribute3-the index of the attribute
	*
	* @return true if third attribute has been set
	*/
    public boolean setAttribute3(int attribute3) {
        m_attribute3 = attribute3;
        return true;
    }
	
	/**
	* Method that gets the third attribute of the visualization
	*
	* @return the third attribute
	*/
    public int getAttribute3() {
        return m_attribute3;
    }
	
	/**
	* Method that allows you to set the fourth attribute of the visualization
	*
	* @param attribute4-the index of the attribute
	*
	* @return true if fourth attribute has been set
	*/
    public boolean setAttribute4(int attribute4) {
        m_attribute4 = attribute4;
        return true;
    }
	
	/**
	* Method that gets the fourth attribute of the visualization
	*
	* @return the fourth attribute
	*/
    public int getAttribute4() {
        return m_attribute4;
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
		Object[] column3Data = super.getDataset().GetColumnData(
			getAttribute3());
        Object[] column4Data = super.getDataset().GetColumnData(
			getAttribute4());
		
		boolean addDataset = true;
		String errors = "";
		int errorCounter = 0;
		
		DefaultXYZDataset dataset = new DefaultXYZDataset();
		
        for (int i=0; i<super.getDataset().GetNoOfEntrys();i++) {	
		
			Double nextValue2 = null;
			Double nextValue3 = null;
			Double nextValue4 = null;
            boolean addThis = true;
			
			Comparable<Object> nextValue1;
           
            nextValue1 = (Comparable<Object>) column1Data[i];
			
			try {
				int intNextValue2 = Integer.parseInt(column2Data[i].toString());
				nextValue2 = (Double) ((double) intNextValue2);
			} catch (NumberFormatException nfe21) {
				try {
					double doubleNextValue2 = Double.parseDouble(
							column2Data[i].toString());
					nextValue2 = (Double) doubleNextValue2;
				} catch (NumberFormatException nfe22) {
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
			
			try {
				int intNextValue3 = Integer.parseInt(column3Data[i].toString());
				nextValue3 = (Double) ((double) intNextValue3);
			} catch (NumberFormatException nfe31) {
				try {
					double doubleNextValue3 = Double.parseDouble(
							column3Data[i].toString());
					nextValue3 = (Double) doubleNextValue3;
				} catch (NumberFormatException nfe32) {
					String strNextValue3 = column3Data[i].toString();
					if (strNextValue3.equalsIgnoreCase("True")) {
						nextValue3 = TRUE;
					} else if (strNextValue3.equalsIgnoreCase("False")) {
						nextValue3 = FALSE;
					} else {
						addThis = false;
					}
				}
			} catch (Exception e) {
				addThis = false;
			}
			
			try {
				int intNextValue4 = Integer.parseInt(column4Data[i].toString());
				nextValue4 = (Double) ((double) intNextValue4);
			} catch (NumberFormatException nfe41) {
				try {
					double doubleNextValue4 = Double.parseDouble(
							column4Data[i].toString());
					nextValue4 = (Double) doubleNextValue4;
				} catch (NumberFormatException nfe42) {
					String strNextValue4 = column4Data[i].toString();
					if (strNextValue4.equalsIgnoreCase("True")) {
						nextValue4 = TRUE;
					} else if (strNextValue4.equalsIgnoreCase("False")) {
						nextValue4 = FALSE;
					} else {
						addThis = false;
					}
				}
			} catch (Exception e) {
				addThis = false;
			}
			
			if (addThis == true) {	
				double x = (double) nextValue2;
				double y = (double) nextValue3;
				double z = (double) nextValue4;
				double[][] data = new double[ARRAYWIDTH][ARRAYLENGTH];
				data[0][0] = x;
				data[1][0] = y;
				data[2][0] = z;
				dataset.addSeries( nextValue1, data );
			} else {
				addDataset = false;
				if (errorCounter < MAX_ERROR_LENGTH) {
					errors = errors + "\n"
						+ column2Data[i].toString()
						+ ", " + column3Data[i].toString()
						+ ", " + column4Data[i].toString();
						errorCounter++;
				}
			}
        }
        
		if (addDataset == false) {
			dataset = new DefaultXYZDataset(); //Reset
			JOptionPane.showMessageDialog(null, 
				"Your selected x-axis, y-axis and value has data in the " + 
				"wrong format" + "\n" +
				"The following data needs to be a number in order to be" + 
				" represented."
					+ errors);
		}
		
        JFreeChart chart = ChartFactory.createBubbleChart( 
                        super.getHeader(),
                        super.getxAxis(),
                        super.getyAxis(),
						dataset,
                        PlotOrientation.VERTICAL, true, false, false);
        return chart;
    }
	
	private int m_attribute3;
	private int m_attribute4;
	private final int ARRAYWIDTH = 3;
	private final int ARRAYLENGTH = 1;
	private final Double TRUE = 1.0;
	private final Double FALSE = 0.0;
	private final int MAX_ERROR_LENGTH = 5;
	
	/**
	* Unit test used to test BubbleChart class.
	*/
	public static void main(String args[]){
		DataSet testdataset = new DataSet();
		File inputfile = new File("test.csv");
		testdataset.BuildDataSet(inputfile);
		
		System.out.println("BubbleChart:: BubbleChart()");
		BubbleChart testChart = new BubbleChart(testdataset);
		System.out.println("BubbleChart::BubbleChart()- Test Passed");
		
		System.out.println("BubbleChart:: MakeChart()");
		testChart.MakeChart();
		System.out.println("BubbleChart::MakeChart()- Test Passed");
	}
} /* end BubbleChart class */