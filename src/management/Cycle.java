package management;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cycle {
	private CycleCode cycle;
    private Map<String, Domain> DomainesMap ;
    
	 public Cycle(String cycle) {
		this.cycle = CycleCode.valueOf(cycle);
		this.DomainesMap = new HashMap<String, Domain>();
	 }
	
	 private CycleCode getCycle() {
			return cycle;
		}

		private void setCycle(CycleCode cycle) {
			this.cycle = cycle;
		}

	private void setCoursesPerTerm(Map<String,Domain> domaines) {
		this.DomainesMap = domaines;
	}
	
	 public void addDomain(Domain domain) {
		 DomainesMap.put(domain.getDomainName(), domain);
	    }
   public void deleteModule(String Domain) {
	
    }
   
public boolean removeDomain(String domainName) {
    if (DomainesMap.containsKey(domainName)) {
    	DomainesMap.remove(domainName);
    return true;
    }
    return false;
    
} 


}