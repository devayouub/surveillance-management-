package management;
import java.util.ArrayList;

public class Module {
      private String uniqueName;
      
	public Module(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	public String getUniqueName() {
		return uniqueName;
	}
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
}
