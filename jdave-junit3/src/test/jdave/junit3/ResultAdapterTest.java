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

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestResult;

/**
 * @author Joni Freeman
 */
public class ResultAdapterTest extends MockObjectTestCase {
    private TestCase test;
    private Mock result;
    private ResultAdapter adapter;
    private Method method;

    @Override
    protected void setUp() throws Exception {
        result = mock(TestResult.class);
        test = new TestCase("test") {};
        adapter = new JDaveSuite.ResultAdapter(test, (TestResult) result.proxy());
        method = ResultAdapterTest.class.getDeclaredMethod("setUp");
    }
    
    public void testShouldAdaptError() {
        Throwable t = new Exception();
        result.expects(once()).method("addError").with(eq(test), eq(t));
        adapter.error(method, t);
    }
    
    public void testShouldAdaptFailure() {
        final ExpectationFailedException e = new ExpectationFailedException("");
        result.expects(once()).method("addFailure").with(eq(test), new Constraint() {
            public boolean eval(Object o) {
                if (o instanceof AssertionFailedError) {
                    return ((AssertionFailedError) o).getCause() == e;
                }
                return false;
            }

            public StringBuffer describeTo(StringBuffer buffer) {
                buffer.append("instanceof");
                return buffer;
            }
        });
        adapter.unexpected(method, e);
    }
}
