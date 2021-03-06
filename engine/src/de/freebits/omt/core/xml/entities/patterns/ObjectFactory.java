//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.13 at 06:41:27 PM MEZ 
//


package de.freebits.omt.core.xml.entities.patterns;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.freebits.omt.core.xml.entities.patterns package. 
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

    private final static QName _Patterns_QNAME = new QName("http://freebits.de/OMT-engine/schemas/Patterns", "patterns");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.freebits.omt.core.xml.entities.patterns
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PatternsType }
     * 
     */
    public PatternsType createPatternsType() {
        return new PatternsType();
    }

    /**
     * Create an instance of {@link NodeType }
     * 
     */
    public NodeType createNodeType() {
        return new NodeType();
    }

    /**
     * Create an instance of {@link NoteType }
     * 
     */
    public NoteType createNoteType() {
        return new NoteType();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link PatternsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freebits.de/OMT-engine/schemas/Patterns", name = "patterns")
    public JAXBElement<PatternsType> createPatterns(PatternsType value) {
        return new JAXBElement<PatternsType>(_Patterns_QNAME, PatternsType.class, null, value);
    }

}
