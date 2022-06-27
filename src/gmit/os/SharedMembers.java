package gmit.os;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class SharedMembers {
	
	private LinkedList<Member> memberListing;
	
	public SharedMembers()
	{
		memberListing = new LinkedList<Member>();
		// variable to store values, line by line
		String line;
		// Temporary array to store the three entries 
		String entry[] = new String[8];
		Member temp;
		
		try {
			// Reading in previous entries
			FileReader fR = new FileReader("memberInfo.txt");
			// Using BufferedReader to read in character stream
			BufferedReader bR = new BufferedReader(fR);
			
			// While there has been previous entries
			while((line = bR.readLine()) != null)
			{
				System.out.println("" + line);
				entry = line.split(" ");
				
				temp = new Member(entry[0], Integer.parseInt(entry[1]), Integer.parseInt(entry[2]), entry[3], entry[4], Double.parseDouble(entry[5]),  entry[6],  entry[7]);
				
				memberListing.add(temp);
			}
			// closing readers
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
	
	// Add member method, caters for both the linked list and the .txt file
	public synchronized void addMember(String mName, int mAge, int mID, String mClubID, String mDate,
			double mFee, String mType, String mPayStatus)
	{
		Member temp = new Member(mName, mAge, mID, mClubID, mDate, mFee, mType, mPayStatus);
		memberListing.add(temp);
		
		String line;
		try {
			// creating the file writers
			FileWriter fR = new FileWriter("memberInfo.txt", true);
			BufferedWriter bR = new BufferedWriter(fR);
			
			line = mName + " " + mAge + " " + mID + " " + mClubID + " " + mDate + " " + mFee + " " + mType + " " + mPayStatus + "\n";
			// appending to the file
			bR.append(line);
			// close writers 
			bR.close();
			fR.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Method to display all members
	public synchronized ArrayList<String> displayMembers()
	{
		Iterator<Member> iter = memberListing.iterator();
		ArrayList<String> result = new ArrayList<String>();
		Member temp;
		
		while(iter.hasNext())
		{
			temp = iter.next();
			
			result.add(temp.toString());
		}
		return result;
	}
	
	// Method to update membership type and fee amount
	public synchronized void updateTypeFee(int mID, String mType, double mFee)
	{
		Iterator<Member> iter = memberListing.iterator();
		Member temp;
		String line;
		
		// Nuke the text file, get ready for new entries
		nukeFile();
		
		while(iter.hasNext())
		{
			temp = iter.next();
			
			if (temp.getID() == mID)
			{
				temp.setType(mType);
				temp.setfee(mFee);
			}
			try {

				// creating the file writers
				FileWriter fR = new FileWriter("memberInfo.txt", true);
				BufferedWriter bR = new BufferedWriter(fR);
				
				line = temp.getName() + " " + temp.getAge() + " " + temp.getID() + " " + temp.getClubID() + " " + temp.getDate() + " "
									+ temp.getFee() + " " + temp.getType() + " " + temp.getStatus() + "\n";
				// appending to the file
				bR.append(line);
				// close writers 
				bR.close();
				fR.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// Method to update payment status
	public synchronized void updateStatus(int mID, String mStatus)
	{
		Iterator<Member> iter = memberListing.iterator();
		Member temp;
		String line;
		
		// Nuke the text file, get ready for new entries
		nukeFile();
		
		while(iter.hasNext())
		{
			temp = iter.next();
			// Checking if the id matches and upon match, change the payment status
			if (temp.getID() == mID)
			{
				temp.setStatus(mStatus);
			}
			try {
				// creating the file writers
				FileWriter fR = new FileWriter("memberInfo.txt", true);
				BufferedWriter bR = new BufferedWriter(fR);
				
				line = temp.getName() + " " + temp.getAge() + " " + temp.getID() + " " + temp.getClubID() + " " + temp.getDate() + " "
									+ temp.getFee() + " " + temp.getType() + " " + temp.getStatus() + "\n";
				// appending to the file
				bR.append(line);
				// close writers 
				bR.close();
				fR.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// Method to populate an array with all the members who visited in the last 14 days
	public synchronized ArrayList<String> list14Days()
	{
		Iterator<Member> iter = memberListing.iterator();
		ArrayList<String> result = new ArrayList<String>();
		Member temp;
		
		while(iter.hasNext())
		{
			temp = iter.next();
			// Calling the below function to compare dates
			if(last14Days(temp.getDate()) < 14)
			{
				// if true, add to array
				result.add(temp.toString());
			}
		}
		return result;
	}
	
	// Method to check if a member has visited the club in the last 14 days
	public long last14Days(String memDate)
	{
		// Getting current date
		Date currDate = new Date();
		// Specifying format
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/dd/MM");
		// Getting a string of current date
		String newCurr = dateFormat.format(currDate);
		// Running both dates through formmater in order to subtract
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/dd/MM");
		LocalDate date1 = LocalDate.parse(newCurr, formatter);
		LocalDate date2 = LocalDate.parse(memDate, formatter);
		// Subtraction process
		long elapsedDays = ChronoUnit.DAYS.between(date2, date1);
		// returning the days between 
		return elapsedDays;
	}
	
	// Method to populate an array with all the members who have paid
	public synchronized ArrayList<String> hasPaid()
	{
		Iterator<Member> iter = memberListing.iterator();
		ArrayList<String> result = new ArrayList<String>();
		Member temp;
		
		while(iter.hasNext())
		{
			temp = iter.next();
			// Calling the below function to compare dates
			if(temp.getStatus().equalsIgnoreCase("Paid"))
			{
				// if true, add to array
				result.add(temp.toString());
			}
		}
		return result;
	}
	
	// Method to remove member
	public synchronized void removeMember(int mID)
	{
		Iterator<Member> iter = memberListing.iterator();
		Member temp;
		String line;
		
		// Nuke the text file, get ready for new entries
		nukeFile();
		
		while(iter.hasNext())
		{
			temp = iter.next();
			// Checking if the id matches and upon match, change the payment status
			if (temp.getID() != mID)
			{
				// write the entries which don't match the chosen id back to file
				try {
					// creating the file writers
					FileWriter fR = new FileWriter("memberInfo.txt", true);
					BufferedWriter bR = new BufferedWriter(fR);
					
					line = temp.getName() + " " + temp.getAge() + " " + temp.getID() + " " + temp.getClubID() + " " + temp.getDate() + " "
										+ temp.getFee() + " " + temp.getType() + " " + temp.getStatus() + "\n";
					// appending to the file
					bR.append(line);
					// close writers 
					bR.close();
					fR.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				// delete the entry
				iter.remove();
			}
		}
	}
	
	// Method to update members club
	public synchronized void updateClub(int mID, String memClub)
	{
		Iterator<Member> iter = memberListing.iterator();
		Member temp;
		String line;
		
		// Nuke the text file, get ready for new entries
		nukeFile();
		
		while(iter.hasNext())
		{
			temp = iter.next();
			// Checking if the id matches and upon match, change the payment status
			if (temp.getID() == mID && temp.getStatus().equalsIgnoreCase("Paid"))
			{
				temp.setClub(memClub);
			}
			try {
				// creating the file writers
				FileWriter fR = new FileWriter("memberInfo.txt", true);
				BufferedWriter bR = new BufferedWriter(fR);
				
				line = temp.getName() + " " + temp.getAge() + " " + temp.getID() + " " + temp.getClubID() + " " + temp.getDate() + " "
									+ temp.getFee() + " " + temp.getType() + " " + temp.getStatus() + "\n";
				// appending to the file
				bR.append(line);
				// close writers 
				bR.close();
				fR.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// This method is invoked in sections of code where the .txt file needs updating 
	public synchronized void nukeFile()
	{
		String line;
		try {
			// creating the file writers, note no true condition, this will overwrite any entries
			FileWriter fR = new FileWriter("memberInfo.txt");
			BufferedWriter bR = new BufferedWriter(fR);
			
			line = "";
			// appending to the file
			bR.append(line);
			// close writers 
			bR.close();
			fR.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
