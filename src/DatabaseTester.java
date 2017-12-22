import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseTester {

	public static StudentDAO dao = new StudentDAO();
	
	public static void main(String[] args) throws SQLException {
		printStudentsInDB();
		System.out.println();
		// Get & Print
		System.out.println(dao.getStudent("14").getName());
		// Delete
		System.out.println();
		String worked = (dao.deleteStu("1")) ? "Worked" : "Failed";
		System.out.println(worked);
		// Print Deleted
		printStudentsInDB();
		System.out.println();
		// Insert
		Student e = new Student("Sean Omahoney", "Male", "12-06-1997", "Manchester", "M10 5QA", 1, "Computer Science", "01-10-2016", 9000, "sean@mail.com");
		String worked2 = (dao.insertStu(e)) ? "Worked" : "Failed";
		System.out.println(worked2);
		// Print
		printStudentsInDB();
		System.out.println();
		// Update
		Student i = new Student("Sean OMahoney", "Male", "12-06-1997", "Manchester", "M10 5QA", 1, "Computer Science", "01-10-2016", 9000, "sean@mail.com");
		String worked3 = (dao.updateStu(i)) ? "Worked" : "Failed";
		System.out.println(worked3);
		// Print
		printStudentsInDB();
	}
	
	public static void printStudentsInDB() throws SQLException {
		ArrayList<Student> students = dao.getAllStudents();
		for (int x=0; x<students.size(); x++)
		    System.out.println(students.get(x).getName());
	}
}
