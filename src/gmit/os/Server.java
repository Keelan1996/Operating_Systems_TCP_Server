package gmit.os;

import java.io.*;
import java.net.*;

public class Server {
	
	ServerSocket serverPort;
	Socket incomingConnection;
	// input/output streams for sending/receiving data
	ObjectOutputStream out;
	ObjectInputStream in;
	SharedObject sharedObject;
	SharedMembers sharedMembers;
	
	// Constructor, keeping this private
	private Server()
	{
		try {
			sharedObject = new SharedObject();
			sharedMembers = new SharedMembers();
			// Creating new ServerSocket on port 8080 with a backlog of 10
			serverPort = new ServerSocket(8080, 10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Put the server to work, keeping this private
	private void serverWork()
	{
		System.out.println("Server awaiting connection.");
		// Constantly looping, waiting for a connection 
		while(true)
		{
			try {
				// block and wait until connection established
				incomingConnection = serverPort.accept();
				System.out.println("Connection recieved!");
				// Creating a new instance of ServerThread, passing socket info and sharedObject
				ServerThread serverT = new ServerThread(incomingConnection, sharedObject, sharedMembers);
				serverT.start();
			} catch (IOException e) {
				System.out.println("Connection lost!");
				// e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// Creating instance of Server
		Server server = new Server();
		// Putting this server instance to work
		server.serverWork();
	}

}
