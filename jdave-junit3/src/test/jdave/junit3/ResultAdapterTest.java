/*
 * Copyright 2006 the original author or authors.
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
package jdave.junit3;

import java.lang.reflect.Method;

import jdave.ExpectationFailedException;
import jdave.junit3.JDaveSuite.ResultAdapter;
import junit.framework.TestCase;
import junit.framework.TestResult;

/**
 * @author Joni Freeman
 */
public class ResultAdapterTest extends TestCase {
    private TestCase test;
    private TestResult result;
    private ResultAdapter adapter;
    private Method method;
        
    @Override
    protected void setUp() throws Exception {
        result = new TestResult();
        test = new TestCase("test") {};
        adapter = new JDaveSuite.ResultAdapter(test, result);
        method = ResultAdapterTest.class.getDeclaredMethod("setUp");
    }
    
    public void testShouldAdaptError() {
        adapter.error(method, new Exception());
        assertEquals(1, result.errorCount());
    }
    
    public void testShouldAdaptFailure() {
        adapter.unexpected(method, new ExpectationFailedException(""));
        assertEquals(1, result.failureCount());
    }
}
