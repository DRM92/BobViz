import java.io.File;
/**
 * @file    Visualization.java
 * @author  Joshua Reynolds
 * @date    20 Feb 2013
 * @see     
 *
 *  \brief Visualization that stores selected attirbutes and data
 */

public class Visualization{
	
	/*
	* Method that gets the first attribute of the visualization
	*
	* @return the first attribute
	*/
    public int getAttribute1() {
        return m_attribute1;
    }
	
	/*
	* Method that gets the second attribute of the visualization
	*
	* @return the second attribute
	*/
    public int getAttribute2() {
        return m_attribute2;
    }
	
	/*
	* Method that gets the dataset of the visualization
	*
	* @return the dataset
	*/
    public DataSet getDataset() {
        return m_dataset;
    }
	
	/*
	* Method that gets the header of the visualization
	*
	* @return the header of the visualization
	*/
    public String getHeader() {
        return m_header;
    }
	
	/*
	* Method that gets the x axis of the visualization
	*
	* @eturn the x axis label
	*/
    public String getxAxis() {
        return m_xAxis;
    }
	
	/*
	* Method that gets the y axis of the visualization
	*
	* @return the y axis label
	*/
    public String getyAxis() {
        return m_yAxis;
    }
	
	/*
	* Method that allows you to set the first attribute of the visualization
	*
	* @param attribute1-the index of the attribute
	*
	* @return true if first attribute has been set
	*/
    public boolean setAttribute1 (int attribute1) {
        m_attribute1 = attribute1;
        return true;
    }
	
	/*
	* Method that allows you to set the second attribute of the visualization
	*
	* @param attribute2-the index of the attribute
	*
	* @return true if second attribute has been set
	*/
    public boolean setAttribute2 (int attribute2) {
        m_attribute2 = attribute2;
        return true;
    }
	
	/*
	* Method that allows you to set the dataset of the visualization
	*
	* @param dataset
	*
	* @return true if dataset has been set
	*/
	public boolean setDataset (DataSet dataset) {
        m_dataset = dataset;
        return true;
    }
	
	/*
	* Method that allows you to set the header of the visualization
	*
	* @param header-the header of the visualization
	*
	* @return true if the header has been set
	*/
    public boolean setHeader (String header) {
        m_header = header;
        return true;
    }
	
	/*
	* Method that allows you to set the x axis label of the visualization
	*
	* @param xAxis-the label for the x axis
	*
	* @return true if the x axis has been set
	*/
    public boolean setxAxis (String xAxis) {
        m_xAxis = xAxis;
        return true;
    }
	
	/*
	* Method that allows you to set the y axis label of the visualization
	*
	* @param yAxis-the label for the y axis
	*
	* @return true if the y axis has been set
	*/
    public boolean setyAxis (String yAxis) {
        m_yAxis = yAxis;
        return true;
    }
	
	private DataSet m_dataset;
    private int m_attribute1;
    private int m_attribute2;
    private String m_header;
    private String m_xAxis;
    private String m_yAxis;

	public static void main(String args[]){
		
		final int TEST_DATA = 100;
		
		//test create visualization class
		System.out.println("Visualization:: Visualization()");
		Visualization testVisualization=new Visualization();
		System.out.println("Visualization:: Visualization() - Test Passed");
		
		//test DataSet class
		DataSet testdataset = new DataSet();
		File inputfile = new File("test.csv");
		testdataset.BuildDataSet(inputfile);
		
		//test setDataset method
		System.out.println("Visualization:: setDataset()");
		testVisualization.setDataset(testdataset);
		System.out.println("Visualization:: setDataset() - Test Passed");
		
		//test setAttribute1 method
		System.out.println("Visualization:: setAttribute1()");
		boolean testSetAttribute1=testVisualization.setAttribute1(TEST_DATA);
		System.out.println("Visualization:: setAttribute1() - Test Passed");
		
		//test setAttribute2 method
		System.out.println("Visualization:: setAttribute2()");
		boolean testSetAttribute2=testVisualization.setAttribute2(TEST_DATA);
		System.out.println("Visualization:: setAttribute2() - Test Passed");
		
		//test setHeader method
		System.out.println("Visualization:: setHeader()");
		boolean testSetHeader=testVisualization.setHeader("Visulization");
		System.out.println("Visualization:: setHeader() - Test Passed");
		
		//test setxAxis method
		System.out.println("Visualization:: setxAxis()");
		boolean testSetxAxis=testVisualization.setxAxis("xAxis");
		System.out.println("Visualization:: setxAxis() - Test Passed");
		
		//test setyAxis method
		System.out.println("Visualization:: setyAxis()");
		boolean testSetyAxis=testVisualization.setyAxis("yAxis");
		System.out.println("Visualization:: setyAxis() - Test Passed");
		
		//test getDataset method
		System.out.println("Visualization:: getDataset()");
		testVisualization.getDataset();
		System.out.println("Visualization:: getDataset() - Test Passed");
		
		//test getAttribute1 method
		System.out.println("Visualization:: getAttribute1()");
		int testGetAttribute1=testVisualization.getAttribute1();
		System.out.println("Visualization:: getAttribute1() - Test Passed");
		
		//test getAttribute2 method
		System.out.println("Visualization:: getAttribute2()");
		int testGetAttribute2=testVisualization.getAttribute2();
		System.out.println("Visualization:: getAttribute2() - Test Passed");
		
		//test getHeader method
		System.out.println("Visualization:: getHeader()");
		String testGetHeader=testVisualization.getHeader();
		System.out.println("Visualization:: getHeader() - Test Passed");
		
		//test getxAxis method
		System.out.println("Visualization:: getxAxis()");
		String testGetxAxis=testVisualization.getxAxis();
		System.out.println("Visualization:: getxAxis() - Test Passed");
		
		//test getyAxis method
		System.out.println("Visualization:: getyAxis()");
		String testGetyAxis=testVisualization.getyAxis();
		System.out.println("Visualization:: getyAxis() - Test Passed");
	}
	
} /* End of Visualization Class */