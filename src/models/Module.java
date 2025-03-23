package models;
import java.util.ArrayList;

public class Module {
      private String uniqueName;
      private String name;
      private Cycle cycle;
	public Module(String uniqueName, String name) {
		this.uniqueName = uniqueName;
		this.name = name;
	}
	public String getUniqueName() {
		return uniqueName;
	}
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Cycle getCycle() {
		return cycle;
	}
	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}
	@Override
	public String toString() {
		return "Module [uniqueName=" + uniqueName + ", name=" + name + ", cycle=" + cycle + ", getUniqueName()="
				+ getUniqueName() + ", getName()=" + getName() + ", getCycle()=" + getCycle() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
      
}
