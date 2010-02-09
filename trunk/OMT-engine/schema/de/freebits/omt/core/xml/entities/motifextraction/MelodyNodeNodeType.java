//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.06 at 12:13:39 PM MEZ 
//


package de.freebits.omt.core.xml.entities.motifextraction;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Bestandteil einer Melodie
 * 
 * <p>Java class for melodyNodeNodeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="melodyNodeNodeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="id" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}idAttributeType" />
 *       &lt;attribute name="time" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}timeAttributeType" />
 *       &lt;attribute name="deltatime" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}deltatimeAttributeType" />
 *       &lt;attribute name="deltatone" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}deltatoneAttributeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "melodyNodeNodeType")
public class MelodyNodeNodeType {

    @XmlAttribute(name = "id", required = true)
    protected BigInteger id;
    @XmlAttribute(name = "time", required = true)
    protected float time;
    @XmlAttribute(name = "deltatime", required = true)
    protected float deltatime;
    @XmlAttribute(name = "deltatone", required = true)
    protected int deltatone;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the time property.
     * 
     */
    public float getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     */
    public void setTime(float value) {
        this.time = value;
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
     * Gets the value of the deltatone property.
     * 
     */
    public int getDeltatone() {
        return deltatone;
    }

    /**
     * Sets the value of the deltatone property.
     * 
     */
    public void setDeltatone(int value) {
        this.deltatone = value;
    }

}