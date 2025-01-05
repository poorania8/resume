package training;

import java.util.ArrayList;
import java.util.Objects;

public class Student {
	String dateOfBirth;
	String fullName;

	ArrayList<String> grades = new ArrayList<String>();

	@Override
	public int hashCode() {
		return Objects.hash(dateOfBirth, fullName, grades);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(grades, other.grades);
	}


	
	
}
