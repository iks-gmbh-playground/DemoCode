package com.iksgmbh.ohocamel.backend.camel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iksgmbh.oho.backend.HoroscopeRequestData;
import com.iksgmbh.ohocamel.backend.camel.config.CamelContextHandler;
import com.iksgmbh.ohocamel.backend.camel.processor.CamelOhoProcessor;

@Service
public class CamelService 
{
	@Autowired
	private CamelContextHandler camelContextHandler;
	
	public String getHtmlHoroscope(HoroscopeRequestData requestData) 
	{
		CamelOhoProcessor processor = new CamelOhoProcessor(camelContextHandler.getCamelContext());
		return processor.process(requestData);
	}

	public String getDemo(HoroscopeRequestData requestData) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<h1>Hello <i>" + requestData.getName() + "</i></h1>");
		
		if (requestData.getGender().equals("m")) {
			sb.append("<p>You are a <b>good</b> boy.</p>");
		} else if (requestData.getGender().equals("w")) {
			sb.append("<p>You are a <b>good</b> girl.</p>");
			
		} else {
			sb.append("<p>You are a <b>good</b> person.</p>");
		}

		return sb.toString();
	}
	
}
