package management;
import java.util.ArrayList;

public class Module {
      private String uniqueName;
      private String name;
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
}
