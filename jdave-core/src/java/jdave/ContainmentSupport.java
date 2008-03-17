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

import jdave.containment.AllContainment;
import jdave.containment.AnyContainment;
import jdave.containment.ExactContainment;
import jdave.containment.InOrderContainment;
import jdave.containment.InPartialOrderContainment;
import jdave.containment.ObjectContainment;
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

    public <T> IContainment containAll(Collection<T> elements) {
        return newAllContainment(elements);
    }

    protected <T> IContainment newAllContainment(Collection<T> elements) {
        return new AllContainment(elements);
    }

    public <T> IContainment containsAll(Collection<T> elements) {
        return containAll(elements);
    }

    public IContainment containAll(Object... elements) {
        return containsAll(Arrays.asList(elements));
    }

    public IContainment containsAll(Object... elements) {
        return containAll(elements);
    }

    public <T> IContainment containAll(Iterator<T> elements) {
        return newAllContainment(elements);
    }

    protected <T> IContainment newAllContainment(Iterator<T> elements) {
        return new AllContainment(elements);
    }

    public <T> IContainment containsAll(Iterator<T> elements) {
        return containAll(elements);
    }

    public <T> IContainment containAll(Iterable<T> elements) {
        return newAllContainment(elements);
    }

    protected <T> IContainment newAllContainment(Iterable<T> elements) {
        return new AllContainment(elements);
    }

    public <T> IContainment containsAll(Iterable<T> elements) {
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

    public <T> IContainment containAny(Collection<T> elements) {
        return newAnyContainment(elements);
    }

    protected <T> IContainment newAnyContainment(Collection<T> elements) {
        return new AnyContainment(elements);
    }

    public <T> IContainment containsAny(Collection<T> elements) {
        return containAny(elements);
    }

    public IContainment containAny(Object... elements) {
        return containsAny(Arrays.asList(elements));
    }

    public IContainment containsAny(Object... elements) {
        return containAny(elements);
    }

    public <T> IContainment containAny(Iterator<T> elements) {
        return newAnyContainment(elements);
    }

    protected <T> IContainment newAnyContainment(Iterator<T> elements) {
        return new AnyContainment(elements);
    }

    public <T> IContainment containsAny(Iterator<T> elements) {
        return containAny(elements);
    }

    public <T> IContainment containAny(Iterable<T> elements) {
        return newAnyContainment(elements);
    }

    protected <T> IContainment newAnyContainment(Iterable<T> elements) {
        return new AnyContainment(elements);
    }

    public <T> IContainment containsAny(Iterable<T> elements) {
        return containAny(elements);
    }

    public <T> IContainment containExactly(Collection<T> elements) {
        return newExactContainment(elements);
    }

    protected <T> IContainment newExactContainment(Collection<T> elements) {
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

    public <T> IContainment containsExactly(Collection<T> elements) {
        return containExactly(elements);
    }

    public IContainment containExactly(Object... elements) {
        return containsExactly(Arrays.asList(elements));
    }

    public IContainment containsExactly(Object... elements) {
        return containExactly(elements);
    }

    public <T> IContainment containExactly(Iterator<T> elements) {
        return newExactContainment(elements);
    }

    protected <T> IContainment newExactContainment(Iterator<T> elements) {
        return new ExactContainment(elements);
    }

    public <T> IContainment containsExactly(Iterator<T> elements) {
        return containExactly(elements);
    }

    public <T> IContainment containExactly(Iterable<T> elements) {
        return newExactContainment(elements);
    }

    protected <T> IContainment newExactContainment(Iterable<T> elements) {
        return new ExactContainment(elements);
    }

    public <T> IContainment containsExactly(Iterable<T> elements) {
        return containExactly(elements);
    }

    public <T> IContainment containInOrder(Collection<T> elements) {
        return newInOrderContainment(elements);
    }

    protected <T> IContainment newInOrderContainment(Collection<T> elements) {
        return new InOrderContainment(elements);
    }

    public <T> IContainment containsInOrder(Collection<T> elements) {
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

    public <T> IContainment containInOrder(Iterator<T> elements) {
        return newInOrderContainment(elements);
    }

    protected <T> IContainment newInOrderContainment(Iterator<T> elements) {
        return new InOrderContainment(elements);
    }

    public <T> IContainment containsInOrder(Iterator<T> elements) {
        return containInOrder(elements);
    }

    public <T> IContainment containInOrder(Iterable<T> elements) {
        return newInOrderContainment(elements);
    }

    protected <T> IContainment newInOrderContainment(Iterable<T> elements) {
        return new InOrderContainment(elements);
    }

    public <T> IContainment containsInOrder(Iterable<T> elements) {
        return containInOrder(elements);
    }
    
    public <T> IContainment containInPartialOrder(Collection<T> elements) {
        return newInPartialOrderContainment(elements);
    }

    protected <T> IContainment newInPartialOrderContainment(Collection<T> elements) {
        return new InPartialOrderContainment(elements);
    }

    public <T> IContainment containsInPartialOrder(Collection<T> elements) {
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

    public <T> IContainment containInPartialOrder(Iterator<T> elements) {
        return newInPartialOrderContainment(elements);
    }

    protected <T> IContainment newInPartialOrderContainment(Iterator<T> elements) {
        return new InPartialOrderContainment(elements);
    }

    public <T> IContainment containsInPartialOrder(Iterator<T> elements) {
        return containInPartialOrder(elements);
    }

    public <T> IContainment containInPartialOrder(Iterable<T> elements) {
        return newInPartialOrderContainment(elements);
    }

    protected <T> IContainment newInPartialOrderContainment(Iterable<T> elements) {
        return new InPartialOrderContainment(elements);
    }

    public <T> IContainment containsInPartialOrder(Iterable<T> elements) {
        return containInPartialOrder(elements);
    }
}
