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
package jdave.runner;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Stack;

/**
 * @author Joni Freeman
 */
class Scanner {
    private final URI path;

    Scanner(String path) {
        try {
            if (path.startsWith("/")) {
                this.path = new URI("file://" + path.replace(" ", "%20"));                
            } else {
                this.path = new URI(path.replace(" ", "%20"));
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    void forEach(String extension, IFileHandler fileHandler) {
        Stack<File> dirs = new Stack<File>();
        dirs.push(new File(path.getPath()));
        forEach(extension, fileHandler, dirs);
    }
    
    private void forEach(final String extension, IFileHandler fileHandler, Stack<File> dirs) {
        do {
            File dir = dirs.pop();
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    dirs.push(file);
                } else {
                    if (file.getName().endsWith("." + extension)) {
                        fileHandler.handle(file);                        
                    }
                }
            }
        } while (!dirs.isEmpty());
    }
}