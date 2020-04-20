package server.controller;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.model.DBManager;
import server.model.RegistrationApp;
import server.model.Student;

/**
 * Contains the main method for the server side of the Program. Main envokes the ServerConController
 * constructor and then calls communicate to begin receveiving from the client.
 * 
 * @author Hunter Coles, Jay Chuang
 * @version 1.0
 * @since April 19, 2020
 */
public class ServerCommController {
	/**
	 * the server socket that clients connect to
	 */
	private ServerSocket serverSocket;
	/**
	 * the thread pool which all clients run off of
	 */
	private ExecutorService pool;
	/**
	 * the serverSocket is created on port 9670 and pool is initialized
	 * @param port, the chosen port to run the server on
	 */
	public ServerCommController(int port) {
		
		try {
			serverSocket = new ServerSocket(9670, 5, InetAddress.getByName("localhost"));
			pool = Executors.newCachedThreadPool();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * the database is created and initialized using readFromDataBase
	 * then a new registration app is created and given to a client on a new thread
	 */
	public void communicate() {
		try {
			DBManager database = new DBManager();
			database.readFromDataBase();
			
			while(true) {
				RegistrationApp app = new RegistrationApp(serverSocket.accept(), database);
				pool.execute(app);
			}
		}catch(Exception e) {
			e.printStackTrace();
			pool.shutdown();
		}
	
	}
	public static void main(String[] args) throws IOException{
		ServerCommController myServer = new ServerCommController(9670);
		System.out.println("[SERVER] Server is running...");
		myServer.communicate();
	}
}
