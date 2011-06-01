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

import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.projecthdata.javahstore.hdr.Extension;
import org.projecthdata.javahstore.hdr.RootDocument;
import org.projecthdata.javahstore.hdr.Section;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "root")
public class RootDocumentImpl implements RootDocument {

  @XmlElement(required = true)
  protected String id;
  @XmlElement(required = true)
  protected String version;
  @XmlElement(required = true)
  @XmlSchemaType(name = "date")
  protected XMLGregorianCalendar created;
  @XmlElement(required = true)
  @XmlSchemaType(name = "date")
  protected XMLGregorianCalendar lastModified;
  @XmlElement(name = "extension")
  @XmlElementWrapper(name = "extensions")
  protected List<ExtensionImpl> extensions;
  @XmlElement(name = "section")
  @XmlElementWrapper(name = "sections")
  protected List<SectionImpl> sections;
  @XmlTransient
  protected File file;

  private static final JAXBContext context;
  private static final String TAG = "Root";

  static {
      try {
        context = JAXBContext.newInstance(RootDocumentImpl.class);
      } catch (JAXBException ex) {
        Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
        throw new RuntimeException(ex);
      }
  }


  public boolean addExtension(ExtensionImpl e) {
    return extensions.add(e);
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
    for (SectionImpl section : sections) {
      section.setParentDir(file.getParentFile());
    }
  }

  protected RootDocumentImpl() {
    this.version = "1";
    this.extensions = new ArrayList<ExtensionImpl>();
    this.sections = new ArrayList<SectionImpl>();
  }

  public RootDocumentImpl(String id, File rootFile) {
    this();
    this.id = id;
    this.file = rootFile;
  }

  @Override
  public String getId() {
    return id;
  }

  public void setId(String value) {
    this.id = value;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String value) {
    this.version = value;
  }

  public XMLGregorianCalendar getCreatedXML() {
    return created;
  }

  public void setCreatedXML(XMLGregorianCalendar value) {
    this.created = value;
  }

  public XMLGregorianCalendar getLastModifiedXML() {
    return lastModified;
  }

  public void setLastModifiedXML(XMLGregorianCalendar value) {
    this.lastModified = value;
  }

  public List<ExtensionImpl> getExtensionsXML() {
    return extensions;
  }

  public void setExtensions(List<ExtensionImpl> value) {
    this.extensions = value;
  }

  public List<SectionImpl> getSections() {
    return sections;
  }

  public void setSections(List<SectionImpl> value) {
    this.sections = value;
  }

  @Override
  public Date getCreated() {
    return xmlCalendarToDate(getCreatedXML());
  }

  @Override
  public Date getLastModified() {
    return xmlCalendarToDate(getCreatedXML());
  }

  @Override
  public Collection<Extension> getExtensions() {
    return (Collection<Extension>) (Collection) (extensions);
  }

  @Override
  public Extension getExtension(String id) {
    for (Extension ext : extensions) {
      if (ext.getId().equals(id)) {
        return ext;
      }
    }
    throw new IllegalArgumentException();
  }

  private Date xmlCalendarToDate(XMLGregorianCalendar cal) {
    if (cal == null) {
      return null;
    }
    return cal.toGregorianCalendar().getTime();

  }

//  JAXB forces generics on us, and in order to map to the hData RI
//  we must use this terrible hack @ssayer
  @Override
  public Collection<Section> getRootSections() {
    return (Collection<Section>) (Collection) (sections);
  }

  @Override
  public void createChildSection(Extension e, String path, String name) {
    File rootDir = this.file.getParentFile();
    SectionImpl sec = new SectionImpl(rootDir, e, name, path, this);
    this.sections.add(sec);
    this.save();
  }

  @Override
  public Section getChildSection(String sectionPath) {
    Section sec = null;
    for (SectionImpl section : sections) {
      if (section.getPath().equals(sectionPath)) {
        sec = section;
      }
    }
    return sec;
  }

  public static RootDocumentImpl load(File file) {
    RootDocumentImpl root = null;
    try {
      Unmarshaller un = context.createUnmarshaller();
      
      root = (RootDocumentImpl) un.unmarshal(file);
      root.setFile(file);
      return root;
    } catch (JAXBException ex) {
      Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  public synchronized final boolean save() {
    try {

      if (file == null || (!file.exists() && !file.createNewFile())) {
        Logger.getLogger(TAG).log(Level.INFO, "Unable to get file or file is null");
        return false;
      }

      Marshaller marshaller = context.createMarshaller();
      marshaller.marshal(this, file);
      return true;
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
}
