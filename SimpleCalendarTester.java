import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
/**
 * 
 * @author Elias Faris
 * 
 * This class is the driver for the calendar GUI.
 * 
 * Note: When starting the application, must have some
 * empty text file, then once the user clicks quit, the
 * events will get saved within events.txt. 
 *
 */
public class SimpleCalendarTester{
	

	public static void main(String args[]) {
		
		Model cal = new Model();
		cal.checkFileExistence(args);
		
		new View(); //start the GUI application
	}
	
	
       }