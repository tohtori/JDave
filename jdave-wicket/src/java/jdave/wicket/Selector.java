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
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * @author Joni Freeman
 * @author Timo Rantalaiho
 */
public class Selector {
    public <T extends Component> T first(MarkupContainer root, Class<T> componentType, final Matcher<?> matcher) {
        return selectFirst(root, componentType, new ComponentsModelMatchesTo<T>(matcher));
    }

    public <T extends Component> List<T> all(MarkupContainer root, Class<T> componentType, final Matcher<?> matcher) {
        return selectAll(root, componentType, new ComponentsModelMatchesTo(matcher));
    }

    public <T extends Component> T first(MarkupContainer root, Class<T> componentType, String wicketId) {
        return selectFirst(root, componentType, new WicketIdEqualsTo(wicketId));
    }

    public <T extends Component> List<T> all(MarkupContainer root, Class<T> componentType, final String wicketId) {
        return selectAll(root, componentType, new WicketIdEqualsTo(wicketId));
    }

    private <T> List<T> selectAll(MarkupContainer root, Class<T> componentType, Matcher componentMatcher) {
        SelectingVisitor<T> visitor = new SelectingVisitor<T>(componentMatcher);
        return visitor.selectFrom(root, componentType, IVisitor.CONTINUE_TRAVERSAL);
    }

    private <T> T selectFirst(MarkupContainer root, Class<T> componentType, Matcher componentMatcher) {
        SelectingVisitor<T> visitor = new SelectingVisitor<T>(componentMatcher);
        List<T> firstMatch = visitor.selectFrom(root, componentType, IVisitor.STOP_TRAVERSAL);
        if (firstMatch.isEmpty()) {
            return null;
        }
        return firstMatch.get(0);
    }

    /**
     * Not thread safe.
     */
    private class SelectingVisitor<T> implements IVisitor {
        private final Matcher componentMatcher;
        private List<T> matches = new ArrayList<T>();
        private Object actionOnMatch;

        public SelectingVisitor(Matcher componentMatcher) {
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

    private static class ComponentsModelMatchesTo<T extends Component> extends BaseMatcher<T> {
        private final Matcher<?> matcher;

        public ComponentsModelMatchesTo(Matcher<?> matcher) {
            this.matcher = matcher;
        }

        @SuppressWarnings({"unchecked"})
        public final boolean matches(Object o) {
            T component = (T) o;
            return matches(component);
        }

        public boolean matches(T component) {
            return (matcher.matches(component.getModelObject()));
        }

        public void describeTo(Description description) {
            matcher.describeTo(description);
        }
    }

    private static class WicketIdEqualsTo<T extends Component> extends BaseMatcher<T> {
        private final String wicketId;

        public WicketIdEqualsTo(String wicketId) {
            this.wicketId = wicketId;
        }

        @SuppressWarnings({"unchecked"})
        public final boolean matches(Object o) {
            T component = (T) o;
            return matches(component);
        }

        public boolean matches(T component) {
            return (wicketId.equals(component.getId()));
        }

        public void describeTo(Description description) {
            description.appendText(wicketId);
        }
    }
}
