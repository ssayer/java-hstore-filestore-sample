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

import org.projecthdata.javahstore.utils.FileUtils;
import java.io.BufferedReader;
import java.io.IOException;
import org.projecthdata.javahstore.hdr.SectionDocument;
import java.io.ByteArrayInputStream;
import org.projecthdata.javahstore.hdr.Section;
import java.io.File;
import java.io.InputStreamReader;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.projecthdata.javahstore.hdr.DocumentMetadata;
import static org.junit.Assert.*;

/**
 *
 * @author ssayer
 */
public class SectionImplTest {
    private File rootDir;
    private File rootFile;
    private RootDocumentImpl root;
    private Section rootSec;
    DocumentMetadata meta;
    private static String rootSectionPath = "allergies";
    private static ExtensionImpl ext = new ExtensionImpl("dicom");
    private static String childPath = "food";
    private static String type = "application/xml";
    private static String content = "<?xml version=\"1.0\"?><test>Important Medical Data</test>";

  @Before
  public void setUp() throws Exception {
    String id = "12345";
    rootDir = new File(id);
    rootDir.mkdir();
    rootFile = new File(rootDir, "root.xml");
    rootFile.createNewFile();
    rootFile.deleteOnExit();
    root = new RootDocumentImpl("1234", rootFile);
    root.createChildSection(ext, rootSectionPath, "Allergies");
    rootSec = root.getChildSection(rootSectionPath);
    meta = new DocumentMetadata("<DocumentMetaData xmlns='http://projecthdata.org/hdata/schemas/2009/11/metadata'>"
          + "<PedigreeInfo>"
          + "<Author>Bob</Author>"
          + "</PedigreeInfo>"
          + "<DocumentId>xyzzy</DocumentId>"
          + "<Title>The Title</Title>"
          + "<RecordDate>"
          + "<CreatedDateTime>2006-05-04T18:13:51.0Z</CreatedDateTime>"
          + "</RecordDate>"
          + "</DocumentMetaData>");
  }

  @After
  public void tearDown() {
    FileUtils.recursiveDelete(rootDir);
  }

  @Test
  public void testSection() {
    SectionImpl sec = (SectionImpl) rootSec;
    assertNotNull(sec.getSectionDir());
  }

  /**
   * Test of createChildSection method, of class SectionImpl.
   */
  @Test
  public void testCreateAndGetChildSection() {
    rootSec.createChildSection(ext, childPath, "Food Allergies");
    RootDocumentImpl root2 = RootDocumentImpl.load(rootFile);
    Section sec2 = root2.getChildSection(rootSectionPath).getChildSection(childPath);
    assertNotNull(sec2);
    assertEquals(childPath, sec2.getPath());
  }

  /**
   * Test of deleteChildSection method, of class SectionImpl.
   */
  @Test
  public void testDeleteChildSection() {
    Section childSec = rootSec.createChildSection(ext, childPath, "Food Allergies");
    rootSec.deleteChildSection(childSec);
    RootDocumentImpl root2 = RootDocumentImpl.load(rootFile);
    assertNull(root2.getChildSection(rootSectionPath).getChildSection(childPath));
  }

  /**
   * Test of createDocument method, of class SectionImpl.
   */
  @Test
  public void testCreateDocument() throws Exception {
    SectionDocument doc = rootSec.createDocument(type, new ByteArrayInputStream(content.getBytes()));
    testDocument(doc);
  }

  /**
   * Test of createDocument method, of class SectionImpl.
   */
  @Test
  public void testCreateDocumentWithMetaData() throws Exception {
    SectionDocument doc = rootSec.createDocument(type, new ByteArrayInputStream(content.getBytes()), meta);
    testDocument(doc);
    assertNotNull(doc.getMetadata());
  }

  /**
   * Test of deleteDocument method, of class SectionImpl.
   */
  @Test
  public void testDeleteDocument() throws IOException {
    assertEquals(0, rootSec.getChildDocuments().size());
    SectionDocument doc = rootSec.createDocument(type, new ByteArrayInputStream(content.getBytes()), meta);
    rootSec.deleteDocument(doc);
    assertEquals(0, rootSec.getChildDocuments().size());
  }

  private void testDocument(SectionDocument doc) throws IOException {
    assertNotNull(doc);
    assertEquals(type, doc.getMediaType());
    BufferedReader io = new BufferedReader(new InputStreamReader(doc.getContent()));
    assertEquals(content, io.readLine());
  }


}