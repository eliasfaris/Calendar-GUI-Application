import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * @author Elias Faris
 * 
 * This class renders the calendar with a red colored boxes when
 * user uses the previous and next button, clicks on the calendar,
 * and signifies the current day as a cyan colored cell. This class
 * extends the DefaultTableCellRenderer to create each day as a cell 
 * and work with the calendar in a more easier way.
 * 
 */
class cellCalendar extends DefaultTableCellRenderer{

	int realYear;
	int realMonth;
	int realDay;
	int currentDay;
	int currentYear;
	int currentMonth;
	int nextDay;

	/**
	 * Constructor for the cellCalendar
	 * 
	 * @param realYear - int for the real year
	 * @param realMonth - int for the real month 
	 * @param realDay - int for the real day
	 * @param currentDay - int for the current day, the day that has been changed on calendar.
	 * @param currentYear - int for the current year, the year that has been changed on calendar.
	 * @param currentMonth - int for the current month, the month that has been changed on calendar.
	 * @param nextDay - int for the next day, this is the updater to render the next day as a red box.
	 * 
	 */
	public cellCalendar(int realYear, int realMonth, int realDay, int currentDay,
			int currentYear, int currentMonth, int nextDay) {

		this.realYear = realYear;
		this.realMonth = realMonth;
		this.realDay = realDay;
		this.currentDay = currentDay;
		this.currentYear = currentYear;
		this.currentMonth = currentMonth;
		this.nextDay = nextDay;

	}

	/*
	 * @param table - the table that wants to be rendered
	 * @param value - the value of the cell to be rendered
	 * @param isSelected - this is true if the cell is to be rendered
	 * @param hasFocus - render cell appropriately.
	 * @param row - row the cell is to be drawn
	 * @param column - col the cell is to be drawn
	 */
	public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

		setBackground(Color.WHITE);

		if (value != null){
			if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
				setBackground(Color.cyan);
			}
			else if(Integer.parseInt(value.toString()) == nextDay){
				setBackground(Color.red);

			}
		}
		return this;  
	}

}