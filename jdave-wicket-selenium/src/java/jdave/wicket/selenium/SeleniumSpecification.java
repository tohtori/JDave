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

import jdave.Specification;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.model.IModel;

import com.thoughtworks.selenium.Selenium;

/**
 * @author Janne Hietam&auml;ki
 */
public abstract class SeleniumSpecification<T extends MarkupContainer> extends Specification<T> {

    protected Selenium selenium;
    SeleniumLifecycleListener<T> lifeCycleListener;

    public final static String COMPONENTFACTORY = "SeleniumSpecification_ComponentFactory";

    public SeleniumSpecification() {
        lifeCycleListener = new SeleniumLifecycleListener<T>(this);
        setContextObjectFactory(new SeleniumContextFactory<T>(this));
        addListener(lifeCycleListener);
    }

    IMarkupContainerFactory getComponentFactory() {
        return new IMarkupContainerFactory() {
            @SuppressWarnings("unchecked")
            public MarkupContainer getMarkupContainer() {
                try {
                    SeleniumContextFactory<T> seleniumContextFactory = (SeleniumContextFactory<T>) getContextObjectFactory();
                    context = seleniumContextFactory.createNewContextObject();
                    return context;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    /**
     * Create a new instance of a Wicket component to be specified.
     * <p>
     * The component must get given id. If the component is a <code>Page</code>,
     * the id is null.
     *
     * @param id The id of a component, null if the component is a <code>Page</code>,
     * @param model A model for the component which was passed in <code>startComponent</code> method.
     * @see #startComponent(IModel)
     */
    protected abstract T newComponent(String id, IModel model);

    public T startComponent(final IModel model) {
        return newComponent(SeleniumTestWebPage.COMPONENT_ID, model);
    }

    protected T startComponent() {
        return startComponent(null);
    }
}
