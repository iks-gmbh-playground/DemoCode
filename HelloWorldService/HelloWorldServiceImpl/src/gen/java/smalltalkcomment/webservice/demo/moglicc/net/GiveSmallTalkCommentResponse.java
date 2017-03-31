
package smalltalkcomment.webservice.demo.moglicc.net;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für GiveSmallTalkCommentResponse complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="GiveSmallTalkCommentResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="smallTalkComment" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GiveSmallTalkCommentResponse", propOrder = {
    "smallTalkComment"
})
public class GiveSmallTalkCommentResponse {

    @XmlElement(required = true)
    protected String smallTalkComment;

    /**
     * Ruft den Wert der smallTalkComment-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmallTalkComment() {
        return smallTalkComment;
    }

    /**
     * Legt den Wert der smallTalkComment-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmallTalkComment(String value) {
        this.smallTalkComment = value;
    }

}
