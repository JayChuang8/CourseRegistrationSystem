package client.controller;

import java.awt.event.ActionEvent;
import java.io.IOException;

import client.view.GUICourseSearchView;
import client.view.GUIStudentView;

/**
 * The gui course search controller, which is the C part of the MVC model for course search. 
 * Depending on what button is pressed in the course search view gui, other functions are called.
 * 
 * @author Jay Chuang, Hunter Coles
 * @version 1.0
 * @since April 19, 2020
 */
public class GUICourseSearchController {

	/**
	 * The string used to send to the client controller which then is sent to the server.
	 */
	private String s;
	
	/**
	 * The string response from the server.
	 */
	private String response;
	
	/**
	 * The class that displays the gui for the course search. The V part of the MVC model for course search.
	 */
	public GUICourseSearchView searchView;
	
	/**
	 * The client controller which is used to communicate with the server.
	 */
	private ClientCommController client;
	
	/**
	 * A functions used to call the view side of the course search.
	 * Action listeners are also implemented here to listen for anything in the view side and execute instructions accordingly.
	 * If the search button is pressed, the text in the course name and course number JText boxes are read into strings.
	 * Based on the function, a string is made containing the information that is to be passed to the client and then the server.
	 * If the cancel button is pressed, the search course window is closed.
	 * 
	 * @param c The client controller object that is used to write to the server.
	 * @param choice The choice of function to perform. This is based on a switch statement in the class GUIStudentController.
	 * @param StudentID The student ID, which is used to register the student for a course if the function is called when choice = 4.
	 */
	public void createSearchMenu(ClientCommController c, String choice, String StudentID) {
		client = c;
		searchView = new GUICourseSearchView();
		searchView.display();
		
		searchView.getSearchButton().addActionListener((ActionEvent e) -> {
			String courseName = searchView.getCourseName();
			String courseNum = searchView.getCourseNum();
			
			if(choice.equals("3"))
				s = choice + " " + courseName + " " + courseNum;
			else if(choice.equals("4") || choice.equals("6"))
				s = choice + " " + StudentID + " " + courseName + " " + courseNum;
			
			searchView.closeWindow();
			
			try {
				response = client.communicate(s);
				searchView.displayServerMessage(response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		searchView.getCancelButton().addActionListener((ActionEvent e) -> {
			searchView.closeWindow();
		});
	}
}
