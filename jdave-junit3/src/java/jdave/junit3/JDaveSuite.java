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
import jdave.Specification;
import jdave.runner.Context;
import jdave.runner.BehaviorResults;
import jdave.runner.SpecRunner;
import jdave.runner.ISpecVisitor;
import jdave.runner.Behavior;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

/**
 * FIXME, make this to scan classpath
 * 
 * @author Joni Freeman
 */
public class JDaveSuite extends TestSuite implements ISpecVisitor {
    private TestSuite suite;

    public JDaveSuite(Class<? extends Specification<?>> specType) throws Exception {
        run(specType);        
    }

    private void run(Class<? extends Specification<?>> specType) throws Exception {
        setName(specType.getName());
        new SpecRunner().run(specType, this);
    }
    
    public void onContext(Context context) {
        suite = new TestSuite(context.getName());
        addTest(suite);
    }
    
    public void onBehavior(final Behavior behavior) {
        suite.addTest(new TestCase(behavior.getName()) {
            private TestResult result;
            private Specification<?> spec;
            
            @Override
            public void runBare() throws Throwable {
                setUp();
                try {
                    runTest();
                    if (!hasErrorsOrFailures()) {
                        spec.verify();
                    }
                } finally {
                    tearDown();
                }
            }
            
            private boolean hasErrorsOrFailures() {
                return result.errorCount() > 0 || result.failureCount() > 0;
            }
            
            @Override
            public void run(TestResult result) {
                this.result = result;
                super.run(result);
            }

            @Override
            protected void runTest() throws Throwable {
                spec = runBehavior(behavior, this, result);
            }
        });
    }
    
    protected Specification<?> runBehavior(Behavior behavior, TestCase testCase, TestResult testResult) throws Throwable {
        return behavior.run(new ResultAdapter(testCase, testResult));
    }

    static class ResultAdapter implements BehaviorResults {
        private final TestResult result;
        private final Test test;

        public ResultAdapter(Test test, TestResult result) {
            this.test = test;
            this.result = result;
        }
        
        public void error(Method method, Throwable t) {
            result.addError(test, t);
        }

        public void expected(Method method) {
        }

        public void unexpected(Method method, ExpectationFailedException e) {
            AssertionFailedError failure = new AssertionFailedError(
                    method.getDeclaringClass().getSimpleName() + " " + method.getName());
            failure.initCause(e);
            result.addFailure(test, failure);
        }
    }
}
