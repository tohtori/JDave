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

import jdave.util.Collections;
import jdave.util.Primitives;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public abstract class Specification<T> extends ContainmentSupport {
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

    public void specify(Block block, ExpectedException<? extends Throwable> expectation) {
        try {
            block.run();
        } catch (Throwable t) {
            if (expectation.matches(t.getClass())) {
                return;
            }
            throw new ExpectationFailedException("The specified block should throw "
                    + expectation.getType().getName() + " but " + t.getClass().getName() + " was thrown.", t);
        } finally {
            resetActualState();
        }
        throw new ExpectationFailedException("The specified block should throw "
                + expectation.getType().getName() + " but nothing was thrown.");
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

    public <E extends Throwable> ExpectedException<E> raise(Class<E> expected) {
        return new ExpectedException<E>(expected);
    }

    public <E extends Throwable> ExpectedException<E> raiseExactly(Class<E> expected) {
        return new ExactExpectedException<E>(expected);
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
