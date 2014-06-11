/**
 * @file 	 ColourMap.java
 * @author 	 Matthew Adshead, Dan Morgan
 * @date	 19.2.13
 * @version      1.5
 * @see          http://www.jfree.org/jfreechart/api/javadoc/index.html and
 *               http://docs.oracle.com/javase/6/docs/api/
 * 
 * \brief Object representing a colour scheme.
 * 
 * This class represents a colour scheme within the system.
 * It consists of a number of Colour objects stored in an array,
 * and a String representing the colour scheme name.
 */

public class ColourMap {
		
	/**
	* Initialises a colour map.
	* @param colours - Array of colour objects.
	* @param name - Name of colour map.
	*/
    public ColourMap(Colour[] colours, String name) {
        InitMap(); //First initialises array to null.
        
        if (colours.length <= m_mapSize) {
            /* Then if array parameter is <= max number of maps */
            for (int i=0; i<colours.length; i++) {
                SetColour(i, colours[i]); //transfer colours to object array.
            }
        } else {
            /* If array parameter is > max number of maps */
            for (int i=0; i<m_mapSize; i++) {
                /* transfer until max number is reached and ignore excess. */
                SetColour(i, colours[i]);
            }
        }
        /* Finally, set the map name. */
        SetMapName(name);
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
     * Access method for the map name.
     * @return String representing map name.
     */
    public String GetMapName() {
        return(m_mapName);
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
    * Gets the capacity of how many colours the map can store
	* @return Int that is how many different colours the map can store
    */
	public int getMapSize() {
		return m_mapSize;
	}
	
    /**
     * Mutator method for items in the Colour array.
     * @param i - Array index to set.
     * @param colour - New Colour object.
     * @return Boolean representing operation success.
     */
    public boolean SetColour(int i, Colour colour) {
        m_colourMap[i] = colour;
        return(true);
    }
	
    /**
     * Mutator method for map name.
     * @param name - New name for map.
     * @return Boolean representing operation success.
     */
    public boolean SetMapName(String name) {
        m_mapName = name;
        return(true);
    }

    public boolean setMapSize() {
		m_mapSize = 5;
		return true;
	}

    /** Constant defining the size of a colour map. (i.e. How many colours it contains.) */
    private int m_mapSize = 5;

    /** The array containing the colour objects. */
    private Colour[] m_colourMap = new Colour[m_mapSize];

    /** String representing the colour map name. */
    private String m_mapName;
    
	
	/**
	*	Tester class testing all the methods of ColourMap
	*/
	public static void main(String[] args){
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
		ColourMap fourcolours = new ColourMap(four, "Four");
		System.out.println("Expected: No output.");
		System.out.println();
		
		//Test that =5 objects can be added.
		ColourMap fivecolours = new ColourMap(five, "Five");
		System.out.println("Expected: No output.");
		System.out.println();
		
		//Test that if >5 objects are added, error shows and first 5 are used.
		ColourMap sixcolours = new ColourMap(six, "Six");
		System.out.println("Expected: No output");
		System.out.println();
		
		//Test the getColour method.
		for (int i=0; i<fivecolours.getMapSize(); i++) {
			System.out.print(fivecolours.GetColour(i).GetHex() + " ");
		}
		System.out.println();
		System.out.println("Expected: #FFFFFF #000000 #FF0000 #00FF00 #0000FF");
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
		
		//Test the getMapName method.
		System.out.println(fivecolours.GetMapName());
		System.out.println("Expected: Five");
		System.out.println();
		
		//Test the setMapName method.
		fivecolours.SetMapName("ELEPHANT");
		
		System.out.println(fivecolours.GetMapName());
		System.out.println("Expected: ELEPHANT");
		System.out.println();
		
		//Test the getNumber method.
		System.out.println(fivecolours.GetNumber());
		System.out.println("Expected: 5");
		System.out.println();
		System.out.println(sixcolours.GetNumber());
		System.out.println("Expected: 5");
		System.out.println();		
	}
} /* end class ColourMap */