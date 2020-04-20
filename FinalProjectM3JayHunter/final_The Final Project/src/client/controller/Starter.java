package client.controller;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

import client.view.GUIMainMenuView;
import server.model.Student;

/**
 * The main class on the client side, contains a main method which starts everything.
 * First a main menu is created and displayed, and a client controller object is created which is used to communicate
 * with the server.
 * 
 * @author Jay Chuang, Hunter Coles
 * @version 1.0
 * @since April 19, 2020
 */
public class Starter {
		
	/**
	 * The portal (either admin or student), which displays if the login is successful.
	 */
	private GUIStudentController portal;
	
	/**
	 * The client controller that communicates with the server.
	 * 
	 */
	private ClientCommController client;
	
	/**
	 * The main menu which displays the login details, and an option to create a new user.
	 */
	private GUIMainMenuView mainMenu;
	
	/**
	 * The string to be sent to the client controller to send to the server.
	 * This contains a number which chooses what function to do, and the username and password of the user.
	 */
	private String s;
	
	/**
	 * The response from the server in the form of a string.
	 */
	private String serverResponse;
	
	/**
	 * Creates the main menu gui, which asks for login credentials, or the option to create a new account.
	 * Action listeners are attached to the buttons.
	 * If the ok button is pressed, the text in both JText fields are stored in a string and sent to the server.
	 * If the cancel button is pressed, the window is closed.
	 * If the create new user button is pressed a window pops up which then asks for details to create a new account.
	 * 
	 * @param ip The ip address for the server and client to run on.
	 */
	public void createMainMenu(InetAddress ip) {
		mainMenu = new GUIMainMenuView();
		mainMenu.display();
		
		mainMenu.getOkButton().addActionListener((ActionEvent e) -> {
			String user, pass;
			user = mainMenu.getUsername();
			pass = mainMenu.getPassword();
			
			s = "1 " + user + " noName " + pass + " noClass"; 
			
			mainMenu.closeWindow();
			
			try {
				client = new ClientCommController(ip, 9670);
				serverResponse = client.communicate(s);
				guiMakeDecision();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		mainMenu.getCreateButton().addActionListener((ActionEvent e) -> {
			GUISignupMenuController signupMenu = new GUISignupMenuController();
			signupMenu.createSignupMenu();
			
			signupMenu.signupMenu.getOkButton().addActionListener((ActionEvent i) -> {
				s = "0 " + signupMenu.signupMenu.getID() + " " + signupMenu.signupMenu.getName() + " " + signupMenu.signupMenu.getPassword();

				signupMenu.signupMenu.closeWindow();
				signupMenu.signupMenu.displayConfirmationMessage("Your account has been created");
				
				try {
					client = new ClientCommController(ip, 9670);
					serverResponse = client.communicate(s);
					guiMakeDecision();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
		});
		mainMenu.getQuitButton().addActionListener((ActionEvent e) -> {
			s = "QUIT";
			
			try {
				serverResponse = client.communicate(s);
				client.closeSockets();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}

	/**
	 * Based on the response of the server following the main menu, a decision is made here.
	 * If the server response contains 0, a student portal is created.
	 * If the server response contains a 1 and the word null, then an incorrect username or password was entered.
	 * If the server response contains a 1 but no null, then a student portal is created.
	 * If the server response contains a 2, a admin portal is created.
	 */
	private void guiMakeDecision() {
		System.out.println("[CLIENT] Message received from server");
		String id = "", name = "";
		
		Scanner line = new Scanner(serverResponse);
		int choice = Integer.parseInt(line.next());
		switch(choice) 
		{
			case 0:
				id = line.next();
				name = line.next();
				portal = new GUIStudentController(0);
				portal.createSignupMenu(id, name, client);
				break;
				
			case 1:
				id = line.next();
				name = line.next();
				
				if(id.equals("null")) {
					displayResponse("Incorrect username or password, ending program now.");
					System.exit(0);
				}
				else {
					portal = new GUIStudentController(1);
					portal.createSignupMenu(id, name, client);
				}
				break;
				
			case 2:
				id = line.next();
				name = line.next();
				portal = new GUIStudentController(2);
				portal.createSignupMenu(id, name, client);		
				break;
		}
	}

	public void displayResponse(String response) {
		mainMenu.displayResponse(response);
	}
	
	public static void main(String[] args) throws IOException{
		Starter mainMenu = new Starter();
		mainMenu.createMainMenu(InetAddress.getByName("localhost"));
	}
}
