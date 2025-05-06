package management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Domain {
	private static int autoincrement=0;
	private int id;
     private String domainName;
     private int cycle;
	public String getDomainName() {
		// TODO Auto-generated method stub
		return  domainName;
	}

	public Domain(String domainName,int cycle) {
		id=++autoincrement;
		this.domainName =domainName;
		this.cycle= cycle;
	}
    public Domain(int id,String domainName,int cycle) {
    	this.id=id;
    	this.cycle=cycle;
        this.domainName = domainName;
    }

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}


	public Domain(int id, String domainName) {
		this.id = id;
		this.domainName = domainName;

	}


	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	@Override
	public String toString() {
		return domainName ;
	}
    
}