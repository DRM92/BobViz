import java.awt.Color;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBubbleRenderer;
import java.awt.Paint;

/**
 * @author  D. Sherratt
 * @file 	CustomBubbleRenderer.java
 * @date	30.03.2013
 * @version     1.0
 * @see     http://www.jfree.org/jfreechart/api/javadoc/org/jfree/
 *				chart/renderer/xy/XYBubbleRenderer.html
 * 
 * \brief a custom bubble chart renderer class. Used to 
 *	change colours and display of a bubble chart.
 */
public class CustomBubbleRenderer extends XYBubbleRenderer {
	
	/**
     * Access method for the renderer colour map.
     * @return The renderer colour map.
     */
    public ColourMap GetMap() {
        return(m_map);
    }
	
	/**
     * Initialises the CustomBubbleRenderer.
     * @param The colour map for rendering the chart.
     */
    public CustomBubbleRenderer(ColourMap map) {
        SetMap(map);
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
        
		CustomBubbleRenderer testRenderer = 
				new CustomBubbleRenderer(greyscale);
		
	    //Test GetMap method
	    System.out.println("CustomBubbleRenderer:: GetMap()");
		testRenderer.GetMap();
	    System.out.println("CustomBubbleRenderer:: GetMap() - Test Passed");
	    //Test SetMap method
	    System.out.println("CustomBubbleRenderer:: SetMap()");
		testRenderer.SetMap(greyscale);
	    System.out.println("CustomBubbleRenderer:: SetMap() - Test Passed");
	}
	
}