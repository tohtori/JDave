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

import java.util.Arrays;
import java.util.Iterator;

import junit.framework.TestCase;

/**
 * @author Joni Freeman
 */
public class ExactContainmentTest extends TestCase {
    private IContainment containment;

    @Override
    protected void setUp() throws Exception {
        containment = new ExactContainment(new Iterable<Integer>() {
            public Iterator<Integer> iterator() {
                return Arrays.asList(1, 2, 3).iterator();
            }
        });
    }
    
    public void testIsInListWhichIsInDifferentOrder() {
        assertTrue(containment.matches(Arrays.asList(2, 3, 1, 1)));
    }
        
    public void testIsNotInListWhichMissesElement() {
        assertFalse(containment.matches(Arrays.asList(1, 2, 2)));
    }
    
    public void testIsNotInListWhichHasExtraElement() {
        assertFalse(containment.matches(Arrays.asList(1, 2, 3, 4)));
    }
}
