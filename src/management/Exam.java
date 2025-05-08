package management;
import java.time.LocalDate;
import java.util.ArrayList;

public class Exam {
	private LocalDate date;
	private String hour;
	private Module module;
	public Exam(LocalDate date, String hour, Module module) {
		super();
		this.date = date;
		this.hour = hour;
		this.module = module;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	
}