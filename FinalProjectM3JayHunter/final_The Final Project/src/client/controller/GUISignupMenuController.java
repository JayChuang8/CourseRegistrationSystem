package client.controller;

import java.awt.event.ActionEvent;

import client.view.GUISignupMenuView;

/**
 * The gui course sign up menu controller, which is the C part of the MVC model for the sign up menu. 
 * Depending on what button is pressed in the sign up menu view gui, other functions are called.
 * 
 * @author Jay Chuang, Hunter Coles
 * @version 1.0
 * @since April 19, 2020
 */
public class GUISignupMenuController {
	
	/**
	 * The class that displays the gui for the sign up menu. The V part of the MVC model for the sign up menu.
	 */
	public GUISignupMenuView signupMenu;
	
	/**
	 * A function used to create and display the view part of the sign up menu. 
	 * Action listeners for the sign up menu are also here to execute instructions accordingly.
	 * If the cancel button is pressed, the sign up window is closed.
	 */
	public void createSignupMenu() {
		signupMenu = new GUISignupMenuView();
		signupMenu.display();
		
		signupMenu.getCancelButton().addActionListener((ActionEvent e) -> {
			signupMenu.closeWindow();
		});
	}
}
