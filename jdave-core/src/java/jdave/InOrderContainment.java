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

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Joni Freeman
 */
public class InOrderContainment extends CollectionContainment {
    public InOrderContainment(Collection<?> elements) {
        super(elements);
    }
    
    public InOrderContainment(Iterator<?> elements) {
        super(elements);
    }
    
    public InOrderContainment(Iterable<?> elements) {
        super(elements);
    }

    public boolean matches(Collection<?> actual) {
        if (elements.size() != actual.size()) {
            return false;
        }
        Iterator<?> i1 = elements.iterator();
        Iterator<?> i2 = actual.iterator();
        while (i1.hasNext()) {
            if (!newEqualityCheck().isEqual(i1.next(), i2.next())) {
                return false;
            }
        }
        return true;
    }
    
    protected EqualityCheck newEqualityCheck() {
        return new EqualsEqualityCheck();
    }
}
