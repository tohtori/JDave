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

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.Component.IVisitor;
import org.apache.wicket.MarkupContainer;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * @author Joni Freeman
 * @author Timo Rantalaiho
 */
public class Selector {
    public <T extends Component> T first(MarkupContainer root, Class<T> componentType, final Matcher<?> matcher) {
        return selectFirst(root, componentType, new ComponentsModelMatchesTo<T>(matcher));
    }

    public <T extends Component> List<T> all(MarkupContainer root, Class<T> componentType, final Matcher<?> matcher) {
        return selectAll(root, componentType, new ComponentsModelMatchesTo<T>(matcher));
    }

    public <T extends Component> T first(MarkupContainer root, Class<T> componentType, String wicketId, Matcher<?> modelMatcher) {
        Matcher<T> bothMatcher = combine(modelMatcher, wicketId);
        return selectFirst(root, componentType, bothMatcher);
    }

    public <T extends Component> List<T> all(MarkupContainer root, Class<T> componentType, final String wicketId, Matcher<?> modelMatcher) {
        Matcher<T> bothMatcher = combine(modelMatcher, wicketId);
        return selectAll(root, componentType, bothMatcher);
    }

    private <T extends Component> List<T> selectAll(MarkupContainer root, Class<T> componentType, Matcher<T> componentMatcher) {
        CollectingVisitor<T> visitor = new CollectingVisitor<T>(componentMatcher);
        return visitor.selectFrom(root, componentType, IVisitor.CONTINUE_TRAVERSAL);
    }

    private <T extends Component> T selectFirst(MarkupContainer root, Class<T> componentType, Matcher<T> componentMatcher) {
        CollectingVisitor<T> visitor = new CollectingVisitor<T>(componentMatcher);
        List<T> firstMatch = visitor.selectFrom(root, componentType, IVisitor.STOP_TRAVERSAL);
        if (firstMatch.isEmpty()) {
            return null;
        }
        return firstMatch.get(0);
    }

    @SuppressWarnings("unchecked")
    private <T extends Component> Matcher<T> combine(Matcher<?> modelMatcher, String wicketId) {
        return Matchers.allOf(new ComponentsModelMatchesTo<T>(modelMatcher), new WicketIdEqualsTo<T>(wicketId));
    }

    /**
     * Not thread safe.
     */
    private class CollectingVisitor<T extends Component> implements IVisitor<T> {
        private final Matcher<T> componentMatcher;
        private List<T> matches = new ArrayList<T>();
        private Object actionOnMatch;

        public CollectingVisitor(Matcher<T> componentMatcher) {
            this.componentMatcher = componentMatcher;
        }

        @SuppressWarnings({"unchecked"})
        public Object component(Component component) {
            if (componentMatcher.matches(component)) {
                matches.add((T) component);
                return actionOnMatch;
            }
            return CONTINUE_TRAVERSAL;
        }

        public List<T> selectFrom(MarkupContainer root, Class<T> componentType, Object actionOnMatch) {
            this.actionOnMatch = actionOnMatch;
            root.visitChildren(componentType, this);
            return matches;
        }
    }
}
