package training;
//import java.util.ArrayList;

public class Teacher{
	String name;
	String dateOfBirth;
	String experience;
	
	boolean assigned;
	
//	int grade;
//	int section;
	
	// ctor
	public Teacher() {
		name = null;
		dateOfBirth = null;
		experience = null;
	}
	// copy ctor
	public Teacher(Teacher other) {
		this.name = other.name;
		this.dateOfBirth = other.dateOfBirth;
		this.experience = other.experience;
		this.assigned = other.assigned;
	}
	//ArrayList<String> teacherNames;
}