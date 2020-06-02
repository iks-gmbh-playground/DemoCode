package com.iksgmbh.ohocamel.backend.camel.processor;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.ExchangeBuilder;

import com.iksgmbh.oho.backend.HoroscopeRequestData;
import com.iksgmbh.ohocamel.backend.camel.config.CamelContextHandler;

public class CamelOhoProcessor 
{
	public static final String OHO_MAIN_SCRIPT = "HoroscopeMainTemplate.groovy";
	public static final String OHO_START_ROUTE = "OhoStartRoute.xml";
	public static final String OHO_ROUTE_START_FROM = "direct:start";
	public static final String OHO_REQUEST_DATA = "HoroscopeRequestData";
	public static final String OHO_RESPONSE_DATA = "HoroscopeResponseData";

	private CamelContext context;

	public CamelOhoProcessor(CamelContext camelContext) {
		this.context = camelContext;
	}

	public String process(HoroscopeRequestData requestData) 
	{
		// prepare
		Exchange transferData = ExchangeBuilder.anExchange(context).build();
		transferData.setProperty(OHO_REQUEST_DATA, requestData);
		
		// main
		transferData = context.createProducerTemplate().send(OHO_ROUTE_START_FROM, transferData);
		
		//finish
		return transferData.getProperty(OHO_RESPONSE_DATA, String.class);
//		
//		StringBuffer sb = new StringBuffer();
//		
//		sb.append("<h1>Hello <i>" + requestData.getName() + "</i></h1>");
//		
//		if (requestData.getGender().equals("m")) {
//			sb.append("<p>You are a <b>good</b> boy.</p>");
//		} else if (requestData.getGender().equals("w")) {
//			sb.append("<p>You are a <b>good</b> girl.</p>");
//			
//		} else {
//			sb.append("<p>You are a <b>good</b> person.</p>");
//		}
//
//		return sb.toString();
		
	}

}
