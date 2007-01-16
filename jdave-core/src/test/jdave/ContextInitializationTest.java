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

import jdave.runner.SpecRunner;
import junit.framework.TestCase;

/**
 * @author Joni Freeman
 */
public class ContextInitializationTest extends TestCase {
    private SpecRunner runner;
    private static Object context;
    private static Object actualBe;
    
    @Override
    protected void setUp() throws Exception {
        runner = new SpecRunner();
        context = new Object();
    }
    
    public void testShouldSetContextAsBe() throws Exception {
        runner.run(TestSpecification.class, new CallbackAdapter(new ResultsAdapter()));
        assertEquals(context, actualBe);
    }
    
    public static class TestSpecification extends Specification<Object> {
        public class TestContext {
            public Object create() {
                return context;
            }
            
            public void testSpec() {
                actualBe = should.be;
            }
        }
    }
}
