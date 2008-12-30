/*
 * Copyright 2008 the original author or authors.
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
package jdave.junit4;

import java.lang.reflect.Modifier;
import jdave.Specification;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.notification.RunNotifier;

public class JDaveContextRunner extends Runner implements Filterable {

    private final JDaveRunner delegate;

    @SuppressWarnings("unchecked")
    public JDaveContextRunner(final Class<?> contextClass) {
        if (thisIsAContext(contextClass)) {
            final Class<? extends Specification<?>> spec = (Class<? extends Specification<?>>) contextClass
                    .getDeclaringClass();
            delegate = new JDaveRunner(spec);
        } else {
            throw new IllegalArgumentException(
                    "you must run only JDave Contexts with JDaveContextRunner");
        }

    }

    private boolean thisIsAContext(final Class<?> contextClass) {
        final boolean isMemberClass = contextClass.isMemberClass();
        if (isMemberClass == false) {
            return false;
        }
        final boolean isStatic = Modifier.isStatic(contextClass.getModifiers());
        if (isStatic) {
            return false;
        }
        final Class<?> declaringClass = contextClass.getDeclaringClass();
        final boolean declaringClassIsASpecification = Specification.class
                .isAssignableFrom(declaringClass);
        return declaringClassIsASpecification;

    }

    @Override
    public Description getDescription() {
        return delegate.getDescription();
    }

    @Override
    public void run(final RunNotifier runNotifier) {
        delegate.run(runNotifier);
    }

    public void filter(final Filter filter) {
        delegate.filter(filter);
    }

}
