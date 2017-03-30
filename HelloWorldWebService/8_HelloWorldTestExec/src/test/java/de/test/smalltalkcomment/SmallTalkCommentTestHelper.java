package de.test.smalltalkcomment;

import java.util.Date;

import de.test.api.utils.XMLGregorianCalendarUtil;
import net.moglicc.demo.webservice.smalltalkcomment.SmallTalkCommentService;
import net.moglicc.demo.webservice.smalltalkcomment.autogen.GiveSmallTalkCommentRequest;
import net.moglicc.demo.webservice.smalltalkcomment.autogen.GiveSmallTalkCommentResponse;

/**
 * This is actually a fake object that replaces the real implementation.
 * However, it does not replaces the business logic!!!
 * This fake represents a short cut, that avoids to call the webservice deployed on a TomCat.
 * Instead the shortcut leads directy to the class files where the business logic is implemented.  
 * 
 * @author Reik Oberrath
 */
public class SmallTalkCommentTestHelper extends SmallTalkCommentHelper 
{
	private SmallTalkCommentService smallTalkCommentService = new SmallTalkCommentService();

	@Override
    public String getCommentForADate(Date requestDate)  
	{
		GiveSmallTalkCommentRequest request = new GiveSmallTalkCommentRequest();
		request.setDate( XMLGregorianCalendarUtil.toGregorianCaldendar(requestDate) );
		
		GiveSmallTalkCommentResponse result = smallTalkCommentService.giveSmallTalkComment(request);
		
		return result.getSmallTalkComment();
	}
}
