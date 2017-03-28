
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
 *         &lt;element name="response" type="{http://net.moglicc.demo.webservice.smalltalkcomment/}GiveSmallTalkCommentResponse"/&gt;
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
    "response"
})
@XmlRootElement(name = "GiveSmallTalkCommentResponseWrapper")
public class GiveSmallTalkCommentResponseWrapper {

    @XmlElement(required = true)
    protected GiveSmallTalkCommentResponse response;

    /**
     * Ruft den Wert der response-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link GiveSmallTalkCommentResponse }
     *     
     */
    public GiveSmallTalkCommentResponse getResponse() {
        return response;
    }

    /**
     * Legt den Wert der response-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link GiveSmallTalkCommentResponse }
     *     
     */
    public void setResponse(GiveSmallTalkCommentResponse value) {
        this.response = value;
    }

}
