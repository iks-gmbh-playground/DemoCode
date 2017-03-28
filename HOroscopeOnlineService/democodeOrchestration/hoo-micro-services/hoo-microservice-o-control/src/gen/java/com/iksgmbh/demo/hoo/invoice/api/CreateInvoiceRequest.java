//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.03.01 um 12:04:07 PM CET 
//


package com.iksgmbh.demo.hoo.invoice.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.order.api.Order;


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
 *         &lt;element ref="{api.order.hoo.demo.iksgmbh.com}Order"/&gt;
 *         &lt;element ref="{api.horoscope.hoo.demo.iksgmbh.com}Horoscope"/&gt;
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
    "order",
    "horoscope"
})
@XmlRootElement(name = "CreateInvoiceRequest")
public class CreateInvoiceRequest {

    @XmlElement(name = "Order", namespace = "api.order.hoo.demo.iksgmbh.com", required = true)
    protected Order order;
    @XmlElement(name = "Horoscope", namespace = "api.horoscope.hoo.demo.iksgmbh.com", required = true)
    protected Horoscope horoscope;

    /**
     * mandatory field
     * 
     * @return
     *     possible object is
     *     {@link Order }
     *     
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Legt den Wert der order-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Order }
     *     
     */
    public void setOrder(Order value) {
        this.order = value;
    }

    /**
     * mandatory field
     * 
     * @return
     *     possible object is
     *     {@link Horoscope }
     *     
     */
    public Horoscope getHoroscope() {
        return horoscope;
    }

    /**
     * Legt den Wert der horoscope-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Horoscope }
     *     
     */
    public void setHoroscope(Horoscope value) {
        this.horoscope = value;
    }

}
