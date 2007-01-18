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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Joni Freeman
 */
public class CloneableContract implements Contract {
    public void isSatisfied(Object obj) throws ExpectationFailedException {
        if (!(obj instanceof Cloneable)) {
            throw new ExpectationFailedException(obj + " does not implement Cloneable");
        }
        
        Method clone = cloneMethod(obj);        
        invoke(obj, clone);
    }

    private void invoke(Object obj, Method clone) {
        try {
            invoke0(obj, clone);
        } catch (CloneNotSupportedException e) {
            throw new ExpectationFailedException("clone not supported in " + obj.getClass(), e);            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Method cloneMethod(Object obj) {
        try {
            return obj.getClass().getMethod("clone");
        } catch (Exception e) {
            throw new ExpectationFailedException("no public clone method in " + obj.getClass());
        }
    }
    
    private void invoke0(Object obj, Method clone) throws CloneNotSupportedException, Exception {
        try {
            clone.invoke(obj);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof CloneNotSupportedException) {
                throw (CloneNotSupportedException) e.getCause();
            }
        }
    }
}
