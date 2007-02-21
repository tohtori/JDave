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
 */
final class VisitingSpecificationMethod extends SpecificationMethod {
    VisitingSpecificationMethod(Method method) {
        super(method);
    }

    @Override
    protected void destroyContext() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object newContext(Specification<?> spec) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Specification<?> newSpecification() throws Exception {
        throw new UnsupportedOperationException();
    }
}