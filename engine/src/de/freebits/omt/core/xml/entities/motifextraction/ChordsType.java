//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.07 at 10:07:24 AM MEZ 
//


package de.freebits.omt.core.xml.entities.motifextraction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Harmoniecontainer
 * 
 * <p>Java class for chordsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="chordsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="node" type="{http://freebits.de/OMT-engine/schemas/MotivEXtraction}chordsNodeType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chordsType", propOrder = {
    "node"
})
public class ChordsType {

    @XmlElement(required = true)
    protected ChordsNodeType node;

    /**
     * Gets the value of the node property.
     * 
     * @return
     *     possible object is
     *     {@link ChordsNodeType }
     *     
     */
    public ChordsNodeType getNode() {
        return node;
    }

    /**
     * Sets the value of the node property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChordsNodeType }
     *     
     */
    public void setNode(ChordsNodeType value) {
        this.node = value;
    }

}
