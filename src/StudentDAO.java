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
	
	public static ArrayList<Student> getAllStudents() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet resultset = null;
		ArrayList<Student> students = new ArrayList<Student>();
		String query = "SELECT * FROM students;";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
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
		} return students;
	}
	
	public static Student getStudent(String StudentNumber) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet resultset = null;
		Student student = null;
		String query = "SELECT * FROM students WHERE StudentNumber = " + StudentNumber + ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute SQL query
			resultset = statement.executeQuery(query);
			while (resultset.next()) {
				String Name = resultset.getString("Name");
				String Gender = resultset.getString("Gender");
				String DOB = resultset.getString("DOB");
				String Address = resultset.getString("Address");
				String Postcode = resultset.getString("Postcode");
				int StudentNumberID = resultset.getInt("StudentNumber");
				String CourseTitle = resultset.getString("CourseTitle");
				String StartDate = resultset.getString("StartDate");
				float Bursary = resultset.getFloat("Bursary");
				String Email = resultset.getString("Email");
				student = new Student(Name, Gender, DOB, Address, Postcode, StudentNumberID, CourseTitle, StartDate, Bursary, Email);
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
		} return student;
	}
	
	public static Boolean insertStu(Student s) throws SQLException {
		Boolean works = false;
		Connection dbConnection = null;
		Statement statement = null;
		String data = "Name, Gender, DOB, Address, Postcode, StudentNumber, CourseTitle, StartDate, Bursary, Email";
		String stringified = "'" + s.getName() + "', '" + s.getGender() + "', '" + s.getDob() + "', '" + s.getAddress() + "', '" + s.getPostcode() + "', " + s.getStudentNumber() + ", '" + s.getCourseTitle() + "', '" + s.getStartDate() + "', " + s.getBursary() + ", '" + s.getEmail() + "'";
		String query = "INSERT INTO students (" + data + ") VALUES (" + stringified + ");";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			statement.executeUpdate(query);
			works = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			works = false;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			} 
		} return works;
	}
	
	public static Boolean deleteStu(String StudentNumber) throws SQLException {
		Boolean works = false;
		Connection dbConnection = null;
		Statement statement = null;
		String query = "DELETE FROM students WHERE StudentNumber = " + StudentNumber + ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			statement.executeUpdate(query);
			works = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			works = false;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			} 
		} return works;
	}
	
	public static Boolean updateStu(Student s) throws SQLException {
		Boolean works = false;
		Connection dbConnection = null;
		Statement statement = null;
		String set = "Name = '" + s.getName() + "', Gender = '" + s.getGender() + "', DOB = '" + s.getDob() + "', Address = '" + s.getAddress() + "', Postcode = '" + s.getPostcode() + "', CourseTitle = '" + s.getCourseTitle() + "', StartDate = '" + s.getStartDate() + "', Bursary = " + s.getBursary() + ", Email = '" + s.getEmail() + "'";
		String query = "UPDATE students SET " + set + " WHERE StudentNumber = " + s.getStudentNumber() + ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			statement.executeUpdate(query);
			works = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			works = false;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			} 
		} return works;
	}
	/*
	public static Boolean checkLoginCredentials(String x, String y) {
		
	}
	
	public static Boolean checkApiKey(String s) {
		
	}
	*/
}