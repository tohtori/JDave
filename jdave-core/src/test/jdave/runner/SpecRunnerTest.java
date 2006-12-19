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
package jdave.runner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jdave.CallbackAdapter;
import jdave.Context;
import jdave.ResultsAdapter;
import jdave.Specification;
import junit.framework.TestCase;

/**
 * @author Pekka Enberg
 */
public class SpecRunnerTest extends TestCase {
    private List<Method> methods;
    private SpecRunner runner;

    @Override
    protected void setUp() throws Exception {
        methods = new ArrayList<Method>();
        runner = new SpecRunner();
    }

    public void testShouldNotifyResultsOfAllPublicMethods() {
        assertTrue(methods.isEmpty());
        runner.run(BooleanSpec.class, new CallbackAdapter(new ResultsAdapter() {
            @Override
            public void expected(Method method) {
                methods.add(method);
            }
        }));
        assertEquals(2, methods.size());
        assertEquals("shouldEqualToFalse", methods.get(0).getName());
        assertEquals("shouldNotBeEqualToTrue", methods.get(1).getName());
    }
    
    public void testShouldInvokeAllSpecificationMethods() {
        BooleanSpec.actualCalls.clear();
        runner.run(BooleanSpec.class, new CallbackAdapter(new ResultsAdapter()));
        assertEquals(Arrays.asList("shouldEqualToFalse", "shouldNotBeEqualToTrue"), BooleanSpec.actualCalls);
    }
    
    public void testShouldNotifyCallbackWhenContextIsStarted() {
        CallbackAdapter adapter = new CallbackAdapter(new ResultsAdapter());
        runner.run(BooleanSpec.class, adapter);
        assertEquals("FalseBoolean", adapter.getCurrentContext().getName());
    }
    
    public static class BooleanSpec extends Specification<Boolean> {
        public static List<String> actualCalls = new ArrayList<String>();
        
        @Context
        public class FalseBoolean {
            public Boolean context() {
                return true;
            }
            
            public void shouldEqualToFalse() {
                actualCalls.add("shouldEqualToFalse");
            }

            public void shouldNotBeEqualToTrue() {
                actualCalls.add("shouldNotBeEqualToTrue");
            }
            
            protected void protectedMethod() {
                actualCalls.add("protectedMethod");
            }
            
            private void privateMethod() {
                actualCalls.add("privateMethod");
            }
            
            void packageProtectedMethod() {
                actualCalls.add("packageProtectedMethod");
            }
        }
    }
}