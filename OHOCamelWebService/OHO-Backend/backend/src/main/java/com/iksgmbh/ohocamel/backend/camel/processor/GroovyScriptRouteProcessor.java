package com.iksgmbh.ohocamel.backend.camel.processor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.RoutesDefinition;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iksgmbh.ohocamel.backend.FilemanClient;
import com.iksgmbh.ohocamel.backend.camel.CamelService;
import com.iksgmbh.ohocamel.backend.camel.tools.ResourceReader;

@Component
public class GroovyScriptRouteProcessor implements Processor 
{
	private static final Logger CAMEL_LOGGER = LoggerFactory.getLogger(CamelService.class);

	public static final String ROUTE_FOLDER = "camelRoutes/";
	public static final String SCRIPT_REFERENCE_IDENTIFIER = "executeScript:";
	
	@Autowired
	private FilemanClient fileman;

	private static String mainRoute;
	
	@Override
	public void process(Exchange exchange) throws Exception 
	{
		String groovyExecRoute = getOhoMainRoute(exchange);
		
		groovyExecRoute = replaceGroovyScriptReferences(groovyExecRoute);

		addToContext(exchange, groovyExecRoute);	
	}

	private String replaceGroovyScriptReferences(String groovyScriptRoute) 
	{
		List<String> usedScripts = new ArrayList<>();
		while (groovyScriptRoute.contains(SCRIPT_REFERENCE_IDENTIFIER))
		{
			String reference = findNextReference(groovyScriptRoute);
			String groovyScriptName = extractScriptName(reference);
			
			if (usedScripts.contains(groovyScriptName)) {
				// prevent recursive script reference and loops without end
				CAMEL_LOGGER.error("Error: Script '" + groovyScriptName + "' cannot be used more than once in the same route!");
				return "";
			} else {
				usedScripts.add(groovyScriptName);
			}
			
			String groovyScriptContent = getContent(groovyScriptName);
			groovyScriptRoute = groovyScriptRoute.replace(reference, groovyScriptContent);
		}
		
		CAMEL_LOGGER.info("Oho Route after replacing groovy script references: " 
				          + System.getProperty("line.separator") + groovyScriptRoute);
		
		return groovyScriptRoute;
	}

	protected String getContent(String groovyScriptName) 
	{
		String groovyScriptContent = fileman.getFileContent(groovyScriptName);
	
		groovyScriptContent = System.getProperty("line.separator") 
				              + "/* " + groovyScriptName + " */"
				              + System.getProperty("line.separator") 
				              + groovyScriptContent
				              + System.getProperty("line.separator");		
		return groovyScriptContent;
	}

	private String findNextReference(String groovyScriptRoute) 
	{
		int pos = groovyScriptRoute.indexOf(SCRIPT_REFERENCE_IDENTIFIER);
		String toReturn = groovyScriptRoute.substring(pos);
		
		pos = Integer.MAX_VALUE;
		pos = findReferenceEnd(toReturn, pos, "<");
		pos = findReferenceEnd(toReturn, pos, " ");
		pos = findReferenceEnd(toReturn, pos, System.getProperty("line.separator"));
		
		return toReturn.substring(0, pos);
	}

	private int findReferenceEnd(String toReturn, int pos, String toSearch) 
	{
		int endPosCandidate = toReturn.indexOf(toSearch);
		if (endPosCandidate == -1) endPosCandidate = Integer.MAX_VALUE;
		return endPosCandidate < pos ? endPosCandidate : pos;
	}

	private String extractScriptName(String reference) {
		reference = reference.trim();
		return reference.substring(SCRIPT_REFERENCE_IDENTIFIER.length());
	}

	private void addToContext(Exchange exchange, String groovyScriptRoute) throws Exception {
		InputStream inputStream = IOUtils.toInputStream(groovyScriptRoute, Charset.defaultCharset());
		CamelContext context = exchange.getContext();
		RoutesDefinition routes = context.loadRoutesDefinition(inputStream);
		context.addRouteDefinitions(routes.getRoutes());
	}

	private String getOhoMainRoute(Exchange exchange) throws IOException 
	{
		if (mainRoute == null) 
		{
			String groovyScriptRouteName = (String)exchange.getProperty("GroovyScriptRoute");
			mainRoute = ResourceReader.getContent(ROUTE_FOLDER + groovyScriptRouteName);
		}
		return mainRoute;
	}

}
