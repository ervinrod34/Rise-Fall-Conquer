package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Query {
	
	/**
	 * Contains the current session id
	 */

	private String strQuery;		//query to be run
	private JsonObject jsonQuery;	//JsonObject holds query to be run
	private String session;			//holds the session id
	static URL url;					//url to connect to
	static String urlstring;
	static String inputline;		//for reading input
	static JsonObject response;		//holds the webservice response
	
	public Query(String session, String query) {
		this.session = session;
		this.strQuery = query;
		
		//create Json to send to webservice
		jsonQuery = new JsonObject();
		jsonQuery.addProperty("action", "run_sql");
		jsonQuery.addProperty("query", strQuery);
		jsonQuery.addProperty("session_key", this.session);
	}
	
	public JsonArray execute() {
		
		String inputline;
		JsonArray response = new JsonArray();
		JsonParser parser = new JsonParser();
		String query = jsonQuery.toString();
		
		System.out.println("From Query: " + query);
		
		
		
		try {
			
			//set up the connection
			urlstring = "https://easel1.fulgentcorp.com/bifrost/ws.php?json=" + query;
			System.out.println(urlstring);
			url = new URL(urlstring);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json, charset=UTF-8");
			
			//read in the response
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while((inputline=in.readLine())!=null) {
				response =  parser.parse(inputline).getAsJsonArray();
				System.out.println("From Query: " + response.toString());
			}					
			in.close();
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
