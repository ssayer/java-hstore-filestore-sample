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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.projecthdata.javahstore.hdr.DocumentMetadata;
import org.projecthdata.javahstore.hdr.SectionDocument;
import org.projecthdata.javahstore.support.MimeTypesRegistry;
import org.projecthdata.javahstore.utils.FileUtils;
import org.xml.sax.SAXException;

/**
 *
 * @author ssayer
 */
public class SectionDocumentImpl implements SectionDocument {

  private File file;
  private DocumentMetadata meta;
  private static String metaExtension = "_meta.xml";
  private static MimeTypesRegistry reg = MimeTypesRegistry.newInstance();

  public SectionDocumentImpl(File file) {
    this.file = file;

    File metaFile = new File(FileUtils.getFilePathWithoutExtension(file) + metaExtension);
    if (metaFile.exists()) {
      String metaXml = "";
      Reader reader = null;
      try {
        reader = new InputStreamReader(new FileInputStream(metaFile));
        BufferedReader bufRead = new BufferedReader(reader);
        while (bufRead.ready()) {
          metaXml += bufRead.readLine();
        }
        this.meta = new DocumentMetadata(metaXml);
      } catch (ParserConfigurationException ex) {
        Logger.getLogger(SectionDocumentImpl.class.getName()).log(Level.SEVERE, null, ex);
      } catch (SAXException ex) {
        Logger.getLogger(SectionDocumentImpl.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
        Logger.getLogger(SectionDocumentImpl.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
        try {
          reader.close();
        } catch (IOException ex) {
          Logger.getLogger(SectionDocumentImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }


  public SectionDocumentImpl(InputStream content, File file, DocumentMetadata meta) throws IOException {
    this(content, file);
    InputStream metaIn = new ByteArrayInputStream(meta.getXml().getBytes());
    FileUtils.writeInputStreamToFile(metaIn, new File(metaFilePath()));
    this.meta = meta;
  }

  public SectionDocumentImpl(InputStream content, File document) throws IOException {
    this(document);
    FileUtils.writeInputStreamToFile(content, file);
  }

  @Override
  public String getMediaType() {
   return reg.fileExtToMimeType(file);
  }

  public void delete() {
    this.file.delete();
    File metaFile = new File(metaFilePath());
    if (metaFile.exists()) {
      metaFile.delete();
    }
  }

  @Override
  public void update(String mediaType, InputStream content) throws IOException {
    String fileMimeType = MimeTypesRegistry.newInstance().fileExtToMimeType(file);
    if (fileMimeType.equals(mediaType)) {
      FileUtils.writeInputStreamToFile(content, file);
    }
    BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    System.out.println(r.readLine());
  }

  @Override
  public InputStream getContent() {
    InputStream stream = null;
    try {
      stream = new FileInputStream(file);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(SectionDocumentImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return stream;
  }

  @Override
  public String getPath() {
    return file.getName();
  }

  @Override
  public Date getLastUpdated() {
    return new Date(file.lastModified());
  }

  @Override
  public DocumentMetadata getMetadata() {
    return meta;
  }

  @Override
  public void setMetadata(DocumentMetadata metadata) {
    this.meta = metadata;
  }

  private String metaFilePath() {
    return FileUtils.getFilePathWithoutExtension(this.file) + metaExtension;
  }

}
