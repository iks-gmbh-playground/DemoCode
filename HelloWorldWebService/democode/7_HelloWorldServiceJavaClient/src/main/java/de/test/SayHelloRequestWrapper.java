package de.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.test.api.autogen.SayHelloRequest;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"request"})
@XmlRootElement(name = "SayHelloRequestWrapper")
public class SayHelloRequestWrapper {

    @XmlElement(required = true)
    protected SayHelloRequest request;

    public SayHelloRequest getRequest() {
        return request;
    }

    public void setRequest(SayHelloRequest value) {
        this.request = value;
    }
}
