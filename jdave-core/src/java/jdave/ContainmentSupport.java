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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import jdave.util.Primitives;

/**
 * @author Joni Freeman
 */
public class ContainmentSupport {
    public Containment contains(Object object) {
        return newObjectContainment(object);
    }

    public Containment contain(Object object) {
        return newObjectContainment(object);
    }

    protected Containment newObjectContainment(Object object) {
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
        return newAllContainment(elements);
    }

    protected Containment newAllContainment(Collection<?> elements) {
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
        return newAllContainment(elements);
    }

    protected Containment newAllContainment(Iterator<?> elements) {
        return new AllContainment(elements);
    }

    public Containment containsAll(Iterator<?> elements) {
        return containAll(elements);
    }

    public Containment containAll(Iterable<?> elements) {
        return newAllContainment(elements);
    }

    protected Containment newAllContainment(Iterable<?> elements) {
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
        return newAnyContainment(elements);
    }

    protected Containment newAnyContainment(Collection<?> elements) {
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
        return newAnyContainment(elements);
    }

    protected Containment newAnyContainment(Iterator<?> elements) {
        return new AnyContainment(elements);
    }

    public Containment containsAny(Iterator<?> elements) {
        return containAny(elements);
    }

    public Containment containAny(Iterable<?> elements) {
        return newAnyContainment(elements);
    }

    protected Containment newAnyContainment(Iterable<?> elements) {
        return new AnyContainment(elements);
    }

    public Containment containsAny(Iterable<?> elements) {
        return containAny(elements);
    }

    public Containment containExactly(Collection<?> elements) {
        return newExactContainment(elements);
    }

    protected Containment newExactContainment(Collection<?> elements) {
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
        return newExactContainment(elements);
    }

    protected Containment newExactContainment(Iterator<?> elements) {
        return new ExactContainment(elements);
    }

    public Containment containsExactly(Iterator<?> elements) {
        return containExactly(elements);
    }

    public Containment containExactly(Iterable<?> elements) {
        return newExactContainment(elements);
    }

    protected Containment newExactContainment(Iterable<?> elements) {
        return new ExactContainment(elements);
    }

    public Containment containsExactly(Iterable<?> elements) {
        return containExactly(elements);
    }

    public Containment containInOrder(Collection<?> elements) {
        return newInOrderContainment(elements);
    }

    protected Containment newInOrderContainment(Collection<?> elements) {
        return new InOrderContainment(elements);
    }

    public Containment containsInOrder(Collection<?> elements) {
        return containInOrder(elements);
    }

    public Containment containInOrder(Object... elements) {
        return newInOrderContainment(Arrays.asList(elements));
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
        return newInOrderContainment(elements);
    }

    protected Containment newInOrderContainment(Iterator<?> elements) {
        return new InOrderContainment(elements);
    }

    public Containment containsInOrder(Iterator<?> elements) {
        return containInOrder(elements);
    }

    public Containment containInOrder(Iterable<?> elements) {
        return newInOrderContainment(elements);
    }

    protected Containment newInOrderContainment(Iterable<?> elements) {
        return new InOrderContainment(elements);
    }

    public Containment containsInOrder(Iterable<?> elements) {
        return containInOrder(elements);
    }
    
    public Containment containInPartialOrder(Collection<?> elements) {
        return newInPartialOrderContainment(elements);
    }

    protected Containment newInPartialOrderContainment(Collection<?> elements) {
        return new InPartialOrderContainment(elements);
    }

    public Containment containsInPartialOrder(Collection<?> elements) {
        return containInPartialOrder(elements);
    }

    public Containment containInPartialOrder(Object... elements) {
        return newInPartialOrderContainment(Arrays.asList(elements));
    }

    public Containment containInPartialOrder(byte[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public Containment containsInPartialOrder(byte[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public Containment containInPartialOrder(short[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public Containment containsInPartialOrder(short[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public Containment containInPartialOrder(int[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public Containment containsInPartialOrder(int[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public Containment containInPartialOrder(long[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public Containment containsInPartialOrder(long[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public Containment containInPartialOrder(float[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public Containment containsInPartialOrder(float[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public Containment containInPartialOrder(double[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public Containment containsInPartialOrder(double[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public Containment containsInPartialOrder(Object... elements) {
        return containInPartialOrder(elements);
    }

    public Containment containInPartialOrder(Iterator<?> elements) {
        return newInPartialOrderContainment(elements);
    }

    protected Containment newInPartialOrderContainment(Iterator<?> elements) {
        return new InPartialOrderContainment(elements);
    }

    public Containment containsInPartialOrder(Iterator<?> elements) {
        return containInPartialOrder(elements);
    }

    public Containment containInPartialOrder(Iterable<?> elements) {
        return newInPartialOrderContainment(elements);
    }

    protected Containment newInPartialOrderContainment(Iterable<?> elements) {
        return new InPartialOrderContainment(elements);
    }

    public Containment containsInPartialOrder(Iterable<?> elements) {
        return containInPartialOrder(elements);
    }
}
