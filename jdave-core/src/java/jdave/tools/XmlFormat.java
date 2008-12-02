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

/**
 * @author Joni Freeman
 */
public class XmlFormat implements IDoxFormat {
    private StringBuilder dox = new StringBuilder();
    
    public void newSpec(String specName, String fqn) {
        dox.append("<specification name=\"").append(specName).append("\" fqn=\"").append(fqn).append("\">\n");
        dox.append("  <contexts>\n");
    }
    
    public void endSpec(String specName) {
        dox.append("  </contexts>\n");
        dox.append("</specification>\n");
    }
    
    public void newContext(String contextName) {
        dox.append("    <context name=\"").append(Sentence.fromCamelCase(contextName)).append("\">\n");
        dox.append("      <behaviors>\n");
    }
    
    public void endContext(String name) {
        dox.append("      </behaviors>\n");
        dox.append("    </context>\n");
    }

    public void newBehavior(String behaviorName) {
        dox.append("        <behavior name=\"").append(Sentence.fromCamelCase(behaviorName)).append("\" />\n");
    }
    
    public String suffix() {
        return "xml";
    }
        
    @Override
    public String toString() {
        return dox.toString();
    }
}
