package server.model;

import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The database manager.
 * Connects to the mySQL database containing all the students and courses, and loads them into arraylists in the program.
 * 
 * @author Jay Chuang, Hunter Coles
 * @version 1.0
 * @since April 19, 2020
 */
public class DBManager {
	
	/**
	 * The connection for connecting to the localhost database.
	 */
	private Connection conn;
	
	/**
	 * The course list which is made from the sql database.
	 */
	ArrayList <Course> courseList;
	
	/**
	 * The student list which is made from the sql database.
	 */
	ArrayList <Student> allStudents;

	/**
	 * The constructor for the database manager, which creates teh arraylists of courses and students.
	 */
	public DBManager () {
		courseList = new ArrayList<Course>();
		allStudents = new ArrayList<Student>();
	}

	/**
	 * This functions reads from the database and loads everything into the program.
	 * First it makes a connection to the database, and then it reads all the courses and students into arraylists.
	 */
	public void readFromDataBase() {
		initializeConnection();
		readCourses();
		readStudents();	
		close();
	}
	
	/**
	 * Reads a table in the database into the arraylist of courses.
	 */
	private void readCourses() {
		String className = "";
		int classNum = 0;
		
		try {
			String query = "SELECT * FROM course";
			PreparedStatement pStat = conn.prepareStatement(query);
			ResultSet myRs = pStat.executeQuery(query);
			
			while(myRs.next()) {
				className = myRs.getString("name");
				classNum = myRs.getInt("num");
				courseList.add(new Course(className, classNum));
			}
			pStat.close();
		} catch(SQLException e) {
			System.out.println("[SERVER] Error loading courses from database");
			e.getMessage();
		}
		System.out.println("[SERVER] Courses have successfully been loaded from the database");
	}

	/**
	 * Reads a table of students from the database into an arraylist of students.
	 */
	private void readStudents() {
		
        int key = 0, studentid = 0;
        String name = "", password = "";
        int courseKeys[];
        ArrayList<Course> courses = new ArrayList<Course>();

		try {
			String query = "SELECT * FROM students";
			PreparedStatement pStat = conn.prepareStatement(query);
			ResultSet myRs = pStat.executeQuery(query);
			
			while(myRs.next()) {
				key = myRs.getInt("id");
				name = myRs.getString("name");
				studentid = myRs.getInt("studentid");
				password = myRs.getString("pass");
				
				courseKeys = getCourseKey(key);
				
				courses = studentCourses(courseKeys);
				
				Student s1 = new Student(name, studentid, password, courses);
		        allStudents.add(s1);
			}
			pStat.close();
		} catch(SQLException e) {
			System.out.println("[SERVER] Error loading students from database");
			e.printStackTrace();;
		}
		
		System.out.println("[SERVER] Students loaded from the database");
    }
	
	/**
	 * Reads a table in the database which relates students to which courses they are taking, and adds all their courses
	 * into an arraylist of courses.
	 * 
	 * @param courseKeys The primary keys of the courses that the student is taking.
	 * @return The arraylist of courses that the student is taking.
	 */
	private ArrayList<Course> studentCourses(int[] courseKeys) {
		String className = "";
		int classNum = 0, index = 0;
		ArrayList<Course> courses = new ArrayList<Course>();
		
		while(courseKeys[index] != 0 && index < courseKeys.length) {
			try {
				String query = "SELECT * FROM course where course.id = " + courseKeys[index];
				PreparedStatement pStat = conn.prepareStatement(query);
				ResultSet myRs = pStat.executeQuery(query);
			
				while(myRs.next()) {
					className = myRs.getString("name");
					classNum = myRs.getInt("num");
					Course temp = new Course(className, classNum);
					for(Course c : courseList ) {
						if(c.getCourseName().equals(className) && c.getCourseNum() == classNum) 
							c.enroll();
					}
					courses.add(temp);
				}
				pStat.close();
			} catch(SQLException e) {
				System.out.println("[SERVER] Error enrolling students from database");
				e.printStackTrace();;
			}
			index++;
		}
		return courses;
	}

