import java.util.ArrayList;

public class Cycle {
	private CycleCode cycle;
  private ArrayList<Module> modules;

public Cycle(CycleCode code) {
	this.cycle= code;
	this.modules = new ArrayList<Module>();
}

private ArrayList<Module> getModules() {
	return modules;
}

private CycleCode getCycle() {
	return cycle;
}

private void setCycle(CycleCode cycle) {
	this.cycle = cycle;
}

private void setModules(ArrayList<Module> modules) {
	this.modules = modules;
}
public void addModule(Module Module) {
	modules.add(Module);
   }
   public void deleteModule(int index) {
	   modules.remove(index);
   }
   public void updateModule(int index,Module Module) {
	   modules.set(index, Module);
   }
}
