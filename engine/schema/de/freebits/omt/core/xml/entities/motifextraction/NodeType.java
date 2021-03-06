//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.07 at 10:07:24 AM MEZ 
//


package de.freebits.omt.core.xml.entities.motifextraction;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * allgemeiner Knotentyp
 * 
 * <p>Java class for nodeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nodeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="id" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}idAttributeType" />
 *       &lt;attribute name="length" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}lengthAttributeType" />
 *       &lt;attribute name="deltalength" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}deltalengthAttributeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nodeType")
@XmlSeeAlso({
    MelodyNodeType.class,
    PattersNodeType.class,
    ChordsNodeType.class
})
public class NodeType {

    @XmlAttribute(name = "id", required = true)
    protected BigInteger id;
    @XmlAttribute(name = "length", required = true)
    protected float length;
    @XmlAttribute(name = "deltalength", required = true)
    protected float deltalength;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link java.math.BigInteger }
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
     *     {@link java.math.BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
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

    /**
     * Gets the value of the deltalength property.
     * 
     */
    public float getDeltalength() {
        return deltalength;
    }

    /**
     * Sets the value of the deltalength property.
     * 
     */
    public void setDeltalength(float value) {
        this.deltalength = value;
    }

}
