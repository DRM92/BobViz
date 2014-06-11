import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

/**
 * @file 	 ColourManager.java
 * @author 	 Matthew Adshead, Dan Morgan
 * @date	 01.03.13
 * @version      2.5
 * @see          http://www.jfree.org/jfreechart/api/javadoc/index.html and
 *               http://docs.oracle.com/javase/6/docs/api/
 *				 http://docs.oracle.com/javase/6/docs/api/javax/swing/JComboBox.html
 * 
 * \brief Object which manages colour schemes.
 * 
 * This class stores and manages a number of colour schemes,
 * as well as providing an interface to change which colour scheme
 * is used when rendering charts.
 * It also stores custom renderer classes for each kind of chart,
 * which allow the charts to be drawn using the colour maps
 * in the ColourMap array.
 */

public class ColourManager extends JFrame {
	
	/**
     * Initialises the colour manager.
     */
    public ColourManager() {
        InitMaps();
        InitGUI();
		this.setLocationRelativeTo(null);
        setSize(new Dimension(CM_WIDTH, CM_HEIGHT));
        setTitle("Colour Options");
    }
	 
	 /**
     * Closes UI window.
     */
    public void CloseFrame() {
        setVisible(false);
    }
	
	 /**
     * Access method for the active colour map.
     * @return Colour map at the active map index in the array.
     */
    public ColourMap GetActiveMap() {
        if (m_activeMap == CUSTOM) {
			return m_custom;
		} else {
			return(m_colourMaps[m_activeMap]);
		}
    }
	
    /**
     * Access method for bar renderer.
     * @return Bar renderer object.
     */
    public CustomBarRenderer GetBarRenderer() {
        return(m_barRenderer);
    }
  	
	/**
     * Takes a chart and creates a new legend for it based on renderer colours.
     * @param chart The JFreeChart object to construct legend for.
     * @param renderer The renderer for the chart.
     * @return JFreeChart object LegendItemCollection, which is
     * used to draw colour legends on charts.
     */
    public LegendItemCollection GetLegend(JFreeChart chart
            ,CustomBubbleRenderer renderer) {
        XYPlot plot = chart.getXYPlot();
        XYDataset dataset = plot.getDataset();
        LegendItemCollection legend = new LegendItemCollection();    
        
        for (int i = 0; i < chart.getXYPlot().getSeriesCount(); ++i) {
            LegendItem li = new LegendItem((String) dataset.getSeriesKey(i),
                                renderer.GetMap().GetColour(i).GetColour());
            legend.add(li);
        }
        return(legend);
    }
	
	/**
     * Takes a chart and creates a new legend for it based on renderer colours.
     * @param chart The JFreeChart object to construct legend for.
     * @param renderer The renderer for the chart.
     * @return JFreeChart object LegendItemCollection, which is
     * used to draw colour legends on charts.
     */
    public LegendItemCollection GetLegend(JFreeChart chart
            ,CustomScatterRenderer renderer) {
        XYPlot plot = chart.getXYPlot();
        XYDataset dataset = plot.getDataset();
        LegendItemCollection legend = new LegendItemCollection();    
        
        for (int i = 0; i < chart.getXYPlot().getSeriesCount(); ++i) {
            LegendItem li = new LegendItem((String) dataset.getSeriesKey(i),
                                renderer.GetMap().GetColour(i).GetColour());
            legend.add(li);
        }
        return(legend);
    }
	
	/**
     * Access method for maps in the array.
     * @param mapIndex - Index of the array.
     * @return ColourMap at index in the array.
     */
    public ColourMap GetMap(int mapIndex) {
        return(m_colourMaps[mapIndex]);
    }
	
    /**
     * Access method for pie renderer.
     * @return Pie renderer object.
     */
    public CustomPieRenderer GetPieRenderer() {
        return(m_pieRenderer);
    }

	/**
     * Access method for line renderer.
     * @return line renderer object.
     */
	public CustomLineRenderer GetLineRenderer() {
		return(m_lineRenderer);
	}
	
	/**
     * Access method for ring renderer.
     * @return ring renderer object.
     */
	public CustomRingRenderer GetRingRenderer() {
		return(m_ringRenderer);
	}
	
	/**
     * Access method for area renderer.
     * @return area renderer object.
     */
	public CustomAreaRenderer GetAreaRenderer() {
		return(m_areaRenderer);
	}
	
