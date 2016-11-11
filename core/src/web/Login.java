package web;

import java.net.*;
import java.io.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Login {

	private String session;
	static URL url;
	static String urlstring;
	static String inputline;
	static JsonObject login;
	static JsonObject response;
	
	public Login() {
		
		
		//create a JsonObject with login creds
		login = new JsonObject();
		login.addProperty("action", "login");
		login.addProperty("login", "fa2016_4x");
		login.addProperty("password", "c136304a175d2a90538f3b1cbc39ecdd4ca711840a148b91ebe312b1fc89835d");
		login.addProperty("app_code", "b482P2teUvqdJRZ2");
		login.addProperty("session_type","session_key");
		login.addProperty("checksum","4699c1ba448272f73f83f15789567900ae61a2c67c9215bea969cbe71439d50f");
	}
	
	/**
	 * Use this function to actually create a session
	 * @return	the session id
	 */
	public String loginToWebservice() {

		JsonParser parser = new JsonParser();
		String loginString = login.toString();
		
		System.out.println("From Login: " + loginString);
		try {
			
			urlstring = "https://easel1.fulgentcorp.com/bifrost/ws.php?json=" + loginString;
			url = new URL(urlstring);
			
			//make the connection
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json, charset=UTF-8");
			
			//read in the session key
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while((inputline=in.readLine())!=null) {
				JsonArray response =  parser.parse(inputline).getAsJsonArray();
				session = response.get(3).getAsJsonObject().get("session_key").getAsString();
				System.out.println("From Login: " + response.toString());
				System.out.println("From Login: " + session);
			}					
			in.close();
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return session;
	}
}
