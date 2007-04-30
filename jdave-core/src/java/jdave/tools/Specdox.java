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
package jdave.tools;

import jdave.Specification;
import jdave.runner.Behavior;
import jdave.runner.Context;
import jdave.runner.ISpecVisitor;
import jdave.runner.SpecRunner;

/**
 * @author Joni Freeman
 */
public class Specdox {
    private final IDoxStore fileStore;

    public Specdox(IDoxStore fileStore) {
        this.fileStore = fileStore;
    }

    public void generate(Class<? extends Specification<?>> specType, final IDoxFormat format) {
        new SpecRunner().visit(specType, new ISpecVisitor() {
            public void afterContext(Context context) {
            }
            
            public void onContext(Context context) {
                format.newContext(context.getName());
            }

            public void onBehavior(Behavior behavior) {
                format.newBehavior(behavior.getName());
            }
        });
        fileStore.store(specType.getSimpleName(), format.suffix(), format.toString());
    }
}
