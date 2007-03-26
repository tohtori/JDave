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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jdave.ResultsAdapter;
import jdave.SpecVisitorAdapter;
import jdave.Specification;
import junit.framework.TestCase;

/**
 * @author Joni Freeman
 */
public class ThreadLocalIsolationTest extends TestCase {
    private List<String> methods = new ArrayList<String>();
    
    public void testThreadLocalsAreIsolatedBetweenBehaviorMethods() throws Exception {
        new SpecRunner().run(TestSpec.class, new SpecVisitorAdapter(new ResultsAdapter() {
            @Override
            public void expected(Method method) {
                methods.add(method.getName());
            }
        }));
        assertEquals(2, methods.size());
    }
    
    public static class TestSpec extends Specification<Object> {
        private static ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();
        
        @Override
        public boolean needsThreadLocalIsolation() {
            return true;
        }
        
        public class Context1 {
            public Object create() {
                return null;
            }
            
            public void threadLocalIsNull() {
                specify(threadLocal.get(), should.equal(null));
                threadLocal.set(new Object());
            }
        }
        
        public class Context2 {
            public Object create() {
                return null;
            }
            
            public void threadLocalIsNull() {
                specify(threadLocal.get(), should.equal(null));
                threadLocal.set(new Object());
            }
        }
    }    
}
