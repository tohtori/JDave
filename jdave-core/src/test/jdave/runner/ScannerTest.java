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

import static java.util.Collections.sort;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * @author Joni Freeman
 */
public class ScannerTest {
    @Test
    public void testFindsRecursivelyClasses() throws Exception {
        Scanner scanner = new Scanner(getClass().getProtectionDomain().getCodeSource().getLocation().getPath() +
                "/jdave/runner/dummies");
        final List<String> files = new ArrayList<String>();
        scanner.forEach("class", new IFileHandler() {
            public void handle(File file) {
                files.add(file.getName());
            }
        });
        sort(files);
        List<String> expected = Arrays.asList("Dummy1.class", "Dummy2.class", "Dummy3.class", "Other.class");
        sort(expected);
        assertEquals(expected, files);
    }
    
    @Test
    /*
     * See http://www.laughingpanda.org/jira/browse/JDAVE-35
     */
    public void testFindsFromDirectoryContainingSpaces() throws Exception {
        Scanner scanner = new Scanner(getClass().getProtectionDomain().getCodeSource().getLocation().getPath() +
                "/jdave/runner/dir with spaces");
        scanner.forEach("class", new IFileHandler() {
            public void handle(File file) {
            }            
        });
    }
}
