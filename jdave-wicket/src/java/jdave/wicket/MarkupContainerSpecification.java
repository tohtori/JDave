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

import jdave.Specification;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.BaseWicketTester;
import org.apache.wicket.util.tester.ITestPageSource;
import org.apache.wicket.util.tester.TestPanelSource;
import org.apache.wicket.util.tester.BaseWicketTester.DummyWebApplication;

/**
 * A base class for Wicket's <code>MarkupContainer</code> specifications.
 *
 * @author Joni Freeman
 */
public abstract class MarkupContainerSpecification<T extends MarkupContainer> extends Specification<T> {
    protected final BaseWicketTester wicket = newWicketTester();
    private T specifiedComponent;

    /**
     * Start container for context.
     */
    public T startContainer() {
        return startContainer(null);
    }

    /**
     * Start container for context.
     * @param model The model passed to container that is used for context.
     */
    public T startContainer(final IModel model) {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> type = (Class<?>) superclass.getActualTypeArguments()[0];
        if (Page.class.isAssignableFrom(type)) {
            startPage(model);
        } else if (Panel.class.isAssignableFrom(type)) {
            startPanel(model);
        } else if (Border.class.isAssignableFrom(type)) {
            startBorder(model);
        } else {
            startComponent(model);
        }
        return specifiedComponent;
    }

    private void startComponent(final IModel model) {
        specifiedComponent = newContainer("component",model);
        wicket.startComponent(specifiedComponent);
    }

    private void startBorder(final IModel model) {
        wicket.startPanel(new TestPanelSource() {
            public Panel getTestPanel(String panelId) {
                Panel panel = new Container(panelId);
                specifiedComponent = newContainer("component", model);
                panel.add(specifiedComponent);
                return panel;
            }
        });
    }

    private void startPanel(final IModel model) {
        wicket.startPanel(new TestPanelSource() {
            public Panel getTestPanel(String panelId) {
                specifiedComponent = newContainer(panelId, model);
                return (Panel) specifiedComponent;
            }
        });
    }

    private void startPage(final IModel model) {
        wicket.startPage(new ITestPageSource() {
            public Page getTestPage() {
                specifiedComponent = newContainer(null, model);
                return (Page) specifiedComponent;
            }
        });
    }

    /**
     * Select an item from a <code>RefreshingView</code>.
     */
    public Item itemAt(RefreshingView view, int index) {
        Iterator<?> items = view.getItems();
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
     * <code></blockquote></pre>
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
     * Create a new instance of a container to be specified.
     * <p>
     * The container must get given id. If the container is a <code>Page</code>,
     * the id is null.
     *
     * @param id The id of a container, null if the container is a <code>Page</code>,
     * @param model A model for the container which was passed in <code>startContainer</code> method.
     * @see #startContainer(IModel)
     */
    protected abstract T newContainer(String id, IModel model);
}
