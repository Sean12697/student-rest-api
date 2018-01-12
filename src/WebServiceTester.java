import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**  
* WebService.java a class that connects the running RESTful Web service (localhost:8005) and provides the data (with an API key) for the CRUD operations
* @author  Sean O'Mahoney - 16042079
* @version 1.0 
* @see Class: WebService
*/
public class WebServiceTester {
	
	public static String key = "key=987tb986r5cv7tn";
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Students = " + serverCallTest("http://localhost:8005/get-json", key));
		System.out.println();
		System.out.println("Add Response = " + serverCallTest("http://localhost:8005/process", "json={\"studentNumber\":3,\"courseTitle\":\"Computer Science\",\"startDate\":\"10-10-2017\",\"bursary\":500,\"email\":\"Bobby@mail.com\",\"name\":\"Bobby Brown\",\"gender\":\"M\",\"dob\":\"18-04-1970\",\"address\":\"Manchester\",\"postcode\":\"M1 6MQ\"}&" + key));
		System.out.println();
		System.out.println("Students = " + serverCallTest("http://localhost:8005/get-json", key));
		System.out.println();
		System.out.println("Update Response = " + serverCallTest("http://localhost:8005/updateStu", "json={\"studentNumber\":3,\"courseTitle\":\"Computer Science\",\"startDate\":\"10-10-2017\",\"bursary\":500,\"email\":\"BobbyJR@mail.com\",\"name\":\"Bobby Brown Jr\",\"gender\":\"M\",\"dob\":\"18-04-1970\",\"address\":\"Manchester\",\"postcode\":\"M1 6MQ\"}&" + key));
		System.out.println();
		System.out.println("Students = " + serverCallTest("http://localhost:8005/get-json", key));
		System.out.println();
		System.out.println("Delete Response = " + serverCallTest("http://localhost:8005/deleteStu", "id=3&" + key));
		System.out.println();
		System.out.println("Get Response = " + serverCallTest("http://localhost:8005/getStu", "id=1&" + key));
	}

	/**
	 * @return
	 * @throws IOException
	 */
	private static String serverCallTest(String urlStr, String urlParms) throws IOException {
		
		URL url;
		url = new URL(urlStr);
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
		writer.write(urlParms);

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