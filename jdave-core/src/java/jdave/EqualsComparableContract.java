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

/**
 * @author Joni Freeman
 */
public abstract class EqualsComparableContract<T> implements Contract {
    public void isSatisfied(Object obj) throws ExpectationFailedException {
        @SuppressWarnings("unchecked")
        Comparable<T> comparable = (Comparable<T>) obj;
        if (comparable.compareTo(preceding()) <= 0) {
            throw new ExpectationFailedException(comparable + " should be after " + preceding());
        }
        if (comparable.compareTo(subsequent()) >= 0) {
            throw new ExpectationFailedException(comparable + " should be before " + subsequent());
        }
        if (equivalentByComparisionButNotByEqual() != null) {
            if (comparable.compareTo(equivalentByComparisionButNotByEqual()) == 0) {
                throw new ExpectationFailedException("compareTo is not consistent with equals, " + 
                        comparable + ", " + subsequent());
            }
        }
    }
    
    protected abstract T preceding();
    protected abstract T subsequent();
    protected abstract T equivalentByComparisionButNotByEqual();
}
