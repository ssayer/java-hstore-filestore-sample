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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.projecthdata.javahstore.hdr.RootDocument;
import org.projecthdata.javahstore.hdr.Section;
import org.projecthdata.javahstore.utils.FileUtils;

/**
 *
 * @author ssayer
 */
public class RootDocumentImplTest {
  
    RootDocumentImpl instance;
    File file;
    String extensionXml = "<extension extensionId=\"allergies\" contentType=\"application/xml\">http://projecthdata.org/allergies</extension>";
    String sectionXml = "<section path=\"allergies\" name=\"Allergies\" extensionId=\"allergies\"/>";
    String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    "<root xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://projecthdata.org/hdata/schemas/2009/06/core\">" +
    "<id>12345</id><version>1</version><created>2010-02-09</created><lastModified>2010-02-09</lastModified>" +
    "<extensions>" + extensionXml + "</extensions>" +
    "<sections>" + sectionXml + "</sections></root>";



    private static String defaultSectionPath = "test";
    
    public RootDocumentImplTest() {
      file = new File("root.xml");
      file.deleteOnExit(); // Just in case @ssayer
    }

    @Before
    public void setUp() throws IOException {
      instance = new RootDocumentImpl("1234", file);
      instance.save();
      File sectionFile = new File(instance.getFile(), defaultSectionPath);
      SectionImpl sec = new SectionImpl(sectionFile, new ExtensionImpl(), "section1", defaultSectionPath);
      instance.getSections().add(sec);
    }


    @After
    public void tearDown() {
      file.delete();
    }
  /**
   * Test of getRootSections method, of class RootDocumentImpl.
   */
  @Test
  public void testGetRootSections() {
    RootDocument root = instance;
    Collection<Section> sections = root.getRootSections();
    assertEquals(sections.size(), instance.sections.size());
  }

  /**
   * Test of createChildSection method, of class RootDocumentImpl.
   */
  @Test
  public void testCreateChildSection() throws IOException, JAXBException {
    ExtensionImpl e = new ExtensionImpl("allergies");
    instance.createChildSection(e, "", "");
    
    assertEquals(2, instance.getRootSections().size());
    Unmarshaller un = JAXBContext.newInstance(RootDocumentImpl.class).createUnmarshaller();
    assertTrue(file.exists());
    instance = (RootDocumentImpl) un.unmarshal(file );
    assertEquals(2, instance.getRootSections().size());
  }

  /**
   * Test of getChildSection method, of class RootDocumentImpl.
   */
  @Test
  public void testGetChildSection() {
    Section expResult = null;
    Section result = instance.getChildSection(defaultSectionPath);
    assertNotNull(result);
  }

    @Test
  public void testSave() throws IOException {
    RootDocumentImpl root = new RootDocumentImpl();
    root.setId("1234");
    root.setFile(file);
    ExtensionImpl ext = new ExtensionImpl("allergies", "application/xml");
    root.addExtension(ext);
    root.createChildSection(ext, "allergies", "Allergies");
    assertTrue(root.save());
    String text = getFileContents(file);
    assertTrue(text.indexOf("<extension ") != -1);
    assertTrue(text.indexOf("<section ") != -1);
  }

  @Test
  public void testLoad() throws IOException {
    FileUtils.writeInputStreamToFile(new ByteArrayInputStream(content.getBytes()), file);
    RootDocumentImpl doc = RootDocumentImpl.load(file);
    assertNotSame(0, doc.getExtensions().size());
    assertNotSame(0, doc.getSections().size());
  }

  private String getFileContents(File file) throws IOException {
    BufferedReader reader = new BufferedReader( new FileReader (file));
    String line  = null;
    StringBuilder stringBuilder = new StringBuilder();
    String ls = System.getProperty("line.separator");
    while( ( line = reader.readLine() ) != null ) {
        stringBuilder.append( line );
        stringBuilder.append( ls );
    }
    return stringBuilder.toString();
  }

}