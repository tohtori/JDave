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
    
    public InverseSpecification<T> not() {
        actualState = false;
        return new InverseSpecification<T>(this);
    }

    public void specify(boolean expected) {
        try {
            if (expected != actualState) {
                throw new ExpectationFailedException("Expected: " + "true" + ", but was: " + "false");
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
            if (!containment.matches(actual)) {
                throw new ExpectationFailedException(containment.error(actual));
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
            if (!new EqualsEqualityCheck().isEqual(actual, expected)) {
                throw new ExpectationFailedException("Expected: " + expected + ", but was: " + actual);
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
                throw new ExpectationFailedException("Expected: " + expected + ", but was: " + actual);
            }
        } finally {
            resetActualState();
        }
    }

    public <V extends Throwable> void specify(Block block, IExpectedException<V> expectation) {
        try {
            specifyThrow(block, expectation);
        } finally {
            resetActualState();
        }
    }

    private void specifyThrow(Block block, IExpectedException<? extends Throwable> expectation) {
        try {
            block.run();
        } catch (Throwable t) {
            if (!expectation.matches(t)) {
                throw new ExpectationFailedException(expectation.error(t), t);
            }
            if (expectation.propagateException()) {
                throw new RuntimeException(t);
            }
            return;
        }
        if (!expectation.propagateException()) {
            throw new ExpectationFailedException(expectation.nothrow());
        }
    }

    public void specify(Object obj, Contract contract) {
        contract.isSatisfied(obj);
    }

    public Object equal(Object obj) {
        return obj;
    }

    public <E extends Throwable> ExpectedException<E> raise(Class<E> expected) {
        return new ExpectedException<E>(expected);
    }

    public <E extends Throwable> ExpectedException<E> raise(Class<E> expectedType, String expectedMessage) {
        return new ExpectedExceptionWithMessage<E>(expectedType, expectedMessage);
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
