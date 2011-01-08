//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.07 at 10:07:24 AM MEZ 
//


package de.freebits.omt.core.xml.entities.motifextraction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Beschreibung einer Harmonie
 * 
 * <p>Java class for chordType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="chordType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="deltatone" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}deltatoneAttributeType" />
 *       &lt;attribute name="time" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}timeAttributeType" />
 *       &lt;attribute name="deltatime" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}deltatimeAttributeType" />
 *       &lt;attribute name="harmonyform" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}harmonyformAttributeType" />
 *       &lt;attribute name="length" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}lengthAttributeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chordType")
public class ChordType {

    @XmlAttribute(name = "deltatone", required = true)
    protected int deltatone;
    @XmlAttribute(name = "time", required = true)
    protected float time;
    @XmlAttribute(name = "deltatime", required = true)
    protected float deltatime;
    @XmlAttribute(name = "harmonyform", required = true)
    protected String harmonyform;
    @XmlAttribute(name = "length", required = true)
    protected float length;

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
     * Gets the value of the harmonyform property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHarmonyform() {
        return harmonyform;
    }

    /**
     * Sets the value of the harmonyform property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHarmonyform(String value) {
        this.harmonyform = value;
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
