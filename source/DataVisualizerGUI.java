import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.util.ShapeUtilities;
import java.awt.Shape;
import javax.swing.ImageIcon;

/**
 * @file    DataVisualizerGUI.java
 * @author  Robert Rokosz , Perry Bird
 * @date    01.03.13
 * @see     http://docs.oracle.com/javase/tutorial/uiswing
 * 
 * \brief DataVisualizerGUI class creates all components of user interface
 * and handles user's actions
 */

public final class DataVisualizerGUI extends JFrame {
    
	/** Constructs the UI frame */
    public DataVisualizerGUI() {

        m_handler = new GUIEventHandler();
        FILE_CHOOSER = new JFileChooser();
		
        this.setJMenuBar(createMenuBar());
        createControlPanel();	
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        
        /** Sets the minimum size to prevent from ridiculous minimization  */
        setMinimumSize(new Dimension(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT));
        
        /** Instantiates and object of  */
        m_cm = new ColourManager();
        m_cm.setVisible(false);
    }
	
	/** Activates colour options */
    public void ActivateColour() {
        m_exportVisualization_Button.setEnabled(true);
        m_editMenuItem_ChangeColourScheme.setEnabled (true);
        m_ChangeColourScheme_Button.setEnabled (true);
    }
	
	/** Disactivates colour options */
    public void DisactivateColour() {
        
        m_editMenuItem_ChangeColourScheme.setEnabled (false);
        m_ChangeColourScheme_Button.setEnabled (false);
    }
    
	/** Redraws the colour of the chart using the renderers
	* of each chart. It checks the type of the chart stored in
	* the chart class and uses the applicable renderer
	*/
    public void RedrawChartColour() {
    	
        JFreeChart chart;
		final int NOCHART = -1;
		final int BARCHART = 0;
		final int PIECHART = 1;
		final int LINECHART = 2;
		final int RINGCHART = 3;
		final int AREACHART = 4;
		final int BUBBLECHART = 5;
		final int SCATTERCHART = 6;
    	if (m_chart.GetChart() == null) {
            System.out.println("Error: No chart object found.");
    	} else {
            chart = m_chart.GetChart(); 
            
            if (m_chart.GetChartType() == NOCHART) {
                System.out.println("Error: No chart object found.");
                    
            } else if (m_chart.GetChartType() == BARCHART) {
				CustomBarRenderer renderer = new CustomBarRenderer(
					DataVisualizerGUI.m_cm.GetActiveMap());
				CategoryPlot plot = (CategoryPlot) chart.getPlot();
				CategoryDataset barDataset = plot.getDataset();
				for (int i = 1; i <= barDataset.getRowCount(); i++) {
					renderer.SetColor(plot, i);
				}            
            } else if (m_chart.GetChartType() == PIECHART) {
                PiePlot plot = (PiePlot) chart.getPlot();
                PieDataset dataset = plot.getDataset();
                CustomPieRenderer renderer = new CustomPieRenderer(
                		m_cm.GetActiveMap());
                renderer.SetColor(plot, dataset);
            } else if (m_chart.GetChartType() == LINECHART) {
				CustomLineRenderer renderer = new CustomLineRenderer(
						m_cm.GetActiveMap());
				CategoryPlot plot = (CategoryPlot) chart.getPlot();
				renderer.SetColor(plot, 1);
			} else if (m_chart.GetChartType() == RINGCHART) {
				RingPlot plot = (RingPlot) chart.getPlot();
				PieDataset dataset = plot.getDataset();
				CustomRingRenderer renderer = new CustomRingRenderer(
					m_cm.GetActiveMap());
				renderer.SetColor(plot, dataset);
			} else if (m_chart.GetChartType() == AREACHART) {
				CustomAreaRenderer renderer = new CustomAreaRenderer(
					m_cm.GetActiveMap());
				CategoryPlot plot = (CategoryPlot) chart.getPlot();
                plot.setForegroundAlpha(0.5f);
				renderer.SetColor(plot, 1);
			} else if (m_chart.GetChartType() == BUBBLECHART) {
				XYPlot plot = chart.getXYPlot();
				CustomBubbleRenderer renderer = DataVisualizerGUI.m_cm
                    .GetBubbleRenderer();
				plot.setRenderer(renderer);
                plot.setForegroundAlpha(0.5f);
				plot.setFixedLegendItems(DataVisualizerGUI.m_cm.GetLegend(
                    chart,renderer));
			} else if (m_chart.GetChartType() == SCATTERCHART) {
				XYPlot plot = chart.getXYPlot();
				CustomScatterRenderer renderer = DataVisualizerGUI.m_cm
						.GetScatterRenderer();
				int length = 3;
				int thickness = 1;
				Shape cross = ShapeUtilities.createDiagonalCross(
						length, thickness);
				renderer.setSeriesShape(0, cross);
						
				plot.setRenderer(renderer);
				plot.setFixedLegendItems(DataVisualizerGUI.m_cm.GetLegend(
						chart,renderer));
			}
    	}
    }
	
