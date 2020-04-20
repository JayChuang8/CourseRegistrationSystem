package client.controller;

import java.awt.event.ActionEvent;
import java.io.IOException;

import client.view.GUIStudentView;

/**
 * The gui portal menu controller, which is the C part of the MVC model for the portal menu. 
 * Depending on what button is pressed in the portal menu view gui, other functions are called.
 * 
 * @author Jay Chuang, Hunter Coles
 * @version 1.0
 * @since April 19, 2020
 */
public class GUIStudentController {

	/**
	 * The number representing which function to do, which comes from the starter class. 
	 * Choices 0 or 1 mean to display a student portal, and choice 2 will display a admin portal.
	 */
	private int choice;
	
	/**
	 * The string used to communicate with the server. It is sent to the client controller first.
	 * It contains information such as a number that tells the server what to do.
	 */
	private String s;
	
	/**
	 * The string response from the server.
	 */
	private String response;
	
	/**
	 * The view of the portal, which is the V part of the MVC of the portal menu.
	 */
	public GUIStudentView studentView;
	
	/**
	 * The client object which is used to communicate with the server.
	 */
	private ClientCommController client;
	
	/**
	 * The gui object for the course search controller, which is the C part of the MVC of the course search menu.
	 */
	private GUICourseSearchController searchMenu;
	
	/**
	 * The constructor of the  portal menu, which takes an int which represents a choice of what function to do, whether
	 * it be display a student portal or a admin portal.
	 * 
	 * @param choice The number representing what function to perform.
	 */
	public GUIStudentController(int choice) {
		this.choice = choice;
	}
	
	/**
	 * A function used to create and display the portal menu view. Multiple listeners are integrated and based on the 
	 * button pressed, instructions are executed.
	 * If the int choice is a 0 or 1, a student portal is displayed and the corresponding buttons are linked with
	 * action listeners.
	 * The results from pressing the buttons either send a string to the client controller, or other menus are created.
	 * If the view courses button pressed in the student portal, a message is sent to the client controller and the server 
	 * sends back all the available courses in the system.
	 * If the logout button is pressed, the client side is terminated.
	 * If the search courses button is pressed, a search menu is created.
	 * If the enroll button is pressed, a menu to search for a course is created.
	 * If the display my courses button is pressed, a string is sent to the client controller, and then the server 
	 * sends back all the courses that the student is currently taking.
	 * If the add course button is pressed on the admin portal, an object menu is called which displays a menu to add 
	 * a course.
	 * 
	 * @param id The id that is entered from the login page.
	 * @param name The name of the user.
	 * @param c	The client controller object which is used to communicate with the server.
	 */
	public void createSignupMenu(String id, String name, ClientCommController c) {
		client = c;
		studentView = new GUIStudentView(id, name, choice);
		studentView.display();
		
		studentView.getViewCoursesButton().addActionListener((ActionEvent e) -> {
			s = "2 null null null";
			
			try {
				response = client.communicate(s);
				response = response.replace('+', '\n');
				
				studentView.displayAllCourses(response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		studentView.getLogoutButton().addActionListener((ActionEvent e) -> {
			s = "QUIT";
			studentView.closeWindow();
			try {
				client.communicate(s);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		});
		
		if(choice != 2) {
			studentView.getSearchCoursesButton().addActionListener((ActionEvent e) -> {
				searchMenu = new GUICourseSearchController();
				searchMenu.createSearchMenu(client, "3", id);					
			});
		
			studentView.getEnrollButton().addActionListener((ActionEvent e) -> {
				searchMenu = new GUICourseSearchController();
				searchMenu.createSearchMenu(client , "4", id);	
			});
			
			studentView.getMyCoursesButton().addActionListener((ActionEvent e) -> {
				s = "5 " + id;
				
				try {
					response = client.communicate(s);
					response = response.replace('+', '\n');
					
					studentView.displayAllCourses(response);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
		}
		
		if(choice == 2) {
			studentView.getAddCourseButton().addActionListener((ActionEvent e) -> {
				searchMenu = new GUICourseSearchController();
				searchMenu.createSearchMenu(client, "6", id);					
			});
		}
	}
}
