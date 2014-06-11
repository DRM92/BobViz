import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.PieDataset;
import javax.swing.JOptionPane;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.RingPlot;
import java.io.File; //Used in testing
import org.jfree.chart.title.DateTitle;
import java.util.Date;
import org.jfree.chart.title.TextTitle;
import java.sql.Time;
import org.jfree.data.category.CategoryDataset;
import java.awt.Shape;
import java.awt.Dimension;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.util.ShapeUtilities;
import javax.swing.ImageIcon;

/**
 * @file    ChartGUI.java
 * @author  Lewis Radcliffe, Gwion Rhys Davies & D. Sherratt
 * @date    1.03.13
 * @see     BigJava 4th edition, p737 and
 *          http://docs.oracle.com/javase/tutorial/uiswing
 *			http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/title/TextTitle.html
 * 
 * \brief 	Chart class that displays the visualisation options.
 */

public class ChartGUI extends JPanel {
	
	/**
    * Creates the UI which holds all the visualisation options
    * @param data 
    */
    public ChartGUI (DataSet data) {
        
		super(new GridLayout(1,0));
		
		m_data = data;
		m_chartType = -1;
        m_chart = null;
		
    }
	
	/**	
    * @return the chart type
    */
    public int GetChartType() {
        return(m_chartType);
    }
    
    /**
    * @param type
    * @return TRUE or FALSE depending on chart type
    */
    public boolean SetChartType(int type) {
        
        if ((type>=0) && (type<NUMOFCHARTS)) {
            m_chartType = type;
            return(true);
        } else {
            return(false);
        }
    }
    
    /**
    * @return the actual chart
    */
    public JFreeChart GetChart() {
        return(m_chart);
    }
    
    /**
    * Sets the chart to m_chart
    * @param chart 
    */
    public void SetChart(JFreeChart chart) {
        m_chart = chart;
    }
    
	/**
    * Converts time from number of millisenconds since January 1, 1970
	* to current time of day.
	*
	* @param long large int representing time
	*
	* @return String of the current time
    */
	public String ConvertTime(long timeMillis) {          
		long elapsedTime = timeMillis;   
		String format = String.format("%%0%dd", FORMATTING);   
		elapsedTime = elapsedTime / MILLISECOND;   
		String seconds = String.format(format, elapsedTime % MINUTE);   
		String minutes = String.format(format, (elapsedTime % HOUR) / MINUTE);   
		
		int hoursTime = (int) (elapsedTime / HOUR);
		
		while (hoursTime > DAY) {
			hoursTime = hoursTime - DAY;
		}
		hoursTime++;
		
		String hours = String.format(format, hoursTime);   
		String time =  hours + ":" + minutes;// + ":" + seconds;   
		return time;   
	}
	
	/**
    * Method that disposes the window
    */
    public void DisposeWindow() {
        m_window.dispose();
    }
	
	/**
    * Sets the window to visible
    */
    public void SetWindowVisible() {
        Object[] possibleValues = new Object[NUMOFCHARTS];
		possibleValues[BARCHART] = "Bar Chart";
		possibleValues[PIECHART] = "Pie Chart";
		possibleValues[LINECHART] = "Line Chart";
		possibleValues[RINGCHART] = "Ring Chart";
		possibleValues[AREACHART] = "Area Chart";
		possibleValues[BUBBLECHART] = "Bubble Chart";
		possibleValues[SCATTERCHART] = "Scatter Chart";
		
		Object selectedValue = JOptionPane.showInputDialog(null,
		"Choose a chart you want to use", "Chart",
		JOptionPane.INFORMATION_MESSAGE, null,
		possibleValues, possibleValues[BARCHART]);
			
		if (selectedValue == null) {
			
		} else if (selectedValue == possibleValues[BARCHART]) {
			SetUpBarChart(m_data);
		} else if (selectedValue == possibleValues[PIECHART]){
			SetUpPieChart(m_data);
		} else if (selectedValue == possibleValues[LINECHART]) {
			SetUpLineChart(m_data);
		} else if (selectedValue == possibleValues[RINGCHART]) {
			SetUpRingChart(m_data);
		} else if (selectedValue == possibleValues[AREACHART]) {
			SetUpAreaChart(m_data);
		} else if (selectedValue == possibleValues[BUBBLECHART]) {
			SetUpBubbleChart(m_data);
		} else if (selectedValue == possibleValues[SCATTERCHART]) {
			SetUpScatterChart(m_data);
		}
	
		if (selectedValue != null) {
			m_window.setVisible(true);
			m_window.setLocationRelativeTo(null);
		}
    }
	
