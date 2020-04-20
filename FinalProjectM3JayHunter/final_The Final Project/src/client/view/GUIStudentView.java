package client.view;

import java.awt.FlowLayout;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * The gui student menu view, which is the V of the MVC model of the student menu.
 * This class displays a gui containing text fields which asks the user to choose options they want.
 * There is a view all courses button, display my courses button, search for a course button, add a course button,
 * enroll in a course button, and a logout button.
 * 
 * @author Jay Chuang, Hunter Coles
 * @version 1.0
 * @since April 19, 2020
 */
public class GUIStudentView {
	
	/**
	 * Based on the choice sent by the student menu controller, the portal is either displayed for an admin or for a student.
	 */
	private int choice;
	
	/**
	 * The panel for the gui.
	 */
	private JPanel panel;
	
	/**
	 * The frame for the gui.
	 */
	private JFrame frame;
	
	/**
	 * The view all courses button.
	 */
	private JButton viewCourses;
	
	/**
	 * The search for a course button.
	 */
	private JButton searchCourses;
	
	/**
	 * The enroll for a course button.
	 */
	private JButton enroll;
	
	/**
	 * The view all my courses button.
	 */
	private JButton myCourses;
	
	/**
	 * The add a course button.
	 */
	private JButton addCourse;
	
	/**
	 * The logout button.
	 */
	private JButton logout;
	
	/**
	 * The label for showing who is logged in.
	 */
	private JLabel label;
	
	/**
	 * A scrollbar for scrolling if the console gets too large.
	 */
	private JScrollPane scrollbar;
	
	/**
	 * The console where all teh courses will be displayed.
	 */
	private JTextArea console;
	
	/**
	 * The constructor for the portal, creates the panel, frame, sets the size, and creates all the labels and buttons.
	 * Displays either a student or admin portal based on what numer choice is.
	 * 
	 * @param id The id of the user.
	 * @param name The name of the user.
	 * @param choice The choice to display either a student or admin portal.
	 */
	public GUIStudentView(String id, String name, int choice) {
		this.choice = choice;
		panel = new JPanel();
		frame = new JFrame();
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Portal");
		frame.add(panel);
		panel.setLayout(new FlowLayout());
		console = new JTextArea(25, 45);
		console.setEditable(false);
		scrollbar = new JScrollPane(console);
		scrollbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollbar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		if(choice != 2) {
			label = new JLabel("Welcome to the portal, " + id + ", " + name);
			searchCourses = new JButton("Search for a course");
			enroll = new JButton("Enroll in a course");
			myCourses = new JButton("View all courses I am enrolled in");
		}
		
		if(choice == 2) {
			label = new JLabel("Welcome to the portal, admin");
			addCourse = new JButton("Add a new course");
		}
		
		viewCourses = new JButton("View all available courses");
		logout = new JButton("Logout");
	}
	
	/**
	 * Displays the gui by adding everything to the portal and setting the frame to be visible.
	 */
	public void display() {
		panel.add(label);
		panel.add(scrollbar);
		
		if(choice != 2) {
			panel.add(searchCourses);
			panel.add(enroll);
			panel.add(myCourses);
		}
		
		if(choice == 2) {
			panel.add(addCourse);
		}
		
		panel.add(viewCourses);
		panel.add(logout);
		frame.setVisible(true);
	}

	/**
	 * Closes the gui window.
	 */
	public void closeWindow() {
		frame.setVisible(false);
	}
	
	/**
	 * Returns the logout button.
	 * 
	 * @return Returns the logout button.
	 */
	public JButton getLogoutButton() {
		return logout;
	}

	/**
	 * Returns the view courses button.
	 * @return Returns the view all courses button.
	 */
	public JButton getViewCoursesButton() {
		return viewCourses;
	}
	
	/**
	 * Returns the search for a course button.
	 * @return Returns the search for a course button.
	 */
	public JButton getSearchCoursesButton() {
		return searchCourses;
	}
	
	/**
	 * Returns the enroll for a course button.
	 * @return Returns the enroll for a course button.
	 */
	public JButton getEnrollButton() {
		return enroll;
	}
	
	/**
	 * Returns the view all my courses button. 
	 * @return Returns the view all my courses button. 
	 */
	public JButton getMyCoursesButton() {
		return myCourses;
	}
	
	/**
	 * Returns the add course button.
	 * @return Returns the add course button.
	 */
	public JButton getAddCourseButton() {
		return addCourse;
	}

	/**
	 * Displays all the courses from the server to the console.
	 * @param response The string received from the server.
	 */
	public void displayAllCourses(String response) {
		console.setText(response);
	}
}
