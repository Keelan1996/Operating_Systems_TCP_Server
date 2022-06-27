package gmit.os;

public class ClubInfo {
	
	private String clubName;
	private String clubID;
	private String email;
	
	// Constructor
	public ClubInfo(String cName, String cID, String cEmail)
	{
		clubName = cName;
		clubID = cID;
		email = cEmail;
	}
	
	// Club name getter
	public String getClubName()
	{
		return clubName;
	}
	
	// Club ID getter
	public String getClubID()
	{
		return clubID;
	}

}
