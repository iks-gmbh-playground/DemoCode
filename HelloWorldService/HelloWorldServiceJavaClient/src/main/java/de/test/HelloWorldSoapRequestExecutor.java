package de.test;

import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;

import de.test.api.HelloWorldServiceException;
import de.test.api.autogen.SayHelloRequest;
import de.test.api.autogen.SmallTalkCommentRequest;
import de.test.api.utils.ContractObjectToStringUtil;
import de.test.api.utils.XMLGregorianCalendarUtil;

/**
 * Java API that provides a method that calls the HelloWorldSoapService 
 * running on local Tomcat.
 * 
 * @author Reik Oberrath
 */
public final class HelloWorldSoapRequestExecutor 
{
    private static final QName SERVICE_NAME = new QName("http://test.de/", "HelloWorldSoapService");
	private static boolean startedFromMain = false;

    private HelloWorldSoapRequestExecutor() {
    }

    public static void main(String args[]) throws java.lang.Exception {
    	startedFromMain = true;
    	final String name = "SOAP RequestExecutor Main Method";
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
        HelloWorldService servicePort = createServicePort();
        SmallTalkCommentRequestWrapper requestWrapper = createSmallTalkCommentRequest(name, requestDate);
        systemOutRequest(requestWrapper);
        
        try {
            SmallTalkCommentResponseWrapper responseWrapper = servicePort.startSmallTalk(requestWrapper);
            systemOutResponse(responseWrapper);
            return responseWrapper.getResponse().getSmallTalkComment();
       } catch (HelloWorldServiceException e) { 
           System.out.println("Expected exception: HelloWorldServiceException has occurred.");
           System.out.println(e.toString());
           return "ERROR: " + e.getMessage();
       } catch (Exception ex) {
           System.out.println("Unexpected exception: " + ex.getClass() + " has occurred.");
       		System.out.println(ex.toString());
       		return "ERROR: " + ex.getMessage();
       }
    }    
    
    /**
     * Sends a sayHello-Request to the Tomcat, where the HelloWorldService is supposed to run.
     * @param name to be used in the request
     * @return helloText contained in the response or error message
     */
    public static String sendSayHelloWordRequest(final String name) 
    {
        HelloWorldService servicePort = createServicePort();
        SayHelloRequestWrapper requestWrapper = createSayHelloRequest(name);
        systemOutRequest(requestWrapper);
        
        try {
             SayHelloResponseWrapper responseWrapper = servicePort.sayHello(requestWrapper);
             systemOutResponse(responseWrapper);
             return responseWrapper.getResponse().getHelloText();
        } catch (HelloWorldServiceException e) { 
            System.out.println("Expected exception: HelloWorldServiceException has occurred.");
            System.out.println(e.toString());
            return "ERROR: " + e.getMessage();
        } catch (Exception ex) {
            System.out.println("Unexpected exception: " + ex.getClass() + " has occurred.");
        	System.out.println(ex.toString());
            return "ERROR: " + ex.getMessage();
        }
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
    
	private static HelloWorldServiceClientProxy createWebServiceProxy() {
		try {			
			return new HelloWorldServiceClientProxy(HelloWorldServiceClientProxy.WSDL_LOCATION, SERVICE_NAME);
		} catch (WebServiceException e) {
			System.err.println("");
			System.err.println("#####################################");
			System.err.println("PROBLEM: Server not found!");
			if (e.getCause() != null && e.getCause().getCause() != null) {				
				System.err.println(e.getCause().getCause().getMessage());
				System.err.println("Likely reasons: TomCat not running or port mismatch.");
			}
			System.err.println("#####################################");
			if (startedFromMain ) System.exit(0);
			return null;
		}
	}

	private static HelloWorldService createServicePort() {
        HelloWorldServiceClientProxy service = createWebServiceProxy();
        if (service == null) throw new RuntimeException("Hello World Soap Service unavailable.");
        return service.getHelloWorldServicePort();
	}

	private static SayHelloRequestWrapper createSayHelloRequest(final String name) {
        System.out.println("Creating SayHelloRequest for HelloWorldRestService...");
        SayHelloRequestWrapper toReturn = new SayHelloRequestWrapper();
        SayHelloRequest request = new SayHelloRequest();
        toReturn.setRequest(request);
		request.setName( name );
		return toReturn;
	}

	private static SmallTalkCommentRequestWrapper createSmallTalkCommentRequest(final String name,
			         															final Date requestDate) 
	{
        System.out.println("Creating SmallTalkCommentRequest for HelloWorldRestService...");
        SmallTalkCommentRequestWrapper toReturn = new SmallTalkCommentRequestWrapper();
        SmallTalkCommentRequest request = new SmallTalkCommentRequest();
        toReturn.setRequest(request);
		request.setName( name );
		request.setDate( XMLGregorianCalendarUtil.toGregorianCaldendar(requestDate) );
		return toReturn;
	}
}
