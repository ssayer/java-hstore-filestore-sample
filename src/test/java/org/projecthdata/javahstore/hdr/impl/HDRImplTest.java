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

import org.junit.Before;
import org.junit.After;
import java.io.IOException;
import org.projecthdata.javahstore.hdr.RootDocument;
import java.io.File;
import org.junit.Test;
import org.projecthdata.jaxb.RootDocumentImpl;
import static org.junit.Assert.*;
import static org.projecthdata.javahstore.utils.FileUtils.*;


/**
 *
 * @author ssayer
 */
public class HDRImplTest {

    private File hdrFile;
    private String id = "1234";

    public HDRImplTest() {
    }

    @Before
    public void setUp() {
      hdrFile = new File("/hstore/test/1234");
      hdrFile.delete();
      hdrFile.deleteOnExit();
    }

    @After
    public void tearDown() {
      assertTrue(recursiveDelete(hdrFile));
    }

    @Test
    public void testCreation() throws IOException {
      assertNotNull(hdrFile);
      RootDocumentImpl root = new RootDocumentImpl(id, new File(hdrFile, "root.xml"));
      HDRImpl hdr = new HDRImpl(hdrFile, root);
      assertTrue(hdr.create());
      RootDocument root2 = hdr.getRootDocument();
      assertEquals(id, root2.getId());
    }


}