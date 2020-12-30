import java.time.LocalDate;
import java.time.LocalTime;


/**
 * @author Elias Faris
 * version 1.0 9/30/2020
 */

/**
 * 
 * This class's purpose is to create a time interval for easier
 * access on grabbing the start and end date, as well as, start and
 * end times for each event. This is crucial to not have any events 
 * overlap and to compare with other events if necessary.
 * 
 * This java file was taken from my assignment #2.
 *
 */
public class TimeInterval {
	LocalDate startDate;
	LocalDate endDate;
	LocalTime startTime;
	LocalTime endTime;


	/**
	 * Constructs a time interval for a recurring event with start date, end date,
	 * start time, and end time.
	 *
	 * @param startDate - the start date of the event
	 * @param endDate - the end date of the event
	 * @param startTime - time of the start of event
	 * @param endTime - time of the end of event, must be after start time
	 */
	public TimeInterval(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Constructs a time interval for a one time event with start and end time.
	 *
	 * @param date - the date of the event
	 * @param startTime - time of the start of event
	 * @param endTime - time of the end of event, must be after start time
	 */
	public TimeInterval(LocalDate date, LocalTime startTime, LocalTime endTime) {
		this.startDate = this.endDate = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}



	/**
	 * @return start time 
	 */
	public LocalTime getStartTime() {return startTime;}

	/**
	 *
	 * @return end time
	 */
	public LocalTime getEndTime() {return endTime;}

	/**
	 *
	 * @return start date
	 */
	public LocalDate getStartDate() {return startDate;}

	/**
	 *
	 * @return end date
	 */
	public LocalDate getEndDate() {return endDate;}


	/**
	 * Compares two TimeInterval objects and returns whether or not they overlap
	 *
	 * @param t - time interval of event
	 * @return true if the two objects overlap, false if they don't
	 */
	public boolean timeOverlap(TimeInterval t) {
		if (this.getStartDate().equals(t.getStartDate())) {
			return this.getStartTime().isBefore(t.getEndTime()) && t.getStartTime().isBefore(this.getEndTime());
		}
		// False if not on the same day
		return false;
	}

	/**
	 * compares start times
	 *
	 * @param t - time interval
	 * @return Negative if start time is before other, positive otherwise
	 */
	public int compareTo(TimeInterval t) {
		return this.getStartTime().compareTo(t.getStartTime());
	}
}