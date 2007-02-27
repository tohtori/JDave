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
public class ExpectedException<T> {
    private final Class<? extends T> expected;

    public ExpectedException(Class<? extends T> expected) {
        this.expected = expected;
    }

    public Class<? extends T> getType() {
        return expected;
    }

    public boolean matches(Class<? extends Throwable> actual) {
        return expected.isAssignableFrom(actual);
    }
}
