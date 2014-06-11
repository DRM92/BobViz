import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @file 	 DataSet.java
 * @author 	 Gwion Rhys Davies, D. Sherratt
 * @date	 28.02.13
 * @version      1.4.9
 * @See          http://www.jfree.org/jfreechart/api/javadoc/index.html
 * 
 * \brief DataSet class to check whether a valid file has been imported.
 * 
 * The class will validate the received file and then store 
 * the attribute data in one single dimensional array with the data
 * values in a 2 dimensional array.
 */

public class DataSet {

	/**
    * @param m_columnNo the desired column
    * @return a String of the 'nth' attribute of the attribute array 'nth'
    * term being the given argument integer which must be a value between
    * 0 and the number of attributes in the data set.
    */
    public String GetAttributeName(int columnNo){
        
       if(!(columnNo<m_attributeCount)){
            JPanel frame = new JPanel();
			JOptionPane.showMessageDialog(frame,
                "Attribute Index out of bounds",
                "File error",
                JOptionPane.ERROR_MESSAGE);
        } else {
            return m_attributes[columnNo];
        }
        return null;
    }
	
	/**
    * @return the array of attribute values.
    */
    public String[] GetAttributes() {
            
        return m_attributes;
    }
	
	/**
    * @param m_columnNo the desired column
    * @return an array containing a column data values, the column of 
    * values returned is chosen by the given argument integer which must
    * be a value between 0 and the number of attributes in the data set.
    */
    public Object[] GetColumnData(int columnNo){
        
        if(!(columnNo<m_attributeCount)){
			JPanel frame = new JPanel();
			JOptionPane.showMessageDialog(frame,
                "Column Index out of bounds",
                "File error",
                JOptionPane.ERROR_MESSAGE);
        } else {
            m_columnData = new Object[m_entryCount];
            for(int i = 0; i<m_entryCount; i++){
                m_columnData[i] = m_data[i][columnNo];
            }
            return m_columnData;
        }
        return null;
    }
	
	/**
    * @return the 2 dimensional array of data values.
    */
    public Object [][] GetData() {  
        return m_data;
    }
	
    /**
    * @return the number of attributes in the data set.
    */
    public int GetNoOfAttributes(){
        return m_attributeCount;
    }
	
	/**
    * @return the number of entries in the data set.
    */
    public int GetNoOfEntrys(){
        return m_entryCount;
    
    }
	
    /**
    * @param m_dataFile the imported .csv file.
    * @return TRUE on success if the .csv file is valid and the data set
    * has been successfully built.
    */
    public Boolean BuildDataSet (File dataFile) {
        
		boolean test = false;
		
        if(!checkForValidFile(dataFile)){
            return false;
        }
        
        try {
            m_fileScan = new Scanner(dataFile);
        } catch (FileNotFoundException e) {
        }
        
        m_attributes = new String[m_attributeCount];
        m_lineScanner = new Scanner(m_fileScan.nextLine());
        m_lineScanner.useDelimiter(m_delimiter);
        
        for(int i=0; i<m_attributeCount; i++){
            m_attributes[i]=m_lineScanner.next();
        }
        
        m_data = new Object[m_entryCount][m_attributeCount];
        
        for(int i=0; i<m_entryCount; i++){
            m_lineScanner = new Scanner(m_fileScan.nextLine());
            m_lineScanner.useDelimiter(m_delimiter);
            
            for(int n=0; n<m_attributeCount; n++){
                if(!(m_lineScanner.hasNext())){
                    i--;
                    break;
                }
                
                m_dataEntry=m_lineScanner.next();
                if(m_dataEntry.equals("")) {
                    n--;
                } else {
					m_data[i][n] = m_dataEntry;
                }	
            }
        }
        if (test) {
			System.out.println("Data Set Built");
		}
        return true;
    }
	
