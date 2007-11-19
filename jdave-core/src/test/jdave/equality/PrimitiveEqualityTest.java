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
package jdave.equality;

import jdave.Specification;

import org.junit.Test;

/**
 * @author Joni Freeman
 */
public class PrimitiveEqualityTest {
    private Specification<?> spec = new Specification<Void>() {};
    
    @Test
    public void longEqualsInt() {
        spec.specify(1, spec.equal(1L));
        spec.specify(1L, spec.equal(1));
    }
    
    @Test
    public void longEqualsByte() {
        byte b = 1;
        spec.specify(b, spec.equal(1L));
        spec.specify(1L, spec.equal(b));
    }
    
    @Test
    public void intEqualsByte() {
        byte b = 1;
        spec.specify(b, spec.equal(1));
        spec.specify(1, spec.equal(b));
    }
}
