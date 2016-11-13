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

public class QueryApache {
	
	private String strQuery;		//query to be run
	private String session;			//holds the session id
	static String urlstring;
	static String inputline;		//for reading input
	JSONArray arr;
	JSONObject obj;
	
	public QueryApache(String session, String query) {
		this.session = session;
		this.strQuery = query;
		
		//create Json to send to webservice
	    arr = new JSONArray();
	    obj = new JSONObject();
	    obj.put("action", "run_sql");
	    arr.put(obj);
	    obj = new JSONObject();
	    obj.put("query", strQuery);
	    arr.put(obj);
	    obj = new JSONObject();
	    obj.put("session_key", this.session);
	    arr.put(obj);
	}
	
	/**
	 * Sends a SQL query to the web service and returns
	 * the results
	 * @return	responseJSON	The resulting data from sql query
	 */
	public JSONArray execute() {
		
		String query = arr.toString();	
		JSONArray responseJSON = null;
		
		//uncomment print for debugging
		//System.out.println("From Query: " + query);
		
		try {
			//make the connection
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet;
			CloseableHttpResponse response = null;
			httpGet = new HttpGet("https://easel1.fulgentcorp.com/bifrost/ws.php?json=" 
					+  URLEncoder.encode(arr.toString(), java.nio.charset.StandardCharsets.UTF_8.toString()));
			
			//get the response JSON
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
		    responseJSON = new JSONArray(EntityUtils.toString(entity));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseJSON;
	}
}
