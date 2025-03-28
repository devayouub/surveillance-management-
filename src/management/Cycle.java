package management;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cycle {
	private CycleCode cycle;
    private Map<Integer, ArrayList<Module>> coursesPerTerm;
    
	 public Cycle(CycleCode cycle) {
		this.cycle = cycle;
		this.coursesPerTerm = new HashMap<>();
		
		// Initialize courses list for each term
		for (int i = 1; i <= 2; i++) {
            coursesPerTerm.put(i, new ArrayList<Module>());
	}
	 }
		public Cycle(CycleCode cycle,int terms) {
			this.cycle = cycle;
			this.coursesPerTerm = new HashMap<>();
			
			// Initialize courses list for each term
			for (int i = 1; i <= terms; i++) {
	            coursesPerTerm.put(i, new ArrayList<Module>());
		}
	}
	
	 private CycleCode getCycle() {
			return cycle;
		}

		private void setCycle(CycleCode cycle) {
			this.cycle = cycle;
		}
	public ArrayList<Module> getCoursesPerTerm(int term) {
		  return this.coursesPerTerm.get(term);
	}

	private void setCoursesPerTerm(Map<Integer, ArrayList<Module>> coursesPerTerm) {
		this.coursesPerTerm = coursesPerTerm;
	}
	
	public void addModule(Module Module,int term) {
		getCoursesPerTerm(term).add(Module);
	   }
   public void deleteModule(int term,String Uniquename) {
	for( Module tempModule : this.getCoursesPerTerm(term)) {
	   if(tempModule.getUniqueName().equals(Uniquename)) {
		   this.getCoursesPerTerm(term).remove(tempModule);
	   }
    }
   }
	   public void updateModule(String Uniquename,int term,Module newModule) {
		   for( Module tempModule : this.getCoursesPerTerm(term)) {
			   if(tempModule.getUniqueName().equals(Uniquename)) {
			 	tempModule.setName(newModule.getName());
			 	tempModule.setUniqueName(newModule.getUniqueName());
			   }
	   }
	   }
	   public static void main(String[]args) {
	   
	   Cycle cycleM2= new Cycle(CycleCode.valueOf("M2"));
	   cycleM2.addModule(new Module("ANALYSE", "anal1", 0),1);
	   cycleM2.addModule(new Module("POO", "POO",4),1);
	   cycleM2.addModule(new Module("BDD", "BDD",5),1);
	   cycleM2.deleteModule(1,"POO");
	   cycleM2.updateModule("ANALYSE",1,new Module("ao2","AO2",6));
	   for(Module tempModule : cycleM2.getCoursesPerTerm(1)) {
	   System.out.println(tempModule.getName());
   }
		   
	   }
}



