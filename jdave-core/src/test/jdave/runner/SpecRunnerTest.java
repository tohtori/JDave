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
import java.util.Collections;
import java.util.List;

import jdave.SpecVisitorAdapter;
import jdave.ResultsAdapter;
import jdave.Specification;
import junit.framework.TestCase;

/**
 * @author Pekka Enberg
 */
public class SpecRunnerTest extends TestCase {
    private List<String> methods;
    private SpecRunner runner;

    @Override
    protected void setUp() throws Exception {
        methods = new ArrayList<String>();
        runner = new SpecRunner();
    }

    public void testShouldNotifyResultsOfAllPublicMethods() throws Exception {
        assertTrue(methods.isEmpty());
        runner.run(BooleanSpec.class, new SpecVisitorAdapter(new ResultsAdapter() {
            @Override
            public void expected(Method method) {
                methods.add(method.getName());
            }
        }));
        Collections.sort(methods);
        assertEquals(3, methods.size());
        assertEquals("shouldBeEqualToTrue", methods.get(0));
        assertEquals("shouldEqualToFalse", methods.get(1));
        assertEquals("shouldNotBeEqualToTrue", methods.get(2));
    }
    
    public void testShouldInvokeAllSpecificationMethods() throws Exception {
        BooleanSpec.actualCalls.clear();
        runner.run(BooleanSpec.class, new SpecVisitorAdapter(new ResultsAdapter()));
        Collections.sort(BooleanSpec.actualCalls);
        assertEquals(Arrays.asList("shouldBeEqualToTrue", "shouldEqualToFalse", "shouldNotBeEqualToTrue"), BooleanSpec.actualCalls);
    }
    
    public void testShouldNotifyCallbackWhenContextIsStarted() throws Exception {
        SpecVisitorAdapter adapter = new SpecVisitorAdapter(new ResultsAdapter());
        runner.run(BooleanSpec.class, adapter);
        assertEquals(Arrays.asList("FalseBoolean", "TrueBoolean"), adapter.getContextNames());
    }
    
    public void testShouldCallDestroyForEachMethod() throws Exception {
        BooleanSpec.destroyCalled = 0;
        runner.run(BooleanSpec.class, new SpecVisitorAdapter(new ResultsAdapter()));
        assertEquals(3, BooleanSpec.destroyCalled);        
    }
    
    public void testShouldCallSpecCreateForEachMethod() throws Exception {
        BooleanSpec.specCreateCalled = 0;
        runner.run(BooleanSpec.class, new SpecVisitorAdapter(new ResultsAdapter()));
        assertEquals(3, BooleanSpec.specCreateCalled);        
    }
    
    public void testShouldCallSpecDestroyForEachMethod() throws Exception {
        BooleanSpec.specDestroyCalled = 0;
        runner.run(BooleanSpec.class, new SpecVisitorAdapter(new ResultsAdapter()));
        assertEquals(3, BooleanSpec.specDestroyCalled);        
    }

    public static class BooleanSpec extends Specification<Boolean> {
        public static List<String> actualCalls = new ArrayList<String>();
        public static int destroyCalled;
        public static int specCreateCalled;
        public static int specDestroyCalled;
        
        public class FalseBoolean {
            public Boolean create() {
                return false;
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
            
            public void destroy() {
                destroyCalled++;
            }
        }

        public class TrueBoolean {
            public Boolean create() {
                return true;
            }
            
            public void shouldBeEqualToTrue() {
                actualCalls.add("shouldBeEqualToTrue");
            }

            public void destroy() {
                destroyCalled++;
            }
        }
        
        public abstract class SomeAbstractClass {            
        }
        
        public static class SomeHelperClass {            
        }
        
        @Override
        public void create() {
            specCreateCalled++;
        }
        
        @Override
        public void destroy() {
            specDestroyCalled++;
        }
    }
}