package client.view;

import java.awt.FlowLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The gui sign up menu view, which is the V of the MVC model of the sign up menu.
 * This class displays a gui containing text fields which asks the user to enter their name and password.
 * There is a ok button and a cancel button.
 * 
 * @author Jay Chuang, Hunter Coles
 * @version 1.0
 * @since April 19, 2020
 */
public class GUISignupMenuView {
	
	/**
	 * The id which is randomly generated for the user/
	 */
	private int id;
	
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
	 * The cancel button.
	 */
	private JButton cancel;
	
	/**
	 * The label which asks the user to enter their details.
	 */
	private JLabel label;
	
	/**
	 * The label for the id.
	 */
	private JLabel idLabel;
	
	/**
	 * The label for the name text box.
	 */
	private JLabel nameLabel;
	
	/**
	 * The label for the password text box.
	 */
	private JLabel passLabel;
	
	/**
	 * The text field for the name.
	 */
	private JTextField name;
	
	/**
	 * The text field for the password.
	 */
	private JTextField password;
	
	/**
	 * The constructor for the signup menu view, which generate a random id, creates the panel and frame, and sets the 
	 * titles, labels, and buttons.
	 */
	public GUISignupMenuView() {
		Random rand = new Random();
		id = rand.nextInt(99999);
		panel = new JPanel();
		frame = new JFrame();
		frame.setSize(350, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("CLIENT - Signup as a new student");
		frame.add(panel);
		panel.setLayout(new FlowLayout());
		label = new JLabel("Please enter you last name, as well as a password");
		idLabel = new JLabel("You have been assigned an ID, remember: " + id);
		nameLabel = new JLabel("Last Name: ");
		passLabel = new JLabel("Password: ");
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		name = new JTextField(20);
		password = new JTextField(20);
	}

	/**
	 * Displays everything by adding everything to the panel and setting the frame visible.
	 */
	public void display() {
		panel.add(label);
		panel.add(idLabel);
		panel.add(nameLabel);
		panel.add(name);
		panel.add(passLabel);
		panel.add(password);
		panel.add(ok);
		panel.add(cancel);
		frame.setVisible(true);
	}

	/**
	 * Returns the ok button.
	 * @return Returns the ok button.
	 */
	public JButton getOkButton() {
		return ok;
	}
	
	/**
	 * Returns the cancel button.
	 * @return Returns the cancel button.
	 */
	public JButton getCancelButton() {
		return cancel;
	}

	/**
	 * Gets the name entered in the text box.
	 * 
	 * @return Returns the name entered from the text box.
	 */
	public String getName() {
		return name.getText();
	}
	
	/**
	 * Gets the password entered in the text box.
	 * @return Returns teh name entered from the text box.
	 */
	public String getPassword() {
		return password.getText();
	}
	
	/**
	 * Returns the id of the user.
	 * @return The user of the user.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Closes the window.
	 */
	public void closeWindow() {
		frame.setVisible(false);
	}

	/**
	 * Displays a confirmation message stating an account has been created.
	 * @param string The string from the server which contains a message.
	 */
	public void displayConfirmationMessage(String string) {
		JOptionPane.showMessageDialog(frame, string);
	}
}
