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

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

/**
 * @author Joni Freeman
 * @author Timo Rantalaiho
 */
@RunWith(JDaveRunner.class)
public class SelectorSpec extends Specification<Matcher<?>> {
    private Selector selector = new Selector();
    private WebMarkupContainer root;
    private Component child1, child2, child3;
    
    public class WithMatchingMatcher {
        public Matcher<String> create() {
            return Matchers.is("foo");
        }
        
        public void returnsFirstMatchingItemWhenFirstItemAsked() {
            specify(selector.first(root, WebMarkupContainer.class, context), does.equal(child1));
        }
        
        public void returnsAllMatchingItemsWhenAllItemsAsked() {
            specify(selector.all(root, WebMarkupContainer.class, context), containsExactly(child1, child3));
        }
    }
    
    public class WithNonMatchingMatcher {
        public Matcher<String> create() {
            return Matchers.is("non-matching");
        }
        
        public void returnsNullWhenFirstItemAsked() {
            specify(selector.first(root, WebMarkupContainer.class, context), does.equal(null));
        }
        
        public void returnsEmptyCollectionWhenAllItemsAsked() {
            specify(selector.all(root, WebMarkupContainer.class, context), containsExactly());
        }
    }

    public class SelectingById {
        public void returnsFirstElementWithGivenId() {
            specify(selector.first(root, WebMarkupContainer.class, "child2"), does.equal(child2));
        }

        public void returnsAllElementsWithGivenId() {
            specify(selector.all(root, WebMarkupContainer.class, "child2"), containsExactly(child2));
        }
    }
    
    @Override
    public void create() {
        new WicketTester();
        root = new WebMarkupContainer("root");
        child1 = new WebMarkupContainer("child1").setModel(new Model("foo"));
        child2 = new WebMarkupContainer("child2").setModel(new Model("bar"));
        child3 = new WebMarkupContainer("child3").setModel(new Model("foo"));
        root.add(child1);
        root.add(child2);
        ((WebMarkupContainer) child2).add(child3);
    }
}
