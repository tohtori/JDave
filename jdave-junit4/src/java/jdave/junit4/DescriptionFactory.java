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
package jdave.junit4;

import jdave.Specification;
import jdave.runner.Context;
import jdave.runner.SpecRunner;
import jdave.runner.ISpecVisitor;
import jdave.runner.Behavior;

import org.junit.runner.Description;

/**
 * The DescriptionFactory's only purpose is to create the appropriate hierarchy
 * of Description objects for the surrounding JUnit 4 framework. Given a
 * <code>Specification</code> class, the DescriptionFactory generates a
 * three-level hierarchy of Descriptions as follows:
 * <ul>
 * <li>The Specification class itself</li>
 * <li>The Context objects defined within the Specification</li>
 * <li>The <i>specification methods</i> for a given context</li>
 * </ul>
 * 
 * @author Lasse Koskela
 * @author Joni Freeman
 */
public class DescriptionFactory implements ISpecVisitor {
    private final Description description;
    private Description contextDescription;
    private Context context;

    public DescriptionFactory(Description description) {
        this.description = description;
    }

    public static Description create(Class<? extends Specification<?>> spec) {
        Description description = Description.createSuiteDescription(spec.getName());
        DescriptionFactory factory = new DescriptionFactory(description);
        new SpecRunner().visit(spec, factory);
        return description;
    }
    
    public void onContext(Context context) {
        contextDescription = Description.createSuiteDescription(context.getName());
        description.addChild(contextDescription);
    }
    
    public void onBehavior(Behavior behavior) {
        contextDescription.addChild(newDescription(behavior));
    }

    static Description newDescription(Behavior behavior) {
        return Description.createTestDescription(behavior.getMethod().getDeclaringClass(), behavior.getName());
    }
    
    public void afterContext(Context context) {
    }
}