    /**
    * @param m_dataFile the imported .csv file.
    * @return TRUE on success.
    */
    private boolean checkForAttributes(File dataFile) {
		
        try {
            m_fileScan = new Scanner(dataFile);
        } catch (FileNotFoundException e) {
			return false;
        }
        m_delimiter=",";
        m_lineScanner= new Scanner(m_fileScan.nextLine());
        m_lineScanner.useDelimiter(m_delimiter);
        
        while(m_lineScanner.hasNext()){
            try {  
                Double.parseDouble(m_lineScanner.next());  
            } catch(NumberFormatException nfe) {  
                return true;  
            }
        }
        return false; 
    }
	
	/**
     * @param m_dataFile the imported .csv file.
     * @return TRUE on success.
     */
    private Boolean checkForCommas (File dataFile) {
        
        m_delimiter="";
        
        try {
            m_fileScan = new Scanner(dataFile);
        } catch (FileNotFoundException e) {
			return false;
        }
        m_fileScan.useDelimiter(m_delimiter);
        
        while(m_fileScan.hasNext()){
            String m_charCheck= m_fileScan.next();
            if(m_charCheck.equals(",")){
                    return true;
            }
        }
		return false;
    }
	
	/**
    * @param m_dataFile the imported .csv file.
	*
    * @return TRUE on success.
    */
    private boolean checkForConsistentData(File dataFile) {
		
        try {
            m_fileScan = new Scanner(dataFile);
        } catch (FileNotFoundException e) {
        }
        
        m_entryCount=0;
        m_attributeCount=countAttributes(dataFile);
        m_delimiter=",";
        
        while(m_fileScan.hasNextLine()){
            m_lineScanner = new Scanner(m_fileScan.nextLine());
            m_lineScanner.useDelimiter(m_delimiter);
            m_lineDataCount=0;
            m_emptyEntryCount=0;
            
            while(m_lineScanner.hasNext()){
                m_dataEntry=m_lineScanner.next();
                if(m_dataEntry.equals("")){
                    m_emptyEntryCount++;	
                } 
                m_lineDataCount++;	
            }
            
            if(!(m_lineDataCount-m_emptyEntryCount==m_attributeCount)) {
                if (!(m_lineDataCount-m_emptyEntryCount==0)){
                    return false;
                } else {
                    m_entryCount--;
                }
            }
            m_entryCount++;
        }
        return true;
    }
	
