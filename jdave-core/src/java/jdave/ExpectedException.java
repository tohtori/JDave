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

/**
 * @author Lasse Koskela
 */
public class ExpectedException<T> implements IExpectedException<T> {
    protected final Class<? extends T> expected;

    public ExpectedException(Class<? extends T> expected) {
        this.expected = expected;
    }

    public boolean matches(Throwable t) {
        return matchesType(t.getClass());
    }
    
    public String error(Throwable t) {
        if (!matchesType(t.getClass())) {
            return "The specified block should throw "
                    + expected.getName() + " but " + t.getClass().getName()
                    + " was thrown.";
        }
        throw new IllegalStateException();
    }

    protected boolean matchesType(Class<? extends Throwable> actual) {
        return expected.isAssignableFrom(actual);
    }

    public String nothrow() {
        return "The specified block should throw " + expected.getName() + " but nothing was thrown.";
    }

    public boolean propagateException() {
        return false;
    }
}
