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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Beschreibung einer Melodie
 * 
 * <p>Java class for melodyNodeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="melodyNodeType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}nodeType">
 *       &lt;sequence>
 *         &lt;element name="validation-notes" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}validationNotesType" minOccurs="0"/>
 *         &lt;element name="node" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}melodyNodeNodeType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="root" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}rootAttributeType" />
 *       &lt;attribute name="level" use="required" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}levelAttributeType" />
 *       &lt;attribute name="basicnote" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}basicnoteAttributeType" />
 *       &lt;attribute name="deltatime" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}deltatimeAttributeType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "melodyNodeType", propOrder = {
    "validationNotes",
    "node"
})
public class MelodyNodeType
    extends NodeType
{

    @XmlElement(name = "validation-notes")
    protected ValidationNotesType validationNotes;
    protected List<MelodyNodeNodeType> node;
    @XmlAttribute(name = "root", required = true)
    protected BigInteger root;
    @XmlAttribute(name = "level", required = true)
    protected BigInteger level;
    @XmlAttribute(name = "basicnote")
    protected Integer basicnote;
    @XmlAttribute(name = "deltatime")
    protected Float deltatime;

    /**
     * Gets the value of the validationNotes property.
     * 
     * @return
     *     possible object is
     *     {@link ValidationNotesType }
     *     
     */
    public ValidationNotesType getValidationNotes() {
        return validationNotes;
    }

    /**
     * Sets the value of the validationNotes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidationNotesType }
     *     
     */
    public void setValidationNotes(ValidationNotesType value) {
        this.validationNotes = value;
    }

    /**
     * Gets the value of the node property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the node property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MelodyNodeNodeType }
     * 
     * 
     */
    public List<MelodyNodeNodeType> getNode() {
        if (node == null) {
            node = new ArrayList<MelodyNodeNodeType>();
        }
        return this.node;
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
     * Gets the value of the basicnote property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBasicnote() {
        return basicnote;
    }

    /**
     * Sets the value of the basicnote property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBasicnote(Integer value) {
        this.basicnote = value;
    }

    /**
     * Gets the value of the deltatime property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getDeltatime() {
        return deltatime;
    }

    /**
     * Sets the value of the deltatime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setDeltatime(Float value) {
        this.deltatime = value;
    }

}
