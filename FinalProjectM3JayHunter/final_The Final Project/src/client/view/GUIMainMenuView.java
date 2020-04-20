package client.view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The gui main menu view, which is the V of the MVC model of the main menu.
 * This class displays a gui containing text fields which asks the user to enter username and password.
 * There is a ok button, create new user button, and a quit button as well as labels which label the text fields.
 * 
 * @author Jay Chuang, Hunter Coles
 * @version 1.0
 * @since April 19, 2020
 */
public class GUIMainMenuView {
	
	/**
	 * The panel for the gui.
	 */
	private JPanel panel;
	
	/**
	 * The frame for the gui.
	 */
	private JFrame frame;
	
	/**
	 * The ok button.
	 */
	private JButton ok;
	
	/**
	 * The quit button.
	 */
	private JButton quit;
	
	/**
	 * The create new user button.
	 */
	private JButton create;
	
	/**
	 * A label which asks the user to enter their user info.
	 */
	private JLabel label;
	
	/**
	 * The label for the username text box.
	 */
	private JLabel idLabel;
	
	/**
	 * The lable for the password text box.
	 */
	private JLabel passLabel;
	
	/**
	 * The text field for the username.
	 */
	private JTextField username;
	
	/**
	 * The text field for the password.
	 */
	private JTextField password;
	
	/**
	 * The constructor for the main menu view, which creates the panel and frame, sets the size of the grame, sets
	 * the title and layout, and labels all the buttons and labels.
	 */
	public GUIMainMenuView() {
		panel = new JPanel();
		frame = new JFrame();
		frame.setSize(350, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("CLIENT");
		frame.add(panel);
		panel.setLayout(new FlowLayout());
		label = new JLabel("Please enter in your info, or create a new account");
		idLabel = new JLabel("Student ID: ");
		passLabel = new JLabel("Password: ");
		ok = new JButton("Ok");
		quit = new JButton("Quit");
		create = new JButton("Create an account");
		username = new JTextField(20);
		password = new JTextField(20);
	}

	/**
	 * Displays the main menu by adding everything to the panel and making it visible.
	 */
	public void display() {
		panel.add(label);
		panel.add(idLabel);
		panel.add(username);
		panel.add(passLabel);
		panel.add(password);
		panel.add(ok);
		panel.add(quit);
		panel.add(create);
		frame.setVisible(true);
	}
	
	/**
	 * Gets the username entered, by reading the text from the text box.
	 * 
	 * @return Returns the username entered.
	 */
	public String getUsername() {
		return username.getText();
	}
	
	/**
	 * Gets the password entered, by reading the text from the texxt box.
	 * 
	 * @return Returns the password entered.
	 */
	public String getPassword() {
		return password.getText();
	}
	
	/**
	 * Gets the ok button.
	 * 
	 * @return Returns the ok button.
	 */
	public JButton getOkButton() {
		return ok;
	}
	
	/**
	 * Gets the quit button.
	 * 
	 * @return Returns the quit button.
	 */
	public JButton getQuitButton() {
		return quit;
	}
	
	/**
	 * Gets the create button.
	 * 
	 * @return Returns the create new user button.
	 */
	public JButton getCreateButton() {
		return create;
	}

	/**
	 * Closes the gui.
	 */
	public void closeWindow() {
		this.frame.setVisible(false);
	}

	/**
	 * Displays the response from the server.
	 * 
	 * @param response The string that is to displayed.
	 */
	public void displayResponse(String response) {
		JOptionPane.showMessageDialog(frame, response);
	}
}
