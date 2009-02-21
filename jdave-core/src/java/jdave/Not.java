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
package jdave;

import java.util.Collection;
import java.util.Iterator;
import jdave.containment.NotContainment;
import jdave.contract.NotContract;
import jdave.equality.NotEqualsEqualityCheck;

/**
 * @author Pekka Enberg
 */
public class Not<A> extends ContainmentSupport {
    public A be;

    public Not(final Specification<A> specification) {
        be = specification.be;
    }

    @Override
    protected <T> IContainment<T> newAllContainment(final Collection<T> elements) {
        return new NotContainment<T>(super.newAllContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newAllContainment(final Iterable<T> elements) {
        return new NotContainment<T>(super.newAllContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newAllContainment(final Iterator<T> elements) {
        return new NotContainment<T>(super.newAllContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newAnyContainment(final Collection<T> elements) {
        return new NotContainment<T>(super.newAnyContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newAnyContainment(final Iterable<T> elements) {
        return new NotContainment<T>(super.newAnyContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newAnyContainment(final Iterator<T> elements) {
        return new NotContainment<T>(super.newAnyContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newExactContainment(final Collection<T> elements) {
        return new NotContainment<T>(super.newExactContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newExactContainment(final Iterable<T> elements) {
        return new NotContainment<T>(super.newExactContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newExactContainment(final Iterator<T> elements) {
        return new NotContainment<T>(super.newExactContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newInOrderContainment(final Collection<T> elements) {
        return new NotContainment<T>(super.newInOrderContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newInOrderContainment(final Iterable<T> elements) {
        return new NotContainment<T>(super.newInOrderContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newInOrderContainment(final Iterator<T> elements) {
        return new NotContainment<T>(super.newInOrderContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newInPartialOrderContainment(final Collection<T> elements) {
        return new NotContainment<T>(super.newInPartialOrderContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newInPartialOrderContainment(final Iterable<T> elements) {
        return new NotContainment<T>(super.newInPartialOrderContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newInPartialOrderContainment(final Iterator<T> elements) {
        return new NotContainment<T>(super.newInPartialOrderContainment(elements));
    }

    @Override
    protected <T> IContainment<T> newObjectContainment(final T object) {
        return new NotContainment<T>(super.newObjectContainment(object));
    }

    public <E extends Throwable> ExpectedNoThrow<E> raise(final Class<E> expected) {
        return new ExpectedNoThrow<E>(new ExpectedException<E>(expected));
    }

    /**
     * No exception is expected from <code>Block</code>.
     * <p>
     * 
     * @see Specification#specify(Block, ExpectedNoThrow)
     */

    public ExpectedNoThrow<Throwable> raiseAnyException() {
        return raise(Throwable.class);
    }

    public <E extends Throwable> ExpectedNoThrow<E> raise(final Class<E> expectedType,
            final String expectedMessage) {
        return new ExpectedNoThrow<E>(new ExpectedExceptionWithMessage<E>(expectedType,
                expectedMessage));
    }

    public <E extends Throwable> ExpectedNoThrow<E> raiseExactly(final Class<E> expected) {
        return new ExpectedNoThrow<E>(new ExactExpectedException<E>(expected));
    }

    public IEqualityCheck equal(final Object obj) {
        return new NotEqualsEqualityCheck(obj);
    }

    public IEqualityCheck equal(final Number expectedNumber, final double delta) {
        return new NotDeltaEqualityCheck(expectedNumber, delta);
    }

    public IContract satisfy(final IContract contract) {
        return new NotContract(contract);
    }
}
