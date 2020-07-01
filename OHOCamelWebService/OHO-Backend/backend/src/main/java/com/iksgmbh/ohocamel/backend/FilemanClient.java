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
		if (false) {
			if (filename.equals("OhoMainScript.groovy"))
				return "\r\nStringBuffer html = new StringBuffer();\r\n"
						+ "horoscopeRequestData = exchange.getProperty(\"HoroscopeRequestData\");\r\n" + 
		              "String name = horoscopeRequestData.getName();\r\n" + 
		              "html.append(\"Hello &lt;i&gt;\" + name + \".&lt;/i&gt;\");\r\n" +
		              "executeScript:OhoSubScript.groovy\r\n" +
		              "exchange.setProperty(\"HoroscopeResponseData\", html.toString());";
			if (filename.equals("OhoSubScript.groovy"))
				return "\r\n" + 
			           "html.append(\"&lt;p&gt;\" + System.getProperty(\"line.separator\") + \"sub1&lt;/p&gt;\");\r\n" +  
		               "executeScript:OhoSubSubScript.groovy";
			if (filename.equals("OhoSubSubScript.groovy"))
				return "\r\n" + 
		           "html.append(\"&lt;p&gt;\" + System.getProperty(\"line.separator\") + \"sub2&lt;/p&gt;\");";
		}
		
		
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
