package client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * The client controller, creates a socket to write and read from to communicate with the server.
 * 
 * @author Jay Chuang, Hunter Coles
 * @version 1.0
 * @since April 19, 2020
 */
public class ClientCommController {
	
	/**
	 * The socket which is used to read and write from to connect with the server.
	 */
	private Socket socket;
	
	/**
	 * The stream used to write to the socket/server side.
	 */
	private PrintWriter socketOut;
	
	/**
	 * The stream used to read from the socket/server side.
	 */
	private BufferedReader socketIn;
	
	/**
	 * Creates the socket and connection with the server, initializing the input and output streams with the socket.
	 * 
	 * @param ip The ip used to connect with the server.
	 * @param portNumber The port number used to run.
	 */
	public ClientCommController(InetAddress ip, int portNumber){
		try {
			socket = new Socket(ip, portNumber);
			System.out.println("[CLIENT] Connected");
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter((socket.getOutputStream()), true);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The function used to communicate with the server. Writes to the server/socket using socketOut, and reads 
	 * from the server/socket using socketIn. 
	 * 
	 * @param line The string that is to be sent to the server.
	 * @return Returns the server's response.
	 * @throws IOException Exception to check for sending errors to the server.
	 */
	public String communicate(String line) throws IOException {
		
		String response = "";
			try {
				socketOut.println(line);
				socketOut.flush();
				response = socketIn.readLine();
				
				if (line.equals("QUIT")) {
					System.out.println("[CLIENT] Program has terminated.");
					System.exit(0);
				}
			} catch (IOException e) {
				System.out.println("[CLIENT] Sending error: " + e.getMessage());
				e.getStackTrace();
			}
		return response;
	}
	
	/**
	 * The function to close all the streams connecting the the socket/server. This function is called when the client disconnects.
	 */
	public void closeSockets() {
		try {
			socketIn.close();
			socketOut.close();
			System.out.println("[CLIENT] Sockets on client side have closed.");
		} catch (IOException e) {
			System.out.println("[CLIENT] Closing error: " + e.getMessage());
		}
	}
}