	/** Set the chart panel to visible when chart tab is pressed */
    public void SelectChartTab() {
        
    	m_tabbedPanel.setSelectedIndex(CHART_PANEL);	
    }
   
	/** 
     * This method activates Draw and Export buttons once the file 
     * is loaded 
     */ 
    private void activateDrawAndExport() {
     
        m_DrawChart_Button.setEnabled (true);
        m_exportVisualization_Button.setEnabled (true);
        m_editMenuItem_DrawChart.setEnabled (true);
        m_fileMenuItem_Export.setEnabled (true); 
    }  
	
    /**
     * Creates Options panel (quick access)
     * and its buttons
     * 
     * @return The button panel
     */
    private JPanel createButtonPanel(){
       
        ImageIcon openImg = new ImageIcon("./img/open.png");            
        m_openFile_Button = new JButton(openImg);
        m_openFile_Button.setOpaque(false);
        m_openFile_Button.setContentAreaFilled(false);
        m_openFile_Button.setBorderPainted(false);
        /** add button to the event listener */
        m_openFile_Button.addActionListener(m_handler);
        
        ImageIcon exportImg = new ImageIcon("./img/save.png");            
        m_exportVisualization_Button = new JButton(exportImg);
        m_exportVisualization_Button.setOpaque(false);
        m_exportVisualization_Button.setContentAreaFilled(false);
        m_exportVisualization_Button.setBorderPainted(false);
        /** add button to the event listener */
        m_exportVisualization_Button.addActionListener(m_handler);

        ImageIcon chartImg = new ImageIcon("./img/chart.png");            
        m_DrawChart_Button = new JButton(chartImg);
        m_DrawChart_Button.setOpaque(false);
        m_DrawChart_Button.setContentAreaFilled(false);
        m_DrawChart_Button.setBorderPainted(false);
        /** add button to the event listener */
        m_DrawChart_Button.addActionListener(m_handler);
        
        ImageIcon colourPickerImg = new ImageIcon("./img/colourpicker.png");            
        m_ChangeColourScheme_Button = new JButton(colourPickerImg);
        m_ChangeColourScheme_Button.setOpaque(false);
        m_ChangeColourScheme_Button.setContentAreaFilled(false);
        m_ChangeColourScheme_Button.setBorderPainted(false);
        /** add button to the event listener */
        m_ChangeColourScheme_Button.addActionListener(m_handler);

        /** create panel and add buttons */
        JPanel panel = new JPanel();       
        panel.setBorder(new TitledBorder(new EtchedBorder(), "Options"));
        panel.add(m_openFile_Button);
        panel.add(m_exportVisualization_Button);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(m_DrawChart_Button);
        panel.add(m_ChangeColourScheme_Button);
        
        /* these buttons are inactive before a valid data file is loaded */
        m_exportVisualization_Button.setEnabled (false);        
        m_DrawChart_Button.setEnabled (false);
        m_ChangeColourScheme_Button.setEnabled (false);
        
        return panel;
    }
    
