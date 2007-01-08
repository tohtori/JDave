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

import jdave.Specification;

/**
 * @author Pekka Enberg
 * @author Joni Freeman
 */
public class SpecRunner {
    public interface Callback {
        void onContext(Context context);
        void onSpecMethod(Specification<?> specification, SpecificationMethod method);
    }

    public <T extends Specification<?>> void run(Class<T> specType, Callback callback) {
        for (Class<?> contextType : specType.getDeclaredClasses()) {
            if (isContextClass(specType, contextType)) {
                Context context = new Context(specType, contextType);
                callback.onContext(context);
                context.run(callback);
            }
        }
    }

    private boolean isContextClass(Class<?> specType, Class<?> contextType) {
        try {
            contextType.getDeclaredConstructor(specType);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}