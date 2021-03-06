//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.05 at 06:04:42 PM MEZ 
//


package de.freebits.omt.core.xml.entities.leadsheets;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for meaningAttributeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="meaningAttributeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="chord"/>
 *     &lt;enumeration value="melody"/>
 *     &lt;enumeration value="accompaniment"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "meaningAttributeType")
@XmlEnum
public enum MeaningAttributeType {

    @XmlEnumValue("chord")
    CHORD("chord"),
    @XmlEnumValue("melody")
    MELODY("melody"),
    @XmlEnumValue("accompaniment")
    ACCOMPANIMENT("accompaniment");
    private final String value;

    MeaningAttributeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MeaningAttributeType fromValue(String v) {
        for (MeaningAttributeType c: MeaningAttributeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
