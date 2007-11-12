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
package jdave.tools;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joni Freeman
 */
@RunWith(JMock.class)
public class XmlFormatTest {
    private Mockery context = new JUnit4Mockery();
    private IDoxStore doxStore = context.mock(IDoxStore.class);
    private Specdox dox = new Specdox(doxStore);
    
    @Test
    public void testFormatsSpecInXml() {
        final String expectedOutput = 
            "<specification name=\"StackSpec\" fqn=\"jdave.tools.StackSpec\">\n" +
            "  <contexts>\n" +
            "    <context name=\"Full stack\">\n" +
            "      <behaviors>\n" +
            "        <behavior name=\"complains on push\" />\n" +
            "      </behaviors>\n" +
            "    </context>\n" +
            "  </contexts>\n" +
            "</specification>\n";
        context.checking(new Expectations() {{ 
            one(doxStore).store("StackSpec", "xml", expectedOutput);
        }});
        dox.generate(StackSpec.class, new XmlFormat());
    }
}
