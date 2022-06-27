package gmit.os;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	
	Socket outgoingConnection;
	// input/output streams for sending/receiving data
	ObjectInputStream in;
	ObjectOutputStream out;
	String message, response, option;
	Scanner userInput;
	
	// Constructor 
	private Client()
	{
		// Creating new instance of scanner
		userInput = new Scanner(System.in);
	}
	
	private void clientWork()
	{
		try {
			// Requesting connection to port 8080
			outgoingConnection = new Socket("127.0.0.1", 8080);
			System.out.println("Connection established.");
			
			// Send data out
			out = new ObjectOutputStream(outgoingConnection.getOutputStream());
			// Get data in
			in = new ObjectInputStream(outgoingConnection.getInputStream());
			
			try {
				
				// Communicating with server
				do 
				{
					// Message coming in - 1 or 2
					message = (String)in.readObject();
					System.out.println(message);
					// Responding to server
					response = userInput.nextLine();
					sendMessage(response);
					
					option = response;
					
					if (response.equals("1"))
					{
						// Enter club name
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
						
						// enter club id
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
						
						// enter club email
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
					}
					else if (response.equals("2"))
					{
						// Enter existing name
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
						
						// Enter existing id
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
						
						response = (String)in.readObject();
					}
					
				}while(response.equals("FALSE") || option.equals("1"));
				
				// Signed in 
				do 
				{
					// Message coming in - 1 or 2
					message = (String)in.readObject();
					System.out.println(message);
					// Responding to server
					response = userInput.nextLine();
					sendMessage(response);
					
					if (response.equals("1"))
					{
						// Enter member name
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
						
						// enter member age
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
						
						// enter club ID
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
						
						// Enter member date
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
						
						// enter member fee
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
						
						// enter member type
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
						
						// enter member payment status
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
					}
					else if(response.equals("2"))
					{
						// displaying all members to user
						message = (String)in.readObject();
						System.out.println(message);
						
						// number of results
						message = (String)in.readObject();
						int temp = Integer.parseInt(message);
						
						for (int i = 0; i<temp; i++)
						{
							message = (String)in.readObject();
							System.out.println(message);
						}
						// enter member id to update
						response = userInput.nextLine();
						sendMessage(response);
						
						// enter member fee amount
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
						
						// enter member type
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
 					}
					
					else if(response.equals("3"))
					{
						// displaying all members to user
						message = (String)in.readObject();
						System.out.println(message);
						
						// number of results
						message = (String)in.readObject();
						int temp = Integer.parseInt(message);
						
						for (int i = 0; i<temp; i++)
						{
							message = (String)in.readObject();
							System.out.println(message);
						}
						// enter member id to update
						response = userInput.nextLine();
						sendMessage(response);
						
						// enter member payment status
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
 					}
					else if(response.equals("4"))
					{
						// displaying initial message request (14 days)
						message = (String)in.readObject();
						System.out.println(message);
						
						// number of results
						message = (String)in.readObject();
						int temp = Integer.parseInt(message);
						
						for (int i = 0; i<temp; i++)
						{
							// displaying correct members
							message = (String)in.readObject();
							System.out.println(message);
						}
 					}
					else if(response.equals("5"))
					{
						// displaying initial message request (have they paid)
						message = (String)in.readObject();
						System.out.println(message);
						
						// number of results
						message = (String)in.readObject();
						int temp = Integer.parseInt(message);
						
						for (int i = 0; i<temp; i++)
						{
							// displaying correct members
							message = (String)in.readObject();
							System.out.println(message);
						}
 					}
					else if(response.equals("6"))
					{
						// displaying all members to user
						message = (String)in.readObject();
						System.out.println(message);
						
						// number of results
						message = (String)in.readObject();
						int temp = Integer.parseInt(message);
						
						for (int i = 0; i<temp; i++)
						{
							message = (String)in.readObject();
							System.out.println(message);
						}
						// enter member id to remove
						response = userInput.nextLine();
						sendMessage(response);
 					}
					
					else if(response.equals("7"))
					{	
						// displaying initial message request (clubs: )
						message = (String)in.readObject();
						System.out.println(message);
						
						// number of results
						message = (String)in.readObject();
						int temp = Integer.parseInt(message);
						
						for (int i = 0; i<temp; i++)
						{
							// displaying clubs on list
							message = (String)in.readObject();
							System.out.println(message);
						}
 					}
					else if(response.equals("8"))
					{
						// displaying all members to user
						message = (String)in.readObject();
						System.out.println(message);
						
						// number of results
						message = (String)in.readObject();
						int temp = Integer.parseInt(message);
						
						for (int i = 0; i<temp; i++)
						{
							message = (String)in.readObject();
							System.out.println(message);
						}
						// enter member id to update the club
						response = userInput.nextLine();
						sendMessage(response);
						
						// enter members new club
						message = (String)in.readObject();
						System.out.println(message);
						response = userInput.nextLine();
						sendMessage(response);
 					}
					
					// Repeat options ?
					message = (String)in.readObject();
					System.out.println(message);
					response = userInput.nextLine();
					sendMessage(response);
					
				}while(response.equals("1"));
				
				// Goodbye message 
				message = (String)in.readObject();
				System.out.println(message);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Method to send data to server
	private void sendMessage(String msg)
	{
		try {
			// writing out message
			out.writeObject(msg);
			// flushing the line, to write out and clear it
			out.flush();
		} catch (IOException e) {
			System.out.println("Could not send message");
			// e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Creating new instance of client 
		Client client = new Client();
		// Putting client to work
		client.clientWork();
	}

}
