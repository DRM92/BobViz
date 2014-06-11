import java.awt.Paint;
import org.jfree.chart.renderer.xy.*;
import java.awt.Color;
import org.jfree.chart.plot.CategoryPlot;

/**
 * @file 	 CustomBarRenderer.java
 * @author 	 Matthew Adshead & X.Zhao
 * @date	 27.02.13
 * @version      1.1
 * @see          http://www.jfree.org/jfreechart/api/javadoc/index.html and
 *               http://docs.oracle.com/javase/6/docs/api/
 * 
 * \brief Custom bar chart renderer class used in changing colours of chart.
 * 
 * This class is a custom JFreeChart renderer class which is used to
 * render bar chart objects using colours from the stored
 * colour map object.
 */

public class CustomBarRenderer extends ClusteredXYBarRenderer {
    /**
     * Initialises the CustomBarRenderer.
     * @param The colour map for rendering the chart.
     */
    public CustomBarRenderer(ColourMap map) {
        SetMap(map);
    }
	
	/**
     * Access method for the renderer colour map.
     * @return The renderer colour map.
     */
    public ColourMap GetMap() {
        return(m_map);
    }
    
    /**
     * Mutator method for the renderer colour map.
     * @param map - New colour map for renderer.
     * @return Boolean representing operation success.
     */
    public boolean SetMap(ColourMap map) {
        m_map = map;
        return(true);
    }
	
	/**
     * Method for determining the colour for the line
     * to be rendered with and applying those colours
     * to the chart object.
     * @param plot - CategoryPlot object from the line chart.
     * @param series - the number representing which line to change
     */
	public void SetColor(CategoryPlot plot, int series) {
        
    	int colours = m_map.GetNumber();
    	for (int i = 0; i < series; i++) { 
            int colourIndex = i % colours; 
            Color colour = m_map.GetColour(colourIndex).GetColour();
            plot.getRenderer().setSeriesPaint(i, colour);
        } 
    }
	        
    /** ColourMap object used to define colours in the renderer. */
    private ColourMap m_map;
        public static void main(String args[]){
		int mapSize = 5;
		//Creating a colour scheme to test the renderer with
		Colour[] colours = new Colour[mapSize];
        
        for (int i=0; i<5; i++) {
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
		
		//Test class
		CustomBarRenderer testCustomBarRenderer=
		                                new CustomBarRenderer(greyscale);
		//Test GetMap class
		System.out.println("CustomBarRenderer:: GetMap()");
		testCustomBarRenderer.GetMap();
		System.out.println("CustomBarRenderer:: GetMap() - Test Passed");
		//Test SetMap class
		System.out.println("CustomBarRenderer:: SetMap()");
		testCustomBarRenderer.SetMap(greyscale);
		System.out.println("CustomBarRenderer:: SetMap() - Test Passed");
	}
} /* end class CustomBarRenderer */
