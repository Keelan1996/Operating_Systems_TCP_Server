package gmit.os;

public class Member {
	
	private String memberName;
	private int memberAge;
	private int memberID;
	private String memberClubID;
	private String memberDate;
	private double memberFee;
	private String memberType;
	private String memberPayStatus;
	
	// Constructor
	public Member(String name, int age, int id, String clubID, String date,
			double fee, String type, String payStatus) {
		this.memberName = name;
		this.memberAge = age;
		this.memberID = id;
		this.memberClubID = clubID;
		this.memberDate = date;
		this.memberFee = fee;
		this.memberType = type;
		this.memberPayStatus = payStatus;
	}
	
	// Getters
	public String getName()
	{
		return memberName;
	}
	
	public int getAge()
	{
		return memberAge;
	}
	
	public int getID()
	{
		return memberID;
	}
	
	public String getClubID()
	{
		return memberClubID;
	}
	
	public String getDate()
	{
		return memberDate;
	}
	
	public double getFee()
	{
		return memberFee;
	}
	
	public String getType()
	{
		return memberType;
	}
	
	public String getStatus()
	{
		return memberPayStatus;
	}
	
	public String getPaid()
	{
		return memberName + " " + memberID + " " + "payment status: " + memberPayStatus;
	}
	
	// Setters
	public void setType(String mType)
	{
		this.memberType = mType;
	}
	
	public void setfee(double mFee)
	{
		this.memberFee = mFee;
	}
	
	public void setStatus(String mStatus)
	{
		this.memberPayStatus = mStatus;
	}
	public void setClub(String mClub)
	{
		this.memberClubID = mClub;
	}
	
	// To String
	public String toString()
	{
		return "Member Name: " + memberName + " Member ID: " + memberID + " Type: " + memberType + " Fee: " + memberFee + " Status: " + memberPayStatus;
	}

}
