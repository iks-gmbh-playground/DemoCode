//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.05.03 um 08:51:26 AM CEST 
//


package com.iksgmbh.demo.hoo.order.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für HoroscopeType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="HoroscopeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="MISC"/&gt;
 *     &lt;enumeration value="LOVE"/&gt;
 *     &lt;enumeration value="JOB"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "HoroscopeType")
@XmlEnum
public enum HoroscopeType {

    MISC,
    LOVE,
    JOB;

    public String value() {
        return name();
    }

    public static HoroscopeType fromValue(String v) {
        return valueOf(v);
    }

}
