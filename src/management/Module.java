package management;
import java.util.ArrayList;

public class Module {
    private String uniqueName;
    private String name;
    private int coefficient;
    private ArrayList<CycleCode> cycles; 

    public Module(String uniqueName, String name, int coeff) {
        this.uniqueName = uniqueName;
        this.name = name;
        this.coefficient = coeff;
        this.cycles = new ArrayList<>(); 
    }

    // Getters et Setters
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

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public ArrayList<CycleCode> getCycles() {
        return cycles;
    }

    public void addCycle(CycleCode cycle) {
        if (!cycles.contains(cycle)) {
            cycles.add(cycle);
        }
    }

    public void removeCycle(CycleCode cycle) {
        cycles.remove(cycle);
    }

    @Override
    public String toString() {
        return "Module [uniqueName=" + uniqueName + ", name=" + name + ", coefficient=" + coefficient + ", cycles=" + cycles + "]";
    }
    public void setCycles(ArrayList<CycleCode> cycles) {
        this.cycles = new ArrayList<>(cycles);  
    }
}
