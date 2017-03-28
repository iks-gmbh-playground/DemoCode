//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.03.21 um 01:19:02 PM CET 
//


package com.iksgmbh.demo.hoo.control.api;

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
 *         &lt;element name="horoscopeText" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="statusInfo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "horoscopeText",
    "statusInfo"
})
@XmlRootElement(name = "HOO_HoroscopeResponse")
public class HOOHoroscopeResponse {

    @XmlElement(required = true)
    protected String horoscopeText;
    @XmlElement(required = true)
    protected String statusInfo;

    /**
     * Ruft den Wert der horoscopeText-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoroscopeText() {
        return horoscopeText;
    }

    /**
     * Legt den Wert der horoscopeText-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoroscopeText(String value) {
        this.horoscopeText = value;
    }

    /**
     * Ruft den Wert der statusInfo-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusInfo() {
        return statusInfo;
    }

    /**
     * Legt den Wert der statusInfo-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusInfo(String value) {
        this.statusInfo = value;
    }

}
