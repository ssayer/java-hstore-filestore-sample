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

import org.junit.BeforeClass;
import java.io.File;
import org.junit.AfterClass;
import org.projecthdata.javahstore.hdr.HDR;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.projecthdata.javahstore.utils.FileUtils.*;

/**
 *
 * @author ssayer
 */
public class HDRProviderImplTest {

    HDRProviderImpl prov;
    String id = "1235";
    
    public HDRProviderImplTest() {
      prov = new HDRProviderImpl();
    
    }

    @BeforeClass
    public static void setUpClass() {
      HDRProviderImpl.path = new File("/hdata/tests");
    }

    @AfterClass
    public static void tearDownClass() {
      recursiveDelete(HDRProviderImpl.path);
    }

   @Test
  public void testGetMissingRecord() {
//    HDR hdr = prov.getHDR(id);
//    assertNull(hdr);
  }


//  @Test
//  public void testCreateRecord() {
//    HDR hdr = prov.createHDR(id);
//    assertNotNull(hdr);
//    assertEquals(id, hdr.getId());
//    assertNotNull(hdr.getRootDocument());
//  }


}