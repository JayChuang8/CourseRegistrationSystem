package client.view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The gui course search menu view, which is the V of the MVC model of the course search.
 * This class displays a gui containing text fields which asks the user to enter detail of a course, which are course name
 * and course number.
 * There is a search button and a cancel button as well as labels which label the text fields.
 * 
 * @author Jay Chuang, Hunter Coles
 * @version 1.0
 * @since April 19, 2020
 */
public class GUICourseSearchView {
	
	/**
	 * The panel of the gui.
	 */
	JPanel panel;
	
	/**
	 * The frame of the gui.
	 */
	JFrame frame;
	
	/**
	 * The label for the class name text field.
	 */
	JLabel className;
	
	/**
	 * The label for the class number text field.
	 */
	JLabel classNum;
	
	/**
	 * The button labeled search.
	 */
	JButton search;
	
	/**
	 * The button labeled cancel.
	 */
	JButton cancel;
	
	/**
	 * The text field for the course name.
	 */
	JTextField textboxName;
	
	/**
	 * The text field for the course number.
	 */
	JTextField textboxNum;
	
	/**
	 * The constructor for the course search menu, it creates teh panel and frame, and sets the size and title of the gui, 
	 * as well as the layout.
	 */
	public GUICourseSearchView() {
		
		panel = new JPanel();
		frame = new JFrame();
		frame.setSize(850, 100);
		frame.setTitle("Search");
		frame.add(panel);
		panel.setLayout(new FlowLayout());
	}
	
	/**
	 * Sets all the labels and buttons with names.
	 * Displays the gui, by first adding everything to the panel, and then setting the frame visible.
	 */
	public void display() {
		
		className = new JLabel("Enter the class name: ");
		classNum = new JLabel("Enter the class num: ");
		textboxName = new JTextField(10);
		textboxNum = new JTextField(10);
		search = new JButton("Search");
		cancel = new JButton("Cancel");
		panel.add(className);
		panel.add(textboxName);
		panel.add(classNum);
		panel.add(textboxNum);
		panel.add(search);
		panel.add(cancel);
		frame.setVisible(true);
	}
	
	/**
	 * Closes the gui window.
	 */
	public void closeWindow() {
		frame.setVisible(false);
	}

	/**
	 * Gets the cancel button.
	 * 
	 * @return Returns the cancel button.
	 */
	public JButton getCancelButton() {
		return cancel;
	}

	/**
	 * Gets the search button.
	 * 
	 * @return Returns the search button.
	 */
	public JButton getSearchButton() {
		return search;
	}
	
	/**
	 * Gets the course name from the JTextField.
	 * 
	 * @return Returns the course name.
	 */
	public String getCourseName() {
		return textboxName.getText();
	}
	
	/**
	 * Gets the course number from the JTextField
	 * .
	 * @return Returns the course number.
	 */
	public String getCourseNum() {
		return textboxNum.getText();
	}
	
	/**
	 * Displays a message from the server.
	 * 
	 * @param string The message to be displayed.
	 */
	public void displayServerMessage(String string) {
		JOptionPane.showMessageDialog(frame, string);
	}
}
