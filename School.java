package training;
import java.util.*;

public class School {
	String schoolName;
	int totalStudents = 0;
	
	ArrayList<Class> classes = new ArrayList<Class>();
	ArrayList<Teacher> allTeachers = new ArrayList<Teacher>();
	
	// add member function to check for duplicates in classes, teachers, and students
	
 
	Teacher duplTeacher (String teacherName) {
		for(Teacher teacher : allTeachers) {
			if(teacherName.equals(teacher.name)) {
				return teacher;
			}
		}
	
		return null; // needed for if teacher is not found
	}
	
	
	
	Class duplClass (Class newClass) {
		for(Class classroom : classes) {
			if(newClass.grade == classroom.grade && newClass.section == classroom.section) {
				return classroom;
			}
		}
		return null; //   if returns newClass = class does not exists
	}
	
	Student duplStudent (Student findStudent) {
		for(Class classroom : classes) {
			for(Student student : classroom.students) {
				if(student.fullName.equals(findStudent.fullName)) {
					return student;
				}
			}
		}
		
		return null;
	}
	
	Class studentClass (Student findStudent) {
		for(Class classroom : classes) {
			for(Student student : classroom.students) {
				if(student.fullName.equals(findStudent.fullName)) {
					return classroom;
				}
			}
		}
		
		return null;
	}
	
	
}
