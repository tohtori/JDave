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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import jdave.runner.dummies.Dummy1;
import jdave.runner.dummies.Dummy2;
import net.sf.cglib.asm.attrs.Annotation;

import org.junit.Test;

/**
 * @author Joni Freeman
 */
public class AnnotatedSpecScannerTest {
    @Test
    public void testFindsClassesWithGroupAnnotation() {
        AnnotatedSpecScanner scanner = new AnnotatedSpecScanner("target/test-classes/jdave/runner/dummies") {
            @Override
            public boolean isInDefaultGroup(String classname, Collection<Annotation> annotations) {
                return false;
            }
        };
        final Map<String, String[]> annotatedSpecs = new HashMap<String, String[]>();
        scanner.forEach(new IAnnotatedSpecHandler() {
            public void handle(String classname, String... groups) {
                annotatedSpecs.put(classname, groups);
            }
        });
        assertEquals(1, annotatedSpecs.size());
        assertEquals(Arrays.asList("group1", "group2"), Arrays.asList(annotatedSpecs.get(Dummy1.class.getName())));
    }
    
    @Test
    public void testHandlesSpecsWhichAreInDefaultGroup() throws Exception {
        AnnotatedSpecScanner scanner = new AnnotatedSpecScanner("target/test-classes/jdave/runner/dummies") {
            @Override
            public boolean isInDefaultGroup(String classname, Collection<Annotation> annotations) {
                return classname.equals(Dummy2.class.getName());
            }
        };
        final Map<String, String[]> annotatedSpecs = new HashMap<String, String[]>();
        scanner.forEach(new IAnnotatedSpecHandler() {
            public void handle(String classname, String... groups) {
                annotatedSpecs.put(classname, groups);
            }
        });
        assertEquals(2, annotatedSpecs.size());
        assertEquals(Arrays.asList("group1", "group2"), Arrays.asList(annotatedSpecs.get(Dummy1.class.getName())));
        assertEquals(Arrays.asList(Groups.DEFAULT), Arrays.asList(annotatedSpecs.get(Dummy2.class.getName())));
    }
}
