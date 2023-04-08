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
	
	private String email;
	private String password;
	
	private boolean con_ok;
	private UserDescription response;
	
	
	
	public AuthQuery(String email, String password){
		this.email = email;
		this.password = password;
		this.con_ok = false;
	}
		
	public boolean getAuth() {
	    try {
	       
	        ObjectMapper objectMapper = new ObjectMapper();
	        ObjectNode authJson = objectMapper.createObjectNode();
	        authJson.put("mail", this.email);
	        authJson.put("password", this.password);

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

	        int statusCode = connection.getResponseCode();
	        
	        if (statusCode != 200) {
	            this.con_ok = false;
		        return this.con_ok;
	        } 

	        ObjectMapper mapper = new ObjectMapper();
	        this.response = mapper.readValue(responseBody, UserDescription.class);
	        
	        this.con_ok =  true;
	        return this.con_ok;

	    
	    } catch (IOException e) {
	        e.printStackTrace();
	        this.con_ok =  false;
	        return this.con_ok;
	    }
	}

	public UserDescription getResponse() {
		return response;
	}

	public boolean isConOk() {
		return con_ok;
	}
	}

