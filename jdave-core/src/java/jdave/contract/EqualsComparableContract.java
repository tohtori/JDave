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
package jdave.contract;

import java.util.Comparator;

import jdave.ExpectationFailedException;
import jdave.IContract;

/**
 * A contract which enforces the contract between equals method and Comparable
 * interface (or Comparator interface). See the javadoc of those classes for
 * explanation of the contract. Example usage:
 * 
 * <blockquote><pre><code>
 * Integer obj = 5;
 * specify(obj, should.satisfy(new EqualsComparableContract<Integer>() {
 *     public Integer preceding() {
 *         return 4;
 *     }
 *     public Integer subsequent() {
 *         return 6;
 *     }
 *     public Integer equivalentByComparisonButNotByEqual() {
 *         // There's no Integer which would be non equal with 5 and still be equal
 *         // by comparison (comparaTo() == 0). So return null.
 *         return null; 
 *     }
 * });
 * </code></pre></blockquote>
 * 
 * @author Joni Freeman
 */
public abstract class EqualsComparableContract<T> implements IContract {
    private Comparator<T> comparator;

    public EqualsComparableContract() {        
    }
    
    public EqualsComparableContract(Comparator<T> comparator) {
        this.comparator = comparator;
    }
    
    @SuppressWarnings("unchecked")
    public void isSatisfied(Object obj) throws ExpectationFailedException {
        T object = (T) obj;
        if (comparator == null) {
            comparator = new ComparableComparator();
            try {
                ((Comparable) obj).compareTo(null);
                throw new ExpectationFailedException(object.getClass().getName() + 
                    ".compareTo(null) should throw NullpointerException");
            } catch (NullPointerException e) {
            }
        }
        
        if (comparator.compare(object, object) != 0) {
            throw new ExpectationFailedException("comparison should be 0, if objects are equal");
        }
        
        if (comparator.compare(object, preceding()) <= 0) {
            throw new ExpectationFailedException(object + " should be after " + preceding());
        }
        if (comparator.compare(object, subsequent()) >= 0) {
            throw new ExpectationFailedException(object + " should be before " + subsequent());
        }
        if (equivalentByComparisonButNotByEqual() != null) {
            if (comparator.compare(object, equivalentByComparisonButNotByEqual()) == 0) {
                throw new ExpectationFailedException("comparison is not consistent with equals, " + 
                        object + ", " + subsequent());
            }
        }
    }

    /**
     * Return an instance which should preceed the given instance.
     * @see #isSatisfied(Object)
     */
    protected abstract T preceding();
    
    /**
     * Return an instance which should be after the given instance.
     * @see #isSatisfied(Object)
     */
    protected abstract T subsequent();

    /**
     * Return an instance whose compareTo is 0 with given instance, but whose equal
     * returns false. Return null if no such instance exists.
     * @see #isSatisfied(Object)
     */
    protected abstract T equivalentByComparisonButNotByEqual();
    
    private static class ComparableComparator<T extends Comparable<Object>> implements Comparator<T> {
        public int compare(T o1, T o2) {
            return o1.compareTo(o2);
        }
    }
}
