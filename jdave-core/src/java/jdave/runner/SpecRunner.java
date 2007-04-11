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
import java.lang.reflect.Modifier;

import jdave.Specification;

/**
 * @author Pekka Enberg
 * @author Joni Freeman
 */
public class SpecRunner {
    public <T extends Specification<?>> void visit(Class<T> specType, ISpecVisitor callback) throws Exception {
        for (Class<?> contextType : specType.getDeclaredClasses()) {
            if (isContextClass(specType, contextType)) {
                Context context = new Context(specType, contextType) {
                    @Override
                    protected Behavior newBehavior(Method method, Class<? extends Specification<?>> specType, Class<?> contextType) {
                        return new VisitingBehavior(method);
                    }
                };
                callback.onContext(context);
                context.run(callback);
            }
        }
    }
    
    public <T extends Specification<?>> void run(Class<T> specType, ISpecVisitor callback) throws Exception {
        for (Class<?> contextType : specType.getDeclaredClasses()) {
            if (isContextClass(specType, contextType)) {
                Context context = new Context(specType, contextType) {
                    @Override
                    protected Behavior newBehavior(Method method, Class<? extends Specification<?>> specType, Class<?> contextType) {
                        return new ExecutingBehavior(method, specType, contextType);
                    }
                };
                callback.onContext(context);
                context.run(callback);
                callback.afterContext(context);
            }
        }
    }

    private boolean isContextClass(Class<?> specType, Class<?> contextType) {
        if (!isConcreteAndPublic(contextType)) {
            return false;
        }
        return isInnerClass(specType, contextType);
    }

    private boolean isConcreteAndPublic(Class<?> contextType) {
        int mod = contextType.getModifiers();
        return (Modifier.isPublic(mod) && !Modifier.isAbstract(mod));
    }

    private boolean isInnerClass(Class<?> specType, Class<?> contextType) {
        try {
            contextType.getDeclaredConstructor(specType);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}