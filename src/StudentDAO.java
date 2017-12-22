import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {
	
	public Connection getDBConnection() {
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
	
	public ArrayList<Student> getAllStudents() throws SQLException {
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
	
	public Student getStudent(String StudentNumber) throws SQLException {
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
	
	public Boolean insertStu(Student s) throws SQLException {
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
	
	public Boolean deleteStu(String StudentNumber) throws SQLException {
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
	
	public Boolean updateStu(Student s) throws SQLException {
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
	
	public String SHA256(String x) throws NoSuchAlgorithmException {
		StringBuffer hexString = new StringBuffer();
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(x.getBytes(StandardCharsets.UTF_8));
	    for (int i = 0; i < encodedhash.length; i++) {
	    String hex = Integer.toHexString(0xff & encodedhash[i]);
	    if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    } return hexString.toString();
	}
	
	public Boolean uniqueUsername(String x) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet resultset = null;
		Boolean match = true;
		String query = "SELECT * FROM users WHERE username = '" + x + "';";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute SQL query
			resultset = statement.executeQuery(query);
			while (resultset.next()) {
				match = false;
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
		} return match;
	}
	
	public String createUser(String x, String y) throws SQLException, NoSuchAlgorithmException {
		Connection dbConnection = null;
		Statement statement = null;
		String t = generateToken();
		String data = "username, password, token";
		String stringified = "'" + x + "', '" + SHA256(y) + "', '" + t + "'";
		String query = "INSERT INTO users (" + data + ") VALUES (" + stringified + ");";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			} 
		} return t;
	}
	
	public String checkLoginCredentials(String x, String y) throws SQLException, NoSuchAlgorithmException {
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet resultset = null;
		String p = "";
		String t = "";
		int id = 0;
		String query = "SELECT * FROM users WHERE username = '" + x + "';";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute SQL query
			resultset = statement.executeQuery(query);
			while (resultset.next()) {
				p = resultset.getString("password");
				id = resultset.getInt("id");
			} if (p.equals(SHA256(y))) {
				t = updateToken(dbConnection, id);
				return t;
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
		} return "";
	}
	
	public String updateToken(Connection dbConnection, int id) throws SQLException {
		String newToken = generateToken();
		String query = "UPDATE users SET token = '" + newToken + "' WHERE id = " + id + ";";
		dbConnection.createStatement().executeUpdate(query);
		return newToken;
	}
	
	private String generateToken() {
		StringBuffer token = new StringBuffer();
		for (int i = 0; i < 30; i++) {
			token.append((char)((int)33+(Math.random()*93)));
		} return token.toString().replaceAll("\'", "$");
	}

	public boolean checkApiKey(String s) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet resultset = null;
		Boolean match = false;
		String query = "SELECT * FROM users WHERE token = '" + s + "';";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute SQL query
			resultset = statement.executeQuery(query);
			while (resultset.next()) {
				System.out.println(resultset.getString("username") + " used their API key.");
				match = true;
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
		} return match;
	}
	
}