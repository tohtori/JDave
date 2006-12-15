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

import jdave.Specification;
import jdave.runner.SpecRunner;
import jdave.runner.SpecRunner.Results;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * FIXME, make this to scan classpath
 * FIXME, there should TestCase for each specification method
 * 
 * @author Joni Freeman
 */
public class JDaveSuite<T> extends TestSuite {
    public JDaveSuite(final Class<? extends Specification<T>> specType) {
        addTest(new TestCase("test") {
            @Override
            protected void runTest() throws Throwable {
                Results results = new Results() {
                    public void expected(Method method) {
                    }

                    public void unexpected(Method method) {
                        throw new AssertionFailedError(method.getName());
                    }
                };
                new SpecRunner().run(specType, results);
            }
        });
    }
}
