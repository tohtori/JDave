/*
 * Copyright 2009 the original author or authors.
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

import java.util.Collection;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * @author Tuomas Karkkainen
 */
public abstract class BaseMatcherContainment<T> extends BaseMatcher<Collection<T>> implements
        IContainment<T> {

    protected Collection<T> actual;

    @SuppressWarnings("unchecked")
    public final boolean matches(final Object item) {
        if (item instanceof Collection<?>) {
            actual = (Collection<T>) item;
            return matches(actual);
        }
        return false;

    }

    public final void describeTo(final Description description) {
        description.appendText(error(actual));
    }

}
