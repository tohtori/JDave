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

import jdave.Specification;
import junit.framework.TestCase;

/**
 * @author Joni Freeman
 */
public class SpecRunnerVisitTest extends TestCase {
    private SpecRunner runner;
    private Behavior visitedMethod;

    @Override
    protected void setUp() throws Exception {
        runner = new SpecRunner();
    }
    
    public void testUsesVisitingSpecMethod() throws Exception {
        runner.visit(TestSpec.class, new ISpecVisitor() {
            public void onContext(Context context) {
            }

            public void onBehavior(Behavior method) throws Exception {
                visitedMethod = method;
            }            
        });
        assertEquals(visitedMethod.getClass(), VisitingBehavior.class);
    }
    
    public static class TestSpec extends Specification<Object> {
        public class SomeContext {
            public Object create() {
                return new Object();
            }

            public void someMethod() {            
            }
        }
    }
}
