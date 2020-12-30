import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * @author Elias Faris
 * 
 * This is the control class for the calendar. This handles all the listeners and sends 
 * the updates to model.
 * 
 * 
 *
 */
public class Control{
	static String nameOfEvent;
	static String date1;
	static String startTime;
	static String endTime;
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
	int val = 0;

	/**
	 * Constructor for the control class.
	 * 
	 * @param prevBtn - jButton for previous button
	 * @param nextBtn - JButton for next button
	 * @param create - JButton for the create button
	 * @param quit - JButton for the quit button
	 */
	public Control(JButton prevBtn, JButton nextBtn, JButton create, JButton quit) {

		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String dte = Integer.toString(View.currentMonth + 1)  + "/" + Integer.toString(View.nextDay) 
				+ "/" + Integer.toString(View.currentYear);

				date1 = dte;

				JFrame frame = new JFrame("New Event");
				JLabel to = new JLabel("to");
				JLabel mtLabel = new JLabel("Input time in 24-hour military time! Ex. h:mm");
				frame.setSize(400, 150);
				frame.setLocation(700, 125);
				JPanel panel = new JPanel(null);
				panel.setBounds(0, 0, 100, 100);
				frame.add(panel);

				String placeholder = "Untitled event";
				JTextField eventName = new JTextField(placeholder, 25);
				JTextField date = new JTextField(dte, 7);
				JTextField start = new JTextField(5);
				JTextField end = new JTextField(5);
				JButton save = new JButton("Save");

				save.setBackground(Color.white);
				eventName.setBounds(40, 10, 325, 30);
				date.setBounds(40, 50, 75, 30);
				start.setBounds(120, 50, 65, 30);
				to.setBounds(188, 50, 60, 30);
				end.setBounds(210, 50, 65, 30);
				save.setBounds(295, 50, 70, 30);
				mtLabel.setBounds(75, 80, 275, 30);

				panel.add(eventName);
				panel.add(date);
				panel.add(start);
				panel.add(to);
				panel.add(end);
				panel.add(save);
				panel.add(mtLabel);

				frame.setVisible(true);

				eventName.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JTextField c = (JTextField) e.getSource();
						String en = c.getText().trim();
						nameOfEvent = en;


					}

				});

				start.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JTextField c = (JTextField) e.getSource();
						String st = c.getText().trim();
						startTime = st;

					}

				});


				end.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JTextField c = (JTextField) e.getSource();
						String et = c.getText().trim();
						endTime = et;

					}

				});



				save.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						Model.create();
						View.printDayCal();

					}

				});

			}



		});

		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Model.saveToTextFile();
				System.exit(0);

			}

		});




		prevBtn.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){

				val = -1;

				GregorianCalendar cal = new GregorianCalendar(View.currentYear, View.currentMonth - 1, 1);
				int x = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);



				//switching months
				if(View.nextDay <= 1) {
					if(View.currentMonth == 0) {
						View.currentMonth = 11;
						View.nextDay = x;
						View.currentYear -= 1;
					}
					else {

						View.currentMonth -= 1;
						View.nextDay = x;
					}

				}
				//switching days
				else{ 
					View.nextDay -= 1;
				}

				View.shiftDay(val);
				View.printDayCal();
				View.refreshCalendar(View.currentDay, View.currentMonth, View.currentYear);
			}
		});



		nextBtn.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){


				val = 1;


				GregorianCalendar cal = new GregorianCalendar(View.currentYear, View.currentMonth, 1);
				int x = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

				//switching months
				if(View.nextDay > x - 1) {
					if(View.currentMonth == 11) {
						View.currentMonth = 0;
						View.nextDay = 1;
						View.currentYear += 1;
					}
					else {
						View.currentMonth += 1;
						View.nextDay = 1;
					}

				}
				//switching days
				else{ 
					View.nextDay += 1;
				}

				View.shiftDay(val);
				View.printDayCal();
				View.refreshCalendar(View.currentDay, View.currentMonth, View.currentYear);
			}
		});


	}


}
