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

/**
 * @author Joni Freeman
 * @author Timo Rantalaiho
 */
public class Selector {
    public <T> T first(MarkupContainer root, Class<T> componentType, Matcher<?> matcher) {
        final List<T> matches = new ArrayList<T>();
        root.visitChildren(componentType, new SelectingComponentsByMatchingModelObject<T>(matcher, matches, IVisitor.STOP_TRAVERSAL));
        return getFirstOrNull(matches);
    }

    public <T> List<T> all(MarkupContainer root, Class<T> componentType, Matcher<?> matcher) {
        final List<T> matches = new ArrayList<T>();
        root.visitChildren(componentType, new SelectingComponentsByMatchingModelObject<T>(matcher, matches, IVisitor.CONTINUE_TRAVERSAL));
        return matches;
    }

    public <T> T first(MarkupContainer root, Class<T> componentType, String wicketId) {
        final List<T> firstMatch = new ArrayList<T>();
        root.visitChildren(componentType, new SelectingComponentsById<T>(wicketId, firstMatch, IVisitor.STOP_TRAVERSAL));
        return getFirstOrNull(firstMatch);
    }

    public <T> List<T> all(MarkupContainer root, Class<T> componentType, final String wicketId) {
        final List<T> matches = new ArrayList<T>();
        root.visitChildren(componentType, new SelectingComponentsById<T>(wicketId, matches, IVisitor.CONTINUE_TRAVERSAL));
        return matches;
    }

    private <T> T getFirstOrNull(List<T> firstMatch) {
        if (firstMatch.isEmpty()) {
            return null;
        }
        return firstMatch.get(0);
    }

    private class SelectingComponentsByMatchingModelObject<T> implements IVisitor {
        private final Matcher<?> matcher;
        private final List<T> matches;
        private final Object actionOnMatch;

        public SelectingComponentsByMatchingModelObject(Matcher<?> matcher, List<T> matches, Object actionOnMatch) {
            this.matcher = matcher;
            this.matches = matches;
            this.actionOnMatch = actionOnMatch;
        }

        @SuppressWarnings("unchecked")
            public Object component(Component component) {
            if (matcher.matches(component.getModelObject())) {
                matches.add((T) component);
                return actionOnMatch;
            }
            return IVisitor.CONTINUE_TRAVERSAL;
        }
    }

    private class SelectingComponentsById<T> implements IVisitor {
        private final String wicketId;
        private final List<T> matches;
        private Object actionOnMatch;

        public SelectingComponentsById(String wicketId, List<T> matches, Object actionOnMatch) {
            this.wicketId = wicketId;
            this.matches = matches;
            this.actionOnMatch = actionOnMatch;
        }

        @SuppressWarnings("unchecked")
            public Object component(Component component) {
            if (wicketId.equals(component.getId())) {
                matches.add((T) component);
                return actionOnMatch;
            }
            return CONTINUE_TRAVERSAL;
        }
    }
}
