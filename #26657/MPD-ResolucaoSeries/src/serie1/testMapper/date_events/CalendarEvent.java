package serie1.testMapper.date_events;

import java.util.Date;

public class CalendarEvent {
	private Date eventDate;
	private String title;
	
	public CalendarEvent(Date eventDate, String title) {
	  super();
	  this.eventDate = eventDate;
	  this.title = title;
  }
	public Date getEventDate() {
  	return eventDate;
  }
	public String getTitle() {
  	return title;
  }
	
}