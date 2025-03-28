package management;
import java.util.ArrayList;

public class Module {
      private String uniqueName;
      private String name;
      private int coefficient;
	public Module(String uniqueName, String name,int coeff) {
		this.uniqueName = uniqueName;
		this.name = name;
		this.coefficient = coeff;
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
	@Override
	public String toString() {
		return "Module [uniqueName=" + uniqueName + ", name=" + name + ", cycle=" +   ", getUniqueName()="
				+ getUniqueName() + ", getName()=" + getName() + ", getCycle()=" +   ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
      
}
