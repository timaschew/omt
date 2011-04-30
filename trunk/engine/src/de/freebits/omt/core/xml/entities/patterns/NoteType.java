//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.13 at 06:41:27 PM MEZ 
//


package de.freebits.omt.core.xml.entities.patterns;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *     			Element-Typ zur Beschreibung eines Tones innerhalb eines
 *     			Motives
 *     		
 * 
 * <p>Java class for noteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="noteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="deltatone" use="required" type="{http://freebits.de/OMT-engine/schemas/Patterns}deltatoneAttributeType" />
 *       &lt;attribute name="deltatime" use="required" type="{http://freebits.de/OMT-engine/schemas/Patterns}deltatimeAttributeType" />
 *       &lt;attribute name="length" use="required" type="{http://freebits.de/OMT-engine/schemas/Patterns}lengthAttributeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "noteType")
public class NoteType {

    @XmlAttribute(name = "deltatone", required = true)
    protected BigInteger deltatone;
    @XmlAttribute(name = "deltatime", required = true)
    protected float deltatime;
    @XmlAttribute(name = "length", required = true)
    protected float length;

    /**
     * Gets the value of the deltatone property.
     * 
     * @return
     *     possible object is
     *     {@link java.math.BigInteger }
     *     
     */
    public BigInteger getDeltatone() {
        return deltatone;
    }

    /**
     * Sets the value of the deltatone property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.math.BigInteger }
     *     
     */
    public void setDeltatone(BigInteger value) {
        this.deltatone = value;
    }

    /**
     * Gets the value of the deltatime property.
     * 
     */
    public float getDeltatime() {
        return deltatime;
    }

    /**
     * Sets the value of the deltatime property.
     * 
     */
    public void setDeltatime(float value) {
        this.deltatime = value;
    }

    /**
     * Gets the value of the length property.
     * 
     */
    public float getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     * 
     */
    public void setLength(float value) {
        this.length = value;
    }

}
