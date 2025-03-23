

public class Professor {
      private String PrFirstName;
      private String PrLastName;
      private String PrEmail;
	public Professor(String prFirstName, String prLastName, String prEmail) {
		super();
		PrFirstName = prFirstName;
		PrLastName = prLastName;
		PrEmail = prEmail;
	}
	private String getPrFirstName() {
		return PrFirstName;
	}
	private void setPrFirstName(String prFirstName) {
		PrFirstName = prFirstName;
	}
	private String getPrLastName() {
		return PrLastName;
	}
	private void setPrLastName(String prLastName) {
		PrLastName = prLastName;
	}
	private String getPrEmail() {
		return PrEmail;
	}
	private void setPrEmail(String prEmail) {
		PrEmail = prEmail;
	}
      
}
