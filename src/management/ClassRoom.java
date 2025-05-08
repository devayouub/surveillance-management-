package management;
import java.util.ArrayList;

public class ClassRoom {
         private String classnumber;
        private  int min;
        private int max;
        private String Type;
        
		public String getType() {
			return Type;
		}
		public void setType(String type) {
			Type = type;
		}
		public ClassRoom(String classnumber, String type) {
			super();
			this.classnumber = classnumber;
			Type = type;
		}
		public ClassRoom(String classnumber, int min, int max) {
			super();
			this.classnumber = classnumber;
			this.min = min;
			this.max = max;
		}
		public String getClassnumber() {
			return classnumber;
		}
		public void setClassnumber(String classnumber) {
			this.classnumber = classnumber;
		}
		public int getMin() {
			return min;
		}
		public void setMin(int min) {
			this.min = min;
		}
		public int getMax() {
			return max;
		}
		public void setMax(int max) {
			this.max = max;

		}
}
		