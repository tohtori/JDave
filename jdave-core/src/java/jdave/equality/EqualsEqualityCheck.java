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
package jdave.equality;

import jdave.EqualityCheck;

/**
 * @author Joni Freeman
 */
public class EqualsEqualityCheck extends EqualityCheck {
    protected final Object expected;

    public EqualsEqualityCheck(Object expected) {
        this.expected = expected;        
    }
    
    public boolean matches(Object actual) {
        if (expected == null) {
            return actual == null;
        }
        return expected.equals(actual);
    }
    
    public String error(Object actual) {
        return "Expected: " + expected + ", but was: " + actual;
    }
}
