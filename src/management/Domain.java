package management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Domain {
     private String domainName;
     private Map<Integer, List<Module>> semesters;
	public String getDomainName() {
		// TODO Auto-generated method stub
		return this.getDomainName();
	}


    public Domain(String domainName) {
        this.domainName = domainName;
        this.semesters = new HashMap<>();
    }

    public void addModule(int semester, Module module) {
        semesters.computeIfAbsent(semester, k -> new ArrayList<>()).add(module);
    }
}