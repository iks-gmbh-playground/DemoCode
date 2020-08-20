package com.iksgmbh.ohocamel.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.iksgmbh.oho.backend.HoroscopeRequestData;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

@TestPropertySource("classpath:application-test.properties")
@ExtendWith(SpringExtension.class)  // RestDocumenationExtension ?
@Import(GroovyScriptExecutionTest.TestConfig.class)
@SpringBootTest
public class GroovyScriptExecutionTest 
{
	@Autowired
	private Binding binding;

	@Autowired
	private GroovyShell groovyShell;
	
	@Test
	public void execGroovyInTestShell() throws IOException
	{
		// arrange
		TestExchange testExchange = new TestExchange();
		binding.setVariable("exchange", testExchange);
		testExchange.setProperty("HoroscopeRequestData", buildTestInputData());
		String groovyScript = buildScript();
		//System.err.println(groovyScript);
		
		// act
		groovyShell.evaluate(groovyScript);
		
		// assert
		String result = testExchange.getProperty("HoroscopeResponseData").toString();
		//System.err.println(result);

		assertTrue(result.startsWith("<section "));
		assertFalse(result.contains("executeScript"));
		assertTrue(result.endsWith("</section>"));
	}
	
	private Object buildTestInputData() 
	{
		HoroscopeRequestData data = new HoroscopeRequestData();
		data.setName("Tester");
		data.setBirthday("10.10.2010");
		data.setGender("f");
		return data;
	}

	private String buildScript() throws IOException 
	{
		File f = new File(getClass().getClassLoader().getResource("scripts/OhoMainScript.groovy").getFile());
		String toReturn = FileUtils.readFileToString(f, StandardCharsets.UTF_8);
		f = new File(getClass().getClassLoader().getResource("scripts/OhoCoreScript.groovy").getFile());
		toReturn = toReturn.replace("executeScript:OhoCoreScript.groovy", FileUtils.readFileToString(f, StandardCharsets.UTF_8));
		return toReturn.replaceAll("&quot;", "\"");
	}

	static class TestConfig 
	{
		@Bean public CompilerConfiguration createCompilerConfig() {
			return new CompilerConfiguration();
		}
		@Bean public Binding binding() {
			return new Binding();
		}
		@Bean public GroovyShell createShell(
			  @Autowired Binding binding,
			  @Autowired CompilerConfiguration compilerConfig) {
			return new GroovyShell(this.getClass().getClassLoader(), 
					               binding,
					               compilerConfig);
		}
	}
	
	static class TestExchange 
	{
		private Map<String, Object> properties = new HashMap<>();
		
		public Object getProperty(String key) { return properties.get(key); }
		public void setProperty(String key, Object value) { properties.put(key, value); }
		public TestExchange getIn() {return this;}
	}
}