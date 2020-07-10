package com.iksgmbh.ohocamel.backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
			URL url = new URL("http://localhost:10002/fileman/files/" + filename);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
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
}
