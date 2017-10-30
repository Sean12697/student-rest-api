import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {
	
	public static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			String dbURL = "jdbc:sqlite:studentdb.sqlite";
			dbConnection = DriverManager.getConnection(dbURL);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
	
	@SuppressWarnings("finally")
	public static ArrayList<Student> getAllStudents() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet resultset = null;
		ArrayList<Student> students = new ArrayList<Student>();
		String query = "SELECT * FROM students;";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println(query);
			// execute SQL query
			resultset = statement.executeQuery(query);
			while (resultset.next()) {
				String Name = resultset.getString("Name");
				String Gender = resultset.getString("Gender");
				String DOB = resultset.getString("DOB");
				String Address = resultset.getString("Address");
				String Postcode = resultset.getString("Postcode");
				int StudentNumber = resultset.getInt("StudentNumber");
				String CourseTitle = resultset.getString("CourseTitle");
				String StartDate = resultset.getString("StartDate");
				float Bursary = resultset.getFloat("Bursary");
				String Email = resultset.getString("Email");
				Student x = new Student(Name, Gender, DOB, Address, Postcode, StudentNumber, CourseTitle, StartDate, Bursary, Email);
				students.add(x);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (resultset != null) {
				resultset.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			} 
			return students;
		}
	}
	/*
	public Student getStudent(String s) {
		
	}
	
	public Boolean insertStu(Student s) {
		
	}
	
	public Boolean deleteStu(String s) {
		
	}
	
	public Boolean updateStu(Student s) {
		
	}
	
	public Boolean checkLoginCredentials(String x, String y) {
		
	}
	
	public Boolean checkApiKey(String s) {
		
	}
	*/
}