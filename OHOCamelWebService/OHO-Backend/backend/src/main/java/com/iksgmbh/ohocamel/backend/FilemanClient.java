package com.iksgmbh.ohocamel.backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

@Component
public class FilemanClient 
{
	public String getFileContent(String filename) 
	{
		StringBuffer sb = new StringBuffer();

		try {
			String authToken = requestAuthToken();
			
			URL url = new URL("http://localhost:10002/fileman/files/" + filename);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Bearer " + authToken);
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() == 404) {
				throw new RuntimeException("Script '" + filename + "' is not available from Fileman!");
			} else  if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append(System.getProperty("line.separator"));
			}

			conn.disconnect();
		  } catch (Exception e) {
			  throw new RuntimeException("Error accessing Fileman.", e);
		  }
		return sb.toString().trim();
	}

	private String requestAuthToken() throws Exception {
		
		URL url = new URL("http://localhost:10002/fileman/authenticate");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		conn.setDoOutput(true);
		
		String jsonInputString = "{"
				+ "\"filemanVersion\": \"1.1.0\","
				+ "\"userId\": \"Bo\","
				+ "\"userPw\": \"\","
				+ "\"tenant\": \"1\"}";
		
		try (OutputStream outputStream = conn.getOutputStream()) {
		    byte[] inputBytes = jsonInputString.getBytes();
		    outputStream.write(inputBytes, 0, inputBytes.length);
		}
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Fileman authentication failed");
		}
		
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line).append(System.getProperty("line.separator"));
		}
		
		conn.disconnect();
		
		String responseString = sb.toString();
	    String tokenPrefix = "authToken\":\"";
	    String tokenSuffix = "\"";
	    int tokenStartIndex = responseString.indexOf(tokenPrefix) + tokenPrefix.length();
	    int tokenEndIndex = responseString.indexOf(tokenSuffix, tokenStartIndex);
	    
	    String authToken = responseString.substring(tokenStartIndex, tokenEndIndex);
	    
	    return authToken;
	}
}
