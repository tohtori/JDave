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

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import org.apache.wicket.MarkupContainer;
import org.hamcrest.Matcher;

/**
 * @author Joni Freeman
 * @author Mikko Peltonen
 */
public class Selection<S> {
    private final Class<S> componentType;
    private Matcher<?> matcher;
    
    Selection(Class<S> componentType) {
        this.componentType = componentType;
        this.matcher = is(anything());
    }

    public S from(MarkupContainer root) {
        return newSelector().first(root, componentType, matcher);
    }

    public Selection<S> which(Matcher<?> matcher) {
        this.matcher = matcher;
        return this;
    }
    
    protected Selector newSelector() {
        return new Selector();
    }
}
