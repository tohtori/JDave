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

/**
 * @author Pekka Enberg
 */
public class InverseSpecification<T> extends ContainmentSupport {
    public T be;

    public InverseSpecification(Specification<T> specification) {
        this.be = specification.be;
    }

    @Override
    protected Containment newAllContainment(Collection<?> elements) {
        return new InverseContainment(super.newAllContainment(elements));
    }

    @Override
    protected Containment newAllContainment(Iterable<?> elements) {
        return new InverseContainment(super.newAllContainment(elements));
    }

    @Override
    protected Containment newAllContainment(Iterator<?> elements) {
        return new InverseContainment(super.newAllContainment(elements));
    }

    @Override
    protected Containment newAnyContainment(Collection<?> elements) {
        return new InverseContainment(super.newAnyContainment(elements));
    }

    @Override
    protected Containment newAnyContainment(Iterable<?> elements) {
        return new InverseContainment(super.newAnyContainment(elements));
    }

    @Override
    protected Containment newAnyContainment(Iterator<?> elements) {
        return new InverseContainment(super.newAnyContainment(elements));
    }

    @Override
    protected Containment newExactContainment(Collection<?> elements) {
        return new InverseContainment(super.newExactContainment(elements));
    }

    @Override
    protected Containment newExactContainment(Iterable<?> elements) {
        return new InverseContainment(super.newExactContainment(elements));
    }

    @Override
    protected Containment newExactContainment(Iterator<?> elements) {
        return new InverseContainment(super.newExactContainment(elements));
    }

    @Override
    protected Containment newInOrderContainment(Collection<?> elements) {
        return new InverseContainment(super.newInOrderContainment(elements));
    }

    @Override
    protected Containment newInOrderContainment(Iterable<?> elements) {
        return new InverseContainment(super.newInOrderContainment(elements));
    }

    @Override
    protected Containment newInOrderContainment(Iterator<?> elements) {
        return new InverseContainment(super.newInOrderContainment(elements));
    }

    @Override
    protected Containment newInPartialOrderContainment(Collection<?> elements) {
        return new InverseContainment(super.newInPartialOrderContainment(elements));
    }

    @Override
    protected Containment newInPartialOrderContainment(Iterable<?> elements) {
        return new InverseContainment(super.newInPartialOrderContainment(elements));
    }

    @Override
    protected Containment newInPartialOrderContainment(Iterator<?> elements) {
        return new InverseContainment(super.newInPartialOrderContainment(elements));
    }

    @Override
    protected Containment newObjectContainment(Object object) {
        return new InverseContainment(super.newObjectContainment(object));
    }

    public <E extends Throwable> InverseExpectedException<E> raise(Class<E> expected) {
        return new InverseExpectedException<E>(new ExpectedException<E>(expected));
    }

    public <E extends Throwable> InverseExpectedException<E> raise(Class<E> expectedType,
            String expectedMessage) {
        return new InverseExpectedException<E>(new ExpectedExceptionWithMessage<E>(expectedType,
                expectedMessage));
    }

    public <E extends Throwable> InverseExpectedException<E> raiseExactly(Class<E> expected) {
        return new InverseExpectedException<E>(new ExactExpectedException<E>(expected));
    }
}
