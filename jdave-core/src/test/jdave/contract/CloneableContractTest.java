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
package jdave.contract;

import jdave.ExpectationFailedException;
import jdave.Specification;
import junit.framework.TestCase;

/**
 * @author Joni Freeman
 */
public class CloneableContractTest extends TestCase {
    private Specification<Object> spec;

    @Override
    protected void setUp() throws Exception {
        spec = new Specification<Object>() {};
    }
    
    public void testCloneableIsAccepted() {
        spec.specify(new TestCloneable(), spec.satisfies(new CloneableContract()));
    }
    
    public void testNonCloneableIsNotAccepted() {
        try {
            spec.specify(new NonCloneable(), spec.satisfies(new CloneableContract()));
            fail();
        } catch (ExpectationFailedException e) {
            assertTrue(e.getMessage().contains("does not implement Cloneable"));
        }
    }
    
    public void testCloneableWithoutPublicCloneMethodNotAccepted() {
        try {
            spec.specify(new CloneableWithoutClone(), spec.satisfies(new CloneableContract()));
            fail();
        } catch (ExpectationFailedException e) {
            assertTrue(e.getMessage().startsWith("no public clone method"));
        }
    }
    
    public void testCloneableWhichDoesNotSupportCloningNotAccepted() {
        try {
            spec.specify(new CloneableWhichDoesNotSupportClone(), spec.satisfies(new CloneableContract()));
            fail();
        } catch (ExpectationFailedException e) {
            assertTrue(e.getMessage().startsWith("clone not supported"));
        }
    }
    
    private static class TestCloneable implements Cloneable {
        @Override
        public TestCloneable clone() {
            return this;
        }
    }
    
    private static class NonCloneable {        
    }
    
    private static class CloneableWithoutClone implements Cloneable {
    }
    
    private static class CloneableWhichDoesNotSupportClone implements Cloneable {
        @Override
        public CloneableWhichDoesNotSupportClone clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException();
        }
    }
}
