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
package jdave.examples;

import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;

import jdave.Group;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

/**
 * Note, this is not a good example for how to write specs.
 * This exists only to show various ways to write collection
 * containment expectations.
 * 
 * @author Joni Freeman
 */
@RunWith(JDaveRunner.class)
@Group("basic")
public class ContainmentSampleSpec extends Specification<Object> {
    public class SampleWithVariousContainments {
        private List<Integer> elements;

        public Object create() {
            elements = Arrays.asList(1, 2, 3);
            return elements;
        }
        
        public void containsAllItems() {
            specify(elements, containsAll(1, 2));
        }
        
        public void doesNotContainAllItems() {
            specify(elements, does.not().containAll(0, 1, 2));
        }
        
        public void containsAnyItems() {
            specify(elements, containsAny(1, 2, 3, 4, 5));
        }
        
        public void doesNotContainAnyItems() {
            specify(elements, does.not().containAny(0, 4, 5));
        }
        
        public void containsExactItems() {
            specify(elements, containsExactly(2, 3, 1));
        }
        
        public void doesNotContainExactItems() {
            specify(elements, does.not().containExactly(1, 2, 3, 4));
        }
        
        public void containsItemsInOrder() {
            specify(elements, containsInOrder(1, 2, 3));
        }
        
        public void doesNotContainItemsInOrder() {
            specify(elements, does.not().containInOrder(1, 3, 2));
        }
        
        public void containsItemsInPartialOrder() {
            specify(elements, containsInPartialOrder(1, 3));
        }
    }
    
    public class SampleWithVariousRightHandSides {
        private List<Integer> elements;

        public Object create() {
            elements = Arrays.asList(1, 2, 3);
            return elements;
        }
        
        public void containsAllItems() {
            specify(elements, containsAll(1, 2));
            specify(elements, containsAll(new Object[] { 1, 2 }));
            specify(elements, containsAll(Arrays.asList(1, 2)));
            specify(elements, containsAll(Arrays.asList(1, 2).iterator()));
            specify(elements, containsAll((Iterable<Integer>) Arrays.asList(1, 2)));
        }
        
        public void doesNotContainAllItems() {
            specify(elements, does.not().containAll(0, 1, 2));
            specify(elements, does.not().containAll(new Object[] { 0, 1, 2 }));
            specify(elements, does.not().containAll(Arrays.asList(0, 1, 2)));
            specify(elements, does.not().containAll(Arrays.asList(0, 1, 2).iterator()));
            specify(elements, does.not().containAll((Iterable<Integer>) Arrays.asList(0, 1, 2)));
        }
    }
    
    public class SampleWithVariousLeftHandSides {
        public Object create() {
            return null;
        }
        
        public void collection() {
            specify(Arrays.asList(1, 2, 3), containsAll(1, 2));
        }
        
        public void iterator() {
            specify(Arrays.asList(1, 2, 3).iterator(), containsAll(1, 2));
        }
        
        public void iterable() {
            specify((Iterable<?>) Arrays.asList(1, 2, 3), containsAll(1, 2));
        }
        
        public void array() {
            specify(new Integer[] { 1, 2, 3 }, containsAll(1, 2));
        }
    }
}
