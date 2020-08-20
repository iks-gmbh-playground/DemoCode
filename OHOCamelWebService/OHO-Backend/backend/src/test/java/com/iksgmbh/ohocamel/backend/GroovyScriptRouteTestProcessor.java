package com.iksgmbh.ohocamel.backend;

import com.iksgmbh.ohocamel.backend.camel.processor.GroovyScriptRouteProcessor;

/**
 * Decouples route processing from Fileman 
 * and provides few groovy lines for test purpose.
 * 
 * @author Reik Oberrath
 */
public class GroovyScriptRouteTestProcessor extends GroovyScriptRouteProcessor {

	@Override
	protected String getContent(String groovyScriptName) 
	{
		if (groovyScriptName.equals("OhoCoreScript.groovy")) {
			return System.getProperty("line.separator") 
					+ "result = \"Your PersonType is '\" + personType + \"'.\";"
					+ System.getProperty("line.separator") 
					+ "exchange.setProperty(\"HoroscopeResponseData\", result);";
		}
		if (groovyScriptName.equals("OhoMainScript.groovy")) {
			return System.getProperty("line.separator") 
					+ "import com.iksgmbh.ohocamel.backend.utils.HoroscopeUtil;"
					+ System.getProperty("line.separator") 
					+ "import com.iksgmbh.ohocamel.backend.utils.DateUtil;"
					+ System.getProperty("line.separator") 
					+ "requestData = exchange.getProperty(\"HoroscopeRequestData\");"
					+ System.getProperty("line.separator") 
					+ "String personType = HoroscopeUtil.getPersonType(requestData);"
					+ System.getProperty("line.separator") 
					+ "executeScript:OhoCoreScript.groovy"
					+ System.getProperty("line.separator");		
		}
		
		
		throw new RuntimeException("Unkown test groovy script: " + groovyScriptName);
	}
}
