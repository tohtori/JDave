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
package jdave.runner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import jdave.Specification;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public abstract class Context {
    private final Class<? extends Specification<?>> specType;
    private final Class<?> contextType;

    public Context(final Class<? extends Specification<?>> specType, final Class<?> contextType) {
        this.specType = specType;
        this.contextType = contextType;
    }

    public String getName() {
        return contextType.getSimpleName();
    }

    protected abstract Behavior newBehavior(Method method,
            Class<? extends Specification<?>> specType, Class<?> contextType);

    void run(final ISpecVisitor callback) {
        for (final Method method : ClassMemberSorter.getMethods(contextType)) {
            if (isBehavior(method)) {
                callback.onBehavior(newBehavior(method, specType, contextType));
            }
        }
    }

    private boolean isBehavior(final Method method) {
        return newIntrospection().isBehavior(method);
    }

    public boolean isContextClass() {
        return newIntrospection().isContextClass(specType, contextType);
    }

    private ISpecIntrospection newIntrospection() {
        try {
            Class<?> clazz = specType;
            do {
                final Collection<Class<?>> types = typesOf(clazz);
                for (final Class<?> type : types) {
                    if (hasStrategy(type)) {
                        return type.getAnnotation(IntrospectionStrategy.class).value()
                                .newInstance();
                    }
                }
            } while ((clazz = clazz.getSuperclass()) != null);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        return new DefaultSpecIntrospection();
    }

    private Collection<Class<?>> typesOf(final Class<?> clazz) {
        final List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(clazz);
        types.addAll(Arrays.asList(clazz.getInterfaces()));
        return types;
    }

    boolean hasStrategy(final Class<?> type) {
        return type.isAnnotationPresent(IntrospectionStrategy.class);
    }
}
