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

import java.util.Comparator;

/**
 * @author Joni Freeman
 */
public abstract class EqualsComparableContract<T> implements Contract {
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
        }
        if (comparator.compare(object, preceding()) <= 0) {
            throw new ExpectationFailedException(object + " should be after " + preceding());
        }
        if (comparator.compare(object, subsequent()) >= 0) {
            throw new ExpectationFailedException(object + " should be before " + subsequent());
        }
        if (equivalentByComparisonButNotByEqual() != null) {
            if (comparator.compare(object, equivalentByComparisonButNotByEqual()) == 0) {
                throw new ExpectationFailedException("compareTo is not consistent with equals, " + 
                        object + ", " + subsequent());
            }
        }
    }
    
    protected abstract T preceding();
    protected abstract T subsequent();
    protected abstract T equivalentByComparisonButNotByEqual();
    
    private static class ComparableComparator<T extends Comparable<Object>> implements Comparator<T> {
        public int compare(T o1, T o2) {
            return o1.compareTo(o2);
        }
    }
}
