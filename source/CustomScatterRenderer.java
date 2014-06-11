import java.awt.Paint;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import java.awt.Color;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import java.awt.Shape;
import org.jfree.util.ShapeUtilities;


/**
 * @file 	 CustomScatterRenderer.java
 * @author 	 D. Sherratt, X. Zhao, K. Zheng
 * @date	 01.04.13
 * @version      1.1
 * @see          http://www.jfree.org/jfreechart/api/javadoc/index.html and
 *               http://docs.oracle.com/javase/6/docs/api/
 * 
 * \brief a custom scatter chart renderer class. Used to 
 *	change colours and display of a scatter chart.
 * 
 * This class is a custom JFreeChart renderer class which is used to
 * render scatter chart objects using colours from the stored
 * colour map object.
 */
 
 public class CustomScatterRenderer extends XYShapeRenderer {

	/**
     * Initialises the CustomscatterRenderer.
     * @param The colour map for rendering the chart.
     */
	public CustomScatterRenderer(ColourMap map) {
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
     * Overridden method from superclass, called by charts when
     * determining the colour for bars to be rendered with.
     * @param row - Index of series.
     * @param column - Index of series item.
     * @return Paint - Color object for rendering bar.
     */
    @Override
    public Paint getItemPaint(final int row, final int column) {
        int colours = m_map.GetNumber();
        int colourIndex = row % colours;
        Colour itemColour = m_map.GetColour(colourIndex);
        return(itemColour.GetColour());
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
        
		CustomScatterRenderer testRenderer = 
				new CustomScatterRenderer(greyscale);
		
	    //Test GetMap method
	    System.out.println("CustomScatterRenderer:: GetMap()");
		testRenderer.GetMap();
	    System.out.println("CustomScatterRenderer:: GetMap() - Test Passed");
	    //Test SetMap method
	    System.out.println("CustomScatterRenderer:: SetMap()");
		testRenderer.SetMap(greyscale);
	    System.out.println("CustomScatterRenderer:: SetMap() - Test Passed");
	}
 }