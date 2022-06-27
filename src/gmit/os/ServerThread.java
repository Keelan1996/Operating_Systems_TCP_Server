package gmit.os;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ServerThread extends Thread {

	private Socket incomingConnection;
	// input/output streams for sending/receiving data
	ObjectOutputStream out;
	ObjectInputStream in;
	
	String userInput;
	// Club details
	String clubName, clubID, email;
	// Login details 
	String loginName, loginID;
	// Boolean used to confirm login details match
	boolean result;
	// Login shared object
	SharedObject sharedObject;
	// Member list shared object
	SharedMembers sharedMembers;
	// Search and display array
	ArrayList<String> searchResult;
	
	// Constructor, receives socket info and an instance of SharedObject
	public ServerThread(Socket soc, SharedObject obj, SharedMembers sMem)
	{
		incomingConnection = soc;
		sharedObject = obj;
		sharedMembers = sMem;
	}
	
	// Thread in action, get's invoked by the .start() method
	public void run()
	{
		try {
			// Send/Receive streams
			out = new ObjectOutputStream(incomingConnection.getOutputStream());
			in = new ObjectInputStream(incomingConnection.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			do
			{
				sendMessage("Please enter 1 to Register Club or 2 to sign in");
				// receiving from client
				userInput = (String)in.readObject();
				
				if(userInput.equals("1"))
				{
					// Sever talking to client
					sendMessage("Please enter Club Name");
					clubName = (String)in.readObject();
					
					sendMessage("Please enter Club ID");
					clubID = (String)in.readObject();
					
					sendMessage("Please enter email");
					email = (String)in.readObject();
					
					// adding new entry to linked list and clubs.txt
					sharedObject.addEntry(clubName, clubID, email);
				}
				else if (userInput.equals("2"))
				{
					// Sever talking to client
					sendMessage("Please enter existing Club Name");
					loginName = (String)in.readObject();
					
					sendMessage("Please enter existing Club ID");
					loginID = (String)in.readObject();
					
					// Getting a true or false value if the club exists
					result = sharedObject.searchClub(loginName, loginID);
					
					// sending message to client - the response controls a do while loop
					if (result == true)
					{
						sendMessage("TRUE");
					}
					else {
						sendMessage("FALSE");
					}
				}
				
			}while(userInput.equals("1")|| result==false);
			
			do
			{
				// After login, initial server response 
				sendMessage("Please enter 1 to add a member \n"
						+ "Please enter 2 to update a membership type or fee \n"
						+ "Please enter 3 to update members payment status \n"
						+ "Please enter 4 to show members who visited in last 14 days \n"
						+ "Please enter 5 to show all members who have paid \n"
						+ "Please enter 6 to remove a member \n"
						+ "Please enter 7 to view all registered clubs \n"
						+ "Please enter 8 to update player club");
				// receiving from client
				userInput = (String)in.readObject();
				
				if(userInput.equals("1"))
				{
					// creating member
					sendMessage("Please enter member name");
					String name = (String)in.readObject();
					
					sendMessage("Please enter member age");
					String age = (String)in.readObject();
					
					sendMessage("Please enter members club name");
					String cID = (String)in.readObject();
					
					sendMessage("Please enter members last visit date - YYYY/DD/MM format, please");
					String date = (String)in.readObject();
					
					sendMessage("Please enter members fee amount");
					String fee = (String)in.readObject();
					
					sendMessage("Please enter membershipe type - Adult, Senior or Junior");
					String type = (String)in.readObject();
					
					sendMessage("Please enter members payment status - Paid, Part or Not");
					String pStatus = (String)in.readObject();
					
					// random number gen for member ID
					int mid = ThreadLocalRandom.current().nextInt(1, 1000 + 1);
					
					// Adding member
					sharedMembers.addMember(name, Integer.parseInt(age), mid, cID, date, Double.parseDouble(fee), type, pStatus);
				}
				
				else if (userInput.equals("2"))
				{
					sendMessage("Please enter the member ID to update");
					searchResult = sharedMembers.displayMembers();
					
					// sending list of memebers name + if
					int temp = searchResult.size();
					sendMessage(""+temp);
					
					for(int i = 0; i <temp; i++)
					{
						sendMessage(searchResult.get(i));
					}
					
					// retrieveing member id to update
					String mID = (String)in.readObject();
					// retrieving new type and fee details
					sendMessage("Please enter members fee amount");
					String fee = (String)in.readObject();
					
					sendMessage("Please enter membershipe type - Adult, Senior or Junior");
					String type = (String)in.readObject();
					
					sharedMembers.updateTypeFee(Integer.parseInt(mID), type, Double.parseDouble(fee));
				}
				
				else if (userInput.equals("3"))
				{
					sendMessage("Please enter the member ID to update");
					searchResult = sharedMembers.displayMembers();
					
					// sending list of members name
					int temp = searchResult.size();
					sendMessage(""+temp);
					
					for(int i = 0; i <temp; i++)
					{
						sendMessage(searchResult.get(i));
					}
					// retrieving member id to update
					String mID = (String)in.readObject();
					// retrieving new payment status details
					sendMessage("Please enter members payment status - Paid, Part or Not");
					String status = (String)in.readObject();
					
					sharedMembers.updateStatus(Integer.parseInt(mID), status);
				}
				
				else if (userInput.equals("4"))
				{
					sendMessage("Member who visited clubs in last 14 days");
					// Calling the method which checks and stores all members who have visited in the last 14 days
					searchResult = sharedMembers.list14Days();
					
					// sending list of members name + if
					int temp = searchResult.size();
					sendMessage(""+temp);
					
					for(int i = 0; i <temp; i++)
					{
						sendMessage(searchResult.get(i));
					}
				}
				
				else if (userInput.equals("5"))
				{
					sendMessage("Member who have paid their fees");
					// Calling the method which checks and stores all members who have paid
					searchResult = sharedMembers.hasPaid();
					
					// sending list of members name + if
					int temp = searchResult.size();
					sendMessage(""+temp);
					
					for(int i = 0; i <temp; i++)
					{
						sendMessage(searchResult.get(i));
					}
				}
				
				else if (userInput.equals("6"))
				{
					sendMessage("Please enter the member ID to remove");
					searchResult = sharedMembers.displayMembers();
					
					// sending list of memebers name + if
					int temp = searchResult.size();
					sendMessage(""+temp);
					
					for(int i = 0; i <temp; i++)
					{
						sendMessage(searchResult.get(i));
					}
					// receiving member id to remove
					String mID = (String)in.readObject();
					
					sharedMembers.removeMember(Integer.parseInt(mID));
				}
				
				else if (userInput.equals("7"))
				{
					sendMessage("All the registered clubs: ");
					// retrieving all club names
					searchResult = sharedObject.displayClubs();
					
					// sending all returned names
					int temp = searchResult.size();
					sendMessage(""+temp);
					
					for(int i = 0; i <temp; i++)
					{
						sendMessage(searchResult.get(i));
					}
				}
				
				else if (userInput.equals("8"))
				{
					sendMessage("Please enter the member ID to update the club");
					searchResult = sharedMembers.displayMembers();
					
					// sending list of memebers name + if
					int temp = searchResult.size();
					sendMessage(""+temp);
					
					for(int i = 0; i <temp; i++)
					{
						sendMessage(searchResult.get(i));
					}
					// retrieveing member id to update
					String mID = (String)in.readObject();
					// retrieving new payment status details
					sendMessage("Please enter members new club ID");
					String cID = (String)in.readObject();
					
					sharedMembers.updateClub(Integer.parseInt(mID), cID);
				}
				
				
				// repeat menu ?
				sendMessage("Please enter 1 to repeat the menu option or 9 to quit");
				userInput = (String)in.readObject();		
			}while(userInput.equals("1"));
			
			//User has pressed 9 ( or anything besides 1 really) and quit.
			sendMessage("Thank you and goodbye");
			
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				// closing streams and sockets
				in.close();
				out.close();
				incomingConnection.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Method to send data to client
	private void sendMessage(String msg)
	{
		try {
			// writing out message
			out.writeObject(msg);
			// flushing the line, to write out and clear it
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

