import java.awt.Color;

/**
 * @file 	Colour.java
 * @author 	Matthew Adshead & K. Zheng
 * @date	15.02.13
 * @version     1.2.2
 * @see         http://www.jfree.org/jfreechart/api/javadoc/index.html and
 *              http://docs.oracle.com/javase/6/docs/api/
 * 
 * \brief Colour wrapper object.
 * 
 * This class represents a singular colour within the system.
 * It consists of a Hexadecimal string representing the colour
 * and a Java Color object, and can be constructed from either
 * one of those two components.
 */

public class Colour {

	/**
    * Initialise a colour object using a hex code string.
    * @param String representing the hex code of the colour.
    */
    public Colour(String hexCode) {
        SetHex(hexCode);
        SetColour(GenColor(hexCode));
    }
    
	/**
    * Initialise a colour object using a Java Color object.
    * @param Java Color object.
    */
    public Colour(Color colour) {
        SetHex(GenHexString(colour));
        SetColour(colour);
    }
	
	/**
    * Method for generating Color object from a given Hex code.
    * @param String representing Hex code to convert.
    * @return Color object corresponding to the Hex code.
    */	
    public Color GenColor(String hex) {
        return(Color.decode(hex));
    }

    /**
    * Method for generating Hex code from a given Color object.
    * @param Color object for converting to the Hex code.
    * @return String representing Hex code.
    */	
    public String GenHexString(Color colour) {
        
        /*
        * This assignment creates a String containing "#" and then the RGB
        * representation of the Color object, with a special mask applied
        * to resolve it to a hex colour code. (e.g. "#AAAAAA")
        */
        String hex = "#" + Integer.toHexString((colour.getRGB() & 0x00FFFFFF));

        return(hex);
    }

    /**
    * Access method for Color object.
    * @return Color object representing the colour.
    */	
    public Color GetColour() {
        return(m_colour);
    }
	
    /**
    * Access method for Hex String.
    * \return String representing the colour's Hex code.
    */
    public String GetHex() {
        return(m_hexCode);
    }
	
    /**
    * Mutator method for Hex String.
    * @param String representing new Hex code.
    * @return Boolean representing operation success.
    */
    public boolean SetHex(String hexCode) {
        if (ValidHex(hexCode)) {
            m_hexCode = hexCode;
            return(true);
        } else {
            return(false);
        }
    }
	
    /**
    * Mutator method for Color object.
    * @param String representing new Hex code.
    * @return Boolean representing operation success.
    */
    public boolean SetColour(Color colour) {
        m_colour = colour;
        return(true);
    }
	
    /**
     * Method for checking a string to see if it is a valid Hex colour code.
     * @param String representing Hex code.
     * @return Boolean - True if Hex valid, False if Hex invalid.
     */
    public boolean ValidHex(String hexCode) {
        try {
            Color test = Color.decode(hexCode);
        } catch (NumberFormatException e) {
            return(false);
        }
        return(true);
    }
        
    /**The hex code string representing the colour. */
    private String m_hexCode;
	
    /** The Color object, which will be used in the recolouring of charts. */
    private Color m_colour;
	
	/**
	*	Tester class testing all the methods of the colour class.
	*/
	public static void main(String[] args) {
		//Set up objects to test with.
		Colour hexColour = new Colour("#FFFFFF");
		Colour colorColour = new Colour(Color.white);
		
		//Test that constructor works the same whether using hex code or color object.
		System.out.println(hexColour.GetHex());
		System.out.println("Expected: #FFFFFF");
		System.out.println(colorColour.GetHex());
		System.out.println("Expected: #FFFFFF");
		System.out.println();
		System.out.println(hexColour.GetColour().toString());
		System.out.println("Expected: java.awt.Color[r=255,g=255,b=255]");
		System.out.println(colorColour.GetColour().toString());
		System.out.println("Expected: java.awt.Color[r=255,g=255,b=255]");
		System.out.println();
		
		//Test the setHex method.
		hexColour.SetHex("#000000");
		colorColour.SetHex("#000000");
		
		System.out.println(hexColour.GetHex());
		System.out.println("Expected: #000000");
		System.out.println(colorColour.GetHex());
		System.out.println("Expected: #000000");
		System.out.println();
		
		//Test that colour corresponds to new hex.
		System.out.println(hexColour.GetColour().toString());
		System.out.println("Expected: java.awt.Color[r=0,g=0,b=0]");
		System.out.println(colorColour.GetColour().toString());
		System.out.println("Expected: java.awt.Color[r=0,g=0,b=0]");
		System.out.println();
		
		//Test setColour method.
		hexColour.SetColour(Color.blue);
		colorColour.SetColour(Color.blue);
		
		System.out.println(hexColour.GetColour().toString());
		System.out.println("Expected: java.awt.Color[r=0,g=0,b=255]");
		System.out.println(colorColour.GetColour().toString());
		System.out.println("Expected: java.awt.Color[r=0,g=0,b=255]");
		System.out.println();
		
		//Test that hex code corresponds to new colour.
		System.out.println(hexColour.GetHex());
		System.out.println("Expected: #0000FF"); 
		System.out.println(colorColour.GetHex());
		System.out.println("Expected: #0000FF");
		System.out.println();
		
		//Test genColor method.
		System.out.println(hexColour.GenColor("#FF0000"));
		System.out.println("Expected: java.awt.Color[r=255,g=0,b=0]");
		System.out.println();
		
		//Test genHexString method.
		System.out.println(hexColour.GenHexString(Color.red));
		System.out.println("Expected: #FF0000");
		System.out.println();
		
		//Test validHex method on various possible cases.
		//Lower border test, hex with numbers.
		System.out.println(hexColour.ValidHex("#000000"));
		System.out.println("True");
		System.out.println();
		
		//Normal test, hex with numbers.
		System.out.println(hexColour.ValidHex("#555555"));
		System.out.println("True");
		System.out.println();
		
		//Upper border test, hex with numbers.
		System.out.println(hexColour.ValidHex("#999999"));
		System.out.println("True");
		System.out.println();
		
		//Lower border test, hex with letters.
		System.out.println(hexColour.ValidHex("#AAAAAA"));
		System.out.println("True");
		System.out.println();
		
		//Normal test, hex with letters.
		System.out.println(hexColour.ValidHex("#CCCCCC"));
		System.out.println("True");
		System.out.println();
		
		//Upper border test, hex with letters.
		System.out.println(hexColour.ValidHex("#FFFFFF"));
		System.out.println("True");
		System.out.println();
		
		//Invalid test, hex without #.
		System.out.println(hexColour.ValidHex("FFFFFF"));
		System.out.println("False");
		System.out.println();
		
		//Invalid test, hex replacing # with other char.
		System.out.println(hexColour.ValidHex("D555555"));
		System.out.println("False");
		System.out.println();
		
		//Invalid test, hex replacing # with other number.
		System.out.println(hexColour.ValidHex("0999999"));
		System.out.println("False");
		System.out.println();
		
		//Invalid test, completely different format string.
		System.out.println(hexColour.ValidHex("Matt Adshead"));
		System.out.println("False");
		System.out.println();
	}
    
} /* end class Colour */