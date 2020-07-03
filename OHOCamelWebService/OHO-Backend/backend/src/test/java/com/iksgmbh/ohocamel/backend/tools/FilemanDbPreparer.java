package com.iksgmbh.ohocamel.backend.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.stream.Collectors;

import com.iksgmbh.ohocamel.backend.camel.tools.ResourceReader;

/**
 * Creates a new insertDefaultData.sql file for the Fileman application,
 * so that required groovy scripts will be available at runtime.
 * 
 * For that purpose the content of the required OHO groovy scripts are
 * prepared, injected in a sql template file and written to Fileman backend project.
 * 
 * To prepare a groovy script for Fileman the symbols '<' and '>' must be replaced by html code
 * and the content has to be Base64 encoded.
 * 
 * In addition the SQL file, the Fileman H2-DB-File is deleted (necessary for recreation)
 * and in the properties file the line '#spring.datasource.data = classpath:/insertDefaultData.sql'
 * is commented in. 
 * 
 * @author Reik Oberrath
 */
public class FilemanDbPreparer 
{
	private static final String SQL_TEMPLATE = "initialFilemanSql.template";
	private static final String OHO_MAIN_SCRIPT_GROOVY = "scripts/OhoMainScript.groovy";
	private static final String OHO_CORE_SCRIPT_GROOVY = "scripts/OhoCoreScript.groovy";
	
	public static final String FILEMAN_PATH = "C:/dev/source/FilemanGithub/Fileman-Backend"; 
	public static final String SQL_PATH = FILEMAN_PATH + "/src/main/resources/insertDefaultData.sql";
	public static final String PROPERTY_FILE_PATH = FILEMAN_PATH + "/src/main/resources/application.properties";
	public static final String H2_DB_PATH = FILEMAN_PATH + "/target/MyLocalDB.mv.db";
	public static final String DATA_SOURCE_PROPERTY = "#spring.datasource.data = classpath:/insertDefaultData.sql";
	
	public static void main(String[] args) throws Exception 
	{
        System.out.println("FilemanDbPreparer: ");
		checkPreconditions();
        System.out.println("Preconditions ok!");
		
		String output = buildTargetFileContent();
		writeFile(SQL_PATH, output);
		prepareApplicationProperties();
        System.out.println("Preparation done.");
	}

	private static void writeFile(String filePath, String content) throws IOException 
	{
		FileWriter writer = new FileWriter(filePath);
        writer.write(content);
        writer.close();
	}


	private static String buildTargetFileContent() throws UnsupportedEncodingException 
	{
		String output = ResourceReader.getContent(SQL_TEMPLATE);
		
		String content = ResourceReader.getContent(OHO_MAIN_SCRIPT_GROOVY);
		content = content.replaceAll("<", "&lt;")
				         .replaceAll(">", "&gt;");
		content = Base64.getEncoder().encodeToString(content.getBytes("UTF-8"));
		output = output.replace("MainContent", content);

		content = ResourceReader.getContent(OHO_CORE_SCRIPT_GROOVY);
		content = content.replaceAll("<", "&lt;")
		         .replaceAll(">", "&gt;");
		content = Base64.getEncoder().encodeToString(content.getBytes("UTF-8"));
		output = output.replace("CoreContent", content);
		return output;
	}


	private static void checkPreconditions() throws FileNotFoundException 
	{
		File f = new File(SQL_PATH);
		if (! f.exists()) {
			System.out.println("Cannot find " + f.getAbsolutePath());
			return;
		}
		
		f = new File(PROPERTY_FILE_PATH);
		if (! f.exists()) {
			System.out.println("Cannot find " + f.getAbsolutePath());
			return;
		}
		
		f = new File(H2_DB_PATH);
		if (f.exists()) {
			boolean ok = f.delete();
			if (!ok) {
				System.out.println("Cannot delete " + f.getAbsolutePath());
				return;
			}
		}
	}

	private static void prepareApplicationProperties() throws IOException 
	{
		String content = readTextFile(PROPERTY_FILE_PATH);
		content = content.replace(DATA_SOURCE_PROPERTY, DATA_SOURCE_PROPERTY.substring(1));
		writeFile(PROPERTY_FILE_PATH, content);
	}
	
	public static String readTextFile(String pathAndfilename) throws FileNotFoundException
	{
		if (!new File(pathAndfilename).exists()) {
			throw new RuntimeException("Cannot read non-existing file '" + pathAndfilename + "'.");
		}

		BufferedReader reader = new BufferedReader(new FileReader(pathAndfilename));
		String content = reader.lines().collect(Collectors.joining(System.getProperty("line.separator")));
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}
	
}
