package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AuthQuery {
	
		
	public boolean getAuth(String email, String password) {
	    try {
	       
	        ObjectMapper objectMapper = new ObjectMapper();
	        ObjectNode authJson = objectMapper.createObjectNode();
	        authJson.put("mail", email);
	        authJson.put("password", password);

	        String encodedAuthJson = objectMapper.writeValueAsString(authJson);

	        URL url = new URL("http://localhost:8080/WebSnake/Auth");
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("POST");

	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setRequestProperty("Accept", "application/json");

	        
	        connection.setDoOutput(true);
	        try (OutputStream outputStream = connection.getOutputStream()) {
	            byte[] input = encodedAuthJson.getBytes("utf-8");
	            outputStream.write(input, 0, input.length);
	        }

	        String responseBody = "";
	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                responseBody += line;
	            }
	        }
	        System.out.println("La réponse : " +responseBody);
	        int statusCode = connection.getResponseCode();
	        if (statusCode == 401) {
	            return false;
	        } else if (statusCode == 200) {
	            return true;
	        } else {
	            // handle other status codes if needed
	            return false;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	}

