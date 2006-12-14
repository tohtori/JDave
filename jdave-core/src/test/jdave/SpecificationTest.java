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

import junit.framework.TestCase;

/**
 * @author Joni Freeman
 */
public class SpecificationTest extends TestCase {
    private Specification<Object> specification;

    @Override
    protected void setUp() throws Exception {
        specification = new Specification<Object>() {};
    }
    
    public void testShouldPassWhenExpectationMet() {
        specification.specify(1, 1);
    }
    
    public void testShouldFailWhenExpectationNotMet() {
        try {
            specification.specify(1, 2);
            fail();
        } catch (ExpectationFailedException e) {            
        }
    }
    
    public void testShouldPassWhenExpectedExceptionRaised() {
        specification.specify(new Block() {
            public void run() throws Exception {
                throw new IllegalArgumentException();
            }
        }, specification.raise(IllegalArgumentException.class));
    }
    
    public void testShouldFailWhenExpectedExceptionNotRaised() {
        try {
            specification.specify(new Block() {
                public void run() throws Exception {
                }
            }, specification.raise(IllegalArgumentException.class));
            fail();
        } catch (ExpectationFailedException e) {            
        }
    }
}
