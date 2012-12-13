import javax.swing.*;
import java.io.*;
import java.awt.*;

/**
 * <b>PhoneList class</b>
 * <p>
 * Creates a GUI that allows a user to load a PhoneContact database from a document
 * and perform sorting and search functions on it.
 * @author James Wen - jrw2175
 */
public class PhoneList {   
	/**
	 * Sets up the GUI and initiates the program.
	 * @exception IOException
	 * @exception NullPointerException
	 * @exception IllegalStateException
	 * @exception IllegalArgumentException
	 */ 
	public static void main (String[] args) throws IOException{      
		try {
		    JFrame frame = new JFrame ("Phone");      
		    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		    frame.getContentPane().add(new PhonePanel());  
	        frame.pack();
	        frame.setVisible(true);
		}
		catch(IOException e){//Bad IO Errors from user
			System.out.println("Please be careful when dealing with files.");
			System.exit(0);
		}
		catch(NullPointerException e){//No file Chosen
			System.out.println("Please select a file next time.");
			System.out.println("The program will now exit.");
			System.exit(0);
		}
		catch(IllegalStateException e){//Divide by 0 Errors
			System.out.println("Please be wary of dividing by 0 errors.");
			System.out.println("The program will now exit.");
			System.exit(0);
		}
		catch(IllegalArgumentException e){//Trying to perform search without choosing a method
			System.out.println("Please do not attempt to perform searches without" +
							   "choosing a search method.");
			System.out.println("The program will now exit.");
			System.exit(0);
		}
    }
}//End of PhoneList Class