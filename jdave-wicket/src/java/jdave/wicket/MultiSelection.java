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
package jdave.wicket;

import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.hamcrest.Matcher;

/**
 * @author Joni Freeman
 */
public class MultiSelection<S> {
    private final Class<S> componentType;
    private MarkupContainer root;
    
    MultiSelection(Class<S> componentType) {
        this.componentType = componentType;                
    }

    public MultiSelection<S> from(MarkupContainer root) {
        this.root = root;
        return this;
    }

    public List<S> which(Matcher<?> matcher) {
        Selector selector = new Selector();
        return selector.all(root, componentType, matcher);
    }
}
