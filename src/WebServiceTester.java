import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class WebServiceTester {
	public static void main(String[] args) throws IOException {
		System.out.println("Students = " + getStudentsTest());
		System.out.println();
		System.out.println("response = " + addStudentTest("json={\"studentNumber\":3,\"courseTitle\":\"Computer Science\",\"startDate\":\"2017-10-01\",\"bursary\":500,\"email\":\"Bobby@mail.com\",\"name\":\"Bobby Brown\",\"gender\":\"M\",\"dob\":\"1970-04-18\",\"address\":\"Manchester\",\"postcode\":\"M1 123\"}"));
		System.out.println();
		System.out.println("Students = " + getStudentsTest());
		System.out.println();
		System.out.println("response = " + updateStudentTest("json={\"studentNumber\":3,\"courseTitle\":\"Computer Science\",\"startDate\":\"2017-10-01\",\"bursary\":500,\"email\":\"BobbyJR@mail.com\",\"name\":\"Bobby Brown Jr\",\"gender\":\"M\",\"dob\":\"1970-04-18\",\"address\":\"Manchester\",\"postcode\":\"M1 123\"}"));
		System.out.println();
		System.out.println("Students = " + getStudentsTest());
		System.out.println();
		System.out.println("Students = " + deleteStudentTest("id=3"));
		System.out.println();
		System.out.println("Students = " + getStudentTest("id=1"));
	}

	private static StringBuffer getStudentsTest() {
		StringBuffer response = new StringBuffer();
		try {
			URL url = new URL("http://localhost:8005/get-json");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String output;
			while ((output = reader.readLine()) != null) {
				response.append(output);
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return response;
	}

	private static String addStudentTest(String urlParameters) throws IOException {

		URL url;
		url = new URL("http://localhost:8005/process");
		// create and configure the connection object
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(15000);
		conn.setConnectTimeout(15000);
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);

		// write/send/POST data to the connection using output stream and buffered writer
		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		writer.write(urlParameters);

		// clear the writer
		writer.flush();
		writer.close();
		// close output stream
		os.close();
		// get the server response code to determine what to do next (i.e. success/error)
		String response = "";
		String line;
		int responseCode = conn.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
			while ((line = br.readLine()) != null) {
				response += line;
			}
		} 
		return response;
	}
	
	private static String updateStudentTest(String urlParameters) throws IOException {

		URL url;
		url = new URL("http://localhost:8005/updateStu");
		// create and configure the connection object
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(15000);
		conn.setConnectTimeout(15000);
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);

		// write/send/POST data to the connection using output stream and buffered writer
		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		writer.write(urlParameters);

		// clear the writer
		writer.flush();
		writer.close();
		// close output stream
		os.close();
		// get the server response code to determine what to do next (i.e. success/error)
		String response = "";
		String line;
		int responseCode = conn.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
			while ((line = br.readLine()) != null) {
				response += line;
			}
		} 
		return response;
	}
	
	private static String deleteStudentTest(String urlParameters) throws IOException {

		URL url;
		url = new URL("http://localhost:8005/deleteStu");
		// create and configure the connection object
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(15000);
		conn.setConnectTimeout(15000);
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);

		// write/send/POST data to the connection using output stream and buffered writer
		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		writer.write(urlParameters);

		// clear the writer
		writer.flush();
		writer.close();
		// close output stream
		os.close();
		// get the server response code to determine what to do next (i.e. success/error)
		String response = "";
		String line;
		int responseCode = conn.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
			while ((line = br.readLine()) != null) {
				response += line;
			}
		} 
		return response;
	}
	
	private static String getStudentTest(String urlParameters) throws IOException {

		URL url;
		url = new URL("http://localhost:8005/getStu");
		// create and configure the connection object
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(15000);
		conn.setConnectTimeout(15000);
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);

		// write/send/POST data to the connection using output stream and buffered writer
		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		writer.write(urlParameters);

		// clear the writer
		writer.flush();
		writer.close();
		// close output stream
		os.close();
		// get the server response code to determine what to do next (i.e. success/error)
		String response = "";
		String line;
		int responseCode = conn.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
			while ((line = br.readLine()) != null) {
				response += line;
			}
		} 
		return response;
	}
}