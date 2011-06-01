/*
 *    Copyright 2011 The MITRE Corporation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.projecthdata.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.projecthdata package. 
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

    private final static QName _Version_QNAME = new QName("http://projecthdata.org/hdata/schemas/2009/06/core", "version");
    private final static QName _Id_QNAME = new QName("http://projecthdata.org/hdata/schemas/2009/06/core", "id");
    private final static QName _LastModified_QNAME = new QName("http://projecthdata.org/hdata/schemas/2009/06/core", "lastModified");
    private final static QName _Created_QNAME = new QName("http://projecthdata.org/hdata/schemas/2009/06/core", "created");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.projecthdata
     * 
     */
    public ObjectFactory() {
    }


    /**
     * Create an instance of {@link RootDocumentImpl }
     * 
     */
    public RootDocumentImpl createRoot() {
        RootDocumentImpl root = new RootDocumentImpl();
        return root;
    }

    /**
     * Create an instance of {@link ExtensionImpl }
     * 
     */
    public ExtensionImpl createExtension() {
        return new ExtensionImpl();
    }

    /**
     * Create an instance of {@link SectionImpl }
     * 
     */
    public SectionImpl createSection() {
        return new SectionImpl();
    }


    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://projecthdata.org/hdata/schemas/2009/06/core", name = "version")
    public JAXBElement<String> createVersion(String value) {
        return new JAXBElement<String>(_Version_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://projecthdata.org/hdata/schemas/2009/06/core", name = "id")
    public JAXBElement<String> createId(String value) {
        return new JAXBElement<String>(_Id_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://projecthdata.org/hdata/schemas/2009/06/core", name = "lastModified")
    public JAXBElement<XMLGregorianCalendar> createLastModified(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_LastModified_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://projecthdata.org/hdata/schemas/2009/06/core", name = "created")
    public JAXBElement<XMLGregorianCalendar> createCreated(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_Created_QNAME, XMLGregorianCalendar.class, null, value);
    }

}
