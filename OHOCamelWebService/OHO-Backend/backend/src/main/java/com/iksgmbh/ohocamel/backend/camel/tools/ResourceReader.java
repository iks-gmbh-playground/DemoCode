package com.iksgmbh.ohocamel.backend.camel.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ResourceReader 
{
	public static final String ROUTE_FOLDER = "camelRoutes/";

	public static String getContent(String relativeFilePath) 
	{
		InputStream resourceAsStream = ResourceReader.class.getClassLoader().getResourceAsStream(relativeFilePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
		String content = reader.lines().collect(Collectors.joining(System.getProperty("line.separator")));
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}
