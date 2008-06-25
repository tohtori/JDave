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

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public abstract class Behavior {
    private final Class<?> contextType;
    protected final Method method;

    public Behavior(Class<?> contextType, Method method) {
        this.contextType = contextType;
        this.method = method;
    }

    public String getName() {
        return method.getName();
    }
    
    public Method getMethod() {
        return method;
    }
    
    public Class<?> getContextType() {
        return contextType;
    }
    
    public abstract void run(final IBehaviorResults results);
}
