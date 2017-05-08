//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.05.03 um 02:01:30 PM CEST 
//


package com.iksgmbh.demo.hoo.control.api;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.iksgmbh.demo.hoo.control.api package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.iksgmbh.demo.hoo.control.api
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HOOOrderRequest }
     * 
     */
    public HOOOrderRequest createHOOOrderRequest() {
        return new HOOOrderRequest();
    }

    /**
     * Create an instance of {@link HOOHoroscopeRequest }
     * 
     */
    public HOOHoroscopeRequest createHOOHoroscopeRequest() {
        return new HOOHoroscopeRequest();
    }

    /**
     * Create an instance of {@link HOOPaymentRequest }
     * 
     */
    public HOOPaymentRequest createHOOPaymentRequest() {
        return new HOOPaymentRequest();
    }

    /**
     * Create an instance of {@link HOOOrderResponse }
     * 
     */
    public HOOOrderResponse createHOOOrderResponse() {
        return new HOOOrderResponse();
    }

    /**
     * Create an instance of {@link HOOHoroscopeResponse }
     * 
     */
    public HOOHoroscopeResponse createHOOHoroscopeResponse() {
        return new HOOHoroscopeResponse();
    }

}
