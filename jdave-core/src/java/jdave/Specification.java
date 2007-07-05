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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import jdave.equality.DeltaEqualityCheck;
import jdave.equality.EqualsEqualityCheck;
import jdave.mock.MockSupport;
import jdave.util.Collections;
import jdave.util.Primitives;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

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
    private List<ILifecycleListener> listeners = new ArrayList<ILifecycleListener>();
    
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
    
    /**
     * Matches the actual object using Hamcrest Matcher.
     * <p>
     * Hamcrest provides a library of matcher objects allowing 'match' rules to be defined declaratively.
     * <blockquote>
     * <pre><code>
     * import static org.hamcrest.Matchers.*;
     * 
     * public class HamcrestSampleSpec extends Specification&lt;Person&gt; {
     *     public class SampleContext {
     *         public void sample() {
     *             specify(person.getAge(), greaterThan(30));
     *         }
     *     }
     * }
     * </code></pre>
     * </blockquote>
     * <p>
     * See <a href="http://code.google.com/p/hamcrest/">Hamcrest home page</a>
     */
    public void specify(Object actual, Matcher<?> matcher) {
        try {
            if (!matcher.matches(actual)) {
                throw new ExpectationFailedException(StringDescription.toString(matcher));
            }
        } finally {
            resetActualState();
        }
    }
    
    /**
     * Matches all the actual objects using Hamcrest Matcher.
     * <p>
     * Hamcrest provides a library of matcher objects allowing 'match' rules to be defined declaratively.
     * <blockquote>
     * <pre><code>
     * import static org.hamcrest.Matchers.*;
     * 
     * public class HamcrestSampleSpec extends Specification&lt;Person&gt; {
     *     public class SampleContext {
     *         public void sample() {
     *             specify(persons, where(new Each&lt;Person&gt;() {{ matches(item.getAge(), is(greaterThan(30))); }}));
     *         }
     *     }
     * }
     * </code></pre>
     * </blockquote>
     * <p>
     * See <a href="http://code.google.com/p/hamcrest/">Hamcrest home page</a>
     */
    public void specify(Collection<?> actual, Where<?> where) {
        specify(actual.iterator(), where);
    }
    
    /**
     * @see #specify(Collection, Where)
     */
    public void specify(Iterable<?> actual, Where<?> where) {
        specify(actual.iterator(), where);
    }
    
    /**
     * @see #specify(Collection, Where)
     */
    public void specify(Object[] actual, Where<?> where) {
        specify(Arrays.asList(actual), where);
    }
    
    /**
     * @see #specify(Collection, Where)
     */
    @SuppressWarnings("unchecked")
    public <E> void specify(Iterator<?> actual, Where<?> where) {
        Where<E> unsafeWhere = (Where<E>) where;
        try {
            int index = 0;
            while (actual.hasNext()) {
                unsafeWhere.match((E) actual.next(), index);
                index++;
            }
            where.areAllMatchersUsed(index);
        } finally {
            resetActualState();
        }
    }
    
    public <E> Where<E> where(Each<E> each) {
        return new Where<E>(each);
    }

    /**
     * The given block is expected to throw an exception.
     * <p>
     * There's two variants for setting exception expectations. 
     * <blockquote><pre><code>
     * specify(new Block() { ... }, should.raise(SomeException.class);
     * specify(new Block() { ... }, should.raiseExactly(SomeException.class);
     * </code></pre></blockquote>
     * The first one accepts the given exception or any of its subclasses, the second
     * one expects an exact exception type. Both can additionally be checked against
     * expected exception message:
     * <blockquote><pre><code>
     * specify(new Block() { ... }, should.raise(SomeException.class, "expected message");
     * specify(new Block() { ... }, should.raiseExactly(SomeException.class, "expected message");
     * </code></pre></blockquote>
     */
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

    /**
     * The given block is expected to not throw an exception.
     * <p>
     * There's two variants for setting exception expectations. 
     * <blockquote><pre><code>
     * specify(new Block() { ... }, should.not().raise(SomeException.class);
     * specify(new Block() { ... }, should.not().raiseExactly(SomeException.class);
     * </code></pre></blockquote>
     * The first one expects that the given block does not throw the exception or any of 
     * its subclasses, the second one expects that the given block does not throw the given
     * exact exception type. 
     */
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

    public IEqualityCheck equal(String obj) {
        return new StringEqualsEqualityCheck(obj);
    }
    
    public IEqualityCheck equal(Object obj) {
        return new EqualsEqualityCheck(obj);
    }

    public IEqualityCheck equal(Number expectedNumber, double delta) {
        return new DeltaEqualityCheck(expectedNumber, delta);
    }

    /**
     * @see #specify(Block, ExpectedException)
     * @see #specify(Block, ExpectedNoThrow)
     */
    public <E extends Throwable> ExpectedException<E> raise(Class<E> expected) {
        return new ExpectedException<E>(expected);
    }

    /**
     * @see #specify(Block, ExpectedException)
     * @see #specify(Block, ExpectedNoThrow)
     */
    public <E extends Throwable> ExpectedException<E> raise(Class<E> expectedType, String expectedMessage) {
        return new ExpectedExceptionWithMessage<E>(expectedType, expectedMessage);
    }

    /**
     * @see #specify(Block, ExpectedException)
     * @see #specify(Block, ExpectedNoThrow)
     */
    public <E extends Throwable> ExpectedException<E> raiseExactly(Class<E> expected) {
        return new ExactExpectedException<E>(expected);
    }

    /**
     * @see #specify(Block, ExpectedException)
     * @see #specify(Block, ExpectedNoThrow)
     */
    public <E extends Throwable> ExpectedException<E> raiseExactly(Class<E> expected, String expectedMessage) {
        return new ExactExpectedExceptionWithMessage<E>(expected, expectedMessage);
    }

    public IContract satisfies(IContract contract) {
        return contract;
    }

    /**
     * Create this specification.
     * <p>
     * This method is called before the <code>create</code> method of the executed context has
     * been called. Override this method to add common initialization code for contexts within
     * a specification.
     */
    public void create() {
    }

    /**
     * Destroy this specification.
     * <p>
     * This method is called after the optional <code>destroy</code> method of the excuted context
     * has been called. Override this method to add common destroy code for contexts within a
     * specification.
     */
    public void destroy() {
    }
    
    /**
     * Returns <code>true</code> if thread local isolation is needed for this specification.
     * <p>
     * Some contexts set thread local variables. This may cause following behaviors to fail if
     * they depend on initial thread local state. Thread locals can be isolated for all behavior
     * methods of current specification by overiding this method and returning <code>true</code>.
     * Then a new fresh thread is created for all methods in the specification.
     *
     * @return  <code>true</code> if thread local isolation is needed for this specification. The
     *          default is <code>false</code>.
     */
    public boolean needsThreadLocalIsolation() {
        return false;
    }
    
    /**
     * Add a <code>ILifecycleListener</code> listener to specification.
     * <p>
     * ILifecycleListener will be notified when contexts are instantiated and context objects 
     * are created and destroyed.
     * 
     * @param listener a listener to get lifecycle event notifications
     */
    protected void addListener(ILifecycleListener listener) {
        listeners.add(listener);
    }

    public void fireAfterContextInstantiation(Object contextInstance) {
        for (ILifecycleListener listener : listeners) {
            listener.afterContextInstantiation(contextInstance);
        }
    }

    public void fireAfterContextCreation(Object contextInstance, Object createdContext) {
        for (ILifecycleListener listener : listeners) {
            listener.afterContextCreation(contextInstance, createdContext);
        }
    }
    
    public void fireAfterContextDestroy(Object contextInstance) {
        for (ILifecycleListener listener : listeners) {
            listener.afterContextDestroy(contextInstance);
        }
    }
}