	/**
	 * Gets the primary keys of the classes that the student is taking.
	 * @param studentId The id of the student, which is used to search the database for the student's courses.
	 * @return An array of primary keys for the courses the student is taking.
	 */
	public int[] getCourseKey(int studentId) {
		int[] courseKeys = {0, 0, 0, 0, 0, 0};
		int index = 0;
		
		try {
			String query = "SELECT * FROM registration where registration.studentid = " + studentId;
			PreparedStatement pStat = conn.prepareStatement(query);
			ResultSet regRs = pStat.executeQuery(query);
			
			while(regRs.next()) {
				courseKeys[index] = regRs.getInt("courseid");
				index++;
			}
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseKeys;
	}
	
	/**
	 * Gets the primary key of the course based on a search fro the course from the course name and number.
	 * @param name The name of the course to search for.
	 * @param num The course number to search fro.
	 * @return The primary key of the course that is found.
	 */
	public int getCourseKey(String name, int num) {
		int courseKey = 0;
		
		try {
			String query = "SELECT * FROM course where course.name = '" + name + "' AND course.num = " + num;
			PreparedStatement pStat = conn.prepareStatement(query);
			ResultSet rs = pStat.executeQuery(query);
			
			rs.next();
			courseKey = rs.getInt("id");
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseKey;
	}
	
	/**
	 * Searches for the primary key of the student based on the provided student id.
	 * @param id The student id that is to be searched for.
	 * @return Returns the primary key of the student.
	 */
	public int getStudentKey(int id) {
		int studentKey = 0;
		
		try {
			String query = "SELECT * FROM students where students.studentid = " + id;
			PreparedStatement pStat = conn.prepareStatement(query);
			ResultSet rs = pStat.executeQuery(query);
			
			rs.next();
			studentKey = rs.getInt("id");
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentKey;
	}
	
	/**
	 * Search for a course in the arraylist of courses by course name and number.
	 * @param courseName The course name to be searched for.
	 * @param courseNum The course number to be searched for.
	 * @return Returns the course that is found, or null if it is not found.
	 */
	public Course searchCourse(String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) &&
					courseNum == c.getCourseNum()) {
				return c;
			}	
		}
		return null;
	}
	
	/**
	 * Searches the arraylist of students from a student id and password.
	 * @param id The student id to search for.
	 * @param password The student password to search for.
	 * @return Returns the student that is found, or null if it is not found.
	 */
	public Student searchStudent(int id, String password) {
		for (Student s : allStudents) {
			if (password.equals(s.getPassword()) && id == s.getStudentId())
			{
				System.out.println("[SERVER] User " + id + " has successfully logged in.");
				return s;
			}	
		}
		return null;
	}
	
	/**
	 * Searches for a student in the arraylist of students by student id.
	 * @param id The student id that is used to search the arraylist with.
	 * @return Returns the student, or null if it is not found.
	 */
	public Student searchStudent(int id) {
		for(Student s : allStudents) if(id == s.getStudentId()) return s;
		return null;
	}
	
	/**
	 * Initializes a connection with the database.
	 */
	public void initializeConnection() {
		try {
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ensf409finalproject", "root", "2511610A9333!B649abfb62Iwtbr8");
		} catch (SQLException e) {
			System.out.println("Problem");
			e.printStackTrace();
		}
	}

	/**
	 * Closes the connection with the database.
	 */
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserts a student into the database table for student.
	 * @param name The student name to be entered.
	 * @param studentid The student id to be entered.
	 * @param password The student password to be entered.
	 */
	public void insertStudent(String name, int studentid, String password) {
		try {
			String query = "INSERT INTO students (name, studentid, pass) values(?,?,?)";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setString(1, name);
			pStat.setInt(2, studentid);
			pStat.setString(3, password);
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			System.out.println("problem inserting student");
		}
	}
	
	/**
	 * Inserts a course into the table course in the database.
	 * @param name The name of the course to be entered.
	 * @param num The course number to be entered.
	 */
	public void insertCourse(String name, int num) {
		try {
			String query = "INSERT INTO course (name, num) values(?,?)";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setString(1, name);
			pStat.setInt(2, num);
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			System.out.println("[SERVER] Problem inserting course");
		}
	}
	
	/**
	 * Inserts a registration into the database, which is entering a student's and course's primary id's into a table.
	 * @param studentid The primary key of the student to be entered.
	 * @param courseid The primary key of the course to be entered.
	 */
	public void insertRegistration(int studentid, int courseid) {
		try {
			String query = "INSERT INTO registration (studentid, courseid) values(?,?)";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, studentid);
			pStat.setInt(2, courseid);
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			System.out.println("[SERVER] Problem inserting registration");
			e.printStackTrace();
		}
	}
}
