package de.test;

import java.util.logging.Logger;

import javax.jws.WebService;

import de.test.api.HelloWorld;
import de.test.api.HelloWorldServiceException;
import de.test.api.autogen.SayHelloRequest;
import de.test.api.autogen.SayHelloResponse;
import de.test.api.autogen.SmallTalkCommentRequest;
import de.test.api.autogen.SmallTalkCommentResponse;

/**
 * This class represents the SSD soap binding between the service impl and service api.
 */
@WebService(targetNamespace = "http://test.de/", 
            portName = "HelloWorldServicePort", 
            serviceName = "HelloWorldSoapService")
public class HelloWorldServiceSoapWrapper implements HelloWorld {

	private static final Logger LOGGER = Logger.getLogger("HelloWorldServiceRESTWrapper");
	
	// do not set private due to access from 
	// HelloWorldTestExec#SmallTalkCommentIntegrationVariant2Test.createHelloWorldServiceSoapWrapper
	HelloWorldServiceImpl helloWorldService = new HelloWorldServiceImpl();
	
	@Override
	public SayHelloResponse sayHello(final SayHelloRequest request) 
		   throws HelloWorldServiceException
	{
		if (request != null) {
			LOGGER.info("SayHelloRequest received with name=" + request.getName());
			return helloWorldService.sayHello(request);
		} else {
			LOGGER.info("SayHelloRequest received with null request!");
			SayHelloResponse sayHelloResponse = new SayHelloResponse();
			sayHelloResponse.setHelloText("Hello ?");
			return sayHelloResponse;
		}
	}
	
	@Override
	public SmallTalkCommentResponse startSmallTalk(final SmallTalkCommentRequest request)
		   throws HelloWorldServiceException
	{
		LOGGER.info("SmallTalkCommentRequest received with name=" + request.getName());
		return helloWorldService.startSmallTalk(request);
	}

}
