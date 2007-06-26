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
package jdave.junit4;

import java.net.URL;

import jdave.support.Assert;

public class CodeSource {
    private URL location;
    private String dir;

    private CodeSource(Class<?> clazz) {
        String resource = clazz.getName().replace('.', '/').concat(".class");
        location = clazz.getClassLoader().getResource(resource);
        Assert.notNull(location, "can't find suite '" + clazz.getName() + "' with classloader");
        String path = location.getPath();
        dir = path.substring(0, path.lastIndexOf(resource));                
    }
    
    public String getDirectory() {
        return dir;
    }
    
    public static CodeSource of(Class<?> clazz) {
        return new CodeSource(clazz);
    }
}
