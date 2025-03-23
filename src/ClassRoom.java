import java.util.ArrayList;

public class ClassRoom {
         private String classnumber;
         private int MinimumPr;
         private int MaximumPr;
         private ArrayList <Professor> professors;
		public ClassRoom(String classnumber, int minimumPr, int maximumPr) {
			if(!classnumber.matches("d{4}$")) {
				  throw new IllegalArgumentException("classroom's number must be constitued of 4 digits");	
				}
			this.classnumber = classnumber;
			this.MinimumPr = minimumPr;
			this.MaximumPr = maximumPr;
			this.professors = new ArrayList<Professor>();
		}
		public String getClassnumber() {
			return classnumber;
		}
		public void setClassnumber(String classnumber) {
			if(!classnumber.matches("d{4}$")) {
				  throw new IllegalArgumentException("classroom's number must be constitued of 4 digits");	
				}
			this.classnumber = classnumber;
		}
		public int getMinimumPr() {
			return MinimumPr;
		}
		public void setMinimumPr(int minimumPr) {
			MinimumPr = minimumPr;
		}
		public int getMaximumPr() {
			return MaximumPr;
		}
		public void setMaximumPr(int maximumPr) {
			MaximumPr = maximumPr;
		}
	
		
}
