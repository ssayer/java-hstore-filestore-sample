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

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.projecthdata.javahstore.hdr.HDR;
import org.projecthdata.javahstore.hdr.RootDocument;
import org.projecthdata.jaxb.RootDocumentImpl;

/**
 * A simple in-memory implementation of the HDR interface.
 * @author mhadley
 */
public class HDRImpl implements HDR {

  private String recordId;
  private File hdrDir;
  private RootDocumentImpl rootDocument;
  
  HDRImpl(File file) {
    this.hdrDir = file;

    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "HDR DIR:{0}", file.getAbsolutePath());
    File rootDocFile = new File(hdrDir, "root.xml");

    if(rootDocFile.exists()) {
      this.rootDocument = RootDocumentImpl.load(rootDocFile);
    } else {
       throw new RuntimeException("No root document file available in " + hdrDir.getAbsolutePath());
    }
  }

  HDRImpl(File file, RootDocumentImpl root) {
    this.hdrDir = file;
    this.rootDocument = root;
  }


  @Override
  public String getId() {
    return rootDocument.getId();
  }

  public boolean exists() {
    return hdrDir.exists();
  }

  @Override
  public RootDocument getRootDocument() {
    return rootDocument;
  }

  boolean create() {
   if (!hdrDir.mkdirs()) {
    throw new RuntimeException("Unable to create directory: " + hdrDir.getPath());
   }
   return this.rootDocument.save();
  }
}