	/**
	*	Creates GUI used to create Area Chart.
	*
	*	@param The dataset that the user has already selected, used for drop
	*	down boxes
	*	@return True if GUI has successfully been created.
	*/	
	public boolean SetUpAreaChart(DataSet data) {
		m_dataSet = data;
        m_chartType = NOCHART;
        m_chart = null;
        m_window = new JFrame("Chart Setup");
        m_window.setSize(WIDTH, HEIGHT);
        m_window.setResizable(false);
        m_window.setLayout(new FlowLayout());
		
		JPanel areaPanel = new JPanel(new GridLayout(B_ROW,B_COL,H_GAP,V_GAP));
        m_window.add(areaPanel);
		
        ImageIcon areaImg = new ImageIcon("./img/areachart.png");            
        m_areaChartLabel = new JLabel(areaImg);
        m_window.add(m_areaChartLabel);

		Border areaPanelBorder = BorderFactory
                .createTitledBorder("Area Chart Settings");
        areaPanel.setBorder(areaPanelBorder);
		
		String[] m_modelString = new String[data.GetNoOfAttributes()];
		
		 for(int i=0; i<data.GetNoOfAttributes(); i++){
                m_modelString[i] = data.GetAttributeName(i);
        }
		
		JLabel label = new JLabel("Select X-Axis:");
        areaPanel.add(label);
		m_attributeDropdownBar1 = new JComboBox(m_modelString);
        m_attributeDropdownBar1.setMaximumRowCount(data.GetNoOfAttributes());
        areaPanel.add(m_attributeDropdownBar1);
        label = new JLabel("Select Y-Axis:");
        areaPanel.add(label);
        m_attributeDropdownBar2 = new JComboBox(m_modelString);
        m_attributeDropdownBar2.setMaximumRowCount(data.GetNoOfAttributes());
        areaPanel.add(m_attributeDropdownBar2);
		
		m_barHeader = new JTextField("Title");
        areaPanel.add(m_barHeader);
		
		m_Caption = new JTextField("Caption");
        areaPanel.add(m_Caption);
        m_Author= new JTextField("Author");
        areaPanel.add(m_Author);
		
		m_generateAreaButton = new JButton("Generate Area Chart");
		
		areaPanel.add(m_generateAreaButton);	
		 
		GUIEventHandler m_eventHandeler = new GUIEventHandler();
        m_generateAreaButton.addActionListener(m_eventHandeler);
		
		return true;
	}
	
	/**
	*	Creates GUI used to create Bar Chart.
	*
	*	@param The dataset that the user has already selected, used for drop
	*	down boxes
	*	@return True if GUI has successfully been created.
	*/	
	public boolean SetUpBarChart(DataSet data) {
		
        m_dataSet = data;
        m_chartType = NOCHART;
        m_chart = null;
        m_window = new JFrame("Chart Setup");
        m_window.setSize(WIDTH, HEIGHT);
        m_window.setResizable(false);
        m_window.setLayout(new FlowLayout());
		
		JPanel barPanel = new JPanel(new GridLayout(B_ROW,B_COL,H_GAP,V_GAP));
        m_window.add(barPanel);

        ImageIcon barImg = new ImageIcon("./img/barchart.png");            
        m_barChartLabel = new JLabel(barImg);
        m_window.add(m_barChartLabel);
		
		Border barPanelBorder = BorderFactory
                .createTitledBorder("Bar Chart Settings");
        barPanel.setBorder(barPanelBorder);
		
		String[] m_modelString = new String[data.GetNoOfAttributes()];
        
        for(int i=0; i<data.GetNoOfAttributes(); i++){
                m_modelString[i] = data.GetAttributeName(i);
        }
		
		JLabel label = new JLabel("Select X-Axis:");
        barPanel.add(label);
        m_attributeDropdownBar1 = new JComboBox(m_modelString);
        m_attributeDropdownBar1.setMaximumRowCount(data.GetNoOfAttributes());
        barPanel.add(m_attributeDropdownBar1);
        label = new JLabel("Select Y-Axis:");
        barPanel.add(label);
        m_attributeDropdownBar2 = new JComboBox(m_modelString);
        m_attributeDropdownBar2.setMaximumRowCount(data.GetNoOfAttributes());
        barPanel.add(m_attributeDropdownBar2);
        m_barHeader = new JTextField("Title");
        barPanel.add(m_barHeader);
		
		m_Caption = new JTextField("Caption");
        barPanel.add(m_Caption);
        m_Author= new JTextField("Author");
        barPanel.add(m_Author);
		
		m_generateBarButton = new JButton("Generate Bar Chart");
		
		barPanel.add(m_generateBarButton);	
		 
		GUIEventHandler m_eventHandeler = new GUIEventHandler();
        m_generateBarButton.addActionListener(m_eventHandeler);
		
		return true;
	}
	
