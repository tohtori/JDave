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
package jdave.wicket.selenium;

import jdave.DefaultContextObjectFactory;

import org.apache.wicket.MarkupContainer;

/**
 * @author Janne Hietam&auml;ki
 */
public class SeleniumContextFactory<T extends MarkupContainer> extends DefaultContextObjectFactory<T> {
    Object context;
    SeleniumSpecification<T> specification;

    public SeleniumContextFactory(SeleniumSpecification<T> specification) {
        this.specification = specification;
    }

    @Override
    public T newContextObject(Object context) throws Exception {
        this.context = context;
        return specification.lifeCycleListener.createContext();
    }

    public T createNewContextObject() throws Exception {
        return super.newContextObject(context);
    }
}
