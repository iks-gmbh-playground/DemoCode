package de.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.test.api.autogen.SayHelloResponse;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"response"})
@XmlRootElement(name = "SayHelloResponseWrapper")
public class SayHelloResponseWrapper {

    @XmlElement(required = true)
    protected SayHelloResponse response;

    public SayHelloResponse getResponse() {
        return response;
    }

    public void setResponse(SayHelloResponse value) {
        this.response = value;
    }
}
