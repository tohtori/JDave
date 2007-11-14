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
package jdave.wicket;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jdave.IContainment;
import jdave.Specification;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.BaseWicketTester;
import org.apache.wicket.util.tester.ITestPageSource;
import org.apache.wicket.util.tester.TestPanelSource;
import org.apache.wicket.util.tester.BaseWicketTester.DummyWebApplication;

/**
 * A base class for Wicket's <code>Component</code> specifications.
 *
 * @author Joni Freeman
 * @author Timo Rantalaiho
 */
public abstract class ComponentSpecification<T extends Component> extends Specification<T> {
    protected BaseWicketTester wicket;
    private T specifiedComponent;
    
    @Override
    public final void create() {
        wicket = newWicketTester();
        onCreate();
    }

    /**
     * Called after create(). No need to call super.onCreate().
     */
    protected void onCreate() {
    }

    /**
     * Start component for context.
     */
    public T startComponent() {
        return startComponent(null);
    }

    /**
     * Start component for context.
     * @param model The model passed to component that is used for context.
     */
    public T startComponent(final IModel model) {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> type = null;
        if (superclass.getActualTypeArguments()[0] instanceof Class<?>) {
            type = (Class<?>) superclass.getActualTypeArguments()[0];            
        } else {
            type = (Class<?>) ((ParameterizedType) superclass.getActualTypeArguments()[0]).getRawType();
        }
        if (Page.class.isAssignableFrom(type)) {
            startPage(model);
        } else if (Panel.class.isAssignableFrom(type)) {
            startPanel(model);
        } else if (Border.class.isAssignableFrom(type)) {
            startBorder(model);
        } else {
            startComponentWithoutMarkup(model);
        }
        return specifiedComponent;
    }

    /**
     * Start component for context.
     * <p>
     * The markup file of a component is not needed.
     * 
     * @param model The model passed to component that is used for context.
     */
    public void startComponentWithoutMarkup(final IModel model) {
        specifiedComponent = newComponent("component",model);
        wicket.startComponent(specifiedComponent);
    }

    private void startBorder(final IModel model) {
        wicket.startPanel(new TestPanelSource() {
            public Panel getTestPanel(String panelId) {
                Panel panel = new Container(panelId);
                specifiedComponent = newComponent("component", model);
                panel.add(specifiedComponent);
                return panel;
            }
        });
    }

    private void startPanel(final IModel model) {
        wicket.startPanel(new TestPanelSource() {
            public Panel getTestPanel(String panelId) {
                specifiedComponent = newComponent(panelId, model);
                return (Panel) specifiedComponent;
            }
        });
    }

    private void startPage(final IModel model) {
        specifiedComponent = newComponent(null, model);
        TestPageSource testPageSource = new TestPageSource((Page) specifiedComponent);
        wicket.startPage(testPageSource);
    }

    private static class TestPageSource implements ITestPageSource {
        private Page page; 
        
        public TestPageSource(Page page) {
            this.page = page;
        }
        
        public Page getTestPage() {
            return page;
        }              
    }
    
    /**
     * Specify that given container contains given model objects.
     * <p>
     * This is most often used with <code>RefreshingViews</code> and <code>ListViews</code>.
     * <pre><blockquote><code>
     *
     * ListView list = new ListView("stooges", Arrays.asList("Larry", "Moe", "Curly")) { ... };
     * specify(list, containsInOrder("Larry", "Moe", "Curly");
     *
     * <code></blockquote></pre>
     * 
     * @param actual the container of Wicket components
     * @param containment any containment, see: http://www.jdave.org/documentation.html#containments
     */
    public void specify(MarkupContainer actual, IContainment containment) {
        super.specify(modelObjects(actual.iterator()), containment);
    }

    /**
     * Select an item from a <code>RepeatingView</code>.
     */
    public Item itemAt(RepeatingView view, int index) {
        Iterator<?> items = view.iterator();
        for (int i = 0; i < index; i++) {
            items.next();
        }
        return (Item) items.next();
    }

    /**
     * Select an item from a <code>ListView</code>.
     */
    public ListItem itemAt(ListView view, int index) {
        Iterator<?> items = view.iterator();
        for (int i = 0; i < index; i++) {
            items.next();
        }
        return (ListItem) items.next();
    }

    /**
     * Collect model objects from given components.
     */
    public List<?> modelObjects(Iterator<?> components) {
        @SuppressWarnings("unchecked")
        Iterator<? extends Component> unsafe = (Iterator<? extends Component>) components;
        List<Object> objects = new ArrayList<Object>();
        while (unsafe.hasNext()) {
            objects.add(unsafe.next().getModelObject());
        }
        return objects;
    }

    /**
     * Create a <code>WicketTester</code> for the specification.
     * <p>
     * By default, <code>WicketTester</code> is created as:
     * <pre><blockquote><code>
     *
     * return new BaseWicketTester(newApplication());
     *
     * </code></blockquote></pre>
     *
     * So, it is possible to overwrite <code>newApplication</code> if you just need
     * a different <code>Application</code> for a specification.
     *
     * @see #newApplication()
     */
    protected BaseWicketTester newWicketTester() {
        return new BaseWicketTester(newApplication());
    }

    /**
     * Create the application for the specification.
     */
    protected WebApplication newApplication() {
        return new DummyWebApplication();
    }

    /**
     * Select first component whose model object matches given Hamcrest matcher:
     * <pre><blockquote><code>
     *
     * Item item = selectFirst(Item.class).from(context).which(is(0));
     *
     * </code></blockquote></pre>
     */
    public <S extends Component> Selection<S> selectFirst(Class<S> type) {
        return new Selection<S>(type);
    }
        
    /**
     * Select all components whose model objects match given Hamcrest matcher:
     * <pre><blockquote><code>
     *
     * List<Label> labels = selectAll(Label.class).from(context).which(is(Person.class));
     *
     * </code></blockquote></pre>
     */
    public <S extends Component> MultiSelection<S> selectAll(Class<S> type) {
        return new MultiSelection<S>(type);
    }

    /**
     * Select first component whose Wicket id is given String:
     * <pre><blockquote><code>
     *
     * Label itemName = selectFirst(Label.class, "name").from(context);
     *
     * </code></blockquote></pre>
     */
    public <S extends Component> Selection<S> selectFirst(Class<S> type, String wicketId) {
        return new Selection<S>(type, wicketId);
    }

    /**
     * Select all components whose ids are given Wicket id:
     * <pre><blockquote><code>
     *
     * List<Label> labels = selectAll(Label.class, "price").from(context);
     *
     * </code></blockquote></pre>
     */
    public <S extends Component> MultiSelection<S> selectAll(Class<S> type, String wicketId) {
        return new MultiSelection<S>(type, wicketId);
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
}
