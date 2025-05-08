package management;


public class Professor {
	  private int ProfId;
      private String PrFirstName;
      private String PrLastName;
      private String PrEmail;

	public Professor(String prFirstName, String prLastName, String prEmail) {
		PrFirstName = prFirstName;
		PrLastName = prLastName;
		PrEmail = prEmail;
	}
	
	
	public Professor(int profId, String prFirstName, String prLastName, String prEmail) {
		ProfId = profId;
		PrFirstName = prFirstName;
		PrLastName = prLastName;
		PrEmail = prEmail;
	}


	public int getProfId() {
		return ProfId;
	}

	public void setProfId(int profId) {
		ProfId = profId;
	}

	public String getPrFirstName() {
		return PrFirstName;
	}
	public void setPrFirstName(String prFirstName) {
		PrFirstName = prFirstName;
	}
	public String getPrLastName() {
		return PrLastName;
	}
	public void setPrLastName(String prLastName) {
		PrLastName = prLastName;
	}
	public String getPrEmail() {
		return PrEmail;
	}
	public void setPrEmail(String prEmail) {
		PrEmail = prEmail;
	}
      
}
