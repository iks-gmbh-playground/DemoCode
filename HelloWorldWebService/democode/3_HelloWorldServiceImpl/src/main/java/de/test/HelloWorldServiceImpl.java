package de.test;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.test.api.HelloWorld;
import de.test.api.HelloWorldServiceException;
import de.test.api.autogen.ErrorType;
import de.test.api.autogen.SayHelloRequest;
import de.test.api.autogen.SayHelloResponse;
import de.test.api.autogen.SmallTalkCommentRequest;
import de.test.api.autogen.SmallTalkCommentResponse;
import de.test.api.jaxbdo.JaxbSayHelloResponse;
import de.test.api.jaxbdo.JaxbSmallTalkCommentResponse;
import de.test.api.utils.XMLGregorianCalendarUtil;
import de.test.smalltalkcomment.SmallTalkCommentHelper;

public class HelloWorldServiceImpl implements HelloWorld 
{
    private static final Logger LOGGER = Logger.getLogger(HelloWorldServiceImpl.class.getName());

	private static final String DEFAULT_NAME = "World";

	SmallTalkCommentHelper smallTalkCommentHelper = new SmallTalkCommentHelper();

	@Override
	public SayHelloResponse sayHello(final SayHelloRequest request) 
	{
		try {
			validateSayHelloRequest(request);
			SayHelloResponse toReturn = new JaxbSayHelloResponse();
			toReturn.setHelloText("Hello " + request.getName() + "!");
			return toReturn;
		} catch (IllegalArgumentException ve) {
			throw new HelloWorldServiceException(ve.getMessage(), ErrorType.MISSING_INPUT.name(), ve);
		} catch (Exception e) {
			throw new HelloWorldServiceException("Unkown error detected!", ErrorType.UNEXPECTED.name(), e);
		}
	}

	@Override
	public SmallTalkCommentResponse startSmallTalk(final SmallTalkCommentRequest request)
	{
		try {
			validateSmallTalkCommentRequest(request);
			
			final Date requestDate = XMLGregorianCalendarUtil.toDate(request.getDate());
			final String comment1 = smallTalkCommentHelper.getCommentForADate(requestDate);
			final String comment2 = smallTalkCommentHelper.getCommentForAName(request.getName());
			
			SmallTalkCommentResponse toReturn = new JaxbSmallTalkCommentResponse();
			toReturn.setSmallTalkComment("Hi " + request.getName() 
			                                   + ", how are you?" 
					                           + System.getProperty("line.separator")
					                           + comment1
										       + System.getProperty("line.separator")
										       + comment2);
			return toReturn;
		} 
		catch (IllegalArgumentException ve) {
			throw new HelloWorldServiceException(ve.getMessage(), ErrorType.MISSING_INPUT.name(), ve);
		} catch (HelloWorldServiceException e) {
			throw new HelloWorldServiceException(e.getError().getMessage());
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "############## Errpr 1");
			throw new HelloWorldServiceException("Unkown error detected!");
		}
	}
	
	private void validateSmallTalkCommentRequest(SmallTalkCommentRequest request) {
		if (request.getName() == null)
		{
			throw new IllegalArgumentException("Name is missing.");
		}
	}

	private void validateSayHelloRequest(SayHelloRequest request) {
		if (request.getName() == null)
		{
			throw new IllegalArgumentException("Name is missing.");
		}
		if (request.getName().trim().equals("?"))
		{
			request.setName(DEFAULT_NAME);
		}
	}
}
