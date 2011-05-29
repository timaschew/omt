//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.07 at 10:07:24 AM MEZ 
//


package de.freebits.omt.core.xml.entities.motifextraction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Container f�r eine Folge von Harmonien
 * 
 * <p>Java class for chordsNodeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="chordsNodeType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}nodeType">
 *       &lt;sequence>
 *         &lt;element name="chord" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}chordType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="root" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}rootAttributeType" />
 *       &lt;attribute name="level" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}levelAttributeType" />
 *       &lt;attribute name="deltatime" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}deltatimeAttributeType" />
 *       &lt;attribute name="deltatone" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}deltatoneAttributeType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chordsNodeType", propOrder = {
    "chord"
})
public class ChordsNodeType
    extends NodeType
{

    protected List<ChordType> chord;
    @XmlAttribute(name = "root", required = true)
    protected BigInteger root;
    @XmlAttribute(name = "level", required = true)
    protected BigInteger level;
    @XmlAttribute(name = "deltatime", required = true)
    protected float deltatime;
    @XmlAttribute(name = "deltatone", required = true)
    protected int deltatone;

    /**
     * Gets the value of the chord property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the chord property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChord().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChordType }
     * 
     * 
     */
    public List<ChordType> getChord() {
        if (chord == null) {
            chord = new ArrayList<ChordType>();
        }
        return this.chord;
    }

    /**
     * Gets the value of the root property.
     * 
     * @return
     *     possible object is
     *     {@link java.math.BigInteger }
     *     
     */
    public BigInteger getRoot() {
        return root;
    }

    /**
     * Sets the value of the root property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.math.BigInteger }
     *     
     */
    public void setRoot(BigInteger value) {
        this.root = value;
    }

    /**
     * Gets the value of the level property.
     * 
     * @return
     *     possible object is
     *     {@link java.math.BigInteger }
     *     
     */
    public BigInteger getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.math.BigInteger }
     *     
     */
    public void setLevel(BigInteger value) {
        this.level = value;
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