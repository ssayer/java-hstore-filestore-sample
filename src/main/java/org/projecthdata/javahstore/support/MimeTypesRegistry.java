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
package org.projecthdata.javahstore.support;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ssayer
 */
public class MimeTypesRegistry {

   private Properties mimeTypes;
   private static MimeTypesRegistry reg;

   private MimeTypesRegistry() {
    mimeTypes = new Properties();
    try {
      String fileName = File.separator + "mime_types.properties";
      InputStream conf = MimeTypesRegistry.class.getResourceAsStream(fileName);
      mimeTypes.load(conf);
    } catch (IOException ex) {
      Logger.getLogger(MimeTypesRegistry.class.getName()).log(Level.SEVERE, null, ex);
    }
   }

   public String mimeTypetoFileExt(String mime) {
    return (String) mimeTypes.get(mime);
   }

   public String fileExtToMimeType(String fileName) {
    int extStart = fileName.lastIndexOf(".");
     if (extStart == -1) {
       return null;
     }
    extStart += 1;
    String ext = fileName.substring(extStart);
    String result = null;
     for (Object object : mimeTypes.keySet()) {
       String key = (String) object;
       String value = (String) mimeTypes.get(key);
       if (value.equals(ext)) {
         result = key;
       }
     }
     return result;
   }

   public static synchronized MimeTypesRegistry newInstance() {
     if (reg == null) {
       reg = new MimeTypesRegistry();
     }
     return reg;
   }

  public String fileExtToMimeType(File file) {
    return fileExtToMimeType(file.getName());
  }

}
