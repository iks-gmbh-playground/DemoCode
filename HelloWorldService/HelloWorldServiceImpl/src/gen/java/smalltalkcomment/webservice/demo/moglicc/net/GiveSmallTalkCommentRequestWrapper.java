
package smalltalkcomment.webservice.demo.moglicc.net;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="request" type="{http://net.moglicc.demo.webservice.smalltalkcomment/}GiveSmallTalkCommentRequest"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "request"
})
@XmlRootElement(name = "GiveSmallTalkCommentRequestWrapper")
public class GiveSmallTalkCommentRequestWrapper {

    @XmlElement(required = true)
    protected GiveSmallTalkCommentRequest request;

    /**
     * Ruft den Wert der request-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link GiveSmallTalkCommentRequest }
     *     
     */
    public GiveSmallTalkCommentRequest getRequest() {
        return request;
    }

    /**
     * Legt den Wert der request-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link GiveSmallTalkCommentRequest }
     *     
     */
    public void setRequest(GiveSmallTalkCommentRequest value) {
        this.request = value;
    }

}
