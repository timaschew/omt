//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.05 at 06:04:42 PM MEZ 
//


package de.freebits.omt.core.xml.entities.leadsheets;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.freebits.omt.core.xml.entities package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Leadsheets_QNAME = new QName("http://freebits.de/OMT-engine/schemas/Leadsheets", "leadsheets");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.freebits.omt.core.xml.entities
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LeadsheetsNodeType }
     * 
     */
    public LeadsheetsNodeType createLeadsheetsNodeType() {
        return new LeadsheetsNodeType();
    }

    /**
     * Create an instance of {@link VoiceNodeType }
     * 
     */
    public VoiceNodeType createVoiceNodeType() {
        return new VoiceNodeType();
    }

    /**
     * Create an instance of {@link LeadsheetNodeType }
     * 
     */
    public LeadsheetNodeType createLeadsheetNodeType() {
        return new LeadsheetNodeType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LeadsheetsNodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freebits.de/OMT-engine/schemas/Leadsheets", name = "leadsheets")
    public JAXBElement<LeadsheetsNodeType> createLeadsheets(LeadsheetsNodeType value) {
        return new JAXBElement<LeadsheetsNodeType>(_Leadsheets_QNAME, LeadsheetsNodeType.class, null, value);
    }

}
