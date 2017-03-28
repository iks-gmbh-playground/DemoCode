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
 *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="birthdayOfTargetPerson" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="horoscopeType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "customerName",
    "birthdayOfTargetPerson",
    "horoscopeType"
})
@XmlRootElement(name = "HOO_OrderRequest")
public class HOOOrderRequest {

    @XmlElement(required = true)
    protected String customerName;
    @XmlElement(required = true)
    protected String birthdayOfTargetPerson;
    @XmlElement(required = true)
    protected String horoscopeType;

    /**
     * Ruft den Wert der customerName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Legt den Wert der customerName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    /**
     * Ruft den Wert der birthdayOfTargetPerson-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthdayOfTargetPerson() {
        return birthdayOfTargetPerson;
    }

    /**
     * Legt den Wert der birthdayOfTargetPerson-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthdayOfTargetPerson(String value) {
        this.birthdayOfTargetPerson = value;
    }

    /**
     * Ruft den Wert der horoscopeType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoroscopeType() {
        return horoscopeType;
    }

    /**
     * Legt den Wert der horoscopeType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoroscopeType(String value) {
        this.horoscopeType = value;
    }

}