    /**
    * Creates Control Panel 
    * In case more button panels are needed, those panels will be added here
    */
    private void createControlPanel() {
    	
    	JPanel m_buttonPanel = createButtonPanel();
    	JTabbedPane tabPanel = createTabPanel();

    	/** Line up component panels */
    	JPanel controlPanel = new JPanel();
    	controlPanel.setLayout(new GridLayout(1,1));
    	controlPanel.add(m_buttonPanel);

    	/** Add panel to content pane */
    	add(controlPanel, BorderLayout.NORTH);
    	add(tabPanel);
    } 
    
	/**
     * Creates Edit Menu with all its options
     * 
     * @return The edit menu
     */
    private JMenu createEditMenu() {
        
        /* Build the Edit menu. */
        m_editMenu = new JMenu("Edit");
        m_bar.add(m_editMenu);
        
        /* create Edit Menu options */
        m_editMenuItem_DrawChart = new JMenuItem("Draw Chart");
        /** add item to the event listener */
        m_editMenuItem_DrawChart.addActionListener(m_handler);
        
        m_editMenuItem_ChangeColourScheme = new JMenuItem(
        		"Change Colour Scheme");
        /** add item to the event listener */
        m_editMenuItem_ChangeColourScheme.addActionListener(m_handler);
        
        /* these Edit Menu options are inactive before the valid data file 
         * is loaded */
        m_editMenuItem_DrawChart.setEnabled (false);
        m_editMenuItem_ChangeColourScheme.setEnabled (false);
 
        /* options are added to Edit Menu */
        m_editMenu.add(m_editMenuItem_DrawChart);
        m_editMenu.add(m_editMenuItem_ChangeColourScheme);
    	
        return m_editMenu;
    }
	
    /**
     * Creates File Menu with all its options
     * 
     * @return The file menu
     */
    private JMenu createFileMenu() {
         
        /* Build the File menu. */
        m_fileMenu = new JMenu("File");
        m_bar.add(m_fileMenu);
        
        /* create File Menu options */
        m_fileMenuItem_Open = new JMenuItem("Open File...");
        /** add item to the event listener */
        m_fileMenuItem_Open.addActionListener(m_handler);
        
        m_fileMenuItem_Export = new JMenuItem("Export Visualization...");
        /** add item to the event listener */
        m_fileMenuItem_Export.addActionListener(m_handler);
        
        m_fileMenuItem_Exit = new JMenuItem("Exit");
        /** add item to the event listener */
        m_fileMenuItem_Exit.addActionListener(m_handler);
        
        /* these File Menu options are inactive before the valid data file 
         * is loaded */
        m_fileMenuItem_Export.setEnabled (false);
 
        /* options are added to File Menu */
        m_fileMenu.add(m_fileMenuItem_Open);
        m_fileMenu.add(m_fileMenuItem_Export);
        m_fileMenu.addSeparator();
        m_fileMenu.add(m_fileMenuItem_Exit);
    	
        return m_fileMenu;
    }
    
    /**
     * Creates Help Menu with all its options
     * 
     * @return The help menu
     */
    private JMenu createHelpMenu() {
        
        /* Build the Help menu. */
        m_helpMenu = new JMenu("Help");
        m_bar.add(m_helpMenu);
        
        /* create Help Menu options */
        m_helpMenuItem_HelpContent = new JMenuItem("Help Content");
        /** add item to the event listener */
        m_helpMenuItem_HelpContent.addActionListener(m_handler);

        m_helpMenuItem_About = new JMenuItem("About");
        /** add item to the event listener */
        m_helpMenuItem_About.addActionListener(m_handler);
        
        /* options are added to Help Menu */
        m_helpMenu.add(m_helpMenuItem_HelpContent);
        m_helpMenu.add(m_helpMenuItem_About);
    	
        return m_helpMenu;
    }
	
