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

import jdave.Specification;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public abstract class Context {
    private final Class<? extends Specification<?>> specType;
    private final Class<?> contextType;

    public Context(Class<? extends Specification<?>> specType, Class<?> contextType) {
        this.specType = specType;
        this.contextType = contextType;
    }

    public String getName() {
        return contextType.getSimpleName();
    }

    protected abstract Behavior newBehavior(Method method, Class<? extends Specification<?>> specType, Class<?> contextType);

    void run(ISpecVisitor callback) {
        for (Method method : contextType.getMethods()) {
            if (isBehavior(method)) {
                callback.onBehavior(newBehavior(method, specType, contextType));
            }
        }
    }

    private boolean isBehavior(Method method) {
        return new DefaultSpecIntrospection().isBehavior(method);
    }

    public boolean isContextClass() {
        return new DefaultSpecIntrospection().isContextClass(specType, contextType);
    }
}
