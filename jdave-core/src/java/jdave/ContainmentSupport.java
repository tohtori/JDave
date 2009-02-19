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
    public <T> IContainment<T> contains(final T object) {
        return newObjectContainment(object);
    }

    public <T> IContainment<T> contain(final T object) {
        return newObjectContainment(object);
    }

    protected <T> IContainment<T> newObjectContainment(final T object) {
        return new ObjectContainment<T>(object);
    }

    public IContainment<?> containAll(final byte[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment<?> containsAll(final byte[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment<?> containAll(final short[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment<?> containsAll(final short[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment<?> containAll(final int[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment<?> containsAll(final int[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment<?> containAll(final long[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment<?> containsAll(final long[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment<?> containAll(final float[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment<?> containsAll(final float[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment<?> containAll(final double[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public IContainment<?> containsAll(final double[] elements) {
        return containAll(Primitives.asList(elements));
    }

    public <T> IContainment<T> containAll(final Collection<T> elements) {
        return newAllContainment(elements);
    }

    protected <T> IContainment<T> newAllContainment(final Collection<T> elements) {
        return new AllContainment<T>(elements);
    }

    public <T> IContainment<T> containsAll(final Collection<T> elements) {
        return containAll(elements);
    }

    public <T> IContainment<T> containAll(final T... elements) {
        return containsAll(Arrays.asList(elements));
    }

    public <T> IContainment<T> containsAll(final T... elements) {
        return containAll(elements);
    }

    public <T> IContainment<T> containAll(final Iterator<T> elements) {
        return newAllContainment(elements);
    }

    protected <T> IContainment<T> newAllContainment(final Iterator<T> elements) {
        return new AllContainment<T>(elements);
    }

    public <T> IContainment<T> containsAll(final Iterator<T> elements) {
        return containAll(elements);
    }

    public <T> IContainment<T> containAll(final Iterable<T> elements) {
        return newAllContainment(elements);
    }

    protected <T> IContainment<T> newAllContainment(final Iterable<T> elements) {
        return new AllContainment<T>(elements);
    }

    public <T> IContainment<T> containsAll(final Iterable<T> elements) {
        return containAll(elements);
    }

    public IContainment<?> containAny(final byte[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment<?> containsAny(final byte[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment<?> containAny(final short[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment<?> containsAny(final short[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment<?> containAny(final int[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment<?> containsAny(final int[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment<?> containAny(final long[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment<?> containsAny(final long[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment<?> containAny(final float[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment<?> containsAny(final float[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment<?> containAny(final double[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public IContainment<?> containsAny(final double[] elements) {
        return containAny(Primitives.asList(elements));
    }

    public <T> IContainment<T> containAny(final Collection<T> elements) {
        return newAnyContainment(elements);
    }

    protected <T> IContainment<T> newAnyContainment(final Collection<T> elements) {
        return new AnyContainment<T>(elements);
    }

    public <T> IContainment<T> containsAny(final Collection<T> elements) {
        return containAny(elements);
    }

    public <T> IContainment<T> containAny(final T... elements) {
        return containsAny(Arrays.asList(elements));
    }

    public <T> IContainment<T> containsAny(final T... elements) {
        return containAny(elements);
    }

    public <T> IContainment<T> containAny(final Iterator<T> elements) {
        return newAnyContainment(elements);
    }

    protected <T> IContainment<T> newAnyContainment(final Iterator<T> elements) {
        return new AnyContainment<T>(elements);
    }

    public <T> IContainment<T> containsAny(final Iterator<T> elements) {
        return containAny(elements);
    }

    public <T> IContainment<T> containAny(final Iterable<T> elements) {
        return newAnyContainment(elements);
    }

    protected <T> IContainment<T> newAnyContainment(final Iterable<T> elements) {
        return new AnyContainment<T>(elements);
    }

    public <T> IContainment<T> containsAny(final Iterable<T> elements) {
        return containAny(elements);
    }

    public <T> IContainment<T> containExactly(final Collection<T> elements) {
        return newExactContainment(elements);
    }

    protected <T> IContainment<T> newExactContainment(final Collection<T> elements) {
        return new ExactContainment<T>(elements);
    }

    public IContainment<?> containExactly(final byte[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment<?> containsExactly(final byte[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment<?> containExactly(final short[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment<?> containsExactly(final short[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment<?> containExactly(final int[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment<?> containsExactly(final int[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment<?> containExactly(final long[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment<?> containsExactly(final long[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment<?> containExactly(final float[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment<?> containsExactly(final float[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment<?> containExactly(final double[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public IContainment<?> containsExactly(final double[] elements) {
        return containsExactly(Primitives.asList(elements));
    }

    public <T> IContainment<T> containsExactly(final Collection<T> elements) {
        return containExactly(elements);
    }

    public <T> IContainment<T> containExactly(final T... elements) {
        return containsExactly(Arrays.asList(elements));
    }

    public <T> IContainment<T> containsExactly(final T... elements) {
        return containExactly(elements);
    }

    public <T> IContainment<T> containExactly(final Iterator<T> elements) {
        return newExactContainment(elements);
    }

    protected <T> IContainment<T> newExactContainment(final Iterator<T> elements) {
        return new ExactContainment<T>(elements);
    }

    public <T> IContainment<T> containsExactly(final Iterator<T> elements) {
        return containExactly(elements);
    }

    public <T> IContainment<T> containExactly(final Iterable<T> elements) {
        return newExactContainment(elements);
    }

    protected <T> IContainment<T> newExactContainment(final Iterable<T> elements) {
        return new ExactContainment<T>(elements);
    }

    public <T> IContainment<T> containsExactly(final Iterable<T> elements) {
        return containExactly(elements);
    }

    public <T> IContainment<T> containInOrder(final Collection<T> elements) {
        return newInOrderContainment(elements);
    }

    protected <T> IContainment<T> newInOrderContainment(final Collection<T> elements) {
        return new InOrderContainment<T>(elements);
    }

    public <T> IContainment<T> containsInOrder(final Collection<T> elements) {
        return containInOrder(elements);
    }

    public <T> IContainment<T> containInOrder(final T... elements) {
        return newInOrderContainment(Arrays.asList(elements));
    }

    public IContainment<?> containInOrder(final byte[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment<?> containsInOrder(final byte[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment<?> containInOrder(final short[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment<?> containsInOrder(final short[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment<?> containInOrder(final int[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment<?> containsInOrder(final int[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment<?> containInOrder(final long[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment<?> containsInOrder(final long[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment<?> containInOrder(final float[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment<?> containsInOrder(final float[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment<?> containInOrder(final double[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public IContainment<?> containsInOrder(final double[] object) {
        return containInOrder(Primitives.asList(object));
    }

    public <T> IContainment<T> containsInOrder(final T... elements) {
        return containInOrder(elements);
    }

    public <T> IContainment<T> containInOrder(final Iterator<T> elements) {
        return newInOrderContainment(elements);
    }

    protected <T> IContainment<T> newInOrderContainment(final Iterator<T> elements) {
        return new InOrderContainment<T>(elements);
    }

    public <T> IContainment<T> containsInOrder(final Iterator<T> elements) {
        return containInOrder(elements);
    }

    public <T> IContainment<T> containInOrder(final Iterable<T> elements) {
        return newInOrderContainment(elements);
    }

    protected <T> IContainment<T> newInOrderContainment(final Iterable<T> elements) {
        return new InOrderContainment<T>(elements);
    }

    public <T> IContainment<T> containsInOrder(final Iterable<T> elements) {
        return containInOrder(elements);
    }

    public <T> IContainment<T> containInPartialOrder(final Collection<T> elements) {
        return newInPartialOrderContainment(elements);
    }

    protected <T> IContainment<T> newInPartialOrderContainment(final Collection<T> elements) {
        return new InPartialOrderContainment<T>(elements);
    }

    public <T> IContainment<T> containsInPartialOrder(final Collection<T> elements) {
        return containInPartialOrder(elements);
    }

    public <T> IContainment<T> containInPartialOrder(final T... elements) {
        return newInPartialOrderContainment(Arrays.asList(elements));
    }

    public IContainment<?> containInPartialOrder(final byte[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment<?> containsInPartialOrder(final byte[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment<?> containInPartialOrder(final short[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment<?> containsInPartialOrder(final short[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment<?> containInPartialOrder(final int[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment<?> containsInPartialOrder(final int[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment<?> containInPartialOrder(final long[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment<?> containsInPartialOrder(final long[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment<?> containInPartialOrder(final float[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment<?> containsInPartialOrder(final float[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment<?> containInPartialOrder(final double[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public IContainment<?> containsInPartialOrder(final double[] object) {
        return containInPartialOrder(Primitives.asList(object));
    }

    public <T> IContainment<T> containsInPartialOrder(final T... elements) {
        return containInPartialOrder(elements);
    }

    public <T> IContainment<T> containInPartialOrder(final Iterator<T> elements) {
        return newInPartialOrderContainment(elements);
    }

    protected <T> IContainment<T> newInPartialOrderContainment(final Iterator<T> elements) {
        return new InPartialOrderContainment<T>(elements);
    }

    public <T> IContainment<T> containsInPartialOrder(final Iterator<T> elements) {
        return containInPartialOrder(elements);
    }

    public <T> IContainment<T> containInPartialOrder(final Iterable<T> elements) {
        return newInPartialOrderContainment(elements);
    }

    protected <T> IContainment<T> newInPartialOrderContainment(final Iterable<T> elements) {
        return new InPartialOrderContainment<T>(elements);
    }

    public <T> IContainment<T> containsInPartialOrder(final Iterable<T> elements) {
        return containInPartialOrder(elements);
    }
}
