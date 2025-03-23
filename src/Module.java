import java.util.ArrayList;

public class Module {
      private String uniqueName;
      private String name;
      private Cycle cycle;
      private int Semestre;
	public Module(String uniqueName, String name, Cycle cycle, int semestre, Professor professor) {
		this.uniqueName = uniqueName;
		this.name = name;
		this.cycle = cycle;
		Semestre = semestre;
	}
	private String getUniqueName() {
		return uniqueName;
	}
	private void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	private String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	private Cycle getCycle() {
		return cycle;
	}
	private void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}
	private int getSemestre() {
		return Semestre;
	}
	private void setSemestre(int semestre) {
		Semestre = semestre;
	}
	
      
}
