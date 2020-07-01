package com.iksgmbh.ohocamel.backend.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import com.iksgmbh.ohocamel.backend.camel.tools.ResourceReader;

public class Base64ScriptDecoder 
{
	public static final String SQL_SCRIPT_PATH = "C:/dev/source/FilemanGithub/Fileman-Backend/src/main/resources/insertDefaultData.sql";
	public static final String DB_FILE_PATH = "C:/dev/source/FilemanGithub/Fileman-Backend/target/MyLocalDB.mv.db";
	public static final String PROPERTY_FILE_PATH = "C:/dev/source/FilemanGithub/Fileman-Backend/src/main/resources/application.properties";
	
	public static void main(String[] args) throws Exception 
	{
		checkPreconditions();
		
		String output = buildTargetFileContent();
		FileWriter writer = new FileWriter(SQL_SCRIPT_PATH);
        writer.write(output);
        writer.close();

        System.out.println("Base64ScriptDecoder: Done!");
	}


	private static String buildTargetFileContent() throws UnsupportedEncodingException 
	{
		String output = ResourceReader.getContent("initialFilemanSql.template");
		
		String content = ResourceReader.getContent("scripts/OhoMainScript.groovy");
		content = content.replaceAll("<", "&lt;")
				         .replaceAll(">", "&gt;");
		content = Base64.getEncoder().encodeToString(content.getBytes("UTF-8"));
		output = output.replace("MainContent", content);

		content = ResourceReader.getContent("scripts/OhoCoreScript.groovy");
		content = content.replaceAll("<", "&lt;")
		         .replaceAll(">", "&gt;");
		content = Base64.getEncoder().encodeToString(content.getBytes("UTF-8"));
		output = output.replace("CoreContent", content);
		return output;
	}


	private static void checkPreconditions() 
	{
		File f = new File(SQL_SCRIPT_PATH);
		if (! f.exists()) {
			System.out.println("Cannot find " + f.getAbsolutePath());
			return;
		}
		
		f = new File(PROPERTY_FILE_PATH);
		if (! f.exists()) {
			System.out.println("Cannot find " + f.getAbsolutePath());
			return;
		}
		
		f = new File(DB_FILE_PATH);
		if (f.exists()) {
			boolean ok = f.delete();
			if (!ok) {
				System.out.println("Cannot delete " + f.getAbsolutePath());
				return;
			}
		}
		
		prepareApplicationProperties();
	}


	private static void prepareApplicationProperties() 
	{
		String output = ResourceReader.getContent(PROPERTY_FILE_PATH);
		System.out.println(output);
	}
}
