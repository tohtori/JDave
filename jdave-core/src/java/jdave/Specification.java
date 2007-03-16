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
    
    public Not<T> not() {
        actualState = false;
        return new Not<T>(this);
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

    public void specify(Iterable<?> actual, IContainment containment) {
        specify(actual.iterator(), containment);
    }

    public void specify(Iterator<?> actual, IContainment containment) {
        specify(Collections.list(actual), containment);
    }

    public void specify(Collection<?> actual, IContainment containment) {
        try {
            if (!containment.matches(actual)) {
                throw new ExpectationFailedException(containment.error(actual));
            }
        } finally {
            resetActualState();
        }
    }

    public void specify(Object[] actual, IContainment containment) {
        specify(Arrays.asList(actual), containment);
    }

    public void specify(boolean[] actual, IContainment containment) {
        specify(Primitives.asList(actual), containment);
    }

    public void specify(byte[] actual, IContainment containment) {
        specify(Primitives.asList(actual), containment);
    }

    public void specify(char[] actual, IContainment containment) {
        specify(Primitives.asList(actual), containment);
    }

    public void specify(double[] actual, IContainment containment) {
        specify(Primitives.asList(actual), containment);
    }

    public void specify(float[] actual, IContainment containment) {
        specify(Primitives.asList(actual), containment);
    }

    public void specify(int[] actual, IContainment containment) {
        specify(Primitives.asList(actual), containment);
    }

    public void specify(long[] actual, IContainment containment) {
        specify(Primitives.asList(actual), containment);
    }

    public void specify(short[] actual, IContainment containment) {
        specify(Primitives.asList(actual), containment);
    }

    public void specify(Object actual, Object expected) {
        IEqualityCheck equalityCheck = new EqualsEqualityCheck(expected);
        try {
            if (!equalityCheck.matches(actual)) {
                throw new ExpectationFailedException(equalityCheck.error(actual));
            }
        } finally {
            resetActualState();
        }
    }
    
    public void specify(Object actual, IEqualityCheck equalityCheck) {
        try {
            if (!equalityCheck.matches(actual)) {
                throw new ExpectationFailedException(equalityCheck.error(actual));
            }
        } finally {
            resetActualState();
        }
    }
    
    public <V extends Throwable> void specify(Block block, ExpectedException<V> expectation) {
        try {
            specifyThrow(block, expectation);
        } finally {
            resetActualState();
        }
    }

    private void specifyThrow(Block block, ExpectedException<? extends Throwable> expectation) {
        try {
            block.run();
        } catch (Throwable t) {
            if (!expectation.matches(t)) {
                throw new ExpectationFailedException(expectation.error(t), t);
            }
            return;
        }
        throw new ExpectationFailedException(expectation.notThrown());
    }

    public <V extends Throwable> void specify(Block block, ExpectedNoThrow<V> expectation) throws Throwable {
        try {
            specifyNoThrow(block, expectation);
        } finally {
            resetActualState();
        }
    }

    private void specifyNoThrow(Block block, ExpectedNoThrow<? extends Throwable> expectation) throws Throwable {
        try {
            block.run();
        } catch (Throwable t) {
            if (!expectation.matches(t)) {
                throw new ExpectationFailedException(expectation.error(t), t);
            }
            throw t;
        }
    }

    public void specify(Object obj, IContract contract) {
        contract.isSatisfied(obj);
    }

    public IEqualityCheck equal(Object obj) {
        return new EqualsEqualityCheck(obj);
    }

    public IEqualityCheck equal(Number expectedNumber, double delta) {
        return new DeltaEqualityCheck(expectedNumber, delta);
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

    public IContract satisfies(IContract contract) {
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
