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
import java.util.Collection;

import jdave.ExpectationFailedException;
import jdave.Specification;
import jdave.runner.Context;
import jdave.runner.SpecRunner;
import jdave.runner.SpecificationMethod;
import jdave.runner.SpecRunner.Results;
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
public class JDaveSuite extends TestSuite implements SpecRunner.Callback {
    private TestSuite suite;

    public <V, T extends Specification<V>> void specs(Collection<Class<T>> specTypes) {
        for (Class<T> specType : specTypes) {
            run(specType);            
        }
    }

    private <V, T extends Specification<V>> void run(Class<T> specType) {
        setName(specType.getName());
        new SpecRunner().run(specType, this);
    }
    
    public void onContext(Context context) {
        suite = new TestSuite(context.getName());
        addTest(suite);
    }
    
    public void onSpecMethod(final SpecificationMethod method) {
        suite.addTest(new TestCase(method.getName()) {
            private TestResult result;
            
            @Override
            public void run(TestResult result) {
                this.result = result;
                super.run(result);
            }

            @Override
            protected void runTest() throws Throwable {
                method.run(new ResultAdapter(this, result));
            }
        });
    }
    
    static class ResultAdapter implements Results {
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
            AssertionFailedError failure = new AssertionFailedError(method.getName());
            failure.initCause(e);
            result.addFailure(test, failure);
        }
    }
}
