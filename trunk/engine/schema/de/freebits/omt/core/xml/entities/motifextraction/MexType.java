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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Container f�r die Beschreibung eines Musikst�cks
 * 
 * <p>Java class for mexType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="patterns" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}patternsType"/>
 *         &lt;element name="chords" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}chordsType"/>
 *         &lt;element name="melody" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}melodyType"/>
 *       &lt;/all>
 *       &lt;attribute name="leadsheet-id" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}leadsheetIdAttributeType" />
 *       &lt;attribute name="is-a-id" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}leadsheetIdAttributeType" />
 *       &lt;attribute name="title" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}titleAttributeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mexType", propOrder = {

})
public class MexType {

    @XmlElement(required = true)
    protected PatternsType patterns;
    @XmlElement(required = true)
    protected ChordsType chords;
    @XmlElement(required = true)
    protected MelodyType melody;
    @XmlAttribute(name = "leadsheet-id", required = true)
    protected BigInteger leadsheetId;
    @XmlAttribute(name = "is-a-id", required = true)
    protected BigInteger isAId;
    @XmlAttribute(name = "title", required = true)
    protected String title;

    /**
     * Gets the value of the patterns property.
     * 
     * @return
     *     possible object is
     *     {@link PatternsType }
     *     
     */
    public PatternsType getPatterns() {
        return patterns;
    }

    /**
     * Sets the value of the patterns property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatternsType }
     *     
     */
    public void setPatterns(PatternsType value) {
        this.patterns = value;
    }

    /**
     * Gets the value of the chords property.
     * 
     * @return
     *     possible object is
     *     {@link ChordsType }
     *     
     */
    public ChordsType getChords() {
        return chords;
    }

    /**
     * Sets the value of the chords property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChordsType }
     *     
     */
    public void setChords(ChordsType value) {
        this.chords = value;
    }

    /**
     * Gets the value of the melody property.
     * 
     * @return
     *     possible object is
     *     {@link MelodyType }
     *     
     */
    public MelodyType getMelody() {
        return melody;
    }

    /**
     * Sets the value of the melody property.
     * 
     * @param value
     *     allowed object is
     *     {@link MelodyType }
     *     
     */
    public void setMelody(MelodyType value) {
        this.melody = value;
    }

    /**
     * Gets the value of the leadsheetId property.
     * 
     * @return
     *     possible object is
     *     {@link java.math.BigInteger }
     *     
     */
    public BigInteger getLeadsheetId() {
        return leadsheetId;
    }

    /**
     * Sets the value of the leadsheetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.math.BigInteger }
     *     
     */
    public void setLeadsheetId(BigInteger value) {
        this.leadsheetId = value;
    }

    /**
     * Gets the value of the isAId property.
     * 
     * @return
     *     possible object is
     *     {@link java.math.BigInteger }
     *     
     */
    public BigInteger getIsAId() {
        return isAId;
    }

    /**
     * Sets the value of the isAId property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.math.BigInteger }
     *     
     */
    public void setIsAId(BigInteger value) {
        this.isAId = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

}
