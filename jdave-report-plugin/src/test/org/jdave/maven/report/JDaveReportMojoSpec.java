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

import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.sink.Sink;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

/**
 * @author Joni Freeman
 */
@RunWith(JDaveRunner.class)
public class JDaveReportMojoSpec extends Specification<JDaveReportMojo> {
    private JDaveReportMojo mojo = new JDaveReportMojo();
    private Sink sink = mock(Sink.class);
    
    public class AnyReport {
        public JDaveReportMojo create() {
            File out = new File(System.getProperty("java.io.tmpdir"));
            mojo = new JDaveReportMojo() {
                @Override
                public Sink getSink() {
                    return sink;
                }
            };
            mojo.outputDirectory = out.getAbsolutePath();
            mojo.xrefLocation = new File("any");
            mojo.reportsDirectory = new File(out, "JDaveReportMojoSpec");
            if (!mojo.reportsDirectory.exists()) {
                mojo.reportsDirectory.mkdir();
            }
            return mojo;
        }
        
        public void createsIframeForReport() throws MavenReportException {
            checking(new Expectations() {{ 
                one(sink).rawText("<iframe src=\"jdave.html\" width=\"100%\" height=\"800\" />");
                one(sink).flush();
                one(sink).close();
            }});
            mojo.executeReport(null);
        }
    }
}