	/**
     * Access method for scatter renderer.
     * @return scatter renderer object.
     */
	public CustomScatterRenderer GetScatterRenderer() {
		return(m_scatterRenderer);
	}
	
	/**
     * Access method for bubble renderer.
     * @return bubble renderer object.
     */
	public CustomBubbleRenderer GetBubbleRenderer() {
		return(m_bubbleRenderer);
	}
	
	 /**
     * Creates/redraws the preview panel.
     * @param mapIndex - The index of the map to use when drawing the panel.
     * @return JPanel containing preview of map colours.
     */
    public JPanel DrawSwatches(int mapIndex) {
        
        JPanel swatchPanel = new JPanel();
        Border swatchPanelBorder = BorderFactory.createTitledBorder("Preview");
        swatchPanel.setBorder(swatchPanelBorder);

        Dimension swatchSize = new Dimension(S_WIDTH, S_HEIGHT);
        int borderWidth = 1;
        Color borderColour = Color.black;

        Border swatchOutline = BorderFactory.createMatteBorder(
			borderWidth,
			borderWidth,
			borderWidth,
			borderWidth,
			borderColour );
		
		Color colour;
		
		if (mapIndex == CUSTOM) {
			
			for (int colourIndex=0; colourIndex<MAPCOUNT; colourIndex++) {
				switch (colourIndex) {
					
					case 0:	JPanel colour1 = new JPanel();
											colour1.setSize(swatchSize);
											colour1.setBorder(swatchOutline);
											colour = m_custom.GetColour(colourIndex)
													.GetColour();
											colour1.setBackground(colour);
											swatchPanel.add(colour1);
											break;
						
					case 1:	JPanel colour2 = new JPanel();
											colour2.setSize(swatchSize);
											colour2.setBorder(swatchOutline);
											colour = m_custom.GetColour(colourIndex)
													.GetColour();
											colour2.setBackground(colour);
											swatchPanel.add(colour2);
											break;
						
					case 2:	JPanel colour3 = new JPanel();
											colour3.setSize(swatchSize);
											colour3.setBorder(swatchOutline);
											colour = m_custom.GetColour(colourIndex)
													.GetColour();
											colour3.setBackground(colour);
											swatchPanel.add(colour3);
											break;
						
					case 3:	JPanel colour4 = new JPanel();
											colour4.setSize(swatchSize);
											colour4.setBorder(swatchOutline);
											colour = m_custom.GetColour(colourIndex)
													.GetColour();
											colour4.setBackground(colour);
											swatchPanel.add(colour4);
											break;
						
					case 4: JPanel colour5 = new JPanel();
											colour5.setSize(swatchSize);
											colour5.setBorder(swatchOutline);
											colour = m_custom.GetColour(colourIndex)
													.GetColour();
											colour5.setBackground(colour);
											swatchPanel.add(colour5);
											break;
				}
			}
			
		} else {
			
			ColourMap map = GetMap(mapIndex);
			
			for (int colourIndex=0; colourIndex<MAPCOUNT; colourIndex++) {
				switch (colourIndex) {
					
					case 0:	JPanel colour1 = new JPanel();
											colour1.setSize(swatchSize);
											colour1.setBorder(swatchOutline);
											colour = map.GetColour(colourIndex)
													.GetColour();
											colour1.setBackground(colour);
											swatchPanel.add(colour1);
											break;
						
					case 1:	JPanel colour2 = new JPanel();
											colour2.setSize(swatchSize);
											colour2.setBorder(swatchOutline);
											colour = map.GetColour(colourIndex)
													.GetColour();
											colour2.setBackground(colour);
											swatchPanel.add(colour2);
											break;
						
					case 2:	JPanel colour3 = new JPanel();
											colour3.setSize(swatchSize);
											colour3.setBorder(swatchOutline);
											colour = map.GetColour(colourIndex)
													.GetColour();
											colour3.setBackground(colour);
											swatchPanel.add(colour3);
											break;
						
					case 3:	JPanel colour4 = new JPanel();
											colour4.setSize(swatchSize);
											colour4.setBorder(swatchOutline);
											colour = map.GetColour(colourIndex)
													.GetColour();
											colour4.setBackground(colour);
											swatchPanel.add(colour4);
											break;
						
					case 4: JPanel colour5 = new JPanel();
											colour5.setSize(swatchSize);
											colour5.setBorder(swatchOutline);
											colour = map.GetColour(colourIndex)
													.GetColour();
											colour5.setBackground(colour);
											swatchPanel.add(colour5);
											break;
				}
			}
		
		}
        return(swatchPanel);
    }	