	/**
	*	Creates GUI used to create Bubble Chart.
	*
	*	@param The dataset that the user has already selected, used for drop
	*	down boxes
	*	@return True if GUI has successfully been created.
	*/	
	public boolean SetUpBubbleChart(DataSet data) {
		m_dataSet = data;
        m_chartType = NOCHART;
        m_chart = null;
        m_window = new JFrame("Chart Setup");
        m_window.setSize(WIDTH, BUBBLEHEIGHT);
        m_window.setResizable(false);
        m_window.setLayout(new FlowLayout());
		
		JPanel bubblePanel = new JPanel(new GridLayout(B_ROW + B_ROW,B_COL,
			H_GAP,V_GAP));
        m_window.add(bubblePanel);

        ImageIcon bubbleImg = new ImageIcon("./img/bubblechart.png");            
        m_bubbleChartLabel = new JLabel(bubbleImg);
        m_window.add(m_bubbleChartLabel);

		Border bubblePanelBorder = BorderFactory
                .createTitledBorder("Bubble Chart Settings");
        bubblePanel.setBorder(bubblePanelBorder);
		
		String[] m_modelString = new String[data.GetNoOfAttributes()];
        
        for(int i=0; i<data.GetNoOfAttributes(); i++){
                m_modelString[i] = data.GetAttributeName(i);
        }
		
		JLabel label = new JLabel("Select Series:");
        bubblePanel.add(label);
        m_attributeDropdownBar1 = new JComboBox(m_modelString);
        m_attributeDropdownBar1.setMaximumRowCount(data.GetNoOfAttributes());
        bubblePanel.add(m_attributeDropdownBar1);
		label = new JLabel("Select X-Axis:");
        bubblePanel.add(label);
        m_attributeDropdownBar2 = new JComboBox(m_modelString);
        m_attributeDropdownBar2.setMaximumRowCount(data.GetNoOfAttributes());
        bubblePanel.add(m_attributeDropdownBar2);
        label = new JLabel("Select Y-Axis:");
        bubblePanel.add(label);
        m_attributeDropdown3 = new JComboBox(m_modelString);
        m_attributeDropdown3.setMaximumRowCount(data.GetNoOfAttributes());
        bubblePanel.add(m_attributeDropdown3);
        label = new JLabel("Select Values:");
        bubblePanel.add(label);
        m_attributeDropdown4 = new JComboBox(m_modelString);
        m_attributeDropdown4.setMaximumRowCount(data.GetNoOfAttributes());
        bubblePanel.add(m_attributeDropdown4);
        
		
		m_barHeader = new JTextField("Title");
        bubblePanel.add(m_barHeader);
		
		m_Caption = new JTextField("Caption");
        bubblePanel.add(m_Caption);
        m_Author= new JTextField("Author");
        bubblePanel.add(m_Author);
		
		m_generateBubbleButton = new JButton("Generate Bubble Chart");
		
		bubblePanel.add(m_generateBubbleButton);	
		 
		GUIEventHandler m_eventHandeler = new GUIEventHandler();
        m_generateBubbleButton.addActionListener(m_eventHandeler);
		
		return true;
	}
	
	/**
	*	Creates GUI used to create Line Chart.
	*
	*	@param The dataset that the user has already selected, used for drop
	*	down boxes
	*	@return True if GUI has successfully been created.
	*/	
	public boolean SetUpLineChart(DataSet data) {
		m_dataSet = data;
        m_chartType = NOCHART;
        m_chart = null;
        m_window = new JFrame("Chart Setup");
        m_window.setSize(WIDTH, HEIGHT);
        m_window.setResizable(false);
        m_window.setLayout(new FlowLayout());
		
		JPanel linePanel = new JPanel(new GridLayout(B_ROW,B_COL,H_GAP,V_GAP));
        m_window.add(linePanel);

        ImageIcon lineImg = new ImageIcon("./img/linechart.png");            
        m_lineChartLabel = new JLabel(lineImg);
        m_window.add(m_lineChartLabel);
		
		Border linePanelBorder = BorderFactory
                .createTitledBorder("Line Chart Settings");
        linePanel.setBorder(linePanelBorder);
		
		String[] m_modelString = new String[data.GetNoOfAttributes()];
		
		 for(int i=0; i<data.GetNoOfAttributes(); i++){
                m_modelString[i] = data.GetAttributeName(i);
        }
		
		JLabel label = new JLabel("Select X-Axis:");
        linePanel.add(label);
		m_attributeDropdownBar1 = new JComboBox(m_modelString);
        m_attributeDropdownBar1.setMaximumRowCount(data.GetNoOfAttributes());
        linePanel.add(m_attributeDropdownBar1);
        label = new JLabel("Select Y-Axis:");
        linePanel.add(label);
        m_attributeDropdownBar2 = new JComboBox(m_modelString);
        m_attributeDropdownBar2.setMaximumRowCount(data.GetNoOfAttributes());
        linePanel.add(m_attributeDropdownBar2);
		
		m_barHeader = new JTextField("Title");
        linePanel.add(m_barHeader);
		
		m_Caption = new JTextField("Caption");
        linePanel.add(m_Caption);
        m_Author= new JTextField("Author");
        linePanel.add(m_Author);
		
		m_generateLineButton = new JButton("Generate Line Chart");
		
		linePanel.add(m_generateLineButton);	
		 
		GUIEventHandler m_eventHandeler = new GUIEventHandler();
        m_generateLineButton.addActionListener(m_eventHandeler);
		
		return true;
	}
	
