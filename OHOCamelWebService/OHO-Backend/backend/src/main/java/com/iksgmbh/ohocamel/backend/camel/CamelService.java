package com.iksgmbh.ohocamel.backend.camel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iksgmbh.oho.backend.HoroscopeRequestData;
import com.iksgmbh.ohocamel.backend.camel.config.CamelContextHandler;
import com.iksgmbh.ohocamel.backend.camel.processor.CamelOhoMainProcessor;

@Service
public class CamelService 
{
	private static final Logger CAMEL_LOGGER = LoggerFactory.getLogger(CamelService.class);

	@Autowired
	private CamelContextHandler camelContextHandler;
	
	public String getHtmlHoroscope(HoroscopeRequestData requestData) 
	{
		try {
			CamelOhoMainProcessor processor = new CamelOhoMainProcessor(camelContextHandler.getCamelContext());
			return processor.process(requestData);
		} catch (Throwable e) {
			CAMEL_LOGGER.error(e.getMessage());
			return "";
		}
	}
	
}
