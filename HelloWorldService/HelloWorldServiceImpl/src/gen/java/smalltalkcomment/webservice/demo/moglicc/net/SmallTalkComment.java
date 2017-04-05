package smalltalkcomment.webservice.demo.moglicc.net;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.1.6
 * 2017-01-24T11:35:54.995+01:00
 * Generated source version: 3.1.6
 * 
 */
@WebService(targetNamespace = "http://net.moglicc.demo.webservice.smalltalkcomment/", name = "SmallTalkComment")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SmallTalkComment {

    @WebMethod(action = "http://localhost:18080/HelloWorldSoapService/services/HelloWorldServicePort/sayHello")
    @WebResult(name = "GiveSmallTalkCommentResponseWrapper", targetNamespace = "http://net.moglicc.demo.webservice.smalltalkcomment/", partName = "parameters")
    public GiveSmallTalkCommentResponseWrapper giveSmallTalkComment(
        @WebParam(partName = "parameters", name = "GiveSmallTalkCommentRequestWrapper", targetNamespace = "http://net.moglicc.demo.webservice.smalltalkcomment/")
        GiveSmallTalkCommentRequestWrapper parameters
    ) throws GiveSmallTalkCommentExceptionMessage;
}
