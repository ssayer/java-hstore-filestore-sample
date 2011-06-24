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
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.projecthdata.javahstore.hdr.HDR;
import org.projecthdata.javahstore.hdr.HDRProvider;

/**
 * Implementation of the HDRProvider interface to provide access to a set of
 * records. Provides a file system storage for HDR files.
 * 
 * @author ssayer
 */
public class HDRProviderImpl implements HDRProvider {

  protected static File path;

  static {

    Properties props= new Properties();
    try {
      String fileName = File.separator + "hstore.properties";
      InputStream conf = HDRProviderImpl.class.getResourceAsStream(fileName);
      props.load(conf);
    } catch (IOException ex) {
      Logger.getLogger(HDRProviderImpl.class.getName()).log(Level.SEVERE, null, ex);
      throw new RuntimeException("Unable to find configuration file - EXITING");
    }

    String fileStorePath = (String) props.get("hdr.dir");

    path = new File(fileStorePath);
    if (!path.exists()) {
      if(!path.mkdirs()) {
        throw new RuntimeException("Unable to create File Store - EXITING");
      }
    }
    Logger.getLogger(HDRProviderImpl.class.getName()).log(Level.FINE, "Created hStore");
  }



  @Override
  /**
   * Responds to any ID and creates a new record for that ID if required, a real
   * implementation would look up a record in some kind of store.
   *
   */
  public synchronized HDR getHDR(String recordId) {
    HDRImpl hdr = new HDRImpl(new File(path, recordId));
    return hdr.exists() ? hdr : null;
  }

  public synchronized HDR createHDR(String recordId) {

    HDRImpl hdr = new HDRImpl(new File(path, recordId));
    if (!hdr.exists()) {
      hdr.create();
    }
    return hdr;
  }


}
