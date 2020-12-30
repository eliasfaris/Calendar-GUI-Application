import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * 
 * @author Elias Faris
 * 
 * This is the model for the calendar. The purpose of this class is to 
 * handle the back end of saving events into a TreeMap and update the 
 * calendar accordingly. This class talks directly to View class. 
 *
 * All these functions and data structures were taken from my assignment #2.
 */
public class Model {

	static TreeMap<LocalDate, ArrayList<Event>> events = new TreeMap<LocalDate, ArrayList<Event>>();

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
	
	/**
	 * Checks if a file exists from the terminal
	 * @param args - an array of args from command line
	 */
	public void checkFileExistence(String[] args) {
		// TODO Auto-generated method stub
		
		//check if text file exists
		if(args.length == 0) {
			
			File file = new File("events.txt");
			//file.createNewFile();
			System.out.println("File has been created.");
		}
		else {
			//read file here and input data from txt file into data structure
			System.out.println("File already exists, using data in file.");
			importTextFile(args[0]);
			
		}
		
	}


	
	/**
	 * imports events from text file into calendar 
	 * 
	 * @param txtFile - the text file name
	 */
	public void importTextFile(String txtFile) {
		String file = txtFile;
		// Read in file
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			// Read file line by line
			while ((line = br.readLine()) != null) {
				// First line contains name
				String name = line;
				line = br.readLine();
				String arr[] = line.split("\\s+");
				// If line starts with letters, indicates recurring event


				formatter = DateTimeFormatter.ofPattern("M/d/yy");
				LocalDate startDate, endDate;
				startDate = endDate = LocalDate.parse(arr[0], formatter);
				formatter = DateTimeFormatter.ofPattern("H:mm");
				// Add event to the calendar
				saveImports(new Event(name, startDate, endDate, LocalTime.parse(arr[1], formatter), 
						LocalTime.parse(arr[2], formatter), true));

			}
			// If file not found, throw exception
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error " + e.getMessage());
		}
	}

	/**
	 * Used within imporTtextFile function to check if time overlaps from importing 
	 * from text file.
	 * 
	 * @param e - event wanting to check overlap
	 */
	public void saveImports(Event e) {
		LocalDate start = e.getTime().getStartDate();
		// Check if the event overlaps with any other events
		if (!Overlap(start, e)) {
			events.putIfAbsent(start, new ArrayList<Event>());
			events.get(start).add(e);

		} else {
			System.out.println("Time overlaps. Could not add " + e.getName());

		}
	}




	/**
	 *  Creates an event
	 */
	public static void create() {
		LocalDate date;

		// TODO Auto-generated method stub 


		try {


			formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
			date = LocalDate.parse(Control.date1, formatter);
			formatter = DateTimeFormatter.ofPattern("H:mm");

			addEvent(new Event(Control.nameOfEvent, date, date, LocalTime.parse(Control.startTime, formatter),
					LocalTime.parse(Control.endTime, formatter), true));
			// Catch any DateTimeFormatter exceptions
		} catch (Exception e) {
			System.out.println(e.getMessage() + " - Invalid date");
		}

	}


	/**
	 * Adds a one time event to the calendar
	 *
	 * @param e - Event needed to be added
	 */
	public static void addEvent(Event e) {
		LocalDate start = e.getTime().getStartDate();
		// Check if the event overlaps with any other events
		if (!Overlap(start, e)) {
			Model.events.putIfAbsent(start, new ArrayList<Event>());
			Model.events.get(start).add(e);
			System.out.println("Event " + e.getName() + " has been added!");
		} else {
			System.out.println("Time overlaps with another event. Could not add " + e.getName());

		}
	}

	/**
	 * Checks if event overlaps 
	 *
	 * @param date - date want to add event
	 * @param eventCheck - Event object want to add
	 * @return boolean 
	 */
	public static boolean Overlap(LocalDate date, Event eventCheck) {
		ArrayList<Event> dayEvents = Model.events.get(date);
		if (dayEvents != null) { // There are events on that day
			for (Event event : dayEvents) {
				// Call event doesOverlap method
				if (event.doesOverLap(eventCheck) == true) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * export events from calendar to output.txt file
	 * 
	 *
	 */
	public static void saveToTextFile() {
		// Creating a File object that represents the disk file.
		PrintStream printer = null;
		try {
			printer = new PrintStream(new File("events.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Error " + e.getMessage());
		}

		// Assign printer to output stream
		System.setOut(printer);
		eventsList();
	}


	/**
	 * Prints all events in the calendar
	 * 
	 */
	public static void eventsList() {

		DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("M/dd/yy");
		DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("H:mm");
		
		
		
		
		// For every entry in the TreeMap
		for (Entry<LocalDate, ArrayList<Event>> map : events.entrySet()) {
			// Sort the Events ArrayList
			Collections.sort(map.getValue());

			// Print event data for all in the Events ArrayList
			for (Event e : map.getValue()) {
				System.out.println(e.getName());
				System.out.print(dateformatter.format(map.getKey()) + " ");
				System.out.println(timeformatter.format(e.getTime().getStartTime()) +
						" " + timeformatter.format(e.getTime().getEndTime()));
				

			}


		}

	}
	
	

}