	/**
	*	Creates GUI used to create Pie Chart.
	*
	*	@param The dataset that the user has already selected, used for drop
	*	down boxes
	*	@return True if GUI has successfully been created.
	*/	
	public boolean SetUpPieChart(DataSet data) {
		m_dataSet = data;
        m_chartType = NOCHART;
        m_chart = null;
        m_window = new JFrame("Chart Setup");
        m_window.setSize(WIDTH, HEIGHT);
        m_window.setResizable(false);
        m_window.setLayout(new FlowLayout());
		
		JPanel piePanel = new JPanel(new GridLayout(B_ROW,B_COL,H_GAP,V_GAP));
        m_window.add(piePanel);

        ImageIcon pieImg = new ImageIcon("./img/piechart.png");            
        m_pieChartLabel = new JLabel(pieImg);
        m_window.add(m_pieChartLabel);
		
		Border piePanelBorder = BorderFactory
                .createTitledBorder("Pie Chart Settings");
        piePanel.setBorder(piePanelBorder);
		
		String[] m_modelString = new String[data.GetNoOfAttributes()];
        
        for(int i=0; i<data.GetNoOfAttributes(); i++){
                m_modelString[i] = data.GetAttributeName(i);
        }
		
		JLabel label = new JLabel("Select X-Axis:");
		label = new JLabel("Select First Attribute:");
        piePanel.add(label);
        m_attributeDropdownPie1 = new JComboBox(m_modelString);
        m_attributeDropdownPie1.setMaximumRowCount(data.GetNoOfAttributes());
        piePanel.add(m_attributeDropdownPie1);
        label = new JLabel("Select Second Attribute:");
        piePanel.add(label);
        m_attributeDropdownPie2 = new JComboBox(m_modelString);
        m_attributeDropdownPie2.setMaximumRowCount(data.GetNoOfAttributes());
        piePanel.add(m_attributeDropdownPie2);
        m_pieHeader = new JTextField("Title");
        piePanel.add(m_pieHeader);
		
		m_Caption = new JTextField("Caption");
        piePanel.add(m_Caption);
        m_Author= new JTextField("Author");
        piePanel.add(m_Author);
		
		m_generatePieButton = new JButton("Generate Pie Chart");	
        piePanel.add(m_generatePieButton);

        GUIEventHandler m_eventHandeler = new GUIEventHandler();
        m_generatePieButton.addActionListener(m_eventHandeler);
		
		return true;
	}
	
	/**
	*	Creates GUI used to create Ring Chart.
	*
	*	@param The dataset that the user has already selected, used for drop
	*	down boxes
	*	@return True if GUI has successfully been created.
	*/	
	public boolean SetUpRingChart(DataSet data) {
		m_dataSet = data;
        m_chartType = NOCHART;
        m_chart = null;
        m_window = new JFrame("Chart Setup");
        m_window.setSize(WIDTH, HEIGHT);
        m_window.setResizable(false);
        m_window.setLayout(new FlowLayout());
		
		JPanel ringPanel = new JPanel(new GridLayout(B_ROW,B_COL,H_GAP,V_GAP));
        m_window.add(ringPanel);
		
		ImageIcon ringImg = new ImageIcon("./img/ringchart.png");            
        m_ringChartLabel = new JLabel(ringImg);
        m_window.add(m_ringChartLabel);

		Border ringPanelBorder = BorderFactory
                .createTitledBorder("Ring Chart Settings");
        ringPanel.setBorder(ringPanelBorder);
		
		String[] m_modelString = new String[data.GetNoOfAttributes()];
        
        for(int i=0; i<data.GetNoOfAttributes(); i++){
                m_modelString[i] = data.GetAttributeName(i);
        }
		
		JLabel label = new JLabel("Select X-Axis:");
        ringPanel.add(label);
        m_attributeDropdownBar1 = new JComboBox(m_modelString);
        m_attributeDropdownBar1.setMaximumRowCount(data.GetNoOfAttributes());
        ringPanel.add(m_attributeDropdownBar1);
        label = new JLabel("Select Y-Axis:");
        ringPanel.add(label);
        m_attributeDropdownBar2 = new JComboBox(m_modelString);
        m_attributeDropdownBar2.setMaximumRowCount(data.GetNoOfAttributes());
        ringPanel.add(m_attributeDropdownBar2);
        m_barHeader = new JTextField("Title");
        ringPanel.add(m_barHeader);
		
		m_Caption = new JTextField("Caption");
        ringPanel.add(m_Caption);
        m_Author= new JTextField("Author");
        ringPanel.add(m_Author);
		
		m_generateRingButton = new JButton("Generate Ring Chart");
		
		ringPanel.add(m_generateRingButton);	
		 
		GUIEventHandler m_eventHandeler = new GUIEventHandler();
        m_generateRingButton.addActionListener(m_eventHandeler);
		
		return true;
	}
	
