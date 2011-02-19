//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.05 at 06:04:42 PM MEZ 
//


package de.freebits.omt.core.xml.entities.leadsheets;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Stimme innerhalb eines Musikstückes
 * 
 * <p>Java class for voiceNodeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="voiceNodeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="reference-id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="meaning" use="required" type="{http://freebits.de/OMT-engine/schemas/Leadsheets}meaningAttributeType" />
 *       &lt;attribute name="deltatime" use="required" type="{http://freebits.de/OMT-engine/schemas/Leadsheets}deltaTimeAttributeType" />
 *       &lt;attribute name="deltatone" type="{http://freebits.de/OMT-engine/schemas/Leadsheets}deltatoneAttributeType" />
 *       &lt;attribute name="basicnote" type="{http://freebits.de/OMT-engine/schemas/Leadsheets}basicnodeAttributeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "voiceNodeType")
public class VoiceNodeType {

    @XmlAttribute(name = "reference-id", required = true)
    protected int referenceId;
    @XmlAttribute(name = "meaning", required = true)
    protected MeaningAttributeType meaning;
    @XmlAttribute(name = "deltatime", required = true)
    protected float deltatime;
    @XmlAttribute(name = "deltatone")
    protected BigInteger deltatone;
    @XmlAttribute(name = "basicnote")
    protected BigInteger basicnote;

    /**
     * Gets the value of the referenceId property.
     * 
     */
    public int getReferenceId() {
        return referenceId;
    }

    /**
     * Sets the value of the referenceId property.
     * 
     */
    public void setReferenceId(int value) {
        this.referenceId = value;
    }

    /**
     * Gets the value of the meaning property.
     * 
     * @return
     *     possible object is
     *     {@link MeaningAttributeType }
     *     
     */
    public MeaningAttributeType getMeaning() {
        return meaning;
    }

    /**
     * Sets the value of the meaning property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeaningAttributeType }
     *     
     */
    public void setMeaning(MeaningAttributeType value) {
        this.meaning = value;
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
     * @return
     *     possible object is
     *     {@link BigInteger }
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
     *     {@link BigInteger }
     *     
     */
    public void setDeltatone(BigInteger value) {
        this.deltatone = value;
    }

    /**
     * Gets the value of the basicnote property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBasicnote() {
        return basicnote;
    }

    /**
     * Sets the value of the basicnote property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBasicnote(BigInteger value) {
        this.basicnote = value;
    }

}