package web;

import java.io.IOException;
import java.net.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class LoginApache {
	private String session;
	JSONArray arr;
	JSONObject obj;
	
	public LoginApache() {
		
		//assemble the login request JSON
	    arr = new JSONArray();
	    obj = new JSONObject();
	    obj.put("action", "login");
	    arr.put(obj);
	    obj = new JSONObject();
	    obj.put("login", "fa2016_4x");
	    arr.put(obj);
	    obj = new JSONObject();
	    obj.put("password", "c136304a175d2a90538f3b1cbc39ecdd4ca711840a148b91ebe312b1fc89835d");
	    arr.put(obj);
	    obj = new JSONObject();
	    obj.put("app_code", "b482P2teUvqdJRZ2");
	    arr.put(obj);
	    obj = new JSONObject();
	    obj.put("session_type", "session_key");
	    arr.put(obj);
	    obj = new JSONObject();
	    obj.put("session_type", "session_key");
	    arr.put(obj);	
	    obj = new JSONObject();
	    obj.put("checksum", "4699c1ba448272f73f83f15789567900ae61a2c67c9215bea969cbe71439d50f");
	    arr.put(obj);
	}
	
	/**
	 * Login to the webservice and create a session
	 * @return	session		the session key for the created session 
	 */
	public String loginToWebservice() {
		
		String loginString = arr.toString();
		JSONArray responseJSON = null;
		
		//uncomment for debugging
		//System.out.println("From Login: " + loginString);
		
		try {
			//make the connection
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet;
			CloseableHttpResponse response = null;
			httpGet = new HttpGet("https://easel1.fulgentcorp.com/bifrost/ws.php?json=" 
					+  URLEncoder.encode(arr.toString(), java.nio.charset.StandardCharsets.UTF_8.toString()));
			
			//get the response json
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
		    responseJSON = new JSONArray(EntityUtils.toString(entity));
		    session = responseJSON.getJSONObject(3).getString("session_key");
		    
		    //uncomment for debugging
		    //System.out.println(responseJSON.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//returns the session key
		return session;
	}
}
