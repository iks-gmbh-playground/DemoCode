
package smalltalkcomment.webservice.demo.moglicc.net;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.6
 * 2017-04-06T16:58:47.247+02:00
 * Generated source version: 3.1.6
 */

@WebFault(name = "ErrorWrapper", targetNamespace = "http://net.moglicc.demo.webservice.smalltalkcomment/")
public class GiveSmallTalkCommentExceptionMessage extends Exception {
    
    private static final long serialVersionUID = 1L;
    
	private smalltalkcomment.webservice.demo.moglicc.net.ErrorWrapper errorWrapper;

    public GiveSmallTalkCommentExceptionMessage() {
        super();
    }
    
    public GiveSmallTalkCommentExceptionMessage(String message) {
        super(message);
    }
    
    public GiveSmallTalkCommentExceptionMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public GiveSmallTalkCommentExceptionMessage(String message, smalltalkcomment.webservice.demo.moglicc.net.ErrorWrapper errorWrapper) {
        super(message);
        this.errorWrapper = errorWrapper;
    }

    public GiveSmallTalkCommentExceptionMessage(String message, smalltalkcomment.webservice.demo.moglicc.net.ErrorWrapper errorWrapper, Throwable cause) {
        super(message, cause);
        this.errorWrapper = errorWrapper;
    }

    public smalltalkcomment.webservice.demo.moglicc.net.ErrorWrapper getFaultInfo() {
        return this.errorWrapper;
    }
}
