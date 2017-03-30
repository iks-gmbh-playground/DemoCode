package de.test;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class represents the WebService for the client side. 
 */
@WebServiceClient(name = "HelloWorldSoapService", 
                  wsdlLocation = "http://localhost:8080/HelloWorldSoapService/services/HelloWorldServicePort?wsdl",
                  targetNamespace = "http://test.de/") 
public class HelloWorldServiceClientProxy extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://test.de/", "HelloWorldSoapService");
    public final static QName HelloWorldServicePort = new QName("http://test.de/", "HelloWorldServicePort");
    
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/HelloWorldSoapService/services/HelloWorldServicePort?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(HelloWorldServiceClientProxy.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/HelloWorldSoapService/services/HelloWorldServicePort?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public HelloWorldServiceClientProxy(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public HelloWorldServiceClientProxy(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HelloWorldServiceClientProxy() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public HelloWorldServiceClientProxy(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public HelloWorldServiceClientProxy(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public HelloWorldServiceClientProxy(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    

    @WebEndpoint(name = "HelloWorldServicePort")
    public HelloWorldService getHelloWorldServicePort() {
        return super.getPort(HelloWorldServicePort, HelloWorldService.class);
    }
    
    @WebEndpoint(name = "HelloWorldServicePort")
    public HelloWorldService getHelloWorldServicePort(WebServiceFeature... features) {
        return super.getPort(HelloWorldServicePort, HelloWorldService.class, features);
    }
}
