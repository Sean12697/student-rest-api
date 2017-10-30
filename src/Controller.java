import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Student> students = StudentDAO.getAllStudents();
		for (int x=0; x<students.size(); x++)
		    System.out.println(students.get(x).getName());
	}
}