    /**
     * Initialises the GUI components.
     */
    public void InitGUI() {
        
        cmActionListener listener = new cmActionListener();

        Container m_container = getContentPane();
        m_container.setLayout(new FlowLayout());
        
        JPanel optionsPanel = new JPanel(new GridLayout(UI_ROW,UI_COL));
        m_container.add(optionsPanel);

        JLabel dropboxText = new JLabel("Select colour scheme: ");
        optionsPanel.add(dropboxText);

        String[] colourMaps = new String[MAPCOUNT];
        for (int i=0; i<MAPCOUNT; i++) {
            colourMaps[i] = GetMap(i).GetMapName();
        }
		
        m_mapList = new JComboBox(colourMaps);
		m_mapList.setEnabled(true);
        m_mapList.setPreferredSize(new Dimension(UI_WIDTH, UI_HEIGHT));
        m_mapList.addActionListener(listener);
        optionsPanel.add(m_mapList);

        JLabel blankLabel1 = new JLabel("");
        optionsPanel.add(blankLabel1);

        m_previewContainer = new JPanel();
        optionsPanel.add(m_previewContainer);

        m_previewPanel = DrawSwatches(m_mapList.getSelectedIndex());
        m_previewContainer.add(m_previewPanel);

        JPanel spacerPanel1 = new JPanel();
        spacerPanel1.setSize(new Dimension(CP_WIDTH, CP_HEIGHT));
        optionsPanel.add(spacerPanel1);

		
		
        JPanel spacerPanel2 = new JPanel();
        spacerPanel2.setSize(new Dimension(CP_WIDTH, CP_HEIGHT));
        optionsPanel.add(spacerPanel2);
		
		m_CustomButton = new JButton("Custom Colours");
		m_CustomButton.addActionListener(listener);
		spacerPanel2.add(m_CustomButton);
		
        JLabel blankLabel2 = new JLabel("");
        optionsPanel.add(blankLabel2);

		
		
        JPanel buttonPanel = new JPanel(new FlowLayout());
        optionsPanel.add(buttonPanel);
				
        m_saveButton = new JButton("OK");
        m_saveButton.addActionListener(listener);
        buttonPanel.add(m_saveButton);

        m_closeButton = new JButton("Cancel");
        m_closeButton.addActionListener(listener);
        buttonPanel.add(m_closeButton);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        if (m_cmFirstTimeOpen) { 
            setVisible(false);
            m_cmFirstTimeOpen = false;
        } else {
            setVisible(true);
        }
    }

