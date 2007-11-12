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

import static org.junit.Assert.assertSame;
import jdave.runner.SpecRunner;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joni Freeman
 */
public class ContextInitializationTest {
    private SpecRunner runner;
    private static Object contextObject;
    private static Object actualBe;
    private static Object actualContext;
    
    @Before
    public void setUp() throws Exception {
        runner = new SpecRunner();
        contextObject = new Object();
    }
    
    @Test
    public void testShouldSetContextAsBe() throws Exception {
        runner.run(TestSpecification.class, new SpecVisitorAdapter(new ResultsAdapter()));
        assertSame(contextObject, actualBe);
    }
    
    @Test
    public void testVariableContextReferencesBe() throws Exception {
        runner.run(TestSpecification.class, new SpecVisitorAdapter(new ResultsAdapter()));
        assertSame(contextObject, actualContext);
    }
    
    public static class TestSpecification extends Specification<Object> {
        public class TestContext {
            public Object create() {
                return contextObject;
            }
            
            public void testSpec() {
                actualBe = be;
                actualContext = context;
            }
        }
    }
}
