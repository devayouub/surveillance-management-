import java.time.LocalDateTime;
import java.util.ArrayList;

public class Exam {
	private Module module;
	private LocalDateTime date;
    private ArrayList <ClassRoom> classrooms;
    private ArrayList <Professor> professors;
	public Exam(Module module, LocalDateTime date) {
		this.module = module;
		this.date = date;
	    this.professors = new ArrayList <Professor>();
		this.classrooms = new ArrayList<ClassRoom>();
	}
	public void addClassRoom(ClassRoom room) {
		classrooms.add(room);
	   }
	   public void deleteClassRoom(int index) {
		   classrooms.remove(index);
	   }
	   public void updateClassRoom(int index,ClassRoom room) {
		   classrooms.set(index, room);
	   }
		private ArrayList<Professor> getProfessors() {
		  	return professors;
		  }

		  private void setProfessors(ArrayList<Professor> professor) {
		  	this.professors = professor;
		  }
		  public void addProfessor(Professor professor) {
			  professors.add(professor);
		     }
		     public void deleteProfessor(int index) {
		    	 professors.remove(index);
		     }
		     public void updateProfessors(int index,Professor professor) {
		    	 professors.set(index, professor);
		     }
}
