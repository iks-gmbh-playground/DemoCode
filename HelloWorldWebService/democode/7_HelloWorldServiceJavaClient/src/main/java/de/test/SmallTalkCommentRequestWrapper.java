package de.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.test.api.autogen.SmallTalkCommentRequest;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"request"})
@XmlRootElement(name = "SmallTalkCommentRequestWrapper")
public class SmallTalkCommentRequestWrapper 
{
    @XmlElement(required = true)
    protected SmallTalkCommentRequest request;

    public SmallTalkCommentRequest getRequest() {
        return request;
    }

    public void setRequest(SmallTalkCommentRequest value) {
        this.request = value;
    }
}
