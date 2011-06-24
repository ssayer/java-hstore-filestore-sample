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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import org.projecthdata.javahstore.hdr.Extension;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attGroup ref="{http://projecthdata.org/hdata/schemas/2009/06/core}extension"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class ExtensionImpl implements Extension {

    @XmlAttribute(name = "contentType")
    protected String contentType;
    @XmlAttribute(name = "extensionId", required = true)
    protected String extensionId;
    @XmlValue
    protected String url;

    public ExtensionImpl() {}

    public ExtensionImpl(String extensionId) {
      this.extensionId = extensionId;
    }

  public ExtensionImpl(String id, String type) {
    this.extensionId = id;
    this.contentType = type;
  }

  @Override
  public String getId() {
   return this.extensionId;
  }

  @Override
  public String getContentType() {
    return this.contentType;
  }

  @Override
  public String getUrl() {
    return this.url;
  }

}
