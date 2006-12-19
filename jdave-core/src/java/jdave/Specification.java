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

import jdave.util.Primitives;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public abstract class Specification<T> {
    protected Specification<T> should = this;
    protected Specification<T> does = this;
    private boolean actualState = true;
    public T be;
    
    public Specification<T> not() {
        actualState = !actualState;
        return this;
    }

    public void specify(boolean expected) {
        if (expected != actualState) {
            throw newException("true", "false");
        }
    }
    
    public void specify(Collection<?> actual, Containment containment) {
        if (actualState) {
            if (!containment.isIn(actual)) {
                throw new ExpectationFailedException("The specified collection " + actual + " does not contain '" + containment + "'");
            }
        } else {
            if (containment.isIn(actual)) {
                throw new ExpectationFailedException("The specified collection " + actual + " contains '" + containment + "'");
            }
        }
    }
    
    public void specify(Object[] actual, Containment containment) {
        specify(Arrays.asList(actual), containment);
    }
    
    public void specify(short[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }
    
    public void specify(int[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }
    
    public void specify(long[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }
    
    public void specify(float[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }
    
    public void specify(double[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }
    
    public void specify(boolean[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }
    
    public void specify(char[] actual, Containment containment) {
        specify(Primitives.asList(actual), containment);
    }

    public void specify(Object actual, Object expected) {
        if (!actual.equals(expected)) {
            throw newException(expected, actual);
        }
    }
    
    public void specify(Block block, Class<? extends Throwable> expected) {
        try {
            block.run();
        } catch (Throwable t) {
            if (t.getClass().equals(expected)) {
                return;
            }
        }
        throw new ExpectationFailedException("The specified block should throw " + expected.getName() + ".");        
    }

    private ExpectationFailedException newException(Object expected, Object actual) {
        return new ExpectationFailedException("Expected: " + expected + ", but was: " + actual);
    }
    
    public Object equal(Object obj) {
        return obj;
    }
    
    public Class<? extends Throwable> raise(Class<? extends Throwable> expected) {
        return expected;
    }

    public Containment contains(Object object) {
        return new ObjectContainment(object);
    }
    
    public Containment contain(Object object) {
        return new ObjectContainment(object);
    }

    public Containment containsAll(Collection<?> elements) {
        return new CollectionContainment(elements);
    }

    public Containment containsAll(Object... elements) {
        return containsAll(Arrays.asList(elements));
    }
}