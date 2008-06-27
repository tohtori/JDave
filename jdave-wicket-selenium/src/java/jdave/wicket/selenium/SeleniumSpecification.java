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

import jdave.IContextObjectFactory;
import jdave.Specification;
import jdave.wicket.MultiSelection;
import jdave.wicket.Selection;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.model.IModel;

import com.thoughtworks.selenium.Selenium;

/**
 * @author Janne Hietam&auml;ki
 */
public abstract class SeleniumSpecification<T extends MarkupContainer> extends Specification<T> {
    SeleniumManager<T> lifecycleListener = new SeleniumManager<T>(this);

    protected Selenium selenium;
    public WicketState wicket = new WicketState();

    @Override
    public boolean needsThreadLocalIsolation() {
        return true;
    }

    public SeleniumSpecification() {
        addListener(lifecycleListener);
    }

    @Override
    public IContextObjectFactory<T> getContextObjectFactory() {
        return lifecycleListener;
    }

    @Override
    public final void create() {
        lifecycleListener.start();
    }

    public void onCreate() throws Exception {

    }

    /**
     * Create a new instance of a Wicket component to be specified.
     * <p>
     * The component must get given id. If the component is a <code>Page</code>,
     * the id is null.
     * 
     * @param id
     *            The id of a component, null if the component is a
     *            <code>Page</code>,
     * @param model
     *            A model for the component which was passed in
     *            <code>startComponent</code> method.
     * @see #startComponent(IModel)
     */
    protected abstract T newComponent(String id, IModel model);

    public T startComponent(final IModel model) {
        return newComponent(SeleniumTestWebPage.COMPONENT_ID, model);
    }

    public T startComponent() {
        return startComponent(null);
    }

    /**
     * Select first component whose model object matches given Hamcrest matcher:
     * 
     * <pre><blockquote><code>
     * 
     * Item item = selectFirst(Item.class).which(is(0)).from(context);
     * 
     * </code></blockquote>
     * 
     * </pre>
     */
    public <S extends Component> Selection<S> selectFirst(Class<S> type) {
        return new Selection<S>(type);
    }

    /**
     * Select all components whose model objects match given Hamcrest matcher:
     * 
     * <pre><blockquote><code>
     * 
     * List<Label> labels =
     * selectAll(Label.class).which(is(Person.class)).from(context);
     * 
     * </code></blockquote>
     * 
     * </pre>
     */
    public <S extends Component> MultiSelection<S> selectAll(Class<S> type) {
        return new MultiSelection<S>(type);
    }

    /**
     * Select first component whose Wicket id is given String:
     * 
     * <pre><blockquote><code>
     * 
     * Label itemName = selectFirst(Label.class, "name").from(context);
     * 
     * </code></blockquote>
     * 
     * </pre>
     */
    public <S extends Component> Selection<S> selectFirst(Class<S> type, String wicketId) {
        return new Selection<S>(type, wicketId);
    }

    /**
     * Select all components whose ids are given Wicket id:
     * 
     * <pre><blockquote><code>
     * 
     * List<Label> prices = selectAll(Label.class, "price").from(context);
     * 
     * </code></blockquote>
     * 
     * </pre>
     */
    public <S extends Component> MultiSelection<S> selectAll(Class<S> type, String wicketId) {
        return new MultiSelection<S>(type, wicketId);
    }

}
