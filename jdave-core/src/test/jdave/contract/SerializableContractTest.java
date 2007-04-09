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
public class SerializableContractTest extends TestCase {
    private Specification<Object> spec;

    @Override
    protected void setUp() throws Exception {
        spec = new Specification<Object>() {};
    }

    public void testSerializableIsAccepted() {
        spec.specify(5, spec.satisfies(new SerializableContract()));
    }
    
    public void testNonSerializableIsNotAccepted() {
        try {
            spec.specify(new NonSerializable(), spec.satisfies(new SerializableContract()));
            fail();
        } catch (ExpectationFailedException e) {            
        }
    }
    
    private static class NonSerializable {        
    }
}
