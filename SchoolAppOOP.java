package training;
import java.io.File; // need to import in order to access xml file when reading in 
import java.util.*;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.transform.Transformer; 
import javax.xml.transform.TransformerFactory; 
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.stream.StreamResult; 
import org.w3c.dom.Document; 
import org.w3c.dom.Element; 
import org.w3c.dom.NodeList; 
import org.w3c.dom.Node;

public class SchoolAppOOP {

	public static void main(String[] args) throws Exception { // added 
		Scanner sc = new Scanner(System.in);
		int menuSelection = 0;
		School school = new School();
		
		while (menuSelection != 16) {
			String [] menu  = { "0. Reaad in data from xml file",
					 			"1. Enter School Name",
								"2. Add a Teacher",
								"3. Edit a teacher's information",
								"4. Remove a teacher",
								"5. Add a class", 
								"6. Remove a class", 
								"7. Assign a teacher to a class",
								"8. Add a new student", 
								"9. Edit student information",
								"10. Remove a student",
								"11. Grade a student",
								"12. Show the list of teachers", 
								"13. Show the list of classes", 
								"14. Show the list of students in a class",
								"15. Show the student information",
								"16. Exit Application",
								"Input chosen menu option. (1-16)"};
		
			for (int i = 0; i < menu.length; ++i) {
				System.out.println(menu[i]);
			}
		
			menuSelection = sc.nextInt();
			
			
		// 0. Read in data from xml file
			if(menuSelection == 0) {
				File xmlFile = new File("/Users/pooranicoding/Documents/SysThink Training/Java/SchoolApp/src/training/schoolOutput.xml");
				// Create a DocumentBuilder 
		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		        DocumentBuilder builder = factory.newDocumentBuilder(); 
		  
		        // Parse the XML file 
		        Document document = builder.parse(xmlFile);
		        
		        // reading in school name
		        // make sure to convert from string to int
		        Element rootElement = document.getDocumentElement();
		        NodeList schoolName = rootElement.getElementsByTagName("Name"); // returns NodeList, get length and iterate through to read in information
		        // then use getTextContent
		        school.schoolName = schoolName.item(0).getTextContent();
		        
		        //reading in teachers
		        NodeList teachers = rootElement.getElementsByTagName("Teachers"); // returns node list of all teachers
		        for(int i = 0; i < teachers.getLength(); ++i) { //iterate through
		        	Teacher readTeacher = new Teacher(); // create new teacher
		        	NodeList teacherInfo = teachers.item(i).getChildNodes(); // get node list of the infor
		        	readTeacher.name = teacherInfo.item(0).getTextContent(); // assign info
		        	readTeacher.dateOfBirth = teacherInfo.item(1).getTextContent();
		        	readTeacher.experience = teacherInfo.item(2).getTextContent();
		        	
		        	if(teacherInfo.item(3).getTextContent().equals("true")) {
		        		readTeacher.assigned = true;
		        	}
		        	else if(teacherInfo.item(3).getTextContent().equals("false")) {
		        		readTeacher.assigned = false;
		        	}
		        	
		        	
		        	school.allTeachers.add(readTeacher);
		        	
		        }
		        
		        // reading in classes
		        NodeList classes = rootElement.getElementsByTagName("Classes"); // returns node list of all teachers
		        for(int i = 0; i < classes.getLength(); ++i) { //iterate through
		        	Class addClass = new Class();
		        	// read in grade and section
		        	NodeList classInfo = classes.item(i).getChildNodes();
		        	addClass.grade = Integer.parseInt(classInfo.item(0).getTextContent());
		        	addClass.section = Integer.parseInt(classInfo.item(1).getTextContent());
		        	
		        	Teacher teacher = new Teacher();
		        	Node teacherFind = classInfo.item(2);
		        	teacher.name = teacherFind.getFirstChild().getTextContent();
		        	addClass.teacher = school.duplTeacher(teacher.name);
		        	
		        	for(int j = 3; j < classInfo.getLength(); ++ j) { //iterators through just the rest of the nodelist which is students
		        		Student addStudent = new Student();
		        		NodeList studentInfo = classInfo.item(j).getChildNodes();
		        		addStudent.fullName = studentInfo.item(0).getTextContent();
		        		addStudent.dateOfBirth = studentInfo.item(1).getTextContent();
		        		
		        		for(int k = 2; k < studentInfo.getLength(); ++k) { //iterate through rest of student info
		        			addStudent.grades.add(studentInfo.item(k).getTextContent());
		        		}
		        		addClass.students.add(addStudent);
		        		++ school.totalStudents;
		        	}
		        	
		        	school.classes.add(addClass);
		        	
		        }
			}
			
		// 1. Enter school name
			if(menuSelection == 1) {
				sc = new Scanner(System.in);
				
				System.out.println("Enter School Name: ");
				school.schoolName = sc.nextLine();
				
			}
			
		// 2. Add a teacher
			if(menuSelection == 2) {
				sc = new Scanner(System.in);
				Teacher newTeacher = new Teacher();
				//String newTeacherName;
				
				
				while (true) { 
					System.out.println("Enter the new teacher's name: ");
					newTeacher.name = sc.nextLine();
					
					
					if(school.duplTeacher(newTeacher.name) == null || school.allTeachers.isEmpty()) { //teacher does not already exist
						break;
					}
					else if(school.duplTeacher(newTeacher.name) != null) { // teacher does already exist
						System.out.println("A teacher with this name has already been entered. Please enter a different name. ");
						newTeacher.name = null; // makes it null
						
					}
					
					
				}	
				System.out.println("Enter the new teacher's date of birth: ");
				newTeacher.dateOfBirth = sc.nextLine();
				
				System.out.println("Enter the new teacher's experience: ");
				newTeacher.experience = sc.nextLine();
				
				newTeacher.assigned = false; // new teacher by default is unassigned
				
				school.allTeachers.add(newTeacher);
				System.out.println("Teacher sucessfully added");
				
			}
			
		// 3. Edit a teacher
			if(menuSelection == 3) {
				sc = new Scanner(System.in);
				Teacher editTeacher = new Teacher();
				
				while(true) {
					System.out.println("Enter the teacher's name that is being edited: ");
					editTeacher.name = sc.nextLine();
					
					//using duplicate function to determine if teacher exists or not
					if(school.duplTeacher(editTeacher.name) == null) { // teacher does not exist
						System.out.println("This teacher does not exist");
					}
					else {
						System.out.println("Enter the edited date of birth: ");
						school.duplTeacher(editTeacher.name).dateOfBirth = sc.nextLine();
						
						System.out.println("Enter the edited experience");
						school.duplTeacher(editTeacher.name).experience = sc.nextLine();
						break;
					}
				} 
				
				System.out.println("Teacher sucessfully edited");
				
				
			}
		//4. Remove a teacher
			if(menuSelection == 4) {
				sc = new Scanner(System.in);
				
				String teacherName;
				
				while(true) {
					System.out.println("Enter the teacher's name that is being removed: ");
					teacherName = sc.nextLine();
					
					if(school.duplTeacher(teacherName) == null) { // teacher does not exist
						System.out.println("This teacher does not exist");
					}
					else {
						Teacher removeTeacher = school.duplTeacher(teacherName);
						school.allTeachers.remove(removeTeacher);
						
						System.out.println("Teacher sucessfully removed");
						break;
					}
				}
				
				
				
				
			}
			
		// 5. Add a class
			if(menuSelection == 5) {
				sc = new Scanner(System.in);
				
				Class newClass = new Class();
				
				
				// check for already existing class
				while(true) {
					System.out.println("Input the grade of the class you would like to add(1-12): ");
					newClass.grade = sc.nextInt();
					
					System.out.println("Input the section of the class you would like to add(1-3): ");
					newClass.section = sc.nextInt();
					
					if(school.duplClass(newClass) == null || school.classes.isEmpty()) {
						break;
					}
					else if(school.duplClass(newClass) != null) {
						System.out.println("This class already exists.");
						newClass.grade = 0;
						newClass.section = 0;
					}
					
				}
				
				school.classes.add(newClass);
				System.out.println("Class sucessfully added");
					
				
			}
			
		// 6. Remove a class
			if(menuSelection == 6) {
				sc = new Scanner(System.in);
				Class removeClass = new Class();
				System.out.println("Enter the grade of the class you would like to remove: ");
				removeClass.grade = sc.nextInt();
				System.out.println("Enter the section of the class you would like to remvoe");
				removeClass.section = sc.nextInt();
				
				removeClass = school.duplClass(removeClass);
				
				if(removeClass != null) { //class exists
					if(removeClass.students.isEmpty() == false) {
						System.out.println("There are students assigned to this class. Can't be removed");
					}
					else {
						school.classes.remove(removeClass);
						System.out.println("Class sucessfully removed");
					}
				}
				else { // class doesn't exist
					System.out.println("This class cannot be found");
				}
					
			}
		
		//7. Assign a teacher to a class
			if(menuSelection == 7) {
				sc = new Scanner(System.in);
				
				
				boolean classFound = false;
				Class assignClass = new Class();
				// ensuring class exists
				while(true) {
					System.out.println("Input the grade of the class you would like to assign a teacher to(1-12): ");		
					assignClass.grade = sc.nextInt();
					System.out.println("Input the section of the class you would like to assign a reacher to(1-3): ");
					assignClass.section = sc.nextInt();
					
					
					for(Class classroom : school.classes) {
						if(classroom.grade == assignClass.grade && classroom.section == assignClass.section) {
							classFound = true;
						}
					}
					
					if(classFound) {
						
						break;
					}
					else {
						System.out.println("This class does not exist.");
					}
				}
				
				String teacherName = sc.nextLine();
				// printing unassigned teacher ensures only existing, unassigned teacher is chosen
				System.out.println("Unassigned teachers:");
				for(Teacher teach : school.allTeachers){
					
					if(teach.assigned == false) {
						System.out.println(teach.name);
					}
				}
				System.out.println("Enter the name of the teacher you would like to assign.");
				
				teacherName = sc.nextLine();
				
				school.duplClass(assignClass).teacher = school.duplTeacher(teacherName); // creating a duplicate?
				school.duplTeacher(teacherName).assigned = true;
							
				
			}
			
		// 8. Add a new student
			if(menuSelection == 8) {
				sc = new Scanner(System.in);
				
				Student newStudent = new Student();
				
				while(true) {
					System.out.println("Enter the full name of the new student");
					newStudent.fullName = sc.nextLine();
					
					
					if(school.duplStudent(newStudent) == null) {
						System.out.println("Enter the date of birth of the new student");
						newStudent.dateOfBirth = sc.nextLine();
						break;
					}
					else {
						System.out.println("This student already exists. Enter a different name.");
					}
				}
				
				
				
				while(true) {
					Class addClass = new Class();
					System.out.println("Enter the grade of the class you would like to assign the student to: ");
					addClass.grade = sc.nextInt();
					
					System.out.println("Enter the section of the class you would like to assign the student to: ");
					addClass.section = sc.nextInt();
					addClass = school.duplClass(addClass);
					
					if(addClass == null) { // class doesn't exist
						System.out.println("This class does not exist. Enter a valid class");
					}
					else { //class exists
						addClass.students.add(newStudent);
						System.out.println("New student added sucessfully");
						++ school.totalStudents;
						break;
					}
				}
				
			}
			
		//9. Edit student information
			if(menuSelection == 9) {
				sc = new Scanner(System.in);
				
				Student editedStudent = new Student();
				
				
				while(true) {
					System.out.println("Enter the full name of the student you would like to edit");
					editedStudent.fullName = sc.nextLine();
					
					
					
					
					
					if(school.duplStudent(editedStudent) != null) {
						
						//remove student from current
						Class studentClass = new Class();
						studentClass = school.studentClass(editedStudent);
						Student removeStudent = school.duplStudent(editedStudent);
						studentClass.students.remove(removeStudent);
						
						// get edited date of birth
						System.out.println("Enter the edited date of birth of the student");
						editedStudent.dateOfBirth = sc.nextLine();
						break;
					}
					else {
						System.out.println("This student doesn't exist. Enter a valid name.");
					}
				}
				
				
				while(true) {
					Class studentClass = new Class();
					System.out.println("Enter the edited grade.");
					studentClass.grade = sc.nextInt();
					System.out.println("Enter the edited section.");
					studentClass.section = sc.nextInt();
					
					studentClass = school.duplClass(studentClass);
					
					if(studentClass != null) {
						studentClass.students.add(editedStudent);
						break;
					}
					else {
						System.out.println("This class doesn't exist and the student cannot be moved here.");
					}
					
				}
				
				
			}
		
		//10. Remove a student
			if(menuSelection == 10) {
				sc = new Scanner(System.in);
				
				while(true) {
					Student removeStudent = new Student();
					System.out.println("Enter the full name of the student you would like to remove");
					removeStudent.fullName = sc.nextLine();
					
					removeStudent = school.duplStudent(removeStudent);
					
					if(removeStudent == null) {
						System.out.println("This student doesn't exist. Enter a valid name.");
					}
					else {
						Class removeClass = school.studentClass(removeStudent);
						removeClass.students.remove(removeStudent);
						System.out.println("Student sucessfully removed.");
						break;
					}
				}
				
				
				
				--school.totalStudents;
				
			}

		//11. Grade a student
			if(menuSelection == 11) {
				sc = new Scanner(System.in);
				
				while(true) {
					Student findStudent = new Student();
					System.out.println("Enter the full name of the student you would like to grade");
					findStudent.fullName = sc.nextLine();
					
					findStudent = school.duplStudent(findStudent);
					
					if(findStudent == null) {
						System.out.println("This student doesn't exist. Enter a valid name.");
					}
					else {
						System.out.println("Enter this student's grade for English: ");
						findStudent.grades.add(sc.nextLine());
						System.out.println("Enter this student's grade for Math: ");
						findStudent.grades.add(sc.nextLine());
						System.out.println("Enter this student's grade for Science: ");
						findStudent.grades.add(sc.nextLine());
						System.out.println("Enter this student's grade for History: ");
						findStudent.grades.add(sc.nextLine());
						System.out.println("Enter this student's grade for Music: ");
						findStudent.grades.add(sc.nextLine());
						System.out.println("Student sucessfully graded.");
						break;
					}
				}
			}

		//12. Show the list of teachers
			if(menuSelection == 12) {
				System.out.println("School Name: " + school.schoolName);
				// find number of assigned and unassigned teachers
				int numAssigned = 0;
				int numUnassigned = 0;
				for(Teacher teach : school.allTeachers) {
					if(teach.assigned == true) {
						++numAssigned;
					}
					else {
						++numUnassigned;
					}
				}
				
				System.out.println("Total Number of Teachers: " + school.allTeachers.size());
				System.out.println("Total Number of Assigned Teachers: " + numAssigned);
				System.out.println("Total Number of Unassigned Teachers: " +  numUnassigned);
				
				System.out.println("Name    | DOB    | Experience  | Class, Section  |");
				System.out.println("--------------------------------------------------");
				
				// print unassigned teachers from allTeachers 
				for(Teacher teacher : school.allTeachers) {
					if(teacher.assigned == false) {
						System.out.println(teacher.name + "     " + teacher.dateOfBirth + "     " 
								+ teacher.experience + "    " + "Teacher not assigned to class");
					}
				}
				
				//print assigned teacher from school.classes
				for(Class classroom : school.classes) {
					Teacher teacher = classroom.teacher;
					System.out.println(teacher.name + "     " + teacher.dateOfBirth + "     " 
							+ teacher.experience + "    " + classroom.grade + ", " + classroom.section);
				}
			}

			//13. Show the list of Classes
			if(menuSelection == 13) {
				sc = new Scanner(System.in);
				
				System.out.println("School Name: " + school.schoolName);
				
				if(school.classes.isEmpty()) {
					System.out.println("No classes currently");
				}
				else {
					System.out.println("Total number of classes: " + school.classes.size());
					int avgStudents =  school.totalStudents/school.classes.size();
					System.out.println("Avg num students per class: " + avgStudents);
					System.out.println("Grade    | Section    | Teacher     | Num Students  |");
					
					
					for(Class classroom : school.classes) {
						System.out.println(classroom.grade + "    " + classroom.section +  "    " + classroom.teacher.name +  "    " + classroom.students.size());
					}
				}
			}

		//14. Show the list of students in a class
			if(menuSelection == 14) {
				sc = new Scanner(System.in);
				
				System.out.println("What grade would you like to find: ");
				int grade = sc.nextInt();
				
				System.out.println("What section would you like to find: ");
				int section = sc.nextInt();
				
				boolean foundClass = false;
				for(Class classroom : school.classes) {
					if(classroom.grade == grade && classroom.section == section) {
						foundClass = true;
						System.out.println("School name: " + school.schoolName);
						System.out.println("Grade: " + grade + " Section: " + section);
						System.out.println("Teacher: " + classroom.teacher.name);
						System.out.println("Number of students: " + classroom.students.size());
						
						System.out.println("First Name    | Last Name    | Date of Birth     | English | Math  | Science  |  History| Music |");
						
						for(Student student: classroom.students) {
							if(student.grades.isEmpty()) {
								System.out.println(student.fullName + "    " + student.dateOfBirth + "    " + "No grades yet");
							}
							else {
								System.out.println(student.fullName  + "    " + student.dateOfBirth + "    " + student.grades.get(0) + "    " + student.grades.get(1) +
										 "    " + student.grades.get(2) +  "    " + student.grades.get(3) +  "    " + student.grades.get(4));
							}
						}
						
						break;
					}
					else {
						foundClass = false;
					}
				}
				
				if(foundClass == false) {
					System.out.println("This class could not be found");
				}
				
			}

		//15. Show the student information
			if(menuSelection == 15) {
				sc = new Scanner(System.in);
				
				System.out.println("Enter the full name of the student whose information you would like to see. ");
				String studentName = sc.nextLine();
				
				boolean studentExists = false;
				for(Class classroom : school.classes) {
					for(Student student : classroom.students) {
						if(student.fullName.equals(studentName)) {
							
							System.out.println("Grade: " + classroom.grade + "| Section: " + classroom.section);
							System.out.println("Teacher: " + classroom.teacher.name);
							
							System.out.println("Grades");
							System.out.println("| English | Math  | Science  |  History| Music |");
							System.out.println(student.grades.get(0) + "    " + student.grades.get(1) +
									 "    " + student.grades.get(2) +  "    " + student.grades.get(3) +  "    " + student.grades.get(4));
							studentExists = true;
						}
					}
				}
				
				if(studentExists == false) {
					System.out.println("This student does not exist");
				}
				
			}

			// 16. Exit the application
			if(menuSelection == 16) {
				// write school information into xml file
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		        DocumentBuilder builder = factory.newDocumentBuilder(); // had error, added throws exception in start of main func
		  
		        // Create a new Document 
		        Document document = builder.newDocument();
		        
		        // add root element (school)
		        // classes and teachers are appended to this
		        Element schoolRoot = document.createElement("School"); 
		        document.appendChild(schoolRoot);
		        // writing school name
		        Element elem1 = document.createElement("Name"); 
		        elem1.appendChild(document.createTextNode(school.schoolName)); 
		        schoolRoot.appendChild(elem1); 
		        
		        // writing all teachers
		        for(Teacher teach : school.allTeachers) {
		        	Element teacher = document.createElement("Teachers");
			        schoolRoot.appendChild(teacher); // teacher is child of school 
			        
			        Element name = document.createElement("name"); // member variables are child of teacher
			        name.setTextContent(teach.name);
			        teacher.appendChild(name);
			        
			        Element dob = document.createElement("DOB");
			        dob.setTextContent(teach.dateOfBirth);
			        teacher.appendChild(dob);
			        
			        Element exp = document.createElement("Experience");
			        exp.setTextContent(teach.experience);
			        teacher.appendChild(exp);
			        
			        Element assigned = document.createElement("Assigned");
			        if(teach.assigned) {
			        	assigned.setTextContent("true");
			        }
			        else {
			        	assigned.setTextContent("false");
			        }
			        teacher.appendChild(assigned);
			        
		        }
		        
		        // writing all classes
		        for(Class classroom : school.classes) {
		        	Element classElem = document.createElement("Classes");
		        	schoolRoot.appendChild(classElem);
		        	// append grade and section
		        	Element grade = document.createElement("grade");
		        	grade.setTextContent(String.valueOf(classroom.grade));
		        	classElem.appendChild(grade);
		        	Element section = document.createElement("section");
		        	section.setTextContent(String.valueOf(classroom.section));
		        	classElem.appendChild(section);
		        	
		        	// append teacher 
		        	Element teacher = document.createElement("Teacher");
			        classElem.appendChild(teacher); // teacher is child of class
			        
			        Element name = document.createElement("name"); // member variables are child of teacher
			        name.setTextContent(classroom.teacher.name);
			        teacher.appendChild(name);
			        
			        // append students
			        for(Student student : classroom.students) {
			        	Element stud = document.createElement("Students");
			        	classElem.appendChild(stud);
			        	
			        	Element studName = document.createElement("name"); // member variables are child of teacher
				        studName.setTextContent(student.fullName);
				        stud.appendChild(studName);
				        
				        Element dob = document.createElement("DOB");
				        dob.setTextContent(student.dateOfBirth);
				        stud.appendChild(dob);
				        
				        for(int i = 0; i < student.grades.size(); ++ i) {
				        	Element graded = document.createElement("grade");
				        	graded.setTextContent(student.grades.get(i));
				        	stud.appendChild(graded);
				        }
				        
				        
			        }
		        }
		        
		        TransformerFactory transformerFactory = TransformerFactory.newInstance(); 
		        Transformer transformer = transformerFactory.newTransformer();  // had error, added throws exception in start of main func
		        DOMSource source = new DOMSource(document); 
		        
		        StreamResult result = new StreamResult("/Users/pooranicoding/Documents/SysThink Training/Java/SchoolApp/src/training/schoolOutput.xml"); 
		        transformer.transform(source, result);
		        
				sc.close();
				System.exit(0);
				
			}
			
			
			
			

	}
	

}
}

