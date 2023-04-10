package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;






public class EndgameQuery {
	
	public void postQuery(int[] id_players,String map, Float duration,int[] ranks, Float[] moneys) {
		try {
	        URL url = new URL("http://localhost:8080/WebSnake/Stats");

	        // create an HttpURLConnection instance
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	        // set the request method to POST
	        connection.setRequestMethod("POST");

	        // set the content type of the request
	        connection.setRequestProperty("Content-Type", "application/json");

	        // set the parameters of the request
	        Map<String, Object> params = new HashMap<>();
	        
	        params.put("id_players", id_players);
	        params.put("map", map);
	        params.put("duration", duration);
	        params.put("date", new Date());
	        params.put("ranks", ranks);
	        params.put("moneys", moneys);
	        String requestBody = encodeParameters(params);

	        byte[] requestBodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);
	        connection.setDoOutput(true);
	        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
	            outputStream.write(requestBodyBytes);
	        }

	        
	        String responseBody = "";
	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                responseBody += line;
	            }
	        }

	        // print the response body
	        System.out.println(responseBody);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	

public  String encodeParameters(Map<String, Object> params) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(params);

}
}