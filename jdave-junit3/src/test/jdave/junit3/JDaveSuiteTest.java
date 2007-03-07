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

import jdave.Specification;
import jdave.runner.Behavior;
import junit.framework.TestCase;
import junit.framework.TestResult;

/**
 * @author Joni Freeman
 */
public class JDaveSuiteTest extends TestCase {
    private boolean verifyCalled;
    
    @Override
    protected void setUp() throws Exception {
        verifyCalled = false;
    }

    public void testShouldAddTestSuiteForEachContextInSpecification() throws Exception {
        class TestSpec extends Specification<Object> {
            class C1 {}
            class C2 {}
        }
        JDaveSuite suite = new JDaveSuite(TestSpec.class);
        assertEquals(2, suite.testCount());
    }
    
    public void testShouldNotVerifySpecIfTestRunHasErrors() throws Exception {
        JDaveSuite suite = new JDaveSuite(TestSpecWithMethod.class) {
            @Override
            protected Specification<?> runBehavior(Behavior behavior, TestCase testCase, TestResult testResult) throws Throwable {
                testResult.addError(testCase, new RuntimeException());
                return new StubSpecification();
            }
        };
        suite.run(new TestResult());
        assertFalse(verifyCalled);
    }
  
    public void testShouldVerifySpecIfNoErrorsInTestRun() throws Exception {
        JDaveSuite suite = new JDaveSuite(TestSpecWithMethod.class) {
            @Override
            protected Specification<?> runBehavior(Behavior behavior, TestCase testCase, TestResult testResult) throws Throwable {
                return new StubSpecification();
            }
        };
        suite.run(new TestResult());
        assertTrue(verifyCalled);
    }
    
    private class TestSpecWithMethod extends Specification<Object> {
        class C1 {
            @SuppressWarnings("unused")
            public void specMethod() {
            }
        }
    }
    
    private class StubSpecification extends Specification<Object> {
        @Override
        public void verify() {
            verifyCalled = true;
        }
    }
}
