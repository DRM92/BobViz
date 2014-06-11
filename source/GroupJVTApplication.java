import javax.swing.JFrame;/* for JFrame */

/**
 * @file    GroupJVTApplication.java
 * @author  X. Zhao
 * @date    27.02.13
 * @see     BigJava 4th edition, p737
 * 
 * \brief 	GroupJVTApplication class that executes the application
 * 
 * This class is the starting point of the program. 
 * It creates an instance of the UI object DataVisualizerGUI and displays it.
 */

public class GroupJVTApplication {  
	
   public static DataVisualizerGUI dataVisualizer = new DataVisualizerGUI();
    
    /** Execute application. */
    public static void main(String[] args) {  
        dataVisualizer = new DataVisualizerGUI();
        dataVisualizer.setLocationRelativeTo(null);
        dataVisualizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dataVisualizer.setTitle("Data Visualizer");
        dataVisualizer.setVisible(true);
    } /* end main */
} /* end Class GroupFGMApplication */