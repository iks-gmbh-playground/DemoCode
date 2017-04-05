package de.test;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import de.test.api.HelloWorldServiceException;

@WebService(targetNamespace = "http://test.de/", name = "HelloWorldSoapService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface HelloWorldService {

    @WebResult(name = "SmallTalkCommentResponseWrapper", targetNamespace = "http://test.de/", partName = "parameters")
    @WebMethod(action = "http://localhost:18080/HelloWorldSoapService/services/HelloWorldServicePort/startSmallTalk")
    public SmallTalkCommentResponseWrapper startSmallTalk(
        @WebParam(partName = "parameters", name = "SmallTalkCommentRequestWrapper", targetNamespace = "http://test.de/")
        SmallTalkCommentRequestWrapper parameters
    ) throws HelloWorldServiceException;

    @WebResult(name = "SayHelloResponseWrapper", targetNamespace = "http://test.de/", partName = "parameters")
    @WebMethod(action = "http://localhost:18080/HelloWorldSoapService/services/HelloWorldServicePort/sayHello")
    public SayHelloResponseWrapper sayHello(
        @WebParam(partName = "parameters", name = "SayHelloRequestWrapper", targetNamespace = "http://test.de/")
        SayHelloRequestWrapper parameters
    ) throws HelloWorldServiceException;
}
