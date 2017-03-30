
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
 *         &lt;element name="error" type="{http://net.moglicc.demo.webservice.smalltalkcomment/}GiveSmallTalkCommentError"/&gt;
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
    "error"
})
@XmlRootElement(name = "ErrorWrapper")
public class ErrorWrapper {

    @XmlElement(required = true)
    protected GiveSmallTalkCommentError error;

    /**
     * Ruft den Wert der error-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link GiveSmallTalkCommentError }
     *     
     */
    public GiveSmallTalkCommentError getError() {
        return error;
    }

    /**
     * Legt den Wert der error-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link GiveSmallTalkCommentError }
     *     
     */
    public void setError(GiveSmallTalkCommentError value) {
        this.error = value;
    }

}
