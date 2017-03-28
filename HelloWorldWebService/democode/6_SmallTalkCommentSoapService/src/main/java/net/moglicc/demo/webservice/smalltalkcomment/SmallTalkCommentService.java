package net.moglicc.demo.webservice.smalltalkcomment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import javax.jws.WebService;

import org.joda.time.DateTime;

import net.moglicc.demo.webservice.smalltalkcomment.autogen.GiveSmallTalkCommentRequest;
import net.moglicc.demo.webservice.smalltalkcomment.autogen.GiveSmallTalkCommentResponse;

/**
 * This class represents a NON-SSD solution of a webservice. 
 * It serves as an antipattern demonstration.
 * However, is used from HelloWorld.sayHelloPlus! 
 */
@WebService(targetNamespace = "http://net.moglicc.demo.webservice.smalltalkcomment/", 
            portName = "SmallTalkCommentServicePort", 
            serviceName = "SmallTalkCommentService")
public class SmallTalkCommentService 
{
	private static final Logger LOGGER = Logger.getLogger(SmallTalkCommentService.class.getName());
	
	public GiveSmallTalkCommentResponse giveSmallTalkComment(final GiveSmallTalkCommentRequest request)
	{
		LOGGER.info("Method giveSmallTalkComment called.");
		
		String comment;
		if (request.getDate() == null) {
			String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());
			comment = "Nice day, this " + dayOfWeek + ", isn't it?";
		}
		else 
		{
			DateTime requestDate = new DateTime(request.getDate().toGregorianCalendar().getTimeInMillis());
			if (requestDate.plusYears(1).isBeforeNow())
			{
				comment = "how nice to greet a time traveller from the past!";
			}
			else if (requestDate.minusYears(1).isAfterNow())
			{
				comment = "great to greet a time traveller from the future!";
			}
			else
			{
				String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());
				comment = "have a good time on this " + dayOfWeek + "!";
			}
			
			if (requestDate.getHourOfDay() < 4) {
				comment = "Hello, " + comment; 
			}
			else if (requestDate.getHourOfDay() < 12) {  // TODO does not work on Tomcat! Why?
				comment = "Good morning, " + comment; 
			}
			else if (requestDate.getHourOfDay() < 16) {
				comment = "Good day, " + comment; 
			}
			else if (requestDate.getHourOfDay() < 19) {
				comment = "Good afternoon, " + comment; 
			}
			else if (requestDate.getHourOfDay() < 22) {
				comment = "Good evening, " + comment; 
			}
			else {
				comment = "Good night, " + comment; 
			}
		}
		
		GiveSmallTalkCommentResponse toReturn = new GiveSmallTalkCommentResponse();
		toReturn.setSmallTalkComment(comment);
		return toReturn;
	}

}
