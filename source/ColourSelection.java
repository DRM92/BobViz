/**
 * @file 	 ColourSelection.java
 * @author 	 Dan Morgan, Josh Reynolds
 * @date	 19.2.13
 * @version      1.5
 * @see          http://www.jfree.org/jfreechart/api/javadoc/index.html and
 *               http://docs.oracle.com/javase/6/docs/api/
 * 
 * \brief This class is used to select a list of colours which are then put
 * 	into a CustomColourMap.
 */
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.Color; /* for object Color */
import javax.swing.JColorChooser; /* for the colour chooser */

public class ColourSelection{
	
	/**
	* Method that returns current colour value
	*
	* \return current colour value
	*/
	public Color GetColour() {
		return m_colour;
	}
	
	/**
	* Method that allows user to change current colour
	*
	* \return boolean True if a colour is chosen otherwise False
	*/
	public boolean SetColour() {
		m_colour = JColorChooser.showDialog(null, "Pick your colour", m_colour);
		return true;
	}
	
	/**
	* Method that converts RGB to HEX
	*
	* \param the red component of the colour
	* \param the green component of the colour
	* \param the blue component of the colour
	*
	* \return the hex string of the colour
	*/
	public String RGBtoHEX(int r, int g, int b) {
		final int MIN_LENGTH = 2;
		String red = Integer.toHexString(r);
		//String.format("%02", red);
		if (red.length() < MIN_LENGTH) {
			red = "0" + red;
		}
		String green = Integer.toHexString(g);
		//String.format("%02", green);
		if (green.length() < MIN_LENGTH) {
			green = "0" + green;
		}
		String blue = Integer.toHexString(b);
		//String.format("%02", blue);
		if (blue.length() < MIN_LENGTH) {
			blue = "0" + blue;
		}
		String output = "#" + red + blue + green;
		return output.toUpperCase();
	}
	
	/**
	*	Initialises the map that allows users to select colours
	*
	*	\return The CustomColourMap with all the selected colours stored in it
	*/
	public CustomColourMap InitMap() { 
		ArrayList<Colour> colorList = new ArrayList<Colour>();
		boolean continueAdding = true; /* true if user wants to add another 
											colour */
		final int YES = 0;
		while (continueAdding) {
			m_colour = null;
			SetColour();
				
			if (m_colour == null) {
				//Terminate here.
				return null;
			} else {
				int r = m_colour.getRed();
				int b = m_colour.getBlue();
				int g = m_colour.getGreen();
				String hex = RGBtoHEX(r, b, g);
				Colour newColour = new Colour(hex);
				colorList.add(newColour);	
			}
			
			int n = JOptionPane.showConfirmDialog(
				null,
				"Continue adding colours to colour scheme?",
				"Colour Question",
				JOptionPane.YES_NO_OPTION);
			/* checks whether user wants to continue adding colours */
			if (n == YES) {
			/* lets user continue adding */
				continueAdding = true;
			} else {
			/* stops user from adding any more */
				continueAdding = false;
			}			
			
		}
		
		m_colorList = new Colour[colorList.size()];
		for (int i = 0; i < colorList.size(); i++) {
			m_colorList[i] = colorList.get(i);
		}
		CustomColourMap custom = new CustomColourMap(m_colorList, "Custom");
		return custom;
	}
	
	Colour[] m_colorList;
	Color m_colour;
	
	public static void main(String[] args){
	
		final int TEST_DATA1 = 144;
		final int TEST_DATA2 = 231;
		final int TEST_DATA3 = 6;
		final int TEST_DATA4 = 0;
		ColourSelection select = new ColourSelection();
		System.out.println(select.RGBtoHEX(TEST_DATA1, TEST_DATA2, TEST_DATA3));
		System.out.println("Expected: #9006E7");
		System.out.println(select.RGBtoHEX(TEST_DATA4, TEST_DATA4, TEST_DATA4));
		System.out.println("Expected: #000000");
		
		select.InitMap();
	}
}
