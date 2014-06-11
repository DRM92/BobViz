import java.awt.Color;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;

/**
 * @file 	CustomPieRenderer.java
 * @author 	Matthew Adshead, K. Zheng
 * @date	24.02.13
 * @version     1.1
 * @see         http://www.jfree.org/jfreechart/api/javadoc/index.html and
 *              http://docs.oracle.com/javase/6/docs/api/
 * 
 * \brief a custom pie chart renderer class. Used to 
 *	change colours and display of a pie chart.
 * 
 * This class is a custom renderer class which is used to
 * render pie chart objects using colours from the stored
 * colour map object.
 */

public class CustomPieRenderer {
	
    /**
     * Initialises the CustomPieRenderer.
     * @param The colour map for rendering the chart.
     */
    public CustomPieRenderer(ColourMap map) {
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
     * Method for determining the colour for pie segments
     * to be rendered with and applying those colours
     * to the chart object.
     * @param plot - PiePlot object from the pie chart.
     * @param dataset - PieDataset object from the pie chart.
     */
    public void SetColor(PiePlot plot, PieDataset dataset) {
        
    	int colours = m_map.GetNumber();
    	for (int i = 0; i < dataset.getItemCount(); i++) { 
            int colourIndex = i % colours; 
            Color colour = m_map.GetColour(colourIndex).GetColour();
            plot.setSectionPaint(dataset.getKey(i), colour);
        } 
    }

    /** ColourMap object used to define colours in the renderer. */
    private ColourMap m_map;
	
	/**
	*	Unit test to test all the methods in the CustomLineRenderer class.
	*/
	public static void main(String args[]){
	   
	   //Creating a colour scheme to test the renderer with
		Colour[] colours = new Colour[5];
		final int MAP_SIZE = 5;
        
        for (int i=0; i<MAP_SIZE; i++) {
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
        
		CustomPieRenderer testCustomPieRenderer = 
				new CustomPieRenderer(greyscale);
		
	    //Test GetMap method
	    testCustomPieRenderer.GetMap();
	    System.out.println("CustomPieRenderer:: GetMap() - Test Passed");
	    //Test SetMap method
	    testCustomPieRenderer.SetMap(greyscale);
	    System.out.println("CustomPieRenderer:: SetMap() - Test Passed");
	}
	
} /* end class CustomPieRenderer */
