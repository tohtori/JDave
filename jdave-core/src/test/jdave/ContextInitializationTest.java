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
package jdave;

import java.lang.reflect.Method;

import jdave.runner.SpecRunner;
import junit.framework.TestCase;

/**
 * @author Joni Freeman
 */
public class ContextInitializationTest extends TestCase {
    private SpecRunner runner;
    private Object context;
    
    @Override
    protected void setUp() throws Exception {
        runner = new SpecRunner();
        context = new Object();
    }
    
    public void testShouldSetContextAsBe() {
        TestSpecification spec = new TestSpecification();
        runner.run(spec, new SpecRunner.Results() {
            public void expected(Method method) {
            }
        });
        assertEquals(context, spec.be);
    }
    
    public class TestSpecification extends Specification<Object> {
        @Context
        public class Zero {
            public Object context() {
                return context;
            }
            
            public void testSpec() {
            }
        }
    }
}
