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
    public T context;
    
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

    public void specify(T actual, boolean expected) {
        specify(expected);
    }

    public void specify(T actual, Boolean expected) {
        specify(expected);
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
                    throw new ExpectationFailedException("The specified collection " + actual
                            + " does not contain '" + containment + "'");
                }
            } else {
                if (containment.isIn(actual)) {
                    throw new ExpectationFailedException("The specified collection " + actual
                            + " contains '" + containment + "'");
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
            if (!equals(actual, expected)) {
                throw newException(expected, actual);
            }
        } finally {
            resetActualState();
        }
    }

    private boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
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
            throw new ExpectationFailedException("The specified block should throw "
                    + expected.getName() + " but " + t.getClass().getName() + " was thrown.", t);
        } finally {
            resetActualState();
        }
        throw new ExpectationFailedException("The specified block should throw "
                + expected.getName() + " but nothing was thrown.");
    }

    public void specify(Object obj, Contract contract) {
        contract.isSatisfied(obj);
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

    public Containment containAll(byte[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public Containment containsAll(byte[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public Containment containAll(short[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public Containment containsAll(short[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public Containment containAll(int[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public Containment containsAll(int[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public Containment containAll(long[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public Containment containsAll(long[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public Containment containAll(float[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public Containment containsAll(float[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public Containment containAll(double[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public Containment containsAll(double[] elements) {
        return containAll(Primitives.asList(elements));
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

    public Containment containAny(byte[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public Containment containsAny(byte[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public Containment containAny(short[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public Containment containsAny(short[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public Containment containAny(int[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public Containment containsAny(int[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public Containment containAny(long[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public Containment containsAny(long[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public Containment containAny(float[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public Containment containsAny(float[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public Containment containAny(double[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public Containment containsAny(double[] elements) {
        return containAny(Primitives.asList(elements));
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

    public Containment containExactly(byte[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public Containment containsExactly(byte[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public Containment containExactly(short[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public Containment containsExactly(short[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public Containment containExactly(int[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public Containment containsExactly(int[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public Containment containExactly(long[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public Containment containsExactly(long[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public Containment containExactly(float[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public Containment containsExactly(float[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public Containment containExactly(double[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public Containment containsExactly(double[] elements) {
        return containsExactly(Primitives.asList(elements));
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

    public Containment containInOrder(Collection<?> elements) {
        return new InOrderContainment(elements);
    }

    public Containment containsInOrder(Collection<?> elements) {
        return containInOrder(elements);
    }

    public Containment containInOrder(Object... elements) {
        return new InOrderContainment(Arrays.asList(elements));
    }

    public Containment containInOrder(byte[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public Containment containsInOrder(byte[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public Containment containInOrder(short[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public Containment containsInOrder(short[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public Containment containInOrder(int[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public Containment containsInOrder(int[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public Containment containInOrder(long[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public Containment containsInOrder(long[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public Containment containInOrder(float[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public Containment containsInOrder(float[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public Containment containInOrder(double[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public Containment containsInOrder(double[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public Containment containsInOrder(Object... elements) {
        return containInOrder(elements);
    }

    public Containment containInOrder(Iterator<?> elements) {
        return new InOrderContainment(elements);
    }

    public Containment containsInOrder(Iterator<?> elements) {
        return containInOrder(elements);
    }

    public Containment containInOrder(Iterable<?> elements) {
        return new InOrderContainment(elements);
    }

    public Containment containsInOrder(Iterable<?> elements) {
        return containInOrder(elements);
    }

    public Contract satisfies(Contract contract) {
        return contract;
    }

    /**
     * Called before create() method in context has been called.
     * Override this method to add common initialization code for contexts within
     * a specification.
     */
    public void create() {
    }

    /**
     * Called after optional destroy() method in context has been called.
     * Override this method to add common destroy code for contexts within
     * a specification.
     */
    public void destroy() {
    }
}
