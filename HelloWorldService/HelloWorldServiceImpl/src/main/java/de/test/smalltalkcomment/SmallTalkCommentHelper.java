package de.test.smalltalkcomment;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import de.test.SmallTalkCommentProvider;
import de.test.api.HelloWorldServiceException;
import de.test.api.utils.ContractObjectToStringUtil;
import smalltalkcomment.webservice.demo.moglicc.net.GiveSmallTalkCommentRequest;
import smalltalkcomment.webservice.demo.moglicc.net.GiveSmallTalkCommentRequestWrapper;
import smalltalkcomment.webservice.demo.moglicc.net.GiveSmallTalkCommentResponseWrapper;
import smalltalkcomment.webservice.demo.moglicc.net.SmallTalkComment;
import smalltalkcomment.webservice.demo.moglicc.net.SmallTalkCommentService;

/**
 * Provides methods to create comments typically for a small talk conversation.
 * It's a facade that wraps different sources of comments. 
 * 
 * @author Reik Oberrath
 */
public class SmallTalkCommentHelper 
{
    private static final Logger LOGGER = Logger.getLogger(SmallTalkCommentHelper.class.getName());
    
    private static final QName SERVICE_NAME = new QName("http://net.moglicc.demo.webservice.smalltalkcomment/", 
    		                                            "SmallTalkCommentService");
    
    /**
     * Without an running Tomcat test environment,
     * this object must be replaced by an test object (e.g. mock).
     * To inject a mock for test purpose this instance is package friendly!
     */
    SmallTalkComment servicePort;

	/**
     * For integration test on component level this object
     * does NOT need replaced by an test object!
     */
    SmallTalkCommentProvider smallTalkCommentProvider = new SmallTalkCommentProvider();
    
    
    // #######################################################################
    //                  public comment methods
    // #######################################################################

    public String getCommentForAName(String name)  {
    	return smallTalkCommentProvider.getComment(name);
    }
    
    /**
     * Uses a webservice to get a comment, i.e. it serves as Client 
     * which sends an request to the SmallTalkCommentService!
     */
    public String getCommentForADate(Date requestDate)  
    {    
        try {
            GiveSmallTalkCommentRequestWrapper requestWrapper = createRequest(requestDate);
            LOGGER.log(Level.INFO, "SmallTalkCommentService Request:" + ContractObjectToStringUtil.toString(requestWrapper));
            
            GiveSmallTalkCommentResponseWrapper responseWrapper = getServicePort().giveSmallTalkComment(requestWrapper);
            LOGGER.log(Level.INFO, "SmallTalkCommentService Response:" + ContractObjectToStringUtil.toString(responseWrapper));
            
            return responseWrapper.getResponse().getSmallTalkComment();
        } 
        catch (Throwable t) {
			throw new HelloWorldServiceException("Error accessing SmallTalkCommentService.", t);
        }
    }

    // #######################################################################
    //                              private stuff
    // #######################################################################
    
	private SmallTalkComment getServicePort() {
		if (servicePort == null) {
        	SmallTalkCommentService service = new SmallTalkCommentService(SmallTalkCommentService.WSDL_LOCATION, SERVICE_NAME);
            servicePort = service.getSmallTalkCommentPort();
		}
		return servicePort;
	}

	private GiveSmallTalkCommentRequestWrapper createRequest(final Date requestDate) 
			       throws DatatypeConfigurationException 
	{
		GiveSmallTalkCommentRequestWrapper toReturn = new GiveSmallTalkCommentRequestWrapper();
		GiveSmallTalkCommentRequest request = new GiveSmallTalkCommentRequest();
        toReturn.setRequest(request);
		
        if (requestDate != null)
        {
        	GregorianCalendar c = new GregorianCalendar();
        	c.setTimeInMillis(requestDate.getTime());
        	XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        	request.setDate(date);
        }
		
		return toReturn;
	}    
}
