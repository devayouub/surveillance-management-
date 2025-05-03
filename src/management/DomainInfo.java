package management;

public class DomainInfo {
	private int id;
    private String cycleName;
    private String domainName;
    public DomainInfo(String cycleName, String domainName) {
        this.cycleName = cycleName;
        this.domainName = domainName;
    }

    public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getCycleName() { return cycleName; }
    public String getDomainName() { return domainName;  }

    public void setCycleName(String cycleName) {
		this.cycleName = cycleName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

    
}

