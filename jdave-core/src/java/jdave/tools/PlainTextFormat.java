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
public class PlainTextFormat implements IDoxFormat {
    private StringBuilder dox = new StringBuilder();
    
    public void newContext(String contextName) {
        dox.append(camelCaseToSentence(contextName));
        dox.append("\n");
    }

    public void newBehavior(String behaviorName) {
        dox.append("  - ");
        dox.append(camelCaseToSentence(behaviorName));
        dox.append("\n");
    }
    
    public String suffix() {
        return "txt";
    }
    
    private String camelCaseToSentence(String s) {
        StringBuilder sentence = new StringBuilder();        
        for (int pos = 0; pos < s.length(); pos++) {
            char ch = s.charAt(pos);
            if (pos > 0 && Character.isUpperCase(ch)) {
                sentence.append(" ");
                sentence.append(Character.toLowerCase(ch));
            } else {
                sentence.append(ch);
            }
        }
        return sentence.toString();
    }
    
    @Override
    public String toString() {
        return dox.toString();
    }
}
