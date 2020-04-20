package server.model;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

/**
 * Called from serverComController, where the socket connects the client to the server and where the run()
 * method is located for the threadpool
 * @author Jay Chuang, Hunter Coles
 *
 */
public class RegistrationApp implements Runnable{
	/**
	 * where all Student and course data is stored
	 */
    private DBManager data;
    /**
     * the clients socket
     */
	private Socket socket;
	/**
	 * the socket input
	 */
	private BufferedReader socketIn;
	/**
	 * the socket output
	 */
	private PrintWriter socketOut;
	/**
	 * Constructor for RegistrationApp, assigns the sockets inputs and outputs
	 * @param s the socket
	 * @param database, the stored data
	 */
    public RegistrationApp(Socket s, DBManager database) {

    	this.data = database;
        this.socket = s;
        try {
			this.setSocketIn(new BufferedReader(new InputStreamReader(socket.getInputStream())));
			this.setSocketOut(new PrintWriter(socket.getOutputStream(), true));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * Where all the server side switch statements are located. When a button is pressed client
     * side, a message is sent here to create a response
     * @param input, an encoded String
     * @return an encoded string
     */
    public String start(String input) {

    	Scanner line = new Scanner(input);
        int choice = Integer.parseInt(line.next());
        int id, courseNum;
        String name, pass, out = "";
        Student stud;
        Course cour;

        switch(choice) 
        {
            case 0:
            	//create new
                id = Integer.parseInt(line.next());
                name = line.next();
                pass = line.next();
                stud = new Student(name,id,pass, new ArrayList<Course>());
                data.initializeConnection();
                data.insertStudent(name, id, pass);
                data.close();
                data.allStudents.add(stud);

                return "0 " + id+ " " +name+ " "+ pass +" null";

            case 1:
            	//login
                id = Integer.parseInt(line.next());
                line.next();
                pass = line.next();
                stud = data.searchStudent(id,pass);
                if(pass.toLowerCase().equals("admin")) out = "2 " +id+ " admin "+ pass;
                else if(stud == null) out = "1 null null null";
                else  out = "1 " +id+ " " +stud.getStudentName()+ " "+ pass;
                
                return out;

            case 2:
                //view all courses
                for(Course c : data.courseList) out+=c.toString();
                return out;

            case 3:
                //search course
                name = line.next().toUpperCase();
                courseNum = Integer.parseInt(line.next());
                 cour = data.searchCourse(name,courseNum);
                 
                if(cour == null) return "Course does not exists";
                out = cour.toString().replace("+----------------------------------------------------------------------------------------------+", "");
                return out;

            case 4:
                //enroll
                id = Integer.parseInt(line.next());
                name = line.next().toUpperCase();
                courseNum = Integer.parseInt(line.next());
                cour = data.searchCourse(name,courseNum);
           
                if( cour == null) return "Class DNE";

                stud = data.searchStudent(id);
                if(stud.maxCourses()) return "You are already enrolled in 6 classes";
                if(stud.enrolled(name,courseNum)) return "You are already in this class";


                stud.addCourse(cour);
                if(cour.enroll()) return "Class full.";
                data.initializeConnection();
                int coursekey = data.getCourseKey(name, courseNum);
                int studentkey = data.getStudentKey(id);
                data.insertRegistration(studentkey, coursekey);
                data.close();
                return "Success! Class enrolled.";

            case 5:
            	//my courses
            	id = Integer.parseInt(line.next());
            	stud = data.searchStudent(id);
            	out = stud.toString();
            	for(Course c : data.courseList)
            			if(stud.enrolled(c.getCourseName(),c.getCourseNum()))
            				{
            				out+=c.toString();
            				}
            	
            	return out;
            case 6:
            	// create course
            	id = Integer.parseInt(line.next());
                name = line.next().toUpperCase();
                courseNum = Integer.parseInt(line.next());
                if(data.searchCourse(name, courseNum) != null) return "This course already Exists!";
                data.initializeConnection();
            	data.insertCourse(name,courseNum);
            	data.close();
            	data.courseList.add(new Course(name,courseNum));
            	return "Success! Class created by Admin "+ id + ".";
            default:
                System.out.println("Invalid input. Try again.");
                break;
        }
        return input;



    }

    /**
     * This executes when execute is called for the threadpool.
     * This will check the socket for anything sent from the client side.
     */
	@Override
	public void run() {
		
		String line = null;
		System.out.println("[SERVER] Client "+ socket.getInetAddress().getHostAddress() +" Connected");
		
		while(true) {
			try
			{
				line = getSocketIn().readLine();
				
				if(line != null)
				{					
						if(!line.contains(" ")) 
						{
								System.out.println("[SERVER] Client "+ socket.getInetAddress().getHostAddress() +" Disconnected");
								if(line.equals("QUIT")) {
								line = "Goodbye";
								getSocketOut().println(line);
								getSocketOut().flush();
								closeSockets();
								break;
								}
						}
						String l = start(line);
						getSocketOut().println(l);	
						getSocketOut().flush();
				}
			} catch(IOException e) {}
		}
	}
	
	/**
	 * Closes all sockets on the server side.
	 */
	public void closeSockets() {
		try
		{
			getSocketIn().close();
			getSocketOut().close();
		}catch(Exception e){
			System.out.println("[SERVER] Closing Error" + e.getMessage());
		}
	}

	/**
	 * Getter for the socketIn.
	 * @return The buffered reader stream.
	 */
	public BufferedReader getSocketIn() {
		return socketIn;
	}

	/**
	 * Setter for socketIn.
	 * @param socketIn The buffered reader stream.
	 */
	public void setSocketIn(BufferedReader socketIn) {
		this.socketIn = socketIn;
	}

	/**
	 * Getter for the socketOut.
	 * @return The print writer stream.
	 */
	public PrintWriter getSocketOut() {
		return socketOut;
	}

	/**
	 * The setter for the socketOut.
	 * @param socketOut The print writer stream.
	 */
	public void setSocketOut(PrintWriter socketOut) {
		this.socketOut = socketOut;
	}
}