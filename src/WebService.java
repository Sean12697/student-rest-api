import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**  
* WebService.java a class that starts the RESTful API server on the localhost on port 8005
* @author  Sean O'Mahoney - 16042079
* @version 1.0 
* @see Class: WebServiceTester
*/
public class WebService {

	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(8005), 0);
		server.createContext("/get-json", new getJSON());
		server.createContext("/process", new addStu());
		server.createContext("/updateStu", new updateStu());
		server.createContext("/deleteStu", new deleteStu());
		server.createContext("/getStu", new getStu());
		server.createContext("/login", new login());
		server.setExecutor(null);
		server.start();
	}

	static class getJSON implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			Gson gson = new Gson(); // instantiate new gson object
			StudentDAO dao = new StudentDAO();
			String response = "";
			int responseCode = 0;

			BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
			String line = "";
			String request = "";
			while ((line = in.readLine()) != null) {
				request += SQLescape(line);
			}

			if (request.contains("key")) {
				Map<String, String> parms = queryToMap(request);
				String key = parms.get("key");
				// key.equals(accessKey)
				Boolean x = false;
				try {
					x = dao.checkApiKey(key);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (x) {
					ArrayList<Student> allStudents = new ArrayList<>();
					try {
						allStudents = dao.getAllStudents();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// convert arraylist to a JSON formatted string
					response = gson.toJson(allStudents);
					responseCode = 200;
				} else {
					response = "Incorrect Access Token";
					responseCode = 403;
				}
			} else {
				response = "No Access Token Sent";
				responseCode = 403;
			}

			t.sendResponseHeaders(responseCode, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class addStu implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			Gson gson = new Gson(); // instantiate new gson object
			StudentDAO dao = new StudentDAO();
			String response = "Unable to add Student";
			int responseCode = 400;

			BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
			String line = "";
			String request = "";
			while ((line = in.readLine()) != null) {
				request += SQLescape(line);
			}

			if (request.contains("key")) {
				Map<String, String> parms = queryToMap(request);
				String json = parms.get("json");
				String key = parms.get("key");

				Boolean x = false;
				try {
					x = dao.checkApiKey(key);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (x) {
					Student stu = gson.fromJson(json, Student.class);
					if (validStudent(stu)) {
						try {
							response = "Added " + stu.getName() + " to the student database.";
							responseCode = 200;
							dao.insertStu(stu);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						responseCode = 200;
					} else {
						response = "Invalid fields";
						responseCode = 400;
					}
				} else {
					response = "Incorrect Access Token";
					responseCode = 403;
				}
			} else {
				response = "No Access Token Sent";
				responseCode = 403;
			}

			t.sendResponseHeaders(responseCode, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class updateStu implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			Gson gson = new Gson(); // instantiate new gson object
			StudentDAO dao = new StudentDAO();
			String response = "Unable to update student";
			int responseCode = 400;

			BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
			String line = "";
			String request = "";
			while ((line = in.readLine()) != null) {
				request += SQLescape(line);
			}

			if (request.contains("key")) {
				Map<String, String> parms = queryToMap(request);
				String json = parms.get("json");
				String key = parms.get("key");

				Boolean x = false;
				try {
					x = dao.checkApiKey(key);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (x) {
					Student stu = gson.fromJson(json, Student.class);
					if (validStudent(stu)) {
						try {
							response = "Updated " + stu.getName() + " in the student database.";
							responseCode = 200;
							dao.updateStu(stu);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						responseCode = 200;
					} else {
						response = "Invalid fields";
						responseCode = 400;
					}
				} else {
					response = "Incorrect Access Token";
					responseCode = 403;
				}
			} else {
				response = "No Access Token Sent";
				responseCode = 403;
			}

			t.sendResponseHeaders(responseCode, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class deleteStu implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			Gson gson = new Gson(); // instantiate new gson object
			StudentDAO dao = new StudentDAO();
			String response = "Unable to delete student";
			int responseCode = 400;

			BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
			String line = "";
			String request = "";
			while ((line = in.readLine()) != null) {
				request += SQLescape(line);
			}

			if (request.contains("key")) {
				Map<String, String> parms = queryToMap(request);
				String key = parms.get("key");

				Boolean x = false;
				try {
					x = dao.checkApiKey(key);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (x) {
					Student stu = null;
					try {
						stu = dao.getStudent(parms.get("id"));
						response = "Deleted " + stu.getName() + " from the student database.";
						dao.deleteStu(parms.get("id"));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					responseCode = 200;
				} else {
					response = "Incorrect Access Token";
					responseCode = 403;
				}
			} else {
				response = "No Access Token Sent";
				responseCode = 403;
			}

			t.sendResponseHeaders(responseCode, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class getStu implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			Gson gson = new Gson(); // instantiate new gson object
			StudentDAO dao = new StudentDAO();
			String response = "";
			int responseCode = 400;

			BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
			String line = "";
			String request = "";
			while ((line = in.readLine()) != null) {
				request += SQLescape(line);
			}

			if (request.contains("key")) {
				Map<String, String> parms = queryToMap(request);
				String key = parms.get("key");

				Boolean x = false;
				try {
					x = dao.checkApiKey(key);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (x) {
					Student stu = null;
					try {
						stu = dao.getStudent(parms.get("id"));
						response = gson.toJson(stu);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					responseCode = 200;
				} else {
					response = "Incorrect Access Token";
					responseCode = 403;
				}
			} else {
				response = "No Access Token Sent";
				responseCode = 403;
			}

			t.sendResponseHeaders(responseCode, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class login implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			StudentDAO dao = new StudentDAO();
			String response = "";
			int responseCode = 400;

			BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
			String line = "", request = "";
			while ((line = in.readLine()) != null) {
				request += SQLescape(line);
			}

			Map<String, String> parms = queryToMap(request);

			if (request.contains("username") && request.contains("password")) {
				String user = parms.get("username");
				String password = parms.get("password");
				String check = parms.get("register");
				if (check == null) check = "off";
				if (!check.equals("on")) {
					String c = "";
					try {
						c = dao.checkLoginCredentials(user, password);
					} catch (SQLException | NoSuchAlgorithmException e) {
						e.printStackTrace();
					}

					if (c.isEmpty()) {
						response = "Invalid login credentials";
						responseCode = 401;
					} else {
						response = "Hello " + user + ", your login token is " + c;
						responseCode = 200;
					}
				} else {
					try {
						if (dao.uniqueUsername(user)) {
							try {
								response = "Created your account with the token of: " + dao.createUser(user, password);
								responseCode = 200;
							} catch (SQLException | NoSuchAlgorithmException e) {
								e.printStackTrace();
								response = "Oooops!";
								responseCode = 401;
							}
						} else {
							response = "Please enter a unique username";
							responseCode = 401;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				StringBuffer s = new StringBuffer();
				s.append("<html><form method=\"post\">");
				s.append("        Username: <input type=\"text\" name=\"username\"><br>");
				s.append("        Password: <input type=\"text\" name=\"password\"><br>");
				s.append("        Registering? <input type=\"checkbox\" name=\"register\"><br>");
				s.append("        <input type=\"submit\" value=\"Submit\">");
				s.append("</form></html>");
				response = s.toString();
				responseCode = 200;
			}

			t.sendResponseHeaders(responseCode, response.length());

			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	public static Map<String, String> queryToMap(String query) {
		Map<String, String> result = new HashMap<String, String>();
		for (String param : query.split("&")) {
			String pair[] = param.split("=");
			if (pair.length > 1) {
				result.put(pair[0], pair[1]);
			} else {
				result.put(pair[0], "");
			}
		}
		return result;
	}

	// Student Number and Busary not included, since they are caught in the
	// creation of the Student from the JSON
	public static Boolean validStudent(Student s) {
		Pattern date = Pattern
				.compile("([0-2][0-9]|[3][0-1])-([0][1-9]|[1][0-2])-([1][9][0-9][0-9]|[2][0][0-9][0-9])$");
		if (!Pattern.matches("^((?!0-9\\W)[a-zA-Z\\s\\x27]+)*$", s.getName())) {
			return false; // ^ Does not contain number or non-word character, but does a letter, space or apostrophe, globally, any illegal characters will return false
		}
		if (!Pattern.matches("[M/F/m/f]$", s.getGender())) {
			return false;
		}
		if (!date.matcher(s.getDob()).matches()) {
			return false;
		}
		if (!Pattern.matches("^((?!\\W)[a-zA-Z\\s\\x27]+)*$", s.getAddress())) {
			return false;
		}
		if (!Pattern.matches("[A-Z]{1,2}[0-9][0-9A-Z]?\\s?[0-9][A-Z]{2}$", s.getPostcode())) {
			return false;
		}
		if (!Pattern.matches("^((?!0-9\\W)[a-zA-Z\\s\\x27]+)*$", s.getCourseTitle())) {
			return false;
		}
		if (!date.matcher(s.getStartDate()).matches()) {
			return false;
		}
		if (!Pattern.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+\\.{0,1}[a-zA-Z0-9]*$",
				s.getEmail())) {
			return false;
		} return true;
	}

	public static String SQLescape(String s) {
		return s.replaceAll("'", "''").replaceAll("%", "[%]").replaceAll("_", "[_]");
	}
}
