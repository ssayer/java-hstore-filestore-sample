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

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.projecthdata.javahstore.hdr.DocumentMetadata;
import org.projecthdata.javahstore.hdr.Extension;
import org.projecthdata.javahstore.hdr.Section;
import org.projecthdata.javahstore.hdr.SectionDocument;
import org.projecthdata.javahstore.hdr.impl.SectionDocumentImpl;
import org.projecthdata.javahstore.support.MimeTypesRegistry;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://projecthdata.org/hdata/schemas/2009/06/core}section" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="extensionId" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="requirement">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *             &lt;enumeration value="mandatory"/>
 *             &lt;enumeration value="optional"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class SectionImpl implements Section {

    @XmlElement()
    protected List<SectionImpl> section;
    @XmlAttribute(name = "path", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String path;
    @XmlAttribute(name = "name")
    @XmlSchemaType(name = "anySimpleType")
    protected String name;
    @XmlAttribute(name = "extensionId", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String extensionId;
    @XmlAttribute(name = "requirement")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String requirement;
    
    @XmlTransient
    protected RootDocumentImpl root;

    @XmlTransient
    protected SectionImpl parent;

    @XmlTransient
    protected File sectionDir;

    private static MimeTypesRegistry reg = MimeTypesRegistry.newInstance();
    private static MetaDataFilter metaFilter = new MetaDataFilter();

    @XmlTransient
    private Collection<SectionDocumentImpl> documents;

  SectionImpl() {
    this.documents = new ArrayList<SectionDocumentImpl>();
    this.section = new ArrayList<SectionImpl>();
  }

  SectionImpl(File dir, Extension e, String name, String path) {
    this();
    this.sectionDir = new File(dir, path);
    this.sectionDir.mkdir();
    this.extensionId = e.getId();
    this.name = name;
    this.path = path;
  }

  SectionImpl(File dir, Extension e, String name, String path, SectionImpl parent) {
    this(dir, e, name, path);
    this.parent = parent;
  }

  SectionImpl(File dir, Extension e, String name, String path, RootDocumentImpl root) {
    this(dir, e, name, path);
    this.root = root;
  }

    public void afterUnmarshal(Unmarshaller un, Object parent) {
      if (parent.getClass() == RootDocumentImpl.class) {
        this.root = (RootDocumentImpl) parent;
      } else if (parent.getClass() == SectionImpl.class) {
        this.parent = (SectionImpl) parent;
      }
      
    }


    /**
     * Gets the value of the path property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @Override
    public String getPath() {
        return path;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the value of the extensionId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlTransient
    public String getExtensionId() {
        return extensionId;
    }

    /**
     * Sets the value of the extensionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtensionId(String value) {
        this.extensionId = value;
    }

    /**
     * Gets the value of the requirement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @XmlTransient
    public String getRequirement() {
        return requirement;
    }

    /**
     * Sets the value of the requirement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequirement(String value) {
        this.requirement = value;
    }


  public File getSectionDir() {
    return sectionDir;
  }

  public void setParentDir(File parentDir) {
    this.sectionDir = new File(parentDir, this.path);
    for (SectionImpl sectionImpl : section) {
      sectionImpl.setParentDir(this.sectionDir);
    }
  }

  @Override
  public Extension getExtension() {
    return getRoot().getExtension(extensionId);
  }

  @Override
  public Collection<Section> getChildSections() {
    Collection<Section> sectionsCollection = new ArrayList<Section>();
    for (Section sec : section) {
      sectionsCollection.add(sec);
    }
    return sectionsCollection;
  }

  @Override
  public Section getChildSection(String path) {
    Section found = null;

    for (SectionImpl s : section) {
      if (s.getPath().equals(path)) {
        found = s;
      }
      
    }
    return found;
  }

  @Override
  public Section createChildSection(Extension extension, String path, String name) {
    File childDir = new File(this.sectionDir, path);
    SectionImpl sec = new SectionImpl(childDir, extension, name, path, this);
    this.section.add(sec);
    save();
    return sec;
  }

  @Override
  public void deleteChildSection(Section childSection) {
    this.section.remove((SectionImpl) childSection);
    save();
  }

  @Override
  public SectionDocument createDocument(String mediaType, InputStream content) throws IOException {
    File document = buildDocFile(mediaType, content);
    return new SectionDocumentImpl(content, document);
  }

  @Override
  public SectionDocument createDocument(String mediaType, InputStream content, DocumentMetadata metadata) throws IOException {
    File file = buildDocFile(mediaType, content);
    return new SectionDocumentImpl(content, file, metadata);
  }

  @Override
  public SectionDocument getChildDocument(String path) {
    File file = new File(sectionDir, path);
    
    if(file.exists()) {
      return new SectionDocumentImpl(file);
    }
    return null;
  }

  @Override
  public void deleteDocument(SectionDocument document) {
    SectionDocumentImpl doc = (SectionDocumentImpl) document;
    this.documents.remove(doc);
    doc.delete();
  }

  // Lazily load the documents
  @Override
  public Collection<SectionDocument> getChildDocuments() {
    File[] docs = null;
    System.out.println(sectionDir);

    if (this.documents.isEmpty()) {
      docs = this.sectionDir.listFiles(metaFilter);    
      for (File file : docs) {
        this.documents.add(new SectionDocumentImpl(file));
      }
    }

    return (Collection<SectionDocument>) (Collection) this.documents;

  }

  private RootDocumentImpl getRoot() {
    if(this.root != null) {
      return this.root;
    } else {  
      return this.parent.getRoot();
    }
  }

  public boolean save() {
    return this.getRoot().save();
  }

  private File buildDocFile(String mediaType, InputStream content) {
    String fileExt = reg.mimeTypetoFileExt(mediaType);
    String docName = "document" + content.hashCode() + "." + fileExt;
    return new File(sectionDir, docName);
  }

  private static class MetaDataFilter implements FilenameFilter {

    @Override
    public boolean accept(File parentFile, String path) {
      File file = new File(parentFile, path);
       return !file.isDirectory() && !file.isHidden();
    }

  }

}
