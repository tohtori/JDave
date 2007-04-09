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

import jdave.containments.AllContainment;
import jdave.containments.AnyContainment;
import jdave.containments.ExactContainment;
import jdave.containments.InOrderContainment;
import jdave.containments.InPartialOrderContainment;
import jdave.containments.ObjectContainment;
import jdave.util.Primitives;

/**
 * @author Joni Freeman
 */
public class ContainmentSupport {
    public IContainment contains(Object object) {
        return newObjectContainment(object);
    }

    public IContainment contain(Object object) {
        return newObjectContainment(object);
    }

    protected IContainment newObjectContainment(Object object) {
        return new ObjectContainment(object);
    }

    public IContainment containAll(byte[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment containsAll(byte[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment containAll(short[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment containsAll(short[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment containAll(int[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment containsAll(int[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment containAll(long[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment containsAll(long[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment containAll(float[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment containsAll(float[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment containAll(double[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment containsAll(double[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment containAll(Collection<?> elements) {
        return newAllContainment(elements);
    }

    protected IContainment newAllContainment(Collection<?> elements) {
        return new AllContainment(elements);
    }

    public IContainment containsAll(Collection<?> elements) {
        return containAll(elements);
    }

    public IContainment containAll(Object... elements) {
        return containsAll(Arrays.asList(elements));
    }

    public IContainment containsAll(Object... elements) {
        return containAll(elements);
    }

    public IContainment containAll(Iterator<?> elements) {
        return newAllContainment(elements);
    }

    protected IContainment newAllContainment(Iterator<?> elements) {
        return new AllContainment(elements);
    }

    public IContainment containsAll(Iterator<?> elements) {
        return containAll(elements);
    }

    public IContainment containAll(Iterable<?> elements) {
        return newAllContainment(elements);
    }

    protected IContainment newAllContainment(Iterable<?> elements) {
        return new AllContainment(elements);
    }

    public IContainment containsAll(Iterable<?> elements) {
        return containAll(elements);
    }

    public IContainment containAny(byte[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment containsAny(byte[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment containAny(short[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment containsAny(short[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment containAny(int[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment containsAny(int[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment containAny(long[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment containsAny(long[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment containAny(float[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment containsAny(float[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment containAny(double[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment containsAny(double[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment containAny(Collection<?> elements) {
        return newAnyContainment(elements);
    }

    protected IContainment newAnyContainment(Collection<?> elements) {
        return new AnyContainment(elements);
    }

    public IContainment containsAny(Collection<?> elements) {
        return containAny(elements);
    }

    public IContainment containAny(Object... elements) {
        return containsAny(Arrays.asList(elements));
    }

    public IContainment containsAny(Object... elements) {
        return containAny(elements);
    }

    public IContainment containAny(Iterator<?> elements) {
        return newAnyContainment(elements);
    }

    protected IContainment newAnyContainment(Iterator<?> elements) {
        return new AnyContainment(elements);
    }

    public IContainment containsAny(Iterator<?> elements) {
        return containAny(elements);
    }

    public IContainment containAny(Iterable<?> elements) {
        return newAnyContainment(elements);
    }

    protected IContainment newAnyContainment(Iterable<?> elements) {
        return new AnyContainment(elements);
    }

    public IContainment containsAny(Iterable<?> elements) {
        return containAny(elements);
    }

    public IContainment containExactly(Collection<?> elements) {
        return newExactContainment(elements);
    }

    protected IContainment newExactContainment(Collection<?> elements) {
        return new ExactContainment(elements);
    }

    public IContainment containExactly(byte[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment containsExactly(byte[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment containExactly(short[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment containsExactly(short[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment containExactly(int[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment containsExactly(int[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment containExactly(long[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment containsExactly(long[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment containExactly(float[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment containsExactly(float[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment containExactly(double[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment containsExactly(double[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment containsExactly(Collection<?> elements) {
        return containExactly(elements);
    }

    public IContainment containExactly(Object... elements) {
        return containsExactly(Arrays.asList(elements));
    }

    public IContainment containsExactly(Object... elements) {
        return containExactly(elements);
    }

    public IContainment containExactly(Iterator<?> elements) {
        return newExactContainment(elements);
    }

    protected IContainment newExactContainment(Iterator<?> elements) {
        return new ExactContainment(elements);
    }

    public IContainment containsExactly(Iterator<?> elements) {
        return containExactly(elements);
    }

    public IContainment containExactly(Iterable<?> elements) {
        return newExactContainment(elements);
    }

    protected IContainment newExactContainment(Iterable<?> elements) {
        return new ExactContainment(elements);
    }

    public IContainment containsExactly(Iterable<?> elements) {
        return containExactly(elements);
    }

    public IContainment containInOrder(Collection<?> elements) {
        return newInOrderContainment(elements);
    }

    protected IContainment newInOrderContainment(Collection<?> elements) {
        return new InOrderContainment(elements);
    }

    public IContainment containsInOrder(Collection<?> elements) {
        return containInOrder(elements);
    }

    public IContainment containInOrder(Object... elements) {
        return newInOrderContainment(Arrays.asList(elements));
    }

    public IContainment containInOrder(byte[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment containsInOrder(byte[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment containInOrder(short[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment containsInOrder(short[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment containInOrder(int[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment containsInOrder(int[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment containInOrder(long[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment containsInOrder(long[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment containInOrder(float[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment containsInOrder(float[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment containInOrder(double[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment containsInOrder(double[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment containsInOrder(Object... elements) {
        return containInOrder(elements);
    }

    public IContainment containInOrder(Iterator<?> elements) {
        return newInOrderContainment(elements);
    }

    protected IContainment newInOrderContainment(Iterator<?> elements) {
        return new InOrderContainment(elements);
    }

    public IContainment containsInOrder(Iterator<?> elements) {
        return containInOrder(elements);
    }

    public IContainment containInOrder(Iterable<?> elements) {
        return newInOrderContainment(elements);
    }

    protected IContainment newInOrderContainment(Iterable<?> elements) {
        return new InOrderContainment(elements);
    }

    public IContainment containsInOrder(Iterable<?> elements) {
        return containInOrder(elements);
    }
    
    public IContainment containInPartialOrder(Collection<?> elements) {
        return newInPartialOrderContainment(elements);
    }

    protected IContainment newInPartialOrderContainment(Collection<?> elements) {
        return new InPartialOrderContainment(elements);
    }

    public IContainment containsInPartialOrder(Collection<?> elements) {
        return containInPartialOrder(elements);
    }

    public IContainment containInPartialOrder(Object... elements) {
        return newInPartialOrderContainment(Arrays.asList(elements));
    }

    public IContainment containInPartialOrder(byte[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment containsInPartialOrder(byte[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment containInPartialOrder(short[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment containsInPartialOrder(short[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment containInPartialOrder(int[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment containsInPartialOrder(int[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment containInPartialOrder(long[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment containsInPartialOrder(long[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment containInPartialOrder(float[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment containsInPartialOrder(float[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment containInPartialOrder(double[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment containsInPartialOrder(double[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment containsInPartialOrder(Object... elements) {
        return containInPartialOrder(elements);
    }

    public IContainment containInPartialOrder(Iterator<?> elements) {
        return newInPartialOrderContainment(elements);
    }

    protected IContainment newInPartialOrderContainment(Iterator<?> elements) {
        return new InPartialOrderContainment(elements);
    }

    public IContainment containsInPartialOrder(Iterator<?> elements) {
        return containInPartialOrder(elements);
    }

    public IContainment containInPartialOrder(Iterable<?> elements) {
        return newInPartialOrderContainment(elements);
    }

    protected IContainment newInPartialOrderContainment(Iterable<?> elements) {
        return new InPartialOrderContainment(elements);
    }

    public IContainment containsInPartialOrder(Iterable<?> elements) {
        return containInPartialOrder(elements);
    }
}
