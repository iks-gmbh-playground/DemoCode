package de.test;

import java.util.Date;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import de.test.api.autogen.SayHelloRequest;
import de.test.api.autogen.SayHelloResponse;
import de.test.api.autogen.SmallTalkCommentRequest;
import de.test.api.autogen.SmallTalkCommentResponse;
import de.test.api.jaxbdo.JaxbSayHelloRequest;
import de.test.api.jaxbdo.JaxbSmallTalkCommentRequest;
import de.test.api.utils.ContractObjectToStringUtil;
import de.test.api.utils.XMLGregorianCalendarUtil;

/**
 * Java API that provides a method that calls the HelloWorldRestService 
 * running on local Tomcat.
 * 
 * @author Reik Oberrath
 */
public final class HelloWorldRestRequestExecutor {

    private HelloWorldRestRequestExecutor() {
    }

    public static void main(String args[]) throws java.lang.Exception {
    	final String name = "REST RequestExecutor Main Method";
    	sendSayHelloWordRequest(name);
    	System.out.println("");
    	System.out.println("");
    	sendStartSmallTalkRequest(name, null);
    }

    /**
     * Sends a SmallTalkComment-Request to the Tomcat, where the HelloWorldService is supposed to run.
     * @param name and date to be used in the request
     * @return small talk comment contained in the response or error message
     */
    public static String sendStartSmallTalkRequest(final String name,
    		                                       final Date requestDate) 
    {
        SmallTalkCommentRequestWrapper requestWrapper = createSmallTalkCommentRequest(name, requestDate);
        systemOutRequest(requestWrapper);
        
    	ClientConfig config = new DefaultClientConfig();
    	Client client = Client.create(config);
    	SmallTalkCommentResponse result = client.resource("http://localhost:18080/HelloWorldRestService/services/startSmalltalk")
   	                                            .type(MediaType.APPLICATION_XML)
   	                                            .accept(MediaType.APPLICATION_XML).put(SmallTalkCommentResponse.class, 
   	                                            		                               requestWrapper.request);
    	
    	systemOutResponse(result);
    	
   	    return result.getSmallTalkComment();
    }    
    
    /**
     * Sends a sayHello-Request to the Tomcat, where the HelloWorldService is supposed to run.
     * @param name to be used in the request
     * @return helloText contained in the response or error message
     */
    public static String sendSayHelloWordRequest(final String name) 
    {
        SayHelloRequestWrapper requestWrapper = createSayHelloRequest(name);
        systemOutRequest(requestWrapper);
        
    	ClientConfig config = new DefaultClientConfig();
    	Client client = Client.create(config);
    	SayHelloResponse result = client.resource("http://localhost:18080/HelloWorldRestService/services/sayHello")
   	                                    .type(MediaType.APPLICATION_XML)
   	                                    .accept(MediaType.APPLICATION_XML).put(SayHelloResponse.class, 
   	                                         		                           requestWrapper.request);    	 
    	systemOutResponse(result);
   	    return result.getHelloText();
    }

	private static void systemOutResponse(Object responseWrapper) {
		System.out.println("Response:");
		 System.out.println("---------------------------------------------------------");
		 System.out.println(ContractObjectToStringUtil.toString(responseWrapper));
		 System.out.println("---------------------------------------------------------");
	}

	private static void systemOutRequest(Object requestWrapper) {
		System.out.println();
        System.out.println("Request:");
        System.out.println("---------------------------------------------------------");
        System.out.println(ContractObjectToStringUtil.toString(requestWrapper));
        System.out.println("---------------------------------------------------------");
        System.out.println();
	}

	private static SayHelloRequestWrapper createSayHelloRequest(final String name) {
        System.out.println("Creating SayHelloRequest for HelloWorldRestService...");
        SayHelloRequestWrapper toReturn = new SayHelloRequestWrapper();
        SayHelloRequest request = new JaxbSayHelloRequest();
        toReturn.setRequest(request);
		request.setName( name );
		return toReturn;
	}

	private static SmallTalkCommentRequestWrapper createSmallTalkCommentRequest(final String name,
			         															final Date requestDate) 
	{
        System.out.println("Creating SmallTalkCommentRequest for HelloWorldRestService...");
        SmallTalkCommentRequestWrapper toReturn = new SmallTalkCommentRequestWrapper();
        SmallTalkCommentRequest request = new JaxbSmallTalkCommentRequest();
        toReturn.setRequest(request);
		request.setName( name );
		request.setDate( XMLGregorianCalendarUtil.toGregorianCaldendar(requestDate) );
		return toReturn;
	}
}
