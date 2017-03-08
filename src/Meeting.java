/**
 * Represents a meeting with a meeting id, counselor id, location, 
 * start time, end time and title.
 * @author Edie Campbell
 *
 */
public class Meeting {
	private String location;
	private String start_time;
	private String end_time;
	private int meeting_id;
	private int counselor_id;
	private String title;
	
	/**
	 * Initialize the meeting parameters.
	 * @param location
	 * @param start_time
	 * @param end_time
	 * @param meeting_id
	 * @param counselor_id
	 * @param title
	 * @throws IllegalArgumentException 
	 */
	public Meeting(String location, String start_time, String end_time, 
			int meeting_id, int counselor_id, String title) {
		this.location = location;
		this.start_time = start_time;
		this.end_time = end_time;
		this.meeting_id = meeting_id;
		this.counselor_id = counselor_id;
		this.title = title;
	}
	
	@Override
	public String toString() {
		return "Meeting [title=" + title + ", location=" + location 
				+ ", start time=" + start_time + ", end time=" + end_time 
				+ ", counselor id=" + counselor_id
				+ "]";
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getStartTime() {
		return start_time;
	}
	
	public String getEndTime() {
		return end_time;
	}
	
	public int getMeetingId() {
		return meeting_id;
	}
	
	public int getCounselorId() {
		return counselor_id;
	}
	
	public String getLocation() {
		return location;
	}

}
