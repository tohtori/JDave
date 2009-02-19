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
import java.util.Map;
import jdave.containment.MapContainment;
import jdave.equality.DeltaEqualityCheck;
import jdave.equality.EqualsEqualityCheck;
import jdave.equality.LongEqualsEqualityCheck;
import jdave.equality.NotEqualsEqualityCheck;
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
    protected Specification<T> must = this;
    private boolean actualState = true;
    public T be;
    public T context;
    private final List<ILifecycleListener> listeners = new ArrayList<ILifecycleListener>();
    private IContextObjectFactory<T> contextObjectFactory = new DefaultContextObjectFactory<T>();
    private static IStringComparisonFailure stringComparisonFailure = new ExpectationFailedStringComparisonFailure();

    public Not<T> not() {
        actualState = false;
        return new Not<T>(this);
    }

    public void specify(final boolean expected) {
        specify0(null, expected);
    }

    private void specify0(final T actual, final boolean expected) {
        try {
            if (expected != actualState) {
                throw new ExpectationFailedException("Expected: " + "true" + ", but was: "
                        + "false" + (actual != null ? " (actual was '" + actual + "')" : ""));
            }
        } finally {
            resetActualState();
        }
    }

    public void specify(final T actual, final boolean expected) {
        specify0(actual, expected);
    }

    public void specify(final T actual, final Boolean expected) {
        specify0(actual, expected);
    }

    private void resetActualState() {
        actualState = true;
    }

    public <E> void specify(final Iterator<E> actual, final IContainment<E> containment) {
        specify(Collections.<E> list(actual), containment);
    }

    public <E> void specify(final Collection<E> actual, final IContainment<E> containment) {
        try {
            if (!containment.matches(actual)) {
                throw new ExpectationFailedException(containment.error(actual));
            }
        } finally {
            resetActualState();
        }
    }

    public <E> void specify(final Object[] actual, final IContainment<E> containment) {
        specify(Arrays.asList(actual), containment);
    }

    public <E> void specify(final boolean[] actual, final IContainment<E> containment) {
        specify(Primitives.asList(actual), containment);
    }

    public <E> void specify(final byte[] actual, final IContainment<E> containment) {
        specify(Primitives.asList(actual), containment);
    }

    public <E> void specify(final char[] actual, final IContainment<E> containment) {
        specify(Primitives.asList(actual), containment);
    }

    public <E> void specify(final double[] actual, final IContainment<E> containment) {
        specify(Primitives.asList(actual), containment);
    }

    public <E> void specify(final float[] actual, final IContainment<E> containment) {
        specify(Primitives.asList(actual), containment);
    }

    public <E> void specify(final int[] actual, final IContainment<E> containment) {
        specify(Primitives.asList(actual), containment);
    }

    public <E> void specify(final long[] actual, final IContainment<E> containment) {
        specify(Primitives.asList(actual), containment);
    }

    public <E> void specify(final short[] actual, final IContainment<E> containment) {
        specify(Primitives.asList(actual), containment);
    }

    public void specify(final Object actual, final Object expected) {
        final IEqualityCheck equalityCheck = new EqualsEqualityCheck(expected);
        try {
            if (!equalityCheck.matches(actual)) {
                throw new ExpectationFailedException(equalityCheck.error(actual));
            }
        } finally {
            resetActualState();
        }
    }

    public void specify(final Object actual, final IEqualityCheck equalityCheck) {
        try {
            equalityCheck.verify(actual);
        } finally {
            resetActualState();
        }
    }

    /**
     * Matches the actual object using Hamcrest Matcher.
     * <p>
     * Hamcrest provides a library of matcher objects allowing 'match' rules
     * to be defined declaratively. <blockquote>
     * 
     * <pre>
     * &lt;code&gt;
     * import static org.hamcrest.Matchers.*;
     * public class HamcrestSampleSpec extends Specification&lt;Person&gt; {
     *     public class SampleContext {
     *         public void sample() {
     *             specify(person.getAge(), greaterThan(30));
     *         }
     *     }
     * }
     * &lt;/code&gt;
     * </pre>
     * 
     * </blockquote>
     * <p>
     * See <a href="http://code.google.com/p/hamcrest/">Hamcrest home page</a>
     */
    public void specify(final Object actual, final Matcher<?> matcher) {
        try {
            if (!matcher.matches(actual)) {
                throw new ExpectationFailedException(actual + " "
                        + StringDescription.toString(matcher));
            }
        } finally {
            resetActualState();
        }
    }

    /**
     * Matches all the actual objects using Hamcrest Matcher.
     * <p>
     * Hamcrest provides a library of matcher objects allowing 'match' rules
     * to be defined declaratively. <blockquote>
     * 
     * <pre>
     * &lt;code&gt;
     * import static org.hamcrest.Matchers.*;
     * public class HamcrestSampleSpec extends Specification&lt;Person&gt; {
     *     public class SampleContext {
     *         public void sample() {
     *             specify(persons, where(new Each&lt;Person&gt;() {{ matches(item.getAge(), is(greaterThan(30))); }}));
     *         }
     *     }
     * }
     * &lt;/code&gt;
     * </pre>
     * 
     * </blockquote>
     * <p>
     * See <a href="http://code.google.com/p/hamcrest/">Hamcrest home page</a>
     */
    public <E> void specify(final Collection<E> actual, final Where<E> where) {
        specify(actual.iterator(), where);
    }

    /**
     * @see #specify(Collection, Where)
     */
    public <E> void specify(final Iterable<E> actual, final Where<E> where) {
        specify(actual.iterator(), where);
    }

    /**
     * @see #specify(Collection, Where)
     */
    public <E> void specify(final Object[] actual, final Where<E> where) {
        specify(Arrays.asList(actual), where);
    }

    /**
     * @see #specify(Collection, Where)
     */
    public <E> void specify(final Iterator<E> actual, final Where<E> where) {
        try {
            int index = 0;
            while (actual.hasNext()) {
                where.match(actual.next(), index);
                index++;
            }
            where.areAllMatchersUsed(index);
        } finally {
            resetActualState();
        }
    }

    public <E> Where<E> where(final Each<E> each) {
        return new Where<E>(each);
    }

    /**
     * The given block is expected to throw an exception.
     * <p>
     * There's two variants for setting exception expectations. <blockquote>
     * 
     * <pre>
     * &lt;code&gt;
     * specify(new Block() { ... }, should.raise(SomeException.class);
     * specify(new Block() { ... }, should.raiseExactly(SomeException.class);
     * &lt;/code&gt;
     * </pre>
     * 
     * </blockquote> The first one accepts the given exception or any of its
     * subclasses, the second one expects an exact exception type. Both can
     * additionally be checked against expected exception message:
     * <blockquote>
     * 
     * <pre>
     * &lt;code&gt;
     * specify(new Block() { ... }, should.raise(SomeException.class, &quot;expected message&quot;);
     * specify(new Block() { ... }, should.raiseExactly(SomeException.class, &quot;expected message&quot;);
     * &lt;/code&gt;
     * </pre>
     * 
     * </blockquote>
     */
    public <V extends Throwable> void specify(final Block block,
            final ExpectedException<V> expectation) {
        try {
            specifyThrow(block, expectation);
        } finally {
            resetActualState();
        }
    }

    private void specifyThrow(final Block block,
            final ExpectedException<? extends Throwable> expectation) {
        try {
            block.run();
        } catch (final Throwable t) {
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
     * There's two variants for setting exception expectations. <blockquote>
     * 
     * <pre>
     * &lt;code&gt;
     * specify(new Block() { ... }, should.not().raise(SomeException.class);
     * specify(new Block() { ... }, should.not().raiseExactly(SomeException.class);
     * &lt;/code&gt;
     * </pre>
     * 
     * </blockquote> The first one expects that the given block does not throw
     * the exception or any of its subclasses, the second one expects that the
     * given block does not throw the given exact exception type.
     */
    public <V extends Throwable> void specify(final Block block,
            final ExpectedNoThrow<V> expectation) throws Throwable {
        try {
            specifyNoThrow(block, expectation);
        } finally {
            resetActualState();
        }
    }

    private void specifyNoThrow(final Block block,
            final ExpectedNoThrow<? extends Throwable> expectation) throws Throwable {
        try {
            block.run();
        } catch (final Throwable t) {
            if (!expectation.matches(t)) {
                throw new ExpectationFailedException(expectation.error(t), t);
            }
            throw t;
        }
    }

    public void specify(final Object obj, final IContract contract) {
        contract.isSatisfied(obj);
    }

    public IEqualityCheck equal(final long expected) {
        return new LongEqualsEqualityCheck(expected);
    }

    public IEqualityCheck equal(final String obj) {
        if (obj == null) {
            return new EqualsEqualityCheck(obj);
        }
        return new StringEqualsEqualityCheck(this, obj);
    }

    public IEqualityCheck equal(final Object obj) {
        return new EqualsEqualityCheck(obj);
    }

    public IEqualityCheck equal(final Number expectedNumber, final double delta) {
        return new DeltaEqualityCheck(expectedNumber, delta);
    }

    /**
     * @see #specify(Block, ExpectedException)
     * @see #specify(Block, ExpectedNoThrow)
     */
    public <E extends Throwable> ExpectedException<E> raise(final Class<E> expected) {
        return new ExpectedException<E>(expected);
    }

    /**
     * @see #specify(Block, ExpectedException)
     * @see #specify(Block, ExpectedNoThrow)
     */
    public <E extends Throwable> ExpectedException<E> raise(final Class<E> expectedType,
            final String expectedMessage) {
        return new ExpectedExceptionWithMessage<E>(expectedType, expectedMessage);
    }

    /**
     * @see #specify(Block, ExpectedException)
     * @see #specify(Block, ExpectedNoThrow)
     */
    public <E extends Throwable> ExpectedException<E> raiseExactly(final Class<E> expected) {
        return new ExactExpectedException<E>(expected);
    }

    /**
     * @see #specify(Block, ExpectedException)
     * @see #specify(Block, ExpectedNoThrow)
     */
    public <E extends Throwable> ExpectedException<E> raiseExactly(final Class<E> expected,
            final String expectedMessage) {
        return new ExactExpectedExceptionWithMessage<E>(expected, expectedMessage);
    }

    public IContract satisfies(final IContract contract) {
        return contract;
    }

    /**
     * Create this specification.
     * <p>
     * This method is called before the <code>create</code> method of the
     * executed context has been called. Override this method to add common
     * initialization code for contexts within a specification.
     */
    public void create() throws Exception {
    }

    /**
     * Destroy this specification.
     * <p>
     * This method is called after the optional <code>destroy</code> method of
     * the excuted context has been called. Override this method to add common
     * destroy code for contexts within a specification.
     */
    public void destroy() throws Exception {
    }

    /**
     * Returns <code>true</code> if thread local isolation is needed for this
     * specification.
     * <p>
     * Some contexts set thread local variables. This may cause following
     * behaviors to fail if they depend on initial thread local state. Thread
     * locals can be isolated for all behavior methods of current
     * specification by overiding this method and returning <code>true</code>.
     * Then a new fresh thread is created for all methods in the
     * specification.
     * 
     * @return <code>true</code> if thread local isolation is needed for this
     *         specification. The default is <code>false</code>.
     */
    public boolean needsThreadLocalIsolation() {
        return false;
    }

    protected void setContextObjectFactory(final IContextObjectFactory<T> contextObjectFactory) {
        this.contextObjectFactory = contextObjectFactory;
    }

    public IContextObjectFactory<T> getContextObjectFactory() {
        return contextObjectFactory;
    }

    /**
     * Add a <code>ILifecycleListener</code> listener to specification.
     * <p>
     * ILifecycleListener will be notified when contexts are instantiated and
     * context objects are created and destroyed.
     * 
     * @param listener a listener to get lifecycle event notifications
     */
    protected void addListener(final ILifecycleListener listener) {
        listeners.add(listener);
    }

    public void fireAfterContextInstantiation(final Object contextInstance) {
        for (final ILifecycleListener listener : listeners) {
            listener.afterContextInstantiation(contextInstance);
        }
    }

    public void fireAfterContextCreation(final Object contextInstance, final Object createdContext) {
        for (final ILifecycleListener listener : listeners) {
            listener.afterContextCreation(contextInstance, createdContext);
        }
    }

    public void fireAfterContextDestroy(final Object contextInstance) {
        for (final ILifecycleListener listener : listeners) {
            listener.afterContextDestroy(contextInstance);
        }
    }

    public IEqualityCheck isNotNull() {
        return new NotEqualsEqualityCheck(null) {
            @Override
            public String error(final Object actual) {
                return "Expected a non-null value";
            }
        };
    }

    public static void setStringComparisonFailure(final IStringComparisonFailure failure) {
        stringComparisonFailure = failure;
    }

    IStringComparisonFailure stringComparisonFailure() {
        return stringComparisonFailure;
    }

    public MapContainment maps(final Object... keys) {
        return new MapContainment(keys);
    }

    public void specify(final Map<?, ?> actual, final MapContainment containment) {
        containment.verify(actual);
    }

    /**
     * Fail with a message.
     * 
     * @param message a message printed to the console
     */
    public void fail(final String message) {
        throw new ExpectationFailedException(message);
    }
}
