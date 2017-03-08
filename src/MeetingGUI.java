import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * A user interface to view the meetings, add a new meeting and to update an existing meeting.
 * The list is a table with all the meeting information in it. The TableModelListener listens to
 * any changes to the cells to modify the values for each meeting.
 * @author mmuppa, Edie Campbell
 *
 */
public class MeetingGUI extends JFrame implements ActionListener, TableModelListener
{
	
	private static final long serialVersionUID = 1779520078061383929L;
	private JButton btnList, btnSearch, btnAdd, btnDelete;
	private JPanel pnlButtons, pnlContent;
	private MeetingDB db;
	private List<Meeting> list;
	private String[] columnNames = {"Meeting Id", 
			"Start Time",
            "End Time",
            "Location",
            "Counselor Id",
            "Title"};
	
	private Object[][] data;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel pnlSearch;
	private JLabel lblTitle;;
	private JTextField txfTitle;
	private JButton btnTitleSearch;
	
	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[6];
	private JTextField[] txfField = new JTextField[6];
	private JButton btnAddMeeting;
	
	private JPanel pnlDelete;
	private JLabel lblId;
	private JTextField txfId;
	private JButton btnIdDelete;
	
	
	/**
	 * Creates the frame and components and launches the GUI.
	 */
	public MeetingGUI() {
		super("Meeting Planner");
		
		db = new MeetingDB();
		try
		{
			list = db.getMeetings();
			
			data = new Object[list.size()][columnNames.length];
			for (int i=0; i<list.size(); i++) {
				data[i][0] = list.get(i).getMeetingId();
				data[i][1] = list.get(i).getStartTime();
				data[i][2] = list.get(i).getEndTime();
				data[i][3] = list.get(i).getLocation();
				data[i][4] = list.get(i).getCounselorId();
				data[i][5] = list.get(i).getTitle();
				
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		createComponents();
		setVisible(true);
		setSize(800, 500);
	}
    
	/**
	 * Creates panels for Movie list, search, add and adds the corresponding 
	 * components to each panel.
	 */
	private void createComponents()
	{
		pnlButtons = new JPanel();
		btnList = new JButton("Meeting List");
		btnList.addActionListener(this);
		
		btnSearch = new JButton("Meeting Search");
		btnSearch.addActionListener(this);
		
		btnAdd = new JButton("Add Meeting");
		btnAdd.addActionListener(this);
		
		btnDelete = new JButton("Delete Meeting");
		btnDelete.addActionListener(this);
		
		pnlButtons.add(btnList);
		pnlButtons.add(btnSearch);
		pnlButtons.add(btnAdd);
		pnlButtons.add(btnDelete);
		add(pnlButtons, BorderLayout.NORTH);
		
		//List Panel
		pnlContent = new JPanel();
		table = new JTable(data, columnNames);
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
		table.getModel().addTableModelListener(this);
		
		//Search Panel
		pnlSearch = new JPanel();
		lblTitle = new JLabel("Enter Title: ");
		txfTitle = new JTextField(25);
		btnTitleSearch = new JButton("Search");
		btnTitleSearch.addActionListener(this);
		pnlSearch.add(lblTitle);
		pnlSearch.add(txfTitle);
		pnlSearch.add(btnTitleSearch);
		
		//Add Panel
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(7, 0));
		String labelNames[] = {"Enter Meeting Id: ", "Enter Start Time: ", "Enter End Time", "Enter Location: ", "Enter Counselor Id: ", "Enter Title: "};
		for (int i=0; i<labelNames.length; i++) {
			JPanel panel = new JPanel();
			//System.out.println(i + " " + labelNames.length);
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}
		JPanel panel = new JPanel();
		btnAddMeeting = new JButton("Add");
		btnAddMeeting.addActionListener(this);
		panel.add(btnAddMeeting);
		pnlAdd.add(panel);
		
		//Delete Panel
		pnlDelete = new JPanel();
		lblId = new JLabel("Enter Meeting Id to delete: ");
		txfId = new JTextField(10);
		btnIdDelete = new JButton("Delete");
		btnIdDelete.addActionListener(this);
		pnlDelete.add(lblId);
		pnlDelete.add(txfId);
		pnlDelete.add(btnIdDelete);
		
		add(pnlContent, BorderLayout.CENTER);
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		MeetingGUI meetingGUI = new MeetingGUI();
		meetingGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Event handling to change the panels when different tabs are clicked,
	 * add and search buttons are clicked on the corresponding add and search panels.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnList) {
			try {
				list = db.getMeetings();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			data = new Object[list.size()][columnNames.length];
			for (int i=0; i<list.size(); i++) {
				data[i][0] = list.get(i).getMeetingId();
				data[i][1] = list.get(i).getStartTime();
				data[i][2] = list.get(i).getEndTime();
				data[i][3] = list.get(i).getLocation();
				data[i][4] = list.get(i).getCounselorId();
				data[i][5] = list.get(i).getTitle();
			}
			pnlContent.removeAll();
			table = new JTable(data, columnNames);
			table.getModel().addTableModelListener(this);
			scrollPane = new JScrollPane(table);
			pnlContent.add(scrollPane);
			pnlContent.revalidate();
			this.repaint();
			
		} else if (e.getSource() == btnSearch) {
			pnlContent.removeAll();
			pnlContent.add(pnlSearch);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnAdd) {
			pnlContent.removeAll();
			pnlContent.add(pnlAdd);
			pnlContent.revalidate();
			this.repaint();
			
		} else if (e.getSource() == btnDelete) {
			pnlContent.removeAll();
			pnlContent.add(pnlDelete);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnTitleSearch) {
			String title = txfTitle.getText();
			if (title.length() > 0) {
				list = db.getMeeting(title);
				data = new Object[list.size()][columnNames.length];
				for (int i=0; i<list.size(); i++) {
					data[i][0] = list.get(i).getMeetingId();
					data[i][1] = list.get(i).getStartTime();
					data[i][2] = list.get(i).getEndTime();
					data[i][3] = list.get(i).getLocation();
					data[i][4] = list.get(i).getCounselorId();
					data[i][5] = list.get(i).getTitle();
				}
				pnlContent.removeAll();
				table = new JTable(data, columnNames);
				table.getModel().addTableModelListener(this);
				scrollPane = new JScrollPane(table);
				pnlContent.add(scrollPane);
				pnlContent.revalidate();
				this.repaint();
			}
		} else if (e.getSource() == btnAddMeeting) {
			Meeting meeting = new Meeting(txfField[3].getText(), 
					txfField[1].getText(), txfField[2].getText(),
					Integer.parseInt(txfField[0].getText()), Integer.parseInt(txfField[4].getText()), txfField[5].getText());
			db.addMeeting(meeting);
			JOptionPane.showMessageDialog(null, "Added Successfully!");
			for (int i=0; i<txfField.length; i++) {
				txfField[i].setText("");
			}
		} else if (e.getSource() == btnIdDelete) {
			int meetingId = Integer.parseInt(txfId.getText());
			db.deleteMeeting(meetingId);
			JOptionPane.showMessageDialog(null, "Deleted Successfully!");
			txfId.setText("");
		}
		
	}

	/**
	 * Event handling for any cell being changed in the table.
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        
        db.updateMeeting(row, columnName, data);
		
	}

}