    /**
     * Initialises the colour maps.
     */
    public void InitMaps() {
        Colour[] colours = new Colour[MAPCOUNT];

        for (int i=0; i<MAPCOUNT; i++) {
            switch (i) {
                case 0:	Colour black = new Colour("#000000");
			    		colours[i] = black;
			    		break;
                case 1: Colour darkgrey = new Colour("#222222");
			    		colours[i] = darkgrey;
                        break;
                case 2: Colour grey = new Colour("#555555");
			    		colours[i] = grey;
			    		break;
                case 3: Colour lightgrey = new Colour("#BBBBBB");
			    		colours[i] = lightgrey;
			    		break;
                case 4: Colour white = new Colour("#EEEEEE");
			    		colours[i] = white;        
			    		break;
            }	
        } 
        ColourMap greyscale = new ColourMap(colours, "Greyscale");
        SetMap(GREY, greyscale);
        

        for (int i=0; i<MAPCOUNT; i++) {
            switch (i) {
                case 0: Colour colour1 = new Colour("#FF0099");
                        colours[i] = colour1;
                        break;
                case 1: Colour colour2 = new Colour("#F3F315");
                        colours[i] = colour2;
                        break;
                case 2: Colour colour3 = new Colour("#83F52C");
                        colours[i] = colour3;
                        break;
                case 3: Colour colour4 = new Colour("#FF6600");
                        colours[i] = colour4;
                        break;
                case 4: Colour colour5 = new Colour("#6E0DD0");
                        colours[i] = colour5;        
                        break;
            }
        }
        ColourMap neon = new ColourMap(colours, "Neon");
        SetMap(NEON, neon);
        

        for (int i=0; i<MAPCOUNT; i++) {
            switch (i) {
                case 0: Colour colour1 = new Colour("#FFFF00");
                        colours[i] = colour1;
                        break;
                case 1: Colour colour2 = new Colour("#FFCC00");
                        colours[i] = colour2;
                        break;
                case 2: Colour colour3 = new Colour("#FF9900");
                        colours[i] = colour3;
                        break;
                case 3: Colour colour4 = new Colour("#FF6600");
                        colours[i] = colour4;
                        break;
                case 4: Colour colour5 = new Colour("#FF3300");
                        colours[i] = colour5;        
                        break;
            }
        }
        ColourMap warm = new ColourMap(colours, "Warm");
        SetMap(WARM, warm);
		

        for (int i=0; i<MAPCOUNT; i++) {
            switch (i) {
                case 0: Colour colour1 = new Colour("#33FFFF");
                        colours[i] = colour1;
                        break;
                case 1: Colour colour2 = new Colour("#3399FF");
                        colours[i] = colour2;
                        break;
                case 2: Colour colour3 = new Colour("#3333FF");
                        colours[i] = colour3;
                        break;
                case 3: Colour colour4 = new Colour("#9933FF");
                        colours[i] = colour4;
                        break;
                case 4: Colour colour5 = new Colour("#5C00B8");
                        colours[i] = colour5;        
                        break;
            }
        }
        ColourMap cold = new ColourMap(colours, "Cold");
        SetMap(COLD, cold);
		

		for (int i=0; i<MAPCOUNT; i++) {
            switch (i) {
                case 0: Colour colour1 = new Colour("#00E000");
                        colours[i] = colour1;
                        break;
                case 1: Colour colour2 = new Colour("#64D88B");
                        colours[i] = colour2;
                        break;
                case 2: Colour colour3 = new Colour("#99CC66");
                        colours[i] = colour3;
                        break;
                case 3: Colour colour4 = new Colour("#66FF99");
                        colours[i] = colour4;
                        break;
                case 4: Colour colour5 = new Colour("#CAFF7A");
                        colours[i] = colour5;        
                        break;
            }
        }
        ColourMap meadow = new ColourMap(colours, "Meadow");
        SetMap(MEADOW, meadow);
		

        SetActiveMap(GREY);
        SetRenderers();
    }

	/**
     * Saves the choice of active map and closes the UI window.
     */
    public void SaveAndClose() {
        
		if (m_mapList.isEnabled()) {
			SetActiveMap(m_mapList.getSelectedIndex());
		} else {
			SetActiveMap(CUSTOM);
		}
		
        GroupJVTApplication.dataVisualizer.RedrawChartColour();
        m_mapList.setEnabled(true);
		setVisible(false);
    }
	
    /**
     * Mutator method for active map.
     * @param mapIndex - Index for new active map.
     * @return Boolean representing operation success.
     */
    public boolean SetActiveMap(int mapIndex) {
        m_activeMap = mapIndex;
        SetRenderers();
        return(true);
    }
	
    /**
     * Mutator method for maps in the array.
     * @param mapIndex - Index of the array.
     * @param map - New colour map.
     * @return Boolean representing operation success.
     */
    public boolean SetMap(int mapIndex, ColourMap map) {
        m_colourMaps[mapIndex] = map;
        SetRenderers();
        return(true);
    }
	
    /**
     * Initialises/re-initialises renderer objects using active map.
     * @return Boolean representing operation success.
     */
    public boolean SetRenderers() {
        m_barRenderer = new CustomBarRenderer(GetActiveMap());
		m_lineRenderer = new CustomLineRenderer(GetActiveMap());
        m_pieRenderer = new CustomPieRenderer(GetActiveMap());
		m_ringRenderer = new CustomRingRenderer(GetActiveMap());
		m_areaRenderer = new CustomAreaRenderer(GetActiveMap());
		m_bubbleRenderer = new CustomBubbleRenderer(GetActiveMap());
		m_scatterRenderer = new CustomScatterRenderer(GetActiveMap());
        return(true);
    } 

    /**
     * Inner class acting as an action listener for the GUI components.
     * @author Matthew Adshead
     */
    private class cmActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            /* When combo box changed, redraw preview panel. */
            if (e.getSource()==m_mapList) {
                
                m_previewContainer.removeAll();
                m_previewPanel = DrawSwatches(m_mapList.getSelectedIndex());
                m_previewContainer.add(m_previewPanel);
                m_previewPanel.repaint();
                m_previewPanel.revalidate();	
            }
            