	/**
	*	Creates GUI used to create Scatter Chart.
	*
	*	@param The dataset that the user has already selected, used for drop
	*	down boxes
	*	@return True if GUI has successfully been created.
	*/	
	public boolean SetUpScatterChart(DataSet data) {
		m_dataSet = data;
        m_chartType = NOCHART;
        m_chart = null;
        m_window = new JFrame("Chart Setup");
        m_window.setSize(WIDTH, HEIGHT);
        m_window.setResizable(false);
        m_window.setLayout(new FlowLayout());
		
		JPanel scatterPanel = new JPanel(new GridLayout(
			B_ROW,B_COL,H_GAP,V_GAP));
        m_window.add(scatterPanel);

        ImageIcon scatterImg = new ImageIcon("./img/scatterchart.png");            
        m_scatterChartLabel = new JLabel(scatterImg);
        m_window.add(m_scatterChartLabel);
		
		Border scatterPanelBorder = BorderFactory
                .createTitledBorder("Scatter Chart Settings");
        scatterPanel.setBorder(scatterPanelBorder);
		
		String[] m_modelString = new String[data.GetNoOfAttributes()];
		
		 for(int i=0; i<data.GetNoOfAttributes(); i++){
                m_modelString[i] = data.GetAttributeName(i);
        }
		
		JLabel label = new JLabel("Select X-Axis:");
        scatterPanel.add(label);
		m_attributeDropdownBar1 = new JComboBox(m_modelString);
        m_attributeDropdownBar1.setMaximumRowCount(data.GetNoOfAttributes());
        scatterPanel.add(m_attributeDropdownBar1);
        label = new JLabel("Select Y-Axis:");
        scatterPanel.add(label);
        m_attributeDropdownBar2 = new JComboBox(m_modelString);
        m_attributeDropdownBar2.setMaximumRowCount(data.GetNoOfAttributes());
        scatterPanel.add(m_attributeDropdownBar2);
		
		m_barHeader = new JTextField("Title");
        scatterPanel.add(m_barHeader);
		
		m_Caption = new JTextField("Caption");
        scatterPanel.add(m_Caption);
        m_Author= new JTextField("Author");
        scatterPanel.add(m_Author);
		
		m_generateScatterButton = new JButton("Generate Scatter Chart");
		
		scatterPanel.add(m_generateScatterButton);	
		 
		GUIEventHandler m_eventHandeler = new GUIEventHandler();
        m_generateScatterButton.addActionListener(m_eventHandeler);
		
		return true;
		
	}
    
