//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.03.13 um 08:23:35 AM CET 
//


package com.iksgmbh.demo.hoo.order.api;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="orderNumber" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="horoscopeType" type="{api.order.hoo.demo.iksgmbh.com}HoroscopeType"/&gt;
 *         &lt;element name="ageOfTargetPerson" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="creationDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="paid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="horoscopeFetched" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
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
    "orderNumber",
    "customerName",
    "horoscopeType",
    "ageOfTargetPerson",
    "creationDateTime",
    "price",
    "paid",
    "horoscopeFetched"
})
@XmlRootElement(name = "Order")
public class Order {

    protected long orderNumber;
    @XmlElement(required = true)
    protected String customerName;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected HoroscopeType horoscopeType;
    protected long ageOfTargetPerson;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationDateTime;
    protected BigDecimal price;
    protected Boolean paid;
    protected Boolean horoscopeFetched;

    /**
     * Ruft den Wert der orderNumber-Eigenschaft ab.
     * 
     */
    public long getOrderNumber() {
        return orderNumber;
    }

    /**
     * Legt den Wert der orderNumber-Eigenschaft fest.
     * 
     */
    public void setOrderNumber(long value) {
        this.orderNumber = value;
    }

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
     * Ruft den Wert der horoscopeType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link HoroscopeType }
     *     
     */
    public HoroscopeType getHoroscopeType() {
        return horoscopeType;
    }

    /**
     * Legt den Wert der horoscopeType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link HoroscopeType }
     *     
     */
    public void setHoroscopeType(HoroscopeType value) {
        this.horoscopeType = value;
    }

    /**
     * Ruft den Wert der ageOfTargetPerson-Eigenschaft ab.
     * 
     */
    public long getAgeOfTargetPerson() {
        return ageOfTargetPerson;
    }

    /**
     * Legt den Wert der ageOfTargetPerson-Eigenschaft fest.
     * 
     */
    public void setAgeOfTargetPerson(long value) {
        this.ageOfTargetPerson = value;
    }

    /**
     * Ruft den Wert der creationDateTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreationDateTime() {
        return creationDateTime;
    }

    /**
     * Legt den Wert der creationDateTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreationDateTime(XMLGregorianCalendar value) {
        this.creationDateTime = value;
    }

    /**
     * Ruft den Wert der price-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Legt den Wert der price-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrice(BigDecimal value) {
        this.price = value;
    }

    /**
     * Ruft den Wert der paid-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPaid() {
        return paid;
    }

    /**
     * Legt den Wert der paid-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPaid(Boolean value) {
        this.paid = value;
    }

    /**
     * Ruft den Wert der horoscopeFetched-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHoroscopeFetched() {
        return horoscopeFetched;
    }

    /**
     * Legt den Wert der horoscopeFetched-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHoroscopeFetched(Boolean value) {
        this.horoscopeFetched = value;
    }

}
