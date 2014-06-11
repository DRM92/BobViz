/**
 * @file 	 CustomColourMap.java
 * @author 	 Dan Morgan, Josh Reynolds
 * @date	 19.2.13
 * @version      1.5
 * @see          http://www.jfree.org/jfreechart/api/javadoc/index.html and
 *               http://docs.oracle.com/javase/6/docs/api/
 * 
 * \brief Object representing a colour scheme.
 * 
 * This class represents a colour scheme within the system.
 * It consists of an arbitrary number of Colour objects stored in an array,
 * and a String representing the colour scheme name.
 */

public class CustomColourMap extends ColourMap {
	
	/**
	*	Constructor initializing the CustomColourMap
	*
	* @param an array of Colours to go into the colour map
	* @param a String that is the name of the colour map
	*/
	public CustomColourMap(Colour[] colour, String name){
		super(colour, name);
		this.SetMapSize(colour.length);
		m_colourMap = new Colour[this.GetMapSize()];
		this.InitMap();
		
		for (int i = 0; i < this.GetMapSize(); i++) {
			m_colourMap[i] = colour[i];
		}		
	}
	
	/**
     * Access method for items in Colour array.
     * @param i - Array index.
     * @return Colour object at index i in the array.
     */	 
	public Colour GetColour(int i) {
        if (i < m_mapSize) {
			return m_colourMap[i];
		} else {
			i = i - m_mapSize;
			return GetColour(i);
		}
    }
	
	/**
    * Gets the capacity of how many colours the map can store
	* @return Int that is how many different colours the map can store
    */
	public int GetMapSize() {
		return m_mapSize;
	}
	 
	 /**
     * Finds the number of Colour objects in the array.
     * @return Int representing the number of Colour objects in the array.
     */
    public int GetNumber() {
        int num = 0;
        for (int i=0; i<m_colourMap.length; i++) {
            if (!(m_colourMap[i].equals(null))) {
                num++; 
            }
        }
        return(num);
    }
	
	/**
     * Initialises the object Colour array such that all fields are null.
    */
    public void InitMap() {
        for (int i=0; i<m_mapSize; i++) {
            SetColour(i, null);
        }
    }
	
	/**
     * Mutator method for items in the Colour array.
     * @param i - Array index to set.
     * @param colour - New Colour object.
     * @return Boolean representing operation success.
     */
    public boolean SetColour(int i, Colour colour) {
        if (i < m_mapSize) {
			m_colourMap[i] = colour;
			return(true);
		} else {
			return false;
		}
    }
	
	/**
	* Method that allows user to set the size of the map
	*
	* \param int of the new size of the map
	*
	* \return boolean True if the map size has been set
	*/	
    public boolean SetMapSize(int size) {
		m_mapSize = size; 
		return true;
	}	
	private int m_mapSize;
	private Colour[] m_colourMap;
	
		/**
	* Tests that test every method in the Custom Colour Map
	*/
	public static void main(String[] args) {
		//Setting up some objects to test.
		Colour[] four = new Colour[4];
		Colour[] five = new Colour[5];
		Colour[] six = new Colour[6];
		
		Colour white = new Colour("#FFFFFF");
		four[0] = white;
		five[0] = white;
		six[0] = white;
		Colour black = new Colour("#000000");
		four[1] = black;
		five[1] = black;
		six[1] = black;
		Colour red = new Colour("#FF0000");
		four[2] = red;
		five[2] = red;
		six[2] = red;
		Colour green = new Colour("#00FF00");
		four[3] = green;
		five[3] = green;
		six[3] = green;
		Colour blue = new Colour("#0000FF");
		five[4] = blue;
		six[4] = blue;
		Colour bluegreen = new Colour("#00FFFF");
		six[5] = bluegreen;
		
		//Test that <5 objects can be added.
		CustomColourMap fourcolours = new CustomColourMap(four, "Four");
		System.out.println("Expected: No output.");
		System.out.println();
		
		//Test that =5 objects can be added.
		CustomColourMap fivecolours = new CustomColourMap(five, "Five");
		System.out.println("Expected: No output.");
		System.out.println();
		
		//Test that if >5 objects are added, it is acceptable.
		CustomColourMap sixcolours = new CustomColourMap(six, "Six");
		System.out.println("Expected: No output.");
		System.out.println();
		
		//Test the getColour method.
		for (int i=0; i<fivecolours.getMapSize(); i++) {
			System.out.print(fivecolours.GetColour(i).GetHex() + " ");
		}
		System.out.println();
		System.out.println("Expected: #FFFFFF #000000 #FF0000 #00FF00 #0000FF");
		System.out.println();
		
		for (int i=0; i<sixcolours.GetMapSize(); i++) {
			System.out.print(sixcolours.GetColour(i).GetHex() + " ");
		}
		System.out.println();
		System.out.println(
			"Expected: #FFFFFF #000000 #FF0000 #00FF00 #0000FF #00FFFF");
		System.out.println();
		
		//Test the setColour method.
		for (int i=0; i<fivecolours.getMapSize(); i++) {
			fivecolours.SetColour(i, new Colour("#FFFFFF"));
		}
		for (int i=0; i<fivecolours.getMapSize(); i++) {
			System.out.print(fivecolours.GetColour(i).GetHex() + " ");
		}
		System.out.println();
		System.out.println("Expected: #FFFFFF #FFFFFF #FFFFFF #FFFFFF #FFFFFF ");
		System.out.println();
		
		//Test the getMapSize method.
		System.out.println(fivecolours.getMapSize());
		System.out.println("Expected: Five");
		System.out.println();
		
		System.out.println(sixcolours.GetMapSize());
		System.out.println("Expected: Six");
		System.out.println();
		
		//Test the getNumber method.
		System.out.println(fivecolours.GetNumber());
		System.out.println("Expected: Five");
		System.out.println();
		
		System.out.println(sixcolours.GetNumber());
		System.out.println("Expected: Six");
		System.out.println();
	
	
	}
}