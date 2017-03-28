package de.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.test.api.autogen.SmallTalkCommentResponse;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"response"})
@XmlRootElement(name = "SmallTalkCommentResponseWrapper")
public class SmallTalkCommentResponseWrapper 
{
    @XmlElement(required = true)
    protected SmallTalkCommentResponse response;

    public SmallTalkCommentResponse getResponse() {
        return response;
    }

    public void setResponse(SmallTalkCommentResponse value) {
        this.response = value;
    }
}
