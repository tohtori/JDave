/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdave.maven.report;

import java.io.File;
import java.io.StringReader;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import jdave.tools.Specdox;
import net.sf.saxon.TransformerFactoryImpl;

/**
 * @author Joni Freeman
 */
public class SpecdoxTransformer {
    public void transform(String filename, String specXmlDir, String outputDir, File xref) throws TransformerException {
        Source xmlSource = new StreamSource(new StringReader("<?xml version=\"1.0\" ?><foo></foo>"));
        Source xsltSource = new StreamSource(Specdox.class.getResourceAsStream("/specdox.xsl"));
        xsltSource.setSystemId("/specdox.xsl");
        
        TransformerFactory transFact = new TransformerFactoryImpl();
        Transformer trans = transFact.newTransformer(xsltSource);
        trans.setParameter("spec-file-dir", specXmlDir);
        trans.setParameter("xref", xref.getName());
        trans.setParameter("output-dir", outputDir);
        trans.setParameter("frameset-index-filename", filename);
        trans.transform(xmlSource, new StreamResult(System.out));
    }
}
