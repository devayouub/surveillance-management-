package management;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cycle {
	private int id;
	private String cyclename;
 
	 public Cycle(int id,String cycle) {
		this.id=id;
		 this.cyclename = cycle;
	 }
	
	 public String getCycle() {
			return cyclename;
		}
	 

		public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	private String getCyclename() {
		return cyclename;
	}

	private void setCyclename(String cyclename) {
		this.cyclename = cyclename;
	}

		public void setCycle(String cycle) {
			this.cyclename = cycle;
		}


}