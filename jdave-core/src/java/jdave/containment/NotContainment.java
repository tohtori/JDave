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
package jdave.containment;

import java.util.Collection;
import jdave.IContainment;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * @author Pekka Enberg
 */
public class NotContainment extends BaseMatcher<Collection<?>> implements IContainment {
    private final IContainment containment;
    private Collection<?> actual;

    public NotContainment(final IContainment containment) {
        this.containment = containment;
    }

    public boolean matches(final Collection<?> actual) {
        this.actual = actual;
        return !containment.matches(actual);
    }

    public String error(final Collection<?> actual) {
        return "The specified collection '" + actual + "' contains '" + containment + "'";
    }

    public boolean matches(final Object item) {
        if (item instanceof Collection<?>) {
            return matches((Collection<?>) item);
        }
        return false;
    }

    public void describeTo(final Description description) {
        description.appendText(error(actual));
    }
}
