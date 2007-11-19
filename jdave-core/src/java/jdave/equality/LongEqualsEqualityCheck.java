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
public class LongEqualsEqualityCheck extends EqualityCheck {
    private final long expected;

    public LongEqualsEqualityCheck(long expected) {
        this.expected = expected;
    }

    public boolean matches(Object actual) {
        if (actual instanceof Long) {
            return expected == (Long) actual;
        }
        if (actual instanceof Integer) {
            return expected == (Integer) actual;
        }
        if (actual instanceof Byte) {
            return expected == (Byte) actual;
        }
        return false;
    }

    public String error(Object actual) {
        return "Expected: " + expected + ", but was: " + actual;
    }
}
