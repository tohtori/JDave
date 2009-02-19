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

import jdave.BaseMatcherContainment;
import jdave.IContainment;

/**
 * @author Pekka Enberg
 */
public class NotContainment<T> extends BaseMatcherContainment<T> {
    private final IContainment<T> containment;

    public NotContainment(final IContainment<T> containment) {
        this.containment = containment;
    }

    public boolean matches(final Collection<T> actual) {
        this.actual = actual;
        return !containment.matches(actual);
    }

    public String error(final Collection<T> actual) {
        return "The specified collection '" + actual + "' contains '" + containment + "'";
    }

}
