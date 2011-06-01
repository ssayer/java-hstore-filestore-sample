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
package org.projecthdata.javahstore.hdr.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ssayer
 */
public class SectionDocumentImplTest {

    SectionDocumentImpl doc;
    File docFile;
    String mediaType = "application/xml";
    String oldValue = "<test>Something</test>";
    String newValue = "<test>Something else</test>";
    InputStream in = new ByteArrayInputStream(newValue.getBytes());

    @Before
    public void setUp() throws IOException {
      InputStream in = new ByteArrayInputStream(oldValue.getBytes());
      docFile = new File("test.xml");
      doc = new SectionDocumentImpl(in, docFile);
    }

    @After
    public void tearDown() {
      docFile.delete();
    }


      /**
   * Test of update method, of class SectionDocumentImpl.
   */
  @Test
  public void testUpdate() throws Exception {
    doc.update(mediaType, in);
    InputStream content = doc.getContent();
    BufferedReader r = new BufferedReader(new InputStreamReader(content));
    assertEquals(newValue, r.readLine());
  }

  @Test
  public void testUpdateWrongMediaType() throws IOException {
    doc.update("text/html", in);
    InputStream content = doc.getContent();
    BufferedReader r = new BufferedReader(new InputStreamReader(content));
    assertEquals(oldValue, r.readLine());
  }




}