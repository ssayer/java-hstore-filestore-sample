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
package org.projecthdata.javahstore.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author ssayer
 */
public class FileUtils {
  public static boolean recursiveDelete(File f) {
    
        if (f.isDirectory()) {
          for (String string : f.list()) {
            recursiveDelete(new File(f, string));
          }
        }
        return f.delete();
  }

  public static String getFileExtension(File file) {
    String fileName = file.getName();
    return fileName.substring(fileName.lastIndexOf("."));
  }

  public static String getFilePathWithoutExtension(File file) {
    String fileName = file.getAbsolutePath();

    int extStart = fileName.lastIndexOf(".");
    if(extStart == -1) {
      return null;
    }
    return fileName.substring(0, extStart);
  }

  public static void writeInputStreamToFile(InputStream in, File file) throws IOException {
    FileOutputStream out = null;
    file.createNewFile();

    try {
    out = new FileOutputStream(file);
    int nextChar;
      while ((nextChar = in.read()) != -1) {
        out.write(nextChar);
        out.flush();
      }
    }  finally {
      in.close();
      if (out != null) {
        out.close();
      }
      
    }
  }

}
