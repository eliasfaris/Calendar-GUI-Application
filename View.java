import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
/**
 * 
 * @author Elias Faris
 * 
 * This is the view class which portrays all GUI interfaces.
 * 
 * There are a few functions towards the bottom that have been taken form my assignment #2.
 *
 */
public class View extends JFrame {
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE MM/dd");
	static LocalDate now = LocalDate.now();
	
	static JLabel monthHeader, dayLabel;
	static int realYear, realMonth, realDay, currentDay, currentYear, currentMonth, nextDay;
	static DefaultTableModel tableModelCal; //Table model
	static JTable table;
	static ArrayList<JLabel> allEventsOnDay = new ArrayList<JLabel>();
	static JPanel panel;

	
	/**
	 * This is the constructor of the view class.
	 */
	public View() {

		JButton prev, next, create, quit;
		JFrame frame;
		JScrollPane scrollCal; 


		frame = new JFrame ("Calendar"); //Create frame
		frame.setSize(700, 425); //Set size to 400x400 pixels
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked

		//Create controls
		monthHeader = new JLabel();
		dayLabel = new JLabel();
		quit = new JButton("Quit");
		create = new JButton("Create");
		prev = new JButton ("<");
		next = new JButton (">");
		tableModelCal = new DefaultTableModel(){
			public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};

			table = new JTable(tableModelCal);
			scrollCal = new JScrollPane(table);
			panel = new JPanel(null);


			Control ctrl = new Control(prev, next, create, quit);



			//add panel to frame
			frame.add(panel);
			panel.add(monthHeader);
			panel.add(dayLabel);
			panel.add(prev);
			panel.add(next);
			panel.add(create);
			panel.add(quit);
			panel.add(scrollCal);
			
			
			dayLabel.setText(formatter.format(now));
			
			
			printDayCal();
			
		
			


			//Set bounds
			panel.setBounds(0, 0, 320, 335);
			dayLabel.setBounds(500, 100, 180, 25);
			prev.setBackground(Color.LIGHT_GRAY);
			next.setBackground(Color.LIGHT_GRAY);
			create.setBackground(Color.red);
			quit.setBackground(Color.white);
			create.setBounds(10, 70, 90, 25);
			create.setForeground(Color.white);
			prev.setBounds(210, 25, 50, 25);
			next.setBounds(260, 25, 50, 25);
			quit.setBounds(575, 25, 80, 25);
			scrollCal.setBounds(10, 125, 300, 250);

			frame.setVisible(true);

			//Get real month/year
			GregorianCalendar cal = new GregorianCalendar(); //Create calendar
			realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
			realMonth = cal.get(GregorianCalendar.MONTH); //Get month
			realYear = cal.get(GregorianCalendar.YEAR); //Get year
			currentMonth = realMonth; //Match month and year
			currentYear = realYear;
			currentDay = realDay;
			nextDay = realDay;




		
			String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; 
			for (int i=0; i<7; i++){
				tableModelCal.addColumn(headers[i]);
			}


			table.addMouseListener(new MouseAdapter() {
				

				@Override
				public void mousePressed(MouseEvent mouseEvent) {
					
					now = now.with(TemporalAdjusters.firstDayOfMonth());
					
		
					
					int row = table.rowAtPoint(mouseEvent.getPoint());
					int col = table.columnAtPoint(mouseEvent.getPoint());
					
					

					Object check = table.getValueAt(row, col); //check if pressed on cell with no day
					
					
					
					if (check != null) {

						int update = (int) check;
						shiftDay(update - 1);
						nextDay = update;
						printDayCal();
						refreshCalendar(nextDay, currentMonth, currentYear);
					}

				}
			});


			//Set rows and columns on table
			table.setRowHeight(38);
			tableModelCal.setColumnCount(7);
			tableModelCal.setRowCount(6);

			//Refresh calendar
			
			refreshCalendar(realDay, realMonth, realYear);


	}


	/**
	 * 
	 * This function refreshes the calendar by setting the proper days in the correct cell 
	 * in a monthly view format. It calls the cellCalendar class to re-render the calendar
	 * accordingly.
	 * 
	 * @param day - the current day
	 * @param month - the current month
	 * @param year - the current year
	 */
	public static void refreshCalendar(int day, int month, int year){
		//Variables
		String[] months =  {"January", "February", "March", "April", 
				"May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som; //Number Of Days, Start Of Month


		monthHeader.setText(months[month] + " " + year); //Refresh the month label (at the top)

		monthHeader.setBounds(10, 100, 180, 25); //Re-align label with calendar


		//Clear table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){

				tableModelCal.setValueAt(null, i, j);


			}
		}

		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);

		//Draw calendar
		for (int i=1; i<=nod; i++){
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			tableModelCal.setValueAt(i, row, column);
		}

		//Apply renderers
		table.setDefaultRenderer(table.getColumnClass(0), 
				new cellCalendar(realYear, realMonth, realDay, currentDay, currentYear, currentMonth, nextDay));
	}
	

	/**
	 * Advances Calendar by days
	 *
	 * @param numberOfDays - number of days want to move Calendar by
	 * 
	 */
	public static void shiftDay(int numberOfDays) {
		now = now.plusDays(numberOfDays);

	}



	/**
	 * Prints events on day
	 *
	 */
	public static void printDayCal() {
		JLabel oneEvent;
		dayLabel.setText(formatter.format(now));

		for(JLabel i : allEventsOnDay) {
			View.panel.remove(i);
			View.panel.revalidate();
			View.panel.repaint();
		}
		
		// events on day
		ArrayList<Event> eventsOnDay = Model.events.get(now);
		if (eventsOnDay != null) {
			allEventsOnDay.clear();
			Collections.sort(eventsOnDay);
			int y_coor = 125;
			for (Event event : eventsOnDay) {		
				oneEvent = new JLabel(); 
				oneEvent.setText(event.printEvent());
				oneEvent.setBounds(375, y_coor, 300, 25);
				allEventsOnDay.add(oneEvent);
				y_coor += 15;

			}

			for(JLabel j : allEventsOnDay) {
				View.panel.add(j);
			}

		}
		

	}




}
