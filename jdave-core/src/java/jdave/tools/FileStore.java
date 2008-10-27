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

import java.io.File;
import java.io.IOException;

import jdave.support.IO;

/**
 * @author Joni Freeman
 */
public class FileStore implements IDoxStore {
    private String dir;

    public FileStore(String dir) {
        this.dir = dir;
    }

    public void store(String doxName, String suffix, String content) {
        try {
            File directory = new File(dir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = newFile(directory, doxName + "." + suffix);
            IO.write(file, content);
        } catch (IOException e) {
            throw new DoxStoreException(e);
        }
    }
    
    protected File newFile(File dir, String name) throws IOException {
        return new File(dir, name);
    }
}