    /**
    * Inner class acting as an action listener for the GUI components.
    * @author Lewis Radcliffe, Gwion Rhys Davies & D. Sherratt
    */
    private class GUIEventHandler implements ActionListener {
		
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource()== m_generateBarButton){                         
                generateBarChart();                  
            } else if (event.getSource()== m_generatePieButton){           			
                generatePieChart();
            } else if (event.getSource() == m_generateLineButton) {
				generateLineChart();
			} else if (event.getSource() == m_generateRingButton) {
				generateRingChart();
			} else if (event.getSource() == m_generateAreaButton) {
				generateAreaChart();
			} else if (event.getSource() == m_generateBubbleButton) {
				generateBubbleChart();
			} else if (event.getSource() == m_generateScatterButton) {
				generateScatterChart();
			}
        }
		
		/**
		*	Method that gathers information, generates Area chart and
		*	displays that chart.
		*
		*	@return true when chart is displayed.
		*/
		public boolean generateAreaChart() {
			int A1 = m_attributeDropdownBar1.getSelectedIndex();
            int A2 = m_attributeDropdownBar2.getSelectedIndex();
			String header = m_barHeader.getText();
            String caption = m_Caption.getText();
            String author = m_Author.getText();
			String captionAndAuthor;
			if (author.isEmpty()) {
				captionAndAuthor = caption;
			} else {
				captionAndAuthor = caption + "\n" + "Author: " + author;
			}
			AreaChart areaChart = new AreaChart(m_dataSet);
			areaChart.setDataset(m_dataSet);
			areaChart.setAttribute1(A1);
			areaChart.setAttribute2(A2);
			areaChart.setHeader(header);
			areaChart.setxAxis(m_dataSet.GetAttributeName(A1));
			areaChart.setyAxis(m_dataSet.GetAttributeName(A2));
			JFreeChart chart = areaChart.MakeChart();
			DateTitle dateTitle = new DateTitle(); 
			Date now = new Date();
			String dateAndTime = dateTitle.getText();
			dateAndTime = dateAndTime + "\n" + ConvertTime(now.getTime());
			dateTitle.setText(dateAndTime);
			chart.addSubtitle(dateTitle);
			TextTitle capAndAuth = new TextTitle(captionAndAuthor);
			chart.addSubtitle(capAndAuth);
			CustomAreaRenderer renderer = new CustomAreaRenderer(
				DataVisualizerGUI.m_cm.GetActiveMap());
			CategoryPlot plot = (CategoryPlot) chart.getPlot();
			renderer.SetColor(plot, 1);
			plot.setForegroundAlpha(0.5f);
			SetChartType(AREACHART);
			SetChart(chart);
            m_frame = new ChartPanel(chart);
            removeAll();
            add(m_frame);
            repaint();
            revalidate();
            GroupJVTApplication.dataVisualizer.ActivateColour();
            GroupJVTApplication.dataVisualizer.SelectChartTab();
            m_window.setVisible(false);
			return true;
		}
		
		/**
		*	Method that gathers information, generates Bar chart and
		*	displays that chart.
		*
		*	@return true when chart is displayed.
		*/
		public boolean generateBarChart() {
			int A1 = m_attributeDropdownBar1.getSelectedIndex();
            int A2 = m_attributeDropdownBar2.getSelectedIndex();
            String header = m_barHeader.getText();
            String caption = m_Caption.getText();
            String author = m_Author.getText();
			String captionAndAuthor;
			if (author.isEmpty()) {
				captionAndAuthor = caption;
			} else {
				captionAndAuthor = caption + "\n" + "Author: " + author;
			}
            BarChart barChart = new BarChart(m_dataSet);
			barChart.setDataset(m_dataSet);
			barChart.setAttribute1(A1);
			barChart.setAttribute2(A2);
			barChart.setHeader(header);
			barChart.setxAxis(m_dataSet.GetAttributeName(A1));
			barChart.setyAxis(m_dataSet.GetAttributeName(A2));
            JFreeChart chart = barChart.MakeChart();
			DateTitle dateTitle = new DateTitle(); 
			Date now = new Date();
			String dateAndTime = dateTitle.getText();
			dateAndTime = dateAndTime + "\n" + ConvertTime(now.getTime());
			dateTitle.setText(dateAndTime);
			chart.addSubtitle(dateTitle);
			TextTitle capAndAuth = new TextTitle(captionAndAuthor);
			chart.addSubtitle(capAndAuth);
            
			
			CustomBarRenderer renderer = new CustomBarRenderer(
				DataVisualizerGUI.m_cm.GetActiveMap());
			CategoryPlot plot = (CategoryPlot) chart.getPlot();
			CategoryDataset barDataset = plot.getDataset();
			for (int i = 1; i <= barDataset.getRowCount(); i++) {
				renderer.SetColor(plot, i);
			}

			m_frame = new ChartPanel(chart);
            SetChartType(BARCHART);
            SetChart(chart);
            removeAll();
            add(m_frame);
            repaint();
            revalidate();
            GroupJVTApplication.dataVisualizer.ActivateColour();
            GroupJVTApplication.dataVisualizer.SelectChartTab();
            m_window.setVisible(false);
			return true;
		}
		
		/**
		*	Method that gathers information, generates Bubble chart and
		*	displays that chart.
		*
		*	@return true when chart is displayed.
		*/
		public boolean generateBubbleChart() {
			int A1 = m_attributeDropdownBar1.getSelectedIndex();
            int A2 = m_attributeDropdownBar2.getSelectedIndex();
			int A3 = m_attributeDropdown3.getSelectedIndex();
			int A4 = m_attributeDropdown4.getSelectedIndex();
			String header = m_barHeader.getText();
            String caption = m_Caption.getText();
            String author = m_Author.getText();
			String captionAndAuthor;
			if (author.isEmpty()) {
				captionAndAuthor = caption;
			} else {
				captionAndAuthor = caption + "\n" + "Author: " + author;
			}
			BubbleChart bubbleChart = new BubbleChart(m_dataSet);
			bubbleChart.setAttribute1(A1);
			bubbleChart.setAttribute2(A2);
			bubbleChart.setAttribute3(A3);
			bubbleChart.setAttribute4(A4);
			bubbleChart.setHeader(header);
			bubbleChart.setxAxis(m_dataSet.GetAttributeName(A2));
			bubbleChart.setyAxis(m_dataSet.GetAttributeName(A3));
			JFreeChart chart = bubbleChart.MakeChart();
			DateTitle dateTitle = new DateTitle();
			Date now = new Date();
			String dateAndTime = dateTitle.getText();
			dateAndTime = dateAndTime + "\n" + ConvertTime(now.getTime());
			dateTitle.setText(dateAndTime);
			chart.addSubtitle(dateTitle);
			TextTitle capAndAuth = new TextTitle(captionAndAuthor);
			chart.addSubtitle(capAndAuth);
			XYPlot plot = chart.getXYPlot();
            CustomBubbleRenderer renderer = DataVisualizerGUI.m_cm
                    .GetBubbleRenderer();
            plot.setRenderer(renderer);
            plot.setForegroundAlpha(0.5f);
            plot.setFixedLegendItems(DataVisualizerGUI.m_cm.GetLegend(
                    chart,renderer));
			SetChartType(BUBBLECHART);
			SetChart(chart);
            m_frame = new ChartPanel(chart);
            removeAll();
            add(m_frame);
            repaint();
            revalidate();
            GroupJVTApplication.dataVisualizer.ActivateColour();
            GroupJVTApplication.dataVisualizer.SelectChartTab();
            m_window.setVisible(false);
			return true;
		}
		
		/**
		*	Method that gathers information, generates Line chart and
		*	displays that chart.
		*
		*	@return true when chart is displayed.
		*/
		public boolean generateLineChart() {
			int A1 = m_attributeDropdownBar1.getSelectedIndex();
            int A2 = m_attributeDropdownBar2.getSelectedIndex();
			String header = m_barHeader.getText();
            String caption = m_Caption.getText();
            String author = m_Author.getText();
			String captionAndAuthor;
			if (author.isEmpty()) {
				captionAndAuthor = caption;
			} else {
				captionAndAuthor = caption + "\n" + "Author: " + author;
			}
			LineChart lineChart = new LineChart(m_dataSet);
			lineChart.setDataset(m_dataSet);
			lineChart.setAttribute1(A1);
			lineChart.setAttribute2(A2);
			lineChart.setHeader(header);
			lineChart.setxAxis(m_dataSet.GetAttributeName(A1));
			lineChart.setyAxis(m_dataSet.GetAttributeName(A2));
			JFreeChart chart = lineChart.MakeChart();
			
			DateTitle dateTitle = new DateTitle();
			Date now = new Date();
			String dateAndTime = dateTitle.getText();
			dateAndTime = dateAndTime + "\n" + ConvertTime(now.getTime());
			dateTitle.setText(dateAndTime);
			chart.addSubtitle(dateTitle);
			TextTitle capAndAuth = new TextTitle(captionAndAuthor);
			chart.addSubtitle(capAndAuth);
			CustomLineRenderer renderer = new CustomLineRenderer(
				DataVisualizerGUI.m_cm.GetActiveMap());
			CategoryPlot plot = (CategoryPlot) chart.getPlot();
			renderer.SetColor(plot, 1);
			SetChartType(LINECHART);
			SetChart(chart);
            m_frame = new ChartPanel(chart);
            removeAll();
            add(m_frame);
            repaint();
            revalidate();
            GroupJVTApplication.dataVisualizer.ActivateColour();
            GroupJVTApplication.dataVisualizer.SelectChartTab();
            m_window.setVisible(false);
			return true;
		}
		
		/**
		*	Method that gathers information, generates Pie chart and
		*	displays that chart.
		*
		*	@return true when chart is displayed.
		*/
		public boolean generatePieChart() {
			int A1 = m_attributeDropdownPie1.getSelectedIndex();
            int A2 = m_attributeDropdownPie2.getSelectedIndex();
            String header = m_pieHeader.getText();
            String caption = m_Caption.getText();
            String author = m_Author.getText();
			String captionAndAuthor;
			if (author.isEmpty()) {
				captionAndAuthor = caption;
			} else {
				captionAndAuthor = caption + "\n" + "Author: " + author;
			}
			PieChart pieChart = new PieChart(m_dataSet);
			pieChart.setDataset(m_dataSet);
			pieChart.setAttribute1(A1);
			pieChart.setAttribute2(A2);
			pieChart.setHeader(header);
            JFreeChart chart = pieChart.MakeChart();
			DateTitle dateTitle = new DateTitle();
			Date now = new Date();
			String dateAndTime = dateTitle.getText();
			dateAndTime = dateAndTime + "\n" + ConvertTime(now.getTime());
			dateTitle.setText(dateAndTime);
			chart.addSubtitle(dateTitle);
			TextTitle capAndAuth = new TextTitle(captionAndAuthor);
			chart.addSubtitle(capAndAuth);
            PiePlot plot = (PiePlot) chart.getPlot();
            PieDataset dataset = plot.getDataset();
            CustomPieRenderer renderer = new CustomPieRenderer(
                    DataVisualizerGUI.m_cm.GetActiveMap());
            renderer.SetColor(plot, dataset);
            SetChartType(PIECHART);
            SetChart(chart);
            m_frame = new ChartPanel(chart);
            removeAll();
            add(m_frame);
            repaint();
            revalidate();
            GroupJVTApplication.dataVisualizer.ActivateColour();
            GroupJVTApplication.dataVisualizer.SelectChartTab();
            m_window.setVisible(false);
			return true;
		}
		
		/**
		*	Method that gathers information, generates Ring chart and
		*	displays that chart.
		*
		*	@return true when chart is displayed.
		*/
		public boolean generateRingChart() {
			int A1 = m_attributeDropdownBar1.getSelectedIndex();
            int A2 = m_attributeDropdownBar2.getSelectedIndex();
            String header = m_barHeader.getText();
            String caption = m_Caption.getText();
            String author = m_Author.getText();
			String captionAndAuthor;
			if (author.isEmpty()) {
				captionAndAuthor = caption;
			} else {
				captionAndAuthor = caption + "\n" + "Author: " + author;
			}
			RingChart ring = new RingChart(m_dataSet);
			ring.setAttribute1(A1);
			ring.setAttribute2(A2);
			ring.setHeader(header);
			JFreeChart chart = ring.MakeChart();
			DateTitle dateTitle = new DateTitle();
			Date now = new Date();
			String dateAndTime = dateTitle.getText();
			dateAndTime = dateAndTime + "\n" + ConvertTime(now.getTime());
			dateTitle.setText(dateAndTime);
			chart.addSubtitle(dateTitle);
			TextTitle capAndAuth = new TextTitle(captionAndAuthor);
			chart.addSubtitle(capAndAuth);
			RingPlot plot = (RingPlot) chart.getPlot();
			PieDataset dataset = plot.getDataset();

			CustomRingRenderer renderer = new CustomRingRenderer(
				DataVisualizerGUI.m_cm.GetActiveMap());
			renderer.SetColor(plot, dataset);
			SetChartType(RINGCHART);
            SetChart(chart);
            m_frame = new ChartPanel(chart);
            removeAll();
            add(m_frame);
            repaint();
            revalidate();
            GroupJVTApplication.dataVisualizer.ActivateColour();
            GroupJVTApplication.dataVisualizer.SelectChartTab();
            m_window.setVisible(false);
			return true;
		}
		
		/**
		*	Method that gathers information, generates Scatter chart and
		*	displays that chart.
		*
		*	@return true when chart is displayed.
		*/
		public boolean generateScatterChart() {
			int A1 = m_attributeDropdownBar1.getSelectedIndex();
            int A2 = m_attributeDropdownBar2.getSelectedIndex();
			String header = m_barHeader.getText();
            String caption = m_Caption.getText();
            String author = m_Author.getText();
			String captionAndAuthor;
			if (author.isEmpty()) {
				captionAndAuthor = caption;
			} else {
				captionAndAuthor = caption + "\n" + "Author: " + author;
			}
			ScatterChart sChart = new ScatterChart(m_dataSet);
			sChart.setDataset(m_dataSet);
			sChart.setAttribute1(A1);
			sChart.setAttribute2(A2);
			sChart.setHeader(header);
			sChart.setxAxis(m_dataSet.GetAttributeName(A1));
			sChart.setyAxis(m_dataSet.GetAttributeName(A2));
			JFreeChart chart = sChart.MakeChart();
			DateTitle dateTitle = new DateTitle();
			Date now = new Date();
			String dateAndTime = dateTitle.getText();
			dateAndTime = dateAndTime + "\n" + ConvertTime(now.getTime());
			dateTitle.setText(dateAndTime);
			chart.addSubtitle(dateTitle);
			TextTitle capAndAuth = new TextTitle(captionAndAuthor);
			chart.addSubtitle(capAndAuth);
			int length = 3;
			int thickness = 1;
			Shape cross = ShapeUtilities.createDiagonalCross(
					length, thickness);
			
			XYPlot plot = chart.getXYPlot();
			CustomScatterRenderer renderer = DataVisualizerGUI.m_cm
					.GetScatterRenderer();
			renderer.setSeriesShape(0, cross);
			plot.setRenderer(renderer);
			plot.setFixedLegendItems(DataVisualizerGUI.m_cm.GetLegend(
					chart,renderer));
			
			SetChartType(SCATTERCHART);
			SetChart(chart);
            m_frame = new ChartPanel(chart);
            removeAll();
            add(m_frame);
            repaint();
            revalidate();
            GroupJVTApplication.dataVisualizer.ActivateColour();
            GroupJVTApplication.dataVisualizer.SelectChartTab();
            m_window.setVisible(false);
			return true;
		}
		
    }
	
	/**
	*	Tester class that creates an instance of itself and
	*	tests all the methods
	*/
	public static void main(String args[]) {
		
		DataSet testdataset = new DataSet();
		File inputfile = new File("test.csv");
		testdataset.BuildDataSet(inputfile);
		System.out.println("Chart:: Chart()");
		ChartGUI testChart = new ChartGUI(testdataset);
		System.out.println("Chart:: Chart() - Test Passed");
		System.out.println("Chart:: SetWindowVisible()");
		testChart.SetWindowVisible();
		System.out.println("Chart:: SetWindowVisible() - Test Passed");
		System.out.println("Chart:: DisposeWindow()");
		testChart.DisposeWindow();
		System.out.println("Chart:: DisposeWindow() - Test Passed");
	}
    
    private JComboBox m_attributeDropdownBar1;
    private JComboBox m_attributeDropdownBar2;
    private JComboBox m_attributeDropdownPie1;
    private JComboBox m_attributeDropdownPie2;
	private JComboBox m_attributeDropdown3;
	private JComboBox m_attributeDropdown4;
    private JTextField m_barHeader;
    private JTextField m_pieHeader;
    private JButton m_generatePieButton;
    private JButton m_generateBarButton;
	private JButton m_generateLineButton;
	private JButton m_generateRingButton;
	private JButton m_generateAreaButton;
	private JButton m_generateBubbleButton;
	private JButton m_generateScatterButton;
	private JLabel m_areaChartLabel;
	private JLabel m_barChartLabel;
	private JLabel m_bubbleChartLabel;
	private JLabel m_lineChartLabel;
	private JLabel m_pieChartLabel;
	private JLabel m_ringChartLabel;
	private JLabel m_scatterChartLabel;
    private DataSet m_dataSet;
    private JTextField m_Author;
    private JTextField m_Caption;
    private ChartPanel m_frame;
    private JFrame m_window;
    private int m_chartType;
    private JFreeChart m_chart;
    final private int B_ROW = 4;
    final private int B_COL = 2;
    final private int H_GAP = 5;
    final private int V_GAP = 5;
    final private int WIDTH = 650;
    final private int HEIGHT = 230;
	final private int BUBBLEHEIGHT = 250;
	final private int NOCHART = -1;
	final private int BARCHART = 0;
	final private int PIECHART = 1;
	final private int LINECHART = 2;
	final private int RINGCHART = 3;
	final private int AREACHART = 4;
	final private int BUBBLECHART = 5;
	final private int SCATTERCHART = 6;
	final private int NUMOFCHARTS = 7;
	private final int DAY = 24;
	private final int MILLISECOND = 1000;
	private final int MINUTE = 60;
	private final int HOUR = 3600;
	private final int FORMATTING = 2;
	private DataSet m_data;
    
} /* end Chart class */