import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import java.awt.Color;
import org.jfree.chart.plot.CategoryPlot;

/**
 * @author 	D. Sherratt
 * @file 	CustomLineRenderer.java
 * @date	29.03.2013
 * @version     1.0
 * @see     http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/
 *				renderer/category/LineAndShapeRenderer.html    
 *			http://www.jfree.org/phpBB2/viewtopic.php?f=3&t=27967
 * 
 * \brief a custom line chart renderer class. Used to 
 *	change colours and display of a line chart.
 */
public class CustomLineRenderer extends LineAndShapeRenderer {
	
	/**
     * Initialises the CustomPieRenderer.
     * @param The colour map for rendering the chart.
     */
	public CustomLineRenderer(ColourMap map) {
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
		
		//Test class
		CustomLineRenderer testCustomLineRenderer=
		                                new CustomLineRenderer(greyscale);
		//Test GetMap class
		System.out.println("CustomLineRenderer:: GetMap()");
		testCustomLineRenderer.GetMap();
		System.out.println("CustomLineRenderer:: GetMap() - Test Passed");
		//Test SetMap class
		System.out.println("CustomLineRenderer:: SetMap()");
		testCustomLineRenderer.SetMap(greyscale);
		System.out.println("CustomLineRenderer:: SetMap() - Test Passed");
	}
} /* end class CustomLineRenderer */