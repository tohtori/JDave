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
package jdave.containment;

import java.util.Collection;
import java.util.Iterator;
import jdave.IEqualityCheck;
import jdave.equality.EqualsEqualityCheck;

/**
 * @author Joni Freeman
 */
public class InOrderContainment<T> extends CollectionContainment<T> {
    public InOrderContainment(final Collection<T> elements) {
        super(elements);
    }

    public InOrderContainment(final Iterator<T> elements) {
        super(elements);
    }

    public InOrderContainment(final Iterable<T> elements) {
        super(elements);
    }

    @Override
    public boolean nullSafeMatches(final Collection<T> actual) {
        if (elements.size() != actual.size()) {
            return false;
        }
        final Iterator<T> i1 = elements.iterator();
        final Iterator<T> i2 = actual.iterator();
        while (i1.hasNext()) {
            if (!newEqualityCheck(i1.next()).matches(i2.next())) {
                return false;
            }
        }
        return true;
    }

    protected IEqualityCheck newEqualityCheck(final Object expected) {
        return new EqualsEqualityCheck(expected);
    }
}
