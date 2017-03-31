package de.test.api;

import de.test.api.autogen.SayHelloRequest;
import de.test.api.autogen.SayHelloResponse;
import de.test.api.autogen.SmallTalkCommentRequest;
import de.test.api.autogen.SmallTalkCommentResponse;

public interface HelloWorld {
	
	public static final String NAMESPACE = "http://test.de/api/";

	/**
	 * Hello World standard feature.
	 * @param request
	 * @return
	 * @throws HelloWorldServiceException
	 */
	SayHelloResponse sayHello(SayHelloRequest request) throws HelloWorldServiceException;

	/**
	 * Hello World deluxe feature.
	 * @param request
	 * @return
	 * @throws HelloWorldServiceException
	 */
	SmallTalkCommentResponse startSmallTalk(SmallTalkCommentRequest request) throws HelloWorldServiceException;

}