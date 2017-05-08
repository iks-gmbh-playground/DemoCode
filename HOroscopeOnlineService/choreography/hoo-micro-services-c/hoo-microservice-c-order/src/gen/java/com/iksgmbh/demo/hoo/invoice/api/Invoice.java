//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.05.03 um 08:51:13 AM CEST 
//


package com.iksgmbh.demo.hoo.invoice.api;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.iksgmbh.demo.hoo.order.api.HoroscopeType;


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
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="invoiceFactor" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="prePaid" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="bill" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "price",
    "invoiceFactor",
    "prePaid",
    "bill"
})
@XmlRootElement(name = "Invoice")
public class Invoice {

    protected long orderNumber;
    @XmlElement(required = true)
    protected String customerName;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected HoroscopeType horoscopeType;
    @XmlElement(required = true)
    protected BigDecimal price;
    @XmlElement(required = true)
    protected BigDecimal invoiceFactor;
    protected boolean prePaid;
    @XmlElement(required = true)
    protected String bill;

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
     * Ruft den Wert der invoiceFactor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getInvoiceFactor() {
        return invoiceFactor;
    }

    /**
     * Legt den Wert der invoiceFactor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setInvoiceFactor(BigDecimal value) {
        this.invoiceFactor = value;
    }

    /**
     * Ruft den Wert der prePaid-Eigenschaft ab.
     * 
     */
    public boolean isPrePaid() {
        return prePaid;
    }

    /**
     * Legt den Wert der prePaid-Eigenschaft fest.
     * 
     */
    public void setPrePaid(boolean value) {
        this.prePaid = value;
    }

    /**
     * Ruft den Wert der bill-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBill() {
        return bill;
    }

    /**
     * Legt den Wert der bill-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBill(String value) {
        this.bill = value;
    }

}
