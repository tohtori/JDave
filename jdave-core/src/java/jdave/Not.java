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

    public Not(Specification<A> specification) {
        this.be = specification.be;
    }

    @Override
    protected <T> IContainment newAllContainment(Collection<T> elements) {
        return new NotContainment(super.newAllContainment(elements));
    }

    @Override
    protected <T> IContainment newAllContainment(Iterable<T> elements) {
        return new NotContainment(super.newAllContainment(elements));
    }

    @Override
    protected <T> IContainment newAllContainment(Iterator<T> elements) {
        return new NotContainment(super.newAllContainment(elements));
    }

    @Override
    protected <T> IContainment newAnyContainment(Collection<T> elements) {
        return new NotContainment(super.newAnyContainment(elements));
    }

    @Override
    protected <T> IContainment newAnyContainment(Iterable<T> elements) {
        return new NotContainment(super.newAnyContainment(elements));
    }

    @Override
    protected <T> IContainment newAnyContainment(Iterator<T> elements) {
        return new NotContainment(super.newAnyContainment(elements));
    }

    @Override
    protected <T> IContainment newExactContainment(Collection<T> elements) {
        return new NotContainment(super.newExactContainment(elements));
    }

    @Override
    protected <T> IContainment newExactContainment(Iterable<T> elements) {
        return new NotContainment(super.newExactContainment(elements));
    }

    @Override
    protected <T> IContainment newExactContainment(Iterator<T> elements) {
        return new NotContainment(super.newExactContainment(elements));
    }

    @Override
    protected <T> IContainment newInOrderContainment(Collection<T> elements) {
        return new NotContainment(super.newInOrderContainment(elements));
    }

    @Override
    protected <T> IContainment newInOrderContainment(Iterable<T> elements) {
        return new NotContainment(super.newInOrderContainment(elements));
    }

    @Override
    protected <T> IContainment newInOrderContainment(Iterator<T> elements) {
        return new NotContainment(super.newInOrderContainment(elements));
    }

    @Override
    protected <T> IContainment newInPartialOrderContainment(Collection<T> elements) {
        return new NotContainment(super.newInPartialOrderContainment(elements));
    }

    @Override
    protected <T> IContainment newInPartialOrderContainment(Iterable<T> elements) {
        return new NotContainment(super.newInPartialOrderContainment(elements));
    }

    @Override
    protected <T> IContainment newInPartialOrderContainment(Iterator<T> elements) {
        return new NotContainment(super.newInPartialOrderContainment(elements));
    }

    @Override
    protected IContainment newObjectContainment(Object object) {
        return new NotContainment(super.newObjectContainment(object));
    }

    public <E extends Throwable> ExpectedNoThrow<E> raise(Class<E> expected) {
        return new ExpectedNoThrow<E>(new ExpectedException<E>(expected));
    }
    
    /**
     * No exception is expected from <code>Block</code>.
     * <p>
     * @see Specification#specify(Block, ExpectedNoThrow)
     */
    @SuppressWarnings("unchecked")
    public <E extends Throwable> ExpectedNoThrow<E> raiseAnyException() {
        return (ExpectedNoThrow<E>) raise(Throwable.class);
    }

    public <E extends Throwable> ExpectedNoThrow<E> raise(Class<E> expectedType,
            String expectedMessage) {
        return new ExpectedNoThrow<E>(new ExpectedExceptionWithMessage<E>(expectedType,
                expectedMessage));
    }

    public <E extends Throwable> ExpectedNoThrow<E> raiseExactly(Class<E> expected) {
        return new ExpectedNoThrow<E>(new ExactExpectedException<E>(expected));
    }

    public IEqualityCheck equal(Object obj) {
        return new NotEqualsEqualityCheck(obj);
    }
    
    public IEqualityCheck equal(Number expectedNumber, double delta) {
        return new NotDeltaEqualityCheck(expectedNumber, delta);
    }
    
    public IContract satisfy(IContract contract) {
        return new NotContract(contract);
    }
}
