/*
 * Copyright 2008 the original author or authors.
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
package jdave.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Marko Sibakov
 */
public class FieldsTest {
    private TestClass testObject;
    private int excepted;

    @Before
    public void setUp() {
        testObject = new TestClass();
        excepted = 9500;
    }

    @Test
    public void testSetsValueToField() {
        Fields.set(testObject, "foo", excepted);
        assertEquals(excepted, testObject.foo);    
    }
    
    @Test
    public void testGetsValueFromField() {
        assertEquals(500, Fields.get(testObject, "foo"));    
    }
    
    @Test
    public void testGetsValueFromStaticField() {
        assertEquals("lol", Fields.getStatic(TestClass.class, "LOL"));
    }
    
    @Test(expected = RuntimeException.class)
    public void testGetStaticWrapsExceptionsToRuntimeException() {
        Fields.getStatic(TestClass.class, "nonExistingField");
    }

    private class TestClass {
        public int foo = 500;
        @SuppressWarnings("unused")
        private static final String LOL = "lol";
    }
}
