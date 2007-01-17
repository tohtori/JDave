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
import java.util.Collection;
import java.util.Iterator;

import jdave.mock.MockSupport;
import jdave.util.Collections;
import jdave.util.Primitives;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public abstract class Specification<T> extends MockSupport {
    protected Specification<T> should = this;
    protected Specification<T> does = this;
    private boolean actualState = true;
    public T be;
    
    public Specification<T> not() {
        actualState = false;
        return this;
    }

    public void specify(boolean expected) {
        try {
            if (expected != actualState) {
                throw newException("true", "false");
            }
        } finally {
            resetActualState();
        }
    }
    
    private void resetActualState() {
        actualState = true;        
    }
    
    public void specify(Iterable<?> actual, Containment containment) {
        specify(actual.iterator(), containment);
    }
    
    public void specify(Iterator<?> actual, Containment containment) {
        specify(Collections.list(actual), containment);
    }

    public void specify(Collection<?> actual, Containment containment) {
        try {
            if (actualState) {
                if (!containment.isIn(actual)) {
                    throw new ExpectationFailedException("The specified collection " + actual + " does not contain '" + containment + "'");
                }
            } else {
                if (containment.isIn(actual)) {
                    throw new ExpectationFailedException("The specified collection " + actual + " contains '" + containment + "'");
                }
            }
        } finally {
            resetActualState();
        }
    }
    
    public void specify(Object[] actual, Containment containment) {
        specify(Arrays.asList(actual), containment);
    }
    
    public void specify(boolean[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }
    
    public void specify(byte[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }
    
    public void specify(char[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }
    
    public void specify(double[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }
    
    public void specify(float[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }
    
    public void specify(int[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }
    
    public void specify(long[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }

    public void specify(short[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }

    public void specify(Object actual, Object expected) {
        try {
            if (!actual.equals(expected)) {
                throw newException(expected, actual);
            }
        } finally {
            resetActualState();
        }
    }
    
    public void specify(double actual, double expected, double delta) {
        specify(actual, new Double(expected), delta);
    }
    
    public void specify(double actual, Object expected, double delta) {
        try {
            if (Math.abs(actual - ((Number) expected).doubleValue()) > delta) {
                throw newException(expected, actual);            
            }
        } finally {
            resetActualState();
        }
    }
    
    public void specify(Block block, Class<? extends Throwable> expected) {
        try {
            block.run();
        } catch (Throwable t) {
            if (t.getClass().equals(expected)) {
                return;
            }
        } finally {
            resetActualState();            
        }
        throw new ExpectationFailedException("The specified block should throw " + expected.getName() + ".");        
    }

    private ExpectationFailedException newException(Object expected, Object actual) {
        return new ExpectationFailedException("Expected: " + expected + ", but was: " + actual);
    }
    
    public Object equal(Object obj) {
        return obj;
    }
    
    public Class<? extends Throwable> raise(Class<? extends Throwable> expected) {
        return expected;
    }

    public Containment contains(Object object) {
        return new ObjectContainment(object);
    }
    
    public Containment contain(Object object) {
        return new ObjectContainment(object);
    }

    public Containment containAll(Collection<?> elements) {
        return new AllContainment(elements);
    }
    
    public Containment containsAll(Collection<?> elements) {
        return containAll(elements);
    }

    public Containment containAll(Object... elements) {
        return containsAll(Arrays.asList(elements));
    }
    
    public Containment containsAll(Object... elements) {
        return containAll(elements);
    }
    
    public Containment containAll(Iterator<?> elements) {
        return new AllContainment(elements);
    }
    
    public Containment containsAll(Iterator<?> elements) {
        return containAll(elements);
    }
    
    public Containment containAll(Iterable<?> elements) {
        return new AllContainment(elements);
    }
    
    public Containment containsAll(Iterable<?> elements) {
        return containAll(elements);
    }
    
    public Containment containAny(Collection<?> elements) {
        return new AnyContainment(elements);
    }
    
    public Containment containsAny(Collection<?> elements) {
        return containAny(elements);
    }
    
    public Containment containAny(Object... elements) {
        return containsAny(Arrays.asList(elements));
    }
    
    public Containment containsAny(Object... elements) {
        return containAny(elements);
    }
    
    public Containment containAny(Iterator<?> elements) {
        return new AnyContainment(elements);
    }
    
    public Containment containsAny(Iterator<?> elements) {
        return containAny(elements);
    }
    
    public Containment containAny(Iterable<?> elements) {
        return new AnyContainment(elements);
    }
    
    public Containment containsAny(Iterable<?> elements) {
        return containAny(elements);
    }

    public Containment containExactly(Collection<?> elements) {
        return new ExactContainment(elements);
    }
    
    public Containment containsExactly(Collection<?> elements) {
        return containExactly(elements);
    }
    
    public Containment containExactly(Object... elements) {
        return containsExactly(Arrays.asList(elements));
    }
    
    public Containment containsExactly(Object... elements) {
        return containExactly(elements);
    }
    
    public Containment containExactly(Iterator<?> elements) {
        return new ExactContainment(elements);
    }
    
    public Containment containsExactly(Iterator<?> elements) {
        return containExactly(elements);
    }
    
    public Containment containExactly(Iterable<?> elements) {
        return new ExactContainment(elements);
    }
    
    public Containment containsExactly(Iterable<?> elements) {
        return containExactly(elements);
    }
}
