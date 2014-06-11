import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import java.awt.Color;
import org.jfree.chart.plot.CategoryPlot;

/**
 * @author 	K. Zheng
 * @file 	CustomAreaRenderer.java
 * @date	30.03.2013
 * @version     1.0
 * @see     http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/
 *				renderer/AbstractRenderer.html
 * 
 *  \brief a custom area chart renderer class. Used to 
 *	change colours and display of a area chart.
 */
public class CustomAreaRenderer extends LineAndShapeRenderer {
	
	/**
     * Initialises the CustomAreaRenderer.
     * @param The colour map for rendering the chart.
     */
	public CustomAreaRenderer(ColourMap map) {
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
     * Method for determining the colour for the area
     * to be rendered with and applying those colours
     * to the chart object.
     * @param plot - CategoryPlot object from the area chart.
     * @param series - the number representing which area to change
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
	
	
	/**
	*	Test method that tests if the renderers can 
	*`	accept a ColourMap object
	*/
	public static void main(String args[]){
	   
	   //Creating a colour scheme to test the renderer with
		int colourSize = 5;
		Colour[] colours = new Colour[colourSize];
        for (int i=0; i<colourSize; i++) {
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
        
		CustomAreaRenderer testCustomAreaRenderer = 
				new CustomAreaRenderer(greyscale);
		
	    //Test GetMap method
	    System.out.println("CustomAreaRenderer:: GetMap()");
		testCustomAreaRenderer.GetMap();
	    System.out.println("CustomAreaRenderer:: GetMap() - Test Passed");
	    //Test SetMap method
	    System.out.println("CustomAreaRenderer:: SetMap()");
		testCustomAreaRenderer.SetMap(greyscale);
	    System.out.println("CustomAreaRenderer:: SetMap() - Test Passed");
	}
	
}