package de.test;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.test.api.HelloWorldServiceException;
import de.test.api.autogen.SayHelloRequest;
import de.test.api.autogen.SayHelloResponse;
import de.test.api.autogen.SmallTalkCommentRequest;
import de.test.api.autogen.SmallTalkCommentResponse;
import de.test.api.jaxbdo.JaxbSayHelloResponse;
import de.test.api.jaxbdo.JaxbSmallTalkCommentResponse;


/**
 * This class represents the SSD REST binding 
 * between the service impl and service api.
 */
@Path("/")
public class HelloWorldServiceRESTWrapper 
{
	private static final Logger LOGGER = Logger.getLogger("HelloWorldServiceRESTWrapper");

	private HelloWorldServiceImpl helloWorldService = new HelloWorldServiceImpl();
	
	@PUT
	@Path("/sayHello")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public SayHelloResponse sayHello(final SayHelloRequest request) 
		   throws HelloWorldServiceException
	{
		final SayHelloResponse sayHelloResponse;
		
		if (request != null) 
		{
			LOGGER.info("SayHelloRequest received with name=" + request.getName());
			sayHelloResponse = helloWorldService.sayHello(request);
		} 
		else 
		{
			LOGGER.info("SayHelloResponse requested with a null-request!");
			sayHelloResponse = new JaxbSayHelloResponse();
			sayHelloResponse.setHelloText("Hello?");
		}
		
		return sayHelloResponse;
	}

	@PUT
	@Path("/startSmalltalk")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public SmallTalkCommentResponse startSmallTalk(final SmallTalkCommentRequest request)
		   throws HelloWorldServiceException
	{
		final SmallTalkCommentResponse smallTalkCommentResponse;
		
		if (request != null) 
		{
			LOGGER.info("SmallTalkCommentRequest received with name=" + request.getName());
			smallTalkCommentResponse = helloWorldService.startSmallTalk(request);
		} 
		else 
		{
			LOGGER.info("SmallTalkCommentResponse requested with a null-request!");
			smallTalkCommentResponse = new JaxbSmallTalkCommentResponse();
			smallTalkCommentResponse.setSmallTalkComment("What to talk about?");
			return smallTalkCommentResponse;
		}
		
		return smallTalkCommentResponse;
	}
}
