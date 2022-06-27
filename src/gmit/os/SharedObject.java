package gmit.os;

import java.io.*;
import java.util.*;

public class SharedObject {
	
	LinkedList<ClubInfo> clubEntries;
	
	// Constructor
	public SharedObject() {
		clubEntries = new LinkedList<ClubInfo>();
		// variable to store values, line by line
		String line;
		// Temporary array to store the three entries 
		String temp[] = new String[3];
		
		try {
			// Reading in previous entries
			FileReader fR = new FileReader("clubs.txt");
			// Using BufferedReader to read in character stream
			BufferedReader bR = new BufferedReader(fR);
			
			// While loop to read on each value
			while((line = bR.readLine()) != null)
			{
				// Split the string at every white space using StringTokenizer
				StringTokenizer stk = new StringTokenizer(line, " ");
				
				for(int i = 0; i < 3; i++)
				{
					// populate the temp array with each value from the split string
					temp[i] = stk.nextToken();
				}
				// creating a temporary ClubInfo object, which will then be added to the local linked list
				ClubInfo tempEntry = new ClubInfo(temp[0], temp[1], temp[2]);
				clubEntries.add(tempEntry);
			}
			// Closing readers
			bR.close();
			fR.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	// Add new club entry to the clubs.txt file, using a synchronized method so only one new entry can write at a time
	public synchronized void addEntry(String cName, String cID, String cEmail)
	{
		// Creating a temporary object from values sent via parameter
		ClubInfo temp = new ClubInfo(cName, cID, cEmail);
		clubEntries.add(temp);
		
		// Need to also maintain the text file
		String line;
		
		try {
			FileWriter fR = new FileWriter("clubs.txt", true);
			BufferedWriter bR = new BufferedWriter(fR);
			
			// Making one string out of all the parameters received
			line = cName + " " + cID + " " + cEmail + "\n";
			// Appending to file
			bR.append(line);
			// Closing readers
			bR.close();
			fR.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Method to see if a club name and ID are on the linked list
	public synchronized boolean searchClub(String name, String id)
	{
		boolean found= false;
		// Using an iterator to look through the clubEntries list
		Iterator<ClubInfo> it = clubEntries.iterator();
		// Need a temp ClubInfo object to compare
		ClubInfo temp;
		
		while(it.hasNext())
		{
			temp = it.next();
			
			if(temp.getClubName().equals(name) && temp.getClubID().equals(id))
			{
				found = true;
			}
		}
		return found;
	}
	
	public synchronized ArrayList<String> displayClubs()
	{
		Iterator<ClubInfo> iter = clubEntries.iterator();
		ArrayList<String> tempArray = new ArrayList<>();
		ClubInfo temp;
		boolean found = false;
		
		while(iter.hasNext())
		{
			temp = iter.next();
			
			tempArray.add(temp.getClubName());
		}
		return tempArray;
	}
	
}