            if (e.getSource()==m_closeButton) {
                m_mapList.setEnabled(true);
				CloseFrame();
            }
        
            if (e.getSource()==m_saveButton) {
                GroupJVTApplication.dataVisualizer.SelectChartTab(); //line added
                SaveAndClose();
            }
			
			if (e.getSource() == m_CustomButton) {
			
				ColourSelection select = new ColourSelection();
				m_custom = select.InitMap();
				if (m_custom == null) {
					
				} else {
					m_mapList.setEnabled(false);
					m_previewContainer.removeAll();
					m_previewPanel = DrawSwatches(CUSTOM);
					m_previewContainer.add(m_previewPanel);
					m_previewPanel.repaint();
					m_previewPanel.revalidate();
				}
			
			}
        }
    }
            	
    /** Constant representing the total number of colour maps. */
    private final int MAPCOUNT = 5;

    /** Array of colour map objects. */
    private ColourMap[] m_colourMaps = new ColourMap[MAPCOUNT];

    /** Index of current active map. */
    private int m_activeMap;
	
	/* Rouge value representing custom colour map */
	private final int CUSTOM = MAPCOUNT + 1;
	
	/* ColourMap for custom colours */
	private ColourMap m_custom;

    /** Custom renderer class for bar charts. */
    private CustomBarRenderer m_barRenderer;

    /** Custom renderer class for pie charts. */
    private CustomPieRenderer m_pieRenderer;
	
	/** Custom renderer class for line charts */
	private CustomLineRenderer m_lineRenderer;
	
	/** Custom renderer class for ring charts */
	private CustomRingRenderer m_ringRenderer;
	
	/** Custom renderer class for bubble charts */
	private CustomBubbleRenderer m_bubbleRenderer;
	
	/** Custom renderer class for area charts */
	private CustomAreaRenderer m_areaRenderer;
	
	/** Custom renderer class for scatter charts */
	private CustomScatterRenderer m_scatterRenderer;

    /**
     * UI Elements which needed to be accessed by the ActionListener class.
     * A combo box containing the names of the colour maps.
     */
    private JComboBox m_mapList;

    /**
     * UI Elements which needed to be accessed by the ActionListener class.
     * Panel containing samples of the colours in the colour map.
     */
    private JPanel m_previewPanel;

    /**
     * UI Elements which needed to be accessed by the ActionListener class.
     * Panel which holds the preview panel, for the purpose of redrawing it.
     */
    private JPanel m_previewContainer;

    /**
     * UI Elements which needed to be accessed by the ActionListener class.
     * Save button, when clicked saves changes and closes GUI.
     */
    private JButton m_saveButton;

    /**
     * UI Elements which needed to be accessed by the ActionListener class.
     * Close button, when clicked closes GUI.
     */
    private JButton m_closeButton;
	
	/**
     * UI Elements which needed to be accessed by the ActionListener class.
     * Custom button, when clicked lets user create custom colours.
     */
	private JButton m_CustomButton;

    /** flag checking if the manager is opening first time */
    private Boolean m_cmFirstTimeOpen = true;
    private final int GREY = 0;
	private final int NEON = 1;
	private final int WARM = 2;
	private final int COLD = 3;
	private final int MEADOW = 4;
    private int CM_WIDTH = 350;
    private int CM_HEIGHT = 250;
    private int UI_WIDTH = 150;
    private int UI_HEIGHT = 10;
    private int CP_WIDTH = 10;
    private int CP_HEIGHT = 400;
    private int S_WIDTH = 20;
    private int S_HEIGHT = 20;
    private int UI_ROW = 4;
    private int UI_COL = 2;
   
   
   /**
	*	Tester class testing all the methods of ColourManager
	*/
	public static void main(String[] args) {
		ColourManager test = new ColourManager();
		int grey = 0;
		int neon = 1;
		int warm = 2;
		int cold = 3;
		int meadow = 4;
		
		test.SetActiveMap(grey);
		System.out.println("ColourManager:: GetActiveMap()");
		if (test.GetActiveMap() == test.GetMap(grey)) {
			System.out.println("ColourManager:: GetActiveMap() - Test Passed");
		} else {
			System.out.println("ColourManager:: GetActiveMap() - Test Failed");
		}
		
		test.setVisible(true);
	}
   
} /* end class ColourManager */ 
