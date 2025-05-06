package management;

	public class ModuleInfo {
	    private String cycleName;
	    private String domainName;
	    private int semesterNo;
	    private String moduleName;

	    public ModuleInfo(String cycleName, String domainName, int semesterNo, String moduleName) {
	        this.cycleName = cycleName;
	        this.domainName = domainName;
	        this.semesterNo = semesterNo;
	        this.moduleName = moduleName;
	    }

	    public String getCycleName() { return cycleName; }
	    public String getDomainName() { return domainName; }
	    public int getSemesterNo() { return semesterNo; }
	    public String getModuleName() { return moduleName; }

	    public void setCycleName(String cycleName) {
			this.cycleName = cycleName;
		}

		public void setDomainName(String domainName) {
			this.domainName = domainName;
		}

		public void setSemesterNo(int semesterNo) {
			this.semesterNo = semesterNo;
		}

		public void setModuleName(String moduleName) {
			this.moduleName = moduleName;
		}

	    
	}