	/**
    * Creates Menu Bar
    * 
    * @return Menu bar with its components
    */  
    private JMenuBar createMenuBar() {
    	
    	m_bar = new JMenuBar();
    	createFileMenu();
    	createEditMenu();
    	createHelpMenu();	
    	return m_bar;
    }
	
    /**
     * Creates Tab panel (for displaying Table and Chart)
     * 
     * @return The tabbed panel
     */
    private JTabbedPane createTabPanel(){

        m_tabbedPanel = new JTabbedPane();
        /** create tab pane for Table View */
        m_panel1 = new JPanel();
        m_panel1.setLayout(new GridLayout(1, 1));
        m_tabbedPanel.addTab( "TableView", m_panel1);
        /** create tab pane for Chart display */
        m_panel2 = new JPanel();
        m_panel2.setLayout(new GridLayout(1, 1));		
        m_tabbedPanel.addTab( "Chart", m_panel2 );
        add(m_tabbedPanel, BorderLayout.CENTER);
		
        return m_tabbedPanel;
    }

    /**
    * This method opens the file and instantiates an object of DataSet class
    * 
    * @return Boolean
    */
    private Boolean openFile(){

    	/* creates a data backup in case the file is corrupted */
    	m_dataSetBackup = m_dataSet;
    	
    	/* add .csv extension selection to File Chooser 
    	 * m_firstTimeOpen flag ensures that it's added only once
    	 * */
    	if(m_firstTimeOpen) {
            m_firstTimeOpen = false;
            FileFilter extensionFilter = new FileNameExtensionFilter(
            		"CSV Files (.csv)", "csv");
            FILE_CHOOSER.addChoosableFileFilter(extensionFilter);
    	}
    	
    	int returnVal = FILE_CHOOSER.showOpenDialog(DataVisualizerGUI.this);
    	if (returnVal == JFileChooser.APPROVE_OPTION) {
            File m_file = FILE_CHOOSER.getSelectedFile();
            /** instantiate a DataSet object*/
            m_dataSet = new DataSet();
            if(!m_dataSet.BuildDataSet(m_file)){
                /** retrieves the data from backup when the file was 
                 * corrupted */
                m_dataSet = m_dataSetBackup; 
                return false;
                
            } else {	
                /* set buttons to default */
                DisactivateColour();
                m_panel1.removeAll();
                m_panel1.add( new TableView(m_dataSet) );
                m_panel1.repaint();
                m_panel1.revalidate();
                m_tabbedPanel.setSelectedIndex(TABLE_PANEL);

                if (m_firstFileLoaded) {
                	/** create a Chart Setup window when the first file
                	 * is loaded
                	 */
                    m_chart = new ChartGUI(m_dataSet);
                    m_firstFileLoaded = false;
                } else {
                	/** dispose the old Chart Setup window when next file
                	 * is loaded and create a new one
                	 */
                    m_chart.DisposeWindow();
                    m_chart = new ChartGUI(m_dataSet);
                }	
    
                m_panel2.removeAll();
                m_panel2.add(m_chart);
                /** hide the Colour Menu window when the file is loaded */
                m_cm.setVisible(false);
            }
    	} else {
            return false;
    	}
    	return true;
    }
   
    /**
    * Inner class acting as an action listener for the GUI components.
    * 
    * @author Robert Rokosz
    */
    private class GUIEventHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
        	 
