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
package jdave.containment;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joni Freeman
 */
public class InPartialOrderContainmentTest extends ContainmentTest {
    @Before
    public void setUp() throws Exception {
        containment = new InPartialOrderContainment(new Iterable<Integer>() {
            public Iterator<Integer> iterator() {
                return Arrays.asList(1, 2, 3).iterator();
            }
        });
    }

    @Test
    public void testIsInPartialOrderIfCollectionsContainSameElementsInOrder() {
        assertTrue(containment.matches(Arrays.asList(1, 2, 3)));
    }
        
    @Test
    public void testIsNotInPartialOrderIfCollectionsContainSameElementsButNotInOrder() {
        assertFalse(containment.matches(Arrays.asList(1, 3, 2)));
    }
    
    @Test
    public void testIsInPartialOrderWhenActualHasMoreElementsButInOrder() {
        assertTrue(containment.matches(Arrays.asList(4, 1, 2, 3)));
        assertTrue(containment.matches(Arrays.asList(1, 4, 2, 3)));
        assertTrue(containment.matches(Arrays.asList(1, 2, 4, 3)));
        assertTrue(containment.matches(Arrays.asList(1, 2, 3, 4)));
    }
    
    @Test
    public void testIsNotInOrderWhenActualHasLessElementsButInOrder() {
        assertFalse(containment.matches(Arrays.asList(1, 2)));
        assertFalse(containment.matches(Arrays.asList(1, 3)));
    }
    
    @Test
    public void testIsInPartialOrderWhenActualRepeatsSomeElements() {
        assertTrue(containment.matches(Arrays.asList(1, 1, 2, 3)));
        assertTrue(containment.matches(Arrays.asList(1, 3, 2, 3)));
        assertTrue(containment.matches(Arrays.asList(1, 2, 2, 3)));
        assertTrue(containment.matches(Arrays.asList(1, 2, 3, 1, 2, 3)));
    }
}
