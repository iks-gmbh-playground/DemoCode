package com.iksgmbh.ohocamel.backend.camel.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.model.RoutesDefinition;
import org.apache.camel.spring.SpringCamelContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CamelContextHandler 
{
	private static final String BASIC_ROUTES = "BasicRoutes.xml";
	private static final String ROUTE_DIR = "camelRoutes/";
	private static final Map<String, String> globalOptions = new HashMap<>();

	private CamelContext camelContext;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private Logger camelLogger;
	
//	@Autowired
//	private CamelConfigurationProperties camelConfigurationProperties;
	
	@PostConstruct
	public void postConstruct() 
	{
		camelContext = new SpringCamelContext(applicationContext);
		camelContext.setGlobalOptions(globalOptions);

		// define context config here if needed
		camelContext.disableJMX();
		addRoutesToContext(BASIC_ROUTES);

		try {
			camelContext.start();
		} catch (Exception e) {
			throw new RuntimeCamelException("Error constructing CamelContext!", e);
		}
	}

	public CamelContext getCamelContext() {
		return camelContext;
	}
	
	private void addRoutesToContext(String routeFilename) 
	{
		try {
			InputStream resourceAsStream = this.getClass().getResourceAsStream("/" + ROUTE_DIR + routeFilename);
			RoutesDefinition camelRoutes = camelContext.loadRoutesDefinition(resourceAsStream);
			camelContext.addRouteDefinitions(camelRoutes.getRoutes());;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeCamelException("Error loading route " + routeFilename + "!", e);
		}
	}
	
	public void reset(CamelContext context) {
		List<Route> routes = context.getRoutes();
		routes.forEach(route -> removeRoute(context, route));
		camelLogger.info("All routes removed from context.");
	}

	public void removeRoute(CamelContext context, Route route) {
		try {
			context.removeRoute(route.getId());
			camelLogger.info("Route " + route.getId() + " removed from context.");
		} catch (Exception e) {
			throw new RuntimeCamelException("Error removing route " + route.getId() + " from context!", e);
		}
	}

}
