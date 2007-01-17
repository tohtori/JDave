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

import jdave.util.Collections;

/**
 * @author Joni Freeman
 */
abstract class CollectionContainment implements Containment {
    protected final Collection<?> elements;

    public CollectionContainment(Collection<?> elements) {
        this.elements = elements;
    }
    
    public CollectionContainment(Iterator<?> elements) {
        this(Collections.list(elements));
    }
    
    public CollectionContainment(Iterable<?> elements) {
        this(elements.iterator());
    }
    
    @Override
    public String toString() {
        return elements.toString();
    }
}