        if (event.getSource() == m_fileMenuItem_Open
            		 || event.getSource() == m_openFile_Button) {
            try {
                if (openFile()) {
                	/** set Draw Chart and Export buttons active when file 
                	 * is loaded  
                	 * */
                    activateDrawAndExport();
                    m_exportVisualization_Button.setEnabled(false);
                } else {
                	/** do not change anything when "Open File" action 
                	 * is cancelled */
                	}
            } catch (NullPointerException e) {
            	/** catch the exception in case no file is chosen */
            	}
            
        } else if (event.getSource() == m_fileMenuItem_Exit) {
        	/** close application when Exit option in File menu is chosen */
            dispose();
            
        } else if (event.getSource() == m_exportVisualization_Button ||
			event.getSource() == m_fileMenuItem_Export) {
        	/** exports visualization to a .png format file
        	 * stores in the same directory as application
        	 *  */
            int chartwidth = m_panel2.getSize().width;
			int chartheight = m_panel1.getSize().height;
			
			GraphSaver saver = new GraphSaver(m_chart, chartwidth, chartheight);

			saver.setJPanel(m_panel2);
            
        } else if (event.getSource() ==  m_DrawChart_Button 
            		 || event.getSource() ==  m_editMenuItem_DrawChart){
            try {
                m_chart.SetWindowVisible();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            
        } else if (event.getSource() == m_editMenuItem_ChangeColourScheme
                         || event.getSource() == m_ChangeColourScheme_Button) {
            	m_cm.setVisible(true);
                
             } else if (event.getSource() == m_helpMenuItem_HelpContent) {
             	//Help Content pop-up window 
                 
             } else if (event.getSource() == m_helpMenuItem_About) {
              	//About pop-up window 
             }
         }
    } /* end class GUIEventHandler */
   	
    public static ColourManager m_cm;
    
    /** GUI frame size when started and minimized */
    private final int FRAME_WIDTH = 600;
    private final int FRAME_HEIGHT = 600;
    private final int MIN_FRAME_WIDTH = 500;
    private final int MIN_FRAME_HEIGHT = 500;
    
    /** Indexes of Table and Chart tab panes */
    private final int TABLE_PANEL = 0;
    private final int CHART_PANEL = 1;
    
    
    private final JFileChooser FILE_CHOOSER;
    
    /** flags to handle button logic and pop-ups visibility */
    private Boolean m_firstTimeOpen = true;
    private Boolean m_firstFileLoaded = true;
    
    private GUIEventHandler m_handler;
    
    /** Menu bar and its components */
    private JMenuBar m_bar;
    private JMenu m_fileMenu;
    private JMenu m_editMenu;
    private JMenu m_helpMenu;
    
    /** File Menu options */
    private JMenuItem m_fileMenuItem_Open;
    private JMenuItem m_fileMenuItem_Export;
    private JMenuItem m_fileMenuItem_Exit;
    
    /** Edit Menu options */
    private JMenuItem m_editMenuItem_DrawChart;
    private JMenuItem m_editMenuItem_ChangeColourScheme;
    
    /** Help Menu Options  */
    private JMenuItem m_helpMenuItem_HelpContent;
    private JMenuItem m_helpMenuItem_About;
    
    /** Quick-access panel buttons */
    private JButton m_openFile_Button;
    private JButton m_exportVisualization_Button;  
    private JButton m_DrawChart_Button;
    private JButton m_ChangeColourScheme_Button;
    private JTabbedPane m_tabbedPanel;
    
    /** tab panels used as displays for table and charts */
    private JPanel m_panel1;
    private JPanel m_panel2;

    private DataSet m_dataSet;    
    /** backup of dataset */
    private DataSet m_dataSetBackup;
    private ChartGUI m_chart;

	/**
	*	Test class used when testing DataVisualizerGUI
	*/
    public static void main(String[] args) {  
        DataVisualizerGUI dataVisualizer = new DataVisualizerGUI();
        dataVisualizer.setLocationRelativeTo(null);
        dataVisualizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dataVisualizer.setTitle("Data Visualizer");
        dataVisualizer.setVisible(true);
    } /* end main */
} /* end class DataVisualizerGUI */