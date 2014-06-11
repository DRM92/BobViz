import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.jfree.chart.JFreeChart;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartUtilities;
import java.awt.Graphics;
import java.io.File; //Used in testing
import javax.swing.border.Border;
import javax.swing.BorderFactory;

/**
 * @file    GraphSaver.java
 * @author  D. Sherratt
 * @date    10.04.13
 * @see     
 * 
 * \brief Class that creates a GUI to get saving details, and
 *	creates a saved PNG format of the chart.
 */

public class GraphSaver {
		
	/**
	*	Constructor that reads in parameters to initialize
	*	and creates a GUI asking for more information form the user
	*
	* @param the chart that you want to save
	* @param the default chart width
	* @param the default chart height
	*/
	public GraphSaver(ChartGUI chart, int defaultchartwidth,
			int defaultchartheight) {
		m_chart = chart;
		m_Frame = new JFrame("Save Chart");
		m_Frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		m_Frame.setResizable(false);
		m_Frame.setLocationRelativeTo(null);
        m_Frame.setLayout(new FlowLayout());
		
		m_panel = new JPanel(new GridLayout(ROWS,COLUMNS));
		
		Border border = BorderFactory
                .createTitledBorder("Save Settings");
        m_panel.setBorder(border);
		
        m_Frame.add(m_panel);
		
		JLabel label = new JLabel("File Title");
		m_panel.add(label);
		m_fileTitle = new JTextField("Untitled");
        m_panel.add(m_fileTitle);
		
		label = new JLabel("Chart Width");
		m_panel.add(label);
		m_chartWidth = new JTextField(Integer.toString(
			defaultchartwidth));
        m_panel.add(m_chartWidth);
		
		label = new JLabel("Chart Height      ");
		m_panel.add(label);
		m_chartHeight = new JTextField(Integer.toString(
			defaultchartheight));
        m_panel.add(m_chartHeight);
		
		JLabel blankLabel1 = new JLabel("");
        m_panel.add(blankLabel1);
		JLabel blankLabel2 = new JLabel("");
		m_panel.add(blankLabel2);
		JLabel blankLabel3 = new JLabel("");
		m_panel.add(blankLabel3);
		
		m_Save = new JButton("Save");
		m_panel.add(m_Save);
		
		SaveEventHandler eventHandeler = new SaveEventHandler();
        m_Save.addActionListener(eventHandeler);
		
		m_Frame.setVisible(true);
		
	}
	
	/**
	*	method that returns the panel stored
	*
	*	@return the stored panel
	*/
	public JPanel getJPanel() {
		return m_ChartPanel;
	}
	
	/**
	*	method that sets the JPanel that has the chart on
	*
	* 	@param the Jpanel that has the chart on that you want to save
	*
	*	@return true when the new panel is set.
	*/
	public boolean setJPanel(JPanel panel) {
		m_ChartPanel = panel;
		return true;
	}
	
	
	/**
    * Inner class acting as an action listener for the GUI components.
    * @author D. Sherratt
    */
    private class SaveEventHandler implements ActionListener {
		
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource()== m_Save){                         
                Save();                  
			}
		}	
		
	/**
	*	method that saves the chart
	*
	*	@return true if the chart saves as a png format,
	*			false otherwise.
	*/
	public boolean Save() {
		String filename = m_fileTitle.getText();
		
		//Stop saving if no filename is given.
		if (filename.isEmpty()) {
			JOptionPane.showMessageDialog(null, 
				"No filename");
			return false;
		}
		
		int width;
		int height;
		String str_width = m_chartWidth.getText().toString();
		String str_height = m_chartHeight.getText().toString();
		try {
			width = Integer.valueOf(str_width);
			height = Integer.valueOf(str_height);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, 
				"Width and Height entered not numbers");
			return false;
		}
		       
		try {
			
			BufferedImage bi = new BufferedImage(width, 
           		height, BufferedImage.TYPE_INT_ARGB);       
			Graphics g = bi.createGraphics();
			m_ChartPanel.paint(g);  //this == JComponent
			g.dispose();
			
			ImageIO.write(bi,"png",new File(filename + ".png"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, 
				"Save unsuccessful");
			return false;
		}
	
		JOptionPane.showMessageDialog(null, "Save successful");
		m_Frame.setVisible(false);
		return true;
	}
			
        
	
	}
	/* Default width of frame */
	final private int DEFAULT_WIDTH = 200;
	/* Default height of frame */
	final private int DEFAULT_HEIGHT = 200;
	/* number of rows on JPanel Grid layout */
	final private int ROWS = 5;
	/* number of columns on JPanel Grid layout */
	final private int COLUMNS = 2;
	/* member variable for chart panel */
	private JPanel m_ChartPanel;
	/* member variable for jpanel */
	private JPanel m_panel;
	/* member variable for save button */
	private JButton m_Save;
	/* member variable storing chart given in constructor */
	private ChartGUI m_chart;
	/* member variable of JFrame */
	private JFrame m_Frame;
	/* member variable used for getting filename from GUI */
	private JTextField m_fileTitle;
	/* member variable used for getting chart width from GUI */
	private JTextField m_chartWidth;
	/* member variable used for getting chart height from GUI */
	private JTextField m_chartHeight;
	
	/**
	*	Testing method for GraphSaver
	*/
	public static void main(String args[]){
		DataSet testdataset = new DataSet();
		File inputfile = new File("test.csv");
		testdataset.BuildDataSet(inputfile);
		ChartGUI testChart = new ChartGUI(testdataset);
		int width = 400;
		int height = 400;
		System.out.println("GraphSaver:: GraphSaver()");
		GraphSaver testSave = new GraphSaver(testChart, width, height);
		System.out.println("GraphSaver:: GraphSaver() - Test Passed");
	}
}