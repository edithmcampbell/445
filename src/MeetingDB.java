import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * A class that consists of the database operations to insert and update the Movie information.
 * @author mmuppa
 *
 */

public class MeetingDB {
	//private static String userName = "_450bteam11"; //Change to yours
	//private static String password = "Nimewg";
	private static String userName = "meganelc";
	private static String password = "bynVor";
	private static String serverName = "cssgate.insttech.washington.edu";
	private static String dbName = "meganelc.Meetings";
	private static Connection conn;
	private List<Meeting> list;

	/**
	 * Creates a sql connection to MySQL using the properties for
	 * userid, password and server information.
	 * @throws SQLException
	 */
	public static void createConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		conn = DriverManager.getConnection("jdbc:" + "mysql" + "://"
				+ serverName + "/", connectionProps);

		System.out.println("Connected to database");
	}

	/**
	 * Returns a list of meeting objects from the database.
	 * @return list of meeting
	 * @throws SQLException
	 */
	public List<Meeting> getMeetings() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select meeting_id, counselor_id, location, start_time, "
				+ "end_time, title from " + dbName + " ";

		list = new ArrayList<Meeting>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int meeting_id = rs.getInt("meeting_id");
				int counselor_id = rs.getInt("counselor_id");
				String location = rs.getString("location");
				String start_time = rs.getString("start_time");
				String end_time = rs.getString("end_time");
				String title = rs.getString("title");
				Meeting meeting = new Meeting(location, start_time, end_time, 
						meeting_id, counselor_id, title);
				list.add(meeting);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}

	/**
	 * Filters the meeting list to find the given title. Returns a list with the
	 * meeting objects that match the title provided.
	 * @param title
	 * @return list of meetings that contain the title.
	 */
	public List<Meeting> getMeeting(String title) {
		List<Meeting> filterList = new ArrayList<Meeting>();
		try {
			list = getMeetings();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Meeting meeting : list) {
			if (meeting.getTitle().toLowerCase().contains(title.toLowerCase())) {
				filterList.add(meeting);
			}
		}
		return filterList;
	}

	/**
	 * Adds a new meeting to the table.
	 * @param meeting 
	 */
	public void addMeeting(Meeting meeting) {
		String sql = "insert into " + dbName 
				+ " (meeting_id, counselor_id, location, start_time, end_time, title) values " 
				+ "(?, ?, ?, ?, ?, ?); ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, meeting.getMeetingId());
			preparedStatement.setInt(2, meeting.getCounselorId());
			preparedStatement.setString(3, meeting.getLocation());
			preparedStatement.setString(4, meeting.getStartTime());
			preparedStatement.setString(5, meeting.getEndTime());
			preparedStatement.setString(6,  meeting.getTitle());
			//System.out.println(preparedStatement.toString());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} 
	}
	
	
	public void deleteMeeting(int meetingId) {
		String sql = "delete from " + dbName + " where meeting_id = ?; ";
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, meetingId);
			System.out.println(preparedStatement.toString());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	/**
	 * Modifies the meeting information corresponding to the index in the list.
	 * @param row index of the element in the list
	 * @param columnName attribute to modify
	 * @param data value to supply
	 */
	public void updateMeeting(int row, String columnName, Object data) {
		String column = attributeName(columnName);
		Meeting meeting = list.get(row);
		String title = meeting.getTitle();
		int meeting_id = meeting.getMeetingId();
		String sql = "update " + dbName + " set " + columnName + " = ?  where title= ? and meeting_id = ? ";
		//System.out.println(sql);
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			if (data instanceof String)
				preparedStatement.setString(1, (String) data);
			else if (data instanceof Integer)
				preparedStatement.setInt(1, (Integer) data);
			preparedStatement.setString(2, title);
			preparedStatement.setInt(3, meeting_id);
			System.out.println(preparedStatement.toString());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} 
		
	}
	
	private String attributeName(String columnName) {
		String column = "";
		switch (columnName) {
			case "Title": column = "title";
				break;
			case "Location": column = "location";
				break;
			case "Start Time": column = "start_time";
				break;
			case "End Time": column = "end_time";
				break;
			case "Meeting Id": column = "meeting_id";
				break;
			case "Counselor Id": column = "counselor_id";
				break;
		}
		return column;
	}
}

