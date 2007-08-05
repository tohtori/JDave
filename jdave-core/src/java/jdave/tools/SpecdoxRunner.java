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

import jdave.Specification;

/**
 * @author Joni Freeman
 */
public class SpecdoxRunner {
    public static final String FORMAT = "jdave.tools.specdox.format";
    public static final String DIRNAME = "jdave.tools.specdox.dir";
    private final Formats formats;
    
    public SpecdoxRunner() {
        this(new Formats());
    }
    
    public SpecdoxRunner(Formats formats) {
        this.formats = formats;        
    }
    
    public void generate(Class<? extends Specification<?>> specType) {
        if (System.getProperty(FORMAT) != null) {
            for (String s : System.getProperty(FORMAT).split("\\s+")) {
                IDoxFormat format = formats.formatFor(s);
                Specdox specdox = new Specdox(new FileStore(dirname()));
                specdox.generate(specType, format);
            }
        }
    } 

    private String dirname() {
        return System.getProperty(DIRNAME, "target/jdave");
    }
}
