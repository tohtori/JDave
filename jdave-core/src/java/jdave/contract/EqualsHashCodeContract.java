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

import jdave.ExpectationFailedException;
import jdave.IContract;

/**
 * A contract to ensure that equals method is correctly implemented
 * and is consistent with hashCode method.
 * 
 * Example:
 * <blockquote>
 * <pre>
 * public void isConsistentWithEqualsAndHashCode() {
 *     object = new SampleDomainObject(1).setName("John");
 *     specify(object, satisfies(new EqualsHashCodeContract&lt;SampleDomainObject&gt;() {
 *         protected SampleDomainObject equal() {
 *            return new SampleDomainObject(1);
 *         }
 *         protected SampleDomainObject nonEqual() {
 *            return new SampleDomainObject(2);
 *         }
 *         protected SampleDomainObject subType() {
 *            return new SampleDomainObject(1) {};
 *         }
 *      }));
 *  }
 * </pre>
 * </blockquote>
 * 
 * @see java.lang.Object#equals(Object)
 * 
 * @author Joni Freeman
 */
public abstract class EqualsHashCodeContract<T> implements IContract {
    public void isSatisfied(Object obj) throws ExpectationFailedException {
        if (obj.equals(null)) {
            throw new ExpectationFailedException(obj + " equals null");            
        }
        if (!obj.equals(equal())) {
            throw new ExpectationFailedException(obj + " does not equal " + equal());
        }
        if (obj.equals(nonEqual())) {
            throw new ExpectationFailedException(obj + " does equal " + nonEqual());
        }
        if (subType() != null) {
            if (obj.equals(subType())) {
                throw new ExpectationFailedException(obj + " does equal " + subType());
            }            
        }
        int hash1 = obj.hashCode();
        int hash2 = equal().hashCode();
        if (hash1 != hash2) {
            throw new ExpectationFailedException("hashCodes must equal for equal objects, " +
                    hash1 + " != " + hash2);
        }
    }

    /**
     * @return an Object which is equal to the Object whose contract is enforced
     */
    protected abstract T equal();
    
    /**
     * @return an Object which is not equal to the Object whose contract is enforced
     */
    protected abstract T nonEqual();
    
    /**
     * Generally subtypes should not equal with super types. 
     * This check can be skipped by returning null.
     * 
     * @return an Object which is a subtype of the Object whose contract is enforced or null
     */
    protected abstract T subType();
}