	/**
    * @param m_dataFile the imported .csv file.
    * @return TRUE on success if all validation tests return true.
    */
    private Boolean checkForValidFile(File dataFile){
        
        final JPanel frame = new JPanel();
        if(!checkForCommas(dataFile)){
            JOptionPane.showMessageDialog(frame,
                "File not compatible (contains no commas)",
                "File error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if(!checkForAttributes(dataFile)){
            JOptionPane.showMessageDialog(frame,
                "File not compatible (attribute error)",
                "File error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if(!checkForConsistentData(dataFile)){
            JOptionPane.showMessageDialog(frame,
                "File not compatible (Data not consistent)",
                "File error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
	
    /**
    * @param m_dataFile the imported .csv file.
    * @return TRUE on success.
    */
    private int countAttributes(File dataFile) {
        
        m_numberOfAttributes = 0;
        m_delimiter=",";
        
        try {
            m_fileScan = new Scanner(dataFile);
        } catch (FileNotFoundException e) {
        }
        m_lineScanner = new Scanner(m_fileScan.nextLine());
        m_lineScanner.useDelimiter(m_delimiter);
        while(m_lineScanner.hasNext()){
			m_lineScanner.next();
            m_numberOfAttributes++;
        }
        return m_numberOfAttributes;
    }
	   
    /** A scanner that scans the entire imported file */
    private Scanner m_fileScan;

    /** Delimiter separates strings */
    private String m_delimiter;

    /**A scanner that scans the lines in the .csv file */
    private Scanner m_lineScanner;

    /** Number of attributes in the .csv file */
    private int m_attributeCount;

    /** Number of entries of data in .csv file */
    private int m_entryCount;

    /**  Counter that counts the number of pieces of data in a single line. */
    private int m_lineDataCount;

    /** One dimensional string array containing all the data from the .csv file */
    private String[] m_attributes;

    /** Two dimensional object array containing all the data from the .csv file */
    private Object[][] m_data;

    /** Saves all the data of the string temporarily and compares with null */
    private String m_dataEntry;

    /** Counts the number of empty entries from a single line */
    private int m_emptyEntryCount;

    /** One dimensional object array containing the column data from the .csv file */
    private Object[] m_columnData;

    /** Counts the number of attributes from a single line */
    private int m_numberOfAttributes;
        
		 public static void main(String[] args) {
		//Test Class
		DataSet testDataSet = new DataSet();
		//Test One
		System.out.println("DataSet:: GetData()");
		Object[][] test1Object = testDataSet.GetData();
		System.out.println("DataSet:: GetData() - Test Passed");
		//Test Two
		System.out.println("DataSet:: GetAttirbutes()");
		String[] test2Attributes = testDataSet.GetAttributes();
		System.out.println("DataSet:: GetAttributes() - Test Passed");
        //Test Three
		System.out.println("DataSet:: GetColumnData(2) - No File");
		Object[] test3Object = testDataSet.GetColumnData(2);
		//Getting random information from a testDataSet
		//No file has been given, so no data should exist.
		//Expecting null.
		if (test3Object == null) {
			System.out.println("DataSet:: GetColumnData(2) - Test Passed");
		} else {
			System.out.println("DataSet:: GetColumnData(2) - Unexpected Error");
			System.out.println(test3Object);
		}
		//Test Four
		System.out.println("DataSet:: GetAttributeName(2)");
		String test4AttributeName = testDataSet.GetAttributeName(2);
		//Getting random information from a testDataSet
		//No File - Expecting null
		if (test4AttributeName == null) {
			System.out.println("DataSet:: GetAttributeName(2) - Test Passed");
		} else {
			System.out.println("DataSet:: GetAttributeName(2) - Test Failed");
			System.out.println(test4AttributeName);
		}
		//Test Five
		System.out.println("DataSet:: GetNoOfEntrys()");
		int test5entries = testDataSet.GetNoOfEntrys();
		System.out.println("DataSet:: GetNoOfEntrys() - Test Passed");
		//Test Six
		System.out.println("DataSet:: GetNoOfAttributes()");
		int test6entries = testDataSet.GetNoOfAttributes();
		System.out.println("DataSet:: GetNoOfAttributes() - Test Passed");
		
		System.out.println("DataSet:: BuildDataSet(Typical File)");
		File inputfile = new File("test.csv");
		
		//Test Seven
		boolean test7 = testDataSet.BuildDataSet(inputfile);
		if (test7) {
			System.out.println("DataSet:: BuildDataSet - Test Passed");
		} else {
			System.out.println("DataSet:: BuildDataSet - Unexpected Error");
		}
		//Test Eight
		System.out.println("DataSet:: GetNoOfEntrys()");
		int test8entries = testDataSet.GetNoOfEntrys();
		final int ENTRIES = 11;
		if (test8entries == ENTRIES) {
			System.out.println("DataSet:: GetNoOfEntrys() - Test Passed");
		} else {
			System.out.println("DataSet:: GetNoOfEntrys() - Test Failed");
			System.out.println(test8entries);
		}
		//Test Nine
		System.out.println("DataSet:: GetNoOfAttributes()");
		int test9entries = testDataSet.GetNoOfAttributes();
		final int ATTRIBUTES = 2;
		if (test9entries == ATTRIBUTES) {
			System.out.println("DataSet:: GetNoOfAttributes() - Test Passed");
		} else {
			System.out.println("DataSet:: GetNoOfAttributes() - Test Failed");
			System.out.println(test9entries);
		}
	}
} /* end class DataSet */