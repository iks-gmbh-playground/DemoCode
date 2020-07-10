package com.iksgmbh.ohocamel.backend.camel.processor;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.ExchangeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iksgmbh.oho.backend.HoroscopeRequestData;
import com.iksgmbh.ohocamel.backend.camel.CamelService;

/**
 * Main Processor for the OHO Service.
 * It called directly by the RestController.
 * Other processors are embedded in one of the Camel routes and need to represent Spring Components!
 * 
 * @author Reik Oberrath
 */
public class CamelOhoMainProcessor 
{
	private static final Logger CAMEL_LOGGER = LoggerFactory.getLogger(CamelService.class);

	public static final String OHO_MAIN_SCRIPT = "HoroscopeMainTemplate.groovy";
	public static final String OHO_START_ROUTE = "OhoStartRoute.xml";
	public static final String OHO_ROUTE_START_FROM = "direct:start";
	public static final String OHO_REQUEST_DATA = "HoroscopeRequestData";
	public static final String OHO_RESPONSE_DATA = "HoroscopeResponseData";


	private CamelContext context;

	public CamelOhoMainProcessor(CamelContext camelContext) {
		this.context = camelContext;
	}

	public String process(HoroscopeRequestData requestData) 
	{
		// prepare: create dataTransferObject for camel processing
		Exchange exchange = ExchangeBuilder.anExchange(context).build();
		exchange.setProperty(OHO_REQUEST_DATA, requestData);
		
		// main: start cascading camel processing
		exchange = context.createProducerTemplate().send(OHO_ROUTE_START_FROM, exchange);
		
		//finish: read result from dto
		checkForExceptions(exchange.getException());
		return exchange.getProperty(OHO_RESPONSE_DATA, String.class);
	}

	private void checkForExceptions(Exception exception) 
	{
		if (exception == null) return;
		CAMEL_LOGGER.error(exception.getMessage());
		if (exception.getCause() != null) {
			CAMEL_LOGGER.error(exception.getCause().getMessage());
		}
	}

}
